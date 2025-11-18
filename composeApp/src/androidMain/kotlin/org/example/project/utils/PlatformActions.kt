package org.example.project.utils

import android.content.Context
import android.content.Intent
import android.net.Uri

actual fun makePhoneCall(context: Any?, phoneNumber: String) {
    val ctx = context as Context

    val intent = Intent(Intent.ACTION_DIAL).apply {
        data = Uri.parse("tel:${phoneNumber.trim()}")
    }
    ctx.startActivity(intent)
}

actual fun openMapsForDirections(context: Any?, latitude: Double, longitude: Double, address: String) {
    val ctx = context as Context

    val gmmIntentUri = Uri.parse("google.navigation:q=$latitude,$longitude")
    val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)

    ctx.startActivity(mapIntent)
}

