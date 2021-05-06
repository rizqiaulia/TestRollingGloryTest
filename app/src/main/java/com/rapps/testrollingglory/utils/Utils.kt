package com.rapps.testrollingglory.utils

import android.content.Context
import android.os.Build
import android.text.Html
import androidx.appcompat.app.AlertDialog
import kotlin.math.ln
import kotlin.math.pow

fun htmlFormat(text:String):String{
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(text, Html.FROM_HTML_MODE_COMPACT).toString()
    } else {
        Html.fromHtml(text).toString()
    }
}

fun dialogConfirmationWithTitle(
    ctx: Context?,
    title: String?,
    message: String?
) {
    val builder =
        AlertDialog.Builder(ctx!!)
    builder.setTitle(title)
    builder.setMessage(message)
    builder.setNegativeButton("OK") { dialogInterface, i -> dialogInterface.dismiss() }
    builder.show()
}