package com.example.susumapapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.JavascriptInterface
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var webView: WebView
    private var isOnBuildingList = false

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        webView = findViewById(R.id.webview)
        webView.settings.javaScriptEnabled = true
        webView.settings.allowFileAccessFromFileURLs = true
        webView.settings.allowUniversalAccessFromFileURLs = true

        WebView.setWebContentsDebuggingEnabled(true)

        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                url: String
            ): Boolean {
                view?.loadUrl(url)
                return false
            }
        }

        webView.webChromeClient = WebChromeClient()
        webView.addJavascriptInterface(WebAppInterface(), "Android")

        webView.loadUrl("file:///android_asset/index.html")
    }

    override fun onBackPressed() {
        if (isOnBuildingList) {
            finish()
        } else if (webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }

    inner class WebAppInterface {
        @JavascriptInterface
        fun updatePage(isBuildingList: Boolean) {
            isOnBuildingList = isBuildingList
        }
    }
}
