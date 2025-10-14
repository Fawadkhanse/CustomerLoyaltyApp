// File: composeApp/src/androidMain/kotlin/org/example/project/presentation/ui/auth/ShareCode.kt
package org.example.project.presentation.ui.auth

// DELETE or COMMENT OUT this duplicate:
// actual fun ByteArray.encodeBase64(): String {
//     return android.util.Base64.encodeToString(this, android.util.Base64.NO_WRAP)
// }

// Android actual
//actual fun ByteArray.encodeBase64(): String =
//    android.util.Base64.encodeToString(this, android.util.Base64.NO_WRAP)