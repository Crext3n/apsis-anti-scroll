package com.apsis.apsisantiscroll

import android.annotation.SuppressLint
import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.Button
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var tvStatus: TextView
    private lateinit var textView2: TextView
    private lateinit var btnOpenSettings: Button
    private lateinit var btnDisable: Button
    private lateinit var textView: TextView
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private lateinit var mySwitch: Switch

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvStatus = findViewById(R.id.tvStatus)
        btnOpenSettings = findViewById(R.id.btnOpenSettings)
        btnDisable = findViewById(R.id.btnDisableService)
        mySwitch = findViewById(R.id.switch1)
        textView2 = findViewById(R.id.textView2)
        textView = findViewById(R.id.textView)

        val prefs = getSharedPreferences("settings", MODE_PRIVATE)

        // Daha önce kaydedilen değeri yükle
        mySwitch.isChecked = prefs.getBoolean("instagramEski", false)
        // Switch değişince kaydet
        mySwitch.setOnCheckedChangeListener { _, isChecked ->
            prefs.edit().putBoolean("instagramEski", isChecked).commit()
        }

        // İlk açılışta servis durumunu UI'ya yansıt
        updateAccessibilityUI()

        btnOpenSettings.setOnClickListener {
            startActivity(Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS))
        }
        btnDisable.setOnClickListener {
            startActivity(Intent(this, PinLockActivity::class.java))
        }
        textView2.setOnClickListener {
            val url = "https://www.crext3n.xyz"  // .d
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        updateAccessibilityUI()
    }

    private fun updateAccessibilityUI() {
        val isServiceRunning = isMyServiceRunning(this, GeneralAccessibilityService::class.java)
        btnOpenSettings.visibility = if (isServiceRunning) View.GONE else View.VISIBLE
        mySwitch.visibility = if (!isServiceRunning) View.GONE else View.VISIBLE
        btnDisable.visibility = if (!isServiceRunning) View.GONE else View.VISIBLE
        textView.visibility = if (!isServiceRunning) View.GONE else View.VISIBLE
        tvStatus.text = if (isServiceRunning) "Servis aktif (Tiktok,Shorts,Reels)" else "Uygulamanın çalışması için erişilebilirliği açın"
    }

    private fun isMyServiceRunning(context: Context, serviceClass: Class<*>): Boolean {
        val manager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        return manager.getRunningServices(Int.MAX_VALUE).any { it.service.className == serviceClass.name }
    }
}
