package com.ra.pdvwrapper

import android.content.Context
import android.content.Intent
import android.webkit.JavascriptInterface
import android.widget.Toast
import androidx.annotation.Keep

@Keep
class PosBridge(private val ctx: Context) {

    @JavascriptInterface
    fun echo(msg: String): String = "OK: $msg"

    @JavascriptInterface
    fun printCashOpening(json: String) {
        val ticketText = buildString {
            appendLine("Caixa RA Vidraçaria")
            appendLine("--------------------------------")
            appendLine("Abertura de Caixa")
            appendLine("Dados: $json")
            appendLine("--------------------------------")
        }
        printText(ticketText)
    }

    @JavascriptInterface
    fun printText(text: String) {
        try {
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, text)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            ctx.startActivity(Intent.createChooser(intent, "Imprimir via..."))
            Toast.makeText(ctx, "A) Share texto iniciado", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(ctx, "Falha no share: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }

    @JavascriptInterface
    fun openCashDrawer() {
        Toast.makeText(ctx, "openCashDrawer() (stub)", Toast.LENGTH_SHORT).show()
    }
}
