package com.apsis.apsisantiscroll

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class PinLockActivity : AppCompatActivity() {

    private lateinit var pinManager: PinManager

    private val messages = arrayOf(
        "Dur!",
        "Emin misin?",
        "Gerçekten ihtiyacın yoksa yapma.",
        "Sen bilirsin.",
        "Şifre sıfırlandı!!"
    )
    private var index = 0  // Reset mesajı

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pin_lock)

        pinManager = PinManager(this)

        val etPin = findViewById<EditText>(R.id.etPin)
        val btnConfirm = findViewById<Button>(R.id.btnConfirm)
        val btnReset = findViewById<Button>(R.id.btnReset)
        val tvHint = findViewById<TextView>(R.id.tvPinHint)
        val intent = Intent(this, MainActivity::class.java)

        btnReset.visibility = if (pinManager.isPin()) View.VISIBLE else View.GONE

        if (!pinManager.isPinSet()) {
            tvHint.text = "4-6 haneli PIN gir ve ONAYLAYA UZUN BASARAK kaydet."
            btnConfirm.setOnLongClickListener {
                val pin = etPin.text.toString().trim()
                if (pin.length in 4..6 && pin.all { it.isDigit() }) {
                    pinManager.setPin(pin)
                    Toast.makeText(this, "PIN kaydedildi.", Toast.LENGTH_SHORT).show()
                    tvHint.text = "PIN oluşturuldu."
                    btnConfirm.text = "Onayla"
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                    finish()
                    true
                } else {
                    Toast.makeText(this, "PIN 4-6 basamaklı olmalı.", Toast.LENGTH_SHORT).show()
                    true
                }
            }

            btnConfirm.setOnClickListener {
                Toast.makeText(this, "PIN oluşturmak için UZUN BASIN.", Toast.LENGTH_SHORT).show()
            }

        } else {
            tvHint.text = "PIN'i girerek servisi devre dışı bırak."
            btnConfirm.setOnClickListener {
                val pin = etPin.text.toString().trim()
                if (pinManager.verifyPin(pin)) {
                    Toast.makeText(this, "Doğru PIN, ayarlara yönlendiriliyorsunuz...", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS))
                    finish()
                } else {
                    Toast.makeText(this, "Yanlış PIN.", Toast.LENGTH_SHORT).show()
                }
            }
            btnReset.setOnClickListener {
                Toast.makeText(this, messages[index], Toast.LENGTH_SHORT).show()

                index++  // Sonraki mesaj

                if (index == messages.size) {
                    pinManager.clearPin()  // PIN'i sıfırla
                    btnReset.visibility = View.GONE  // Butonu gizle
                    Toast.makeText(this, "PIN sıfırlandı!", Toast.LENGTH_SHORT).show()
                    finish()  // Activity kapanır
                    return@setOnClickListener
                }
            }
        }
    }
}
