package org.example.project.utils

import platform.Foundation.NSURL
import platform.UIKit.UIApplication

actual fun makePhoneCall(context: Any?, phoneNumber: String) {
    // Clean the phone number (remove spaces, dashes, etc.)
    val cleanNumber = phoneNumber.replace(Regex("[^0-9+]"), "")
    val url = NSURL(string = "tel:$cleanNumber") ?: return

    // Check if the device can make phone calls
    if (UIApplication.sharedApplication.canOpenURL(url)) {
        UIApplication.sharedApplication.openURL(url)
    }
}

actual fun openMapsForDirections(
    context: Any?,
    latitude: Double,
    longitude: Double,
    address: String
) {
    // Apple Maps URL scheme for directions
    val url = NSURL(
        string = "http://maps.apple.com/?daddr=$latitude,$longitude"
    ) ?: return

    if (UIApplication.sharedApplication.canOpenURL(url)) {
        UIApplication.sharedApplication.openURL(url)
    }
}