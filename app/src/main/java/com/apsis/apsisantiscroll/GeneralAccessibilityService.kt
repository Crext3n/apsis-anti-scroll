package com.apsis.apsisantiscroll

import android.accessibilityservice.AccessibilityService
import android.os.Handler
import android.os.Looper
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo

class GeneralAccessibilityService : AccessibilityService() {

    private val handler = Handler(Looper.getMainLooper())
    private val checkInterval = 500L
    private var lastBackPressTime = 0L
    private val backPressDebounce = 1000L

    private val youtubePackages = listOf(
        "com.google.android.youtube",       // Normal YouTube
        "com.google.android.youtube.tv",    // Android TV
        "com.samsung.android.app.youtube",  // Samsung ROM varyantı
        "com.miui.youtube"                  // Xiaomi/Redmi varyantı
    )
    private fun isYouTubePackage(pkg: String): Boolean {
        return youtubePackages.any { realPkg ->
            pkg.startsWith(realPkg, ignoreCase = true)
        }
    }

    override fun onServiceConnected() {
        super.onServiceConnected()
        startContinuousCheck()
    }

    private fun startContinuousCheck() {
        handler.post(object : Runnable {
            override fun run() {
                val rootNode = rootInActiveWindow
                if (rootNode != null) {
                    val packageName = rootNode.packageName?.toString() ?: ""

                    when {
                        packageName == "com.instagram.android" -> handleInstagram(rootNode)
                        packageName == "com.zhiliaoapp.musically" -> handleTikTok(rootNode)
                        isYouTubePackage(packageName) -> handleYouTubeShorts(rootNode)
                    }
                }
                handler.postDelayed(this, checkInterval)
            }
        })
    }

    private fun handleInstagram(rootNode: AccessibilityNodeInfo) {
        val eskiSurum = getSharedPreferences("settings", MODE_PRIVATE)
            .getBoolean("instagramEski", false)

        if (eskiSurum) {
            val commentIconNodes = rootNode.findAccessibilityNodeInfosByViewId("com.instagram.android:id/comment_button")
            if (commentIconNodes.isNotEmpty()) pressBack()
        } else {
            val commentIconNodes = rootNode.findAccessibilityNodeInfosByViewId("com.instagram.android:id/direct_share_button")
            if (commentIconNodes.isNotEmpty()) pressBack()
        }
    }

    private fun handleTikTok(rootNode: AccessibilityNodeInfo) {
        val exceptionNodes = rootNode.findAccessibilityNodeInfosByViewId("com.zhiliaoapp.musically:id/j3a")
        val exceptionByIdProfile = rootNode.findAccessibilityNodeInfosByViewId("com.zhiliaoapp.musically:id/b3g")
        if (!exceptionNodes.isNullOrEmpty() || !exceptionByIdProfile.isNullOrEmpty()) return

        val inboxNodes = rootNode.findAccessibilityNodeInfosByViewId("com.zhiliaoapp.musically:id/lxh")
        if (!inboxNodes.isNullOrEmpty()) {
            inboxNodes.first().performAction(AccessibilityNodeInfo.ACTION_CLICK)
            return
        }

        val inboxByDesc = rootNode.findAccessibilityNodeInfosByText("Inbox")
        if (!inboxByDesc.isNullOrEmpty()) {
            inboxByDesc.first().performAction(AccessibilityNodeInfo.ACTION_CLICK)
            return
        }

        val queue = ArrayDeque<AccessibilityNodeInfo>()
        queue.add(rootNode)
        while (queue.isNotEmpty()) {
            val node = queue.removeFirst()
            if (node.contentDescription?.contains("Inbox", ignoreCase = true) == true && node.isClickable) {
                node.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                return
            }
            for (i in 0 until node.childCount) node.getChild(i)?.let { queue.add(it) }
        }
    }

    private fun handleYouTubeShorts(rootNode: AccessibilityNodeInfo) {
        val possibleShortsIds = listOf(
            // Google YouTube
            "com.google.android.youtube:id/reel_progress_bar",
            "com.google.android.youtube:id/reel_watch_container",

            // Samsung YouTube
            "com.samsung.android.app.youtube:id/shorts_container",
            "com.samsung.android.app.youtube:id/reel_watch_container",
            "com.samsung.android.app.youtube:id/reel_pager",

            // Xiaomi YouTube
            "com.miui.youtube:id/reel_progress_bar",
            "com.miui.youtube:id/reel_watch_container"
        )

        for (id in possibleShortsIds) {
            try {
                val nodes = rootNode.findAccessibilityNodeInfosByViewId(id)
                if (!nodes.isNullOrEmpty()) {
                    pressBack()
                    return
                }
            } catch (_: Exception) { }
        }
    }

    private fun pressBack() {
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastBackPressTime >= backPressDebounce) {
            performGlobalAction(GLOBAL_ACTION_BACK)
            lastBackPressTime = currentTime
        }
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {}

    override fun onInterrupt() {}
}
