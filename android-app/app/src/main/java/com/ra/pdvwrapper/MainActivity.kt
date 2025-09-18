package com.ra.pdvwrapper

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity() {

    private lateinit var webView: WebView

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        webView = findViewById(R.id.webview)

        val s = webView.settings
        s.javaScriptEnabled = true
        s.domStorageEnabled = true
        s.mixedContentMode = WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE

        // REGISTRAR A PONTE ANTES do loadUrl:
        webView.addJavascriptInterface(PosBridge(this), "POSPrinter")

        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                view.postDelayed({
                    view.evaluateJavascript(
                        "(function(){return typeof window.POSPrinter==='object'})()"
                    ) { res ->
                        Toast.makeText(this@MainActivity, "POSPrinter: $res", Toast.LENGTH_SHORT).show()
                    }
                }, 300)
            }
        }

        webView.loadUrl(Constants.PDV_URL)
    }
}
