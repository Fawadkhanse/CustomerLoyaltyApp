package org.example.project.utils

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast

actual fun makePhoneCall(context: Any?, phoneNumber: String) {
    val ctx = context as? Context ?: return

    try {
        // Clean the phone number (remove all non-numeric except +)
        val cleanNumber = phoneNumber.trim().replace(Regex("[^0-9+]"), "")

        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:$cleanNumber")
        }

        // Check if there's an app that can handle this intent
        if (intent.resolveActivity(ctx.packageManager) != null) {
            ctx.startActivity(intent)
        } else {
            Toast.makeText(ctx, "No phone app available", Toast.LENGTH_SHORT).show()
        }
    } catch (e: Exception) {
        Toast.makeText(ctx, "Unable to make phone call", Toast.LENGTH_SHORT).show()
    }
}

actual fun openMapsForDirections(context: Any?, latitude: Double, longitude: Double, address: String) {
    val ctx = context as? Context ?: return

    try {
        // Try Google Maps first (navigation mode)
        val gmmIntentUri = Uri.parse("google.navigation:q=$latitude,$longitude")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri).apply {
            setPackage("com.google.android.apps.maps")
        }

        // Check if Google Maps is installed
        if (mapIntent.resolveActivity(ctx.packageManager) != null) {
            ctx.startActivity(mapIntent)
        } else {
            // Fallback to generic map intent (will work with any map app)
            val fallbackUri = Uri.parse("geo:$latitude,$longitude?q=$latitude,$longitude")
            val fallbackIntent = Intent(Intent.ACTION_VIEW, fallbackUri)

            if (fallbackIntent.resolveActivity(ctx.packageManager) != null) {
                ctx.startActivity(fallbackIntent)
            } else {
                Toast.makeText(ctx, "No maps app available", Toast.LENGTH_SHORT).show()
            }
        }
    } catch (e: Exception) {
        Toast.makeText(ctx, "Unable to open maps", Toast.LENGTH_SHORT).show()
    }
}

