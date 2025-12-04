package org.example.project.utils

// Expected declarations for platform-specific implementations
expect fun makePhoneCall(context: Any?, phoneNumber: String)

expect fun openMapsForDirections(context: Any?, latitude: Double, longitude: Double, address: String)

