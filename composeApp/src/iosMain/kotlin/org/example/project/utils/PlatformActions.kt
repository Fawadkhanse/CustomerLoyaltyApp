import platform.Foundation.NSURL
import platform.UIKit.UIApplication



actual fun makePhoneCall(context: Any?, phoneNumber: String) {
    val url = NSURL(string = "tel:$phoneNumber") ?: return
    UIApplication.sharedApplication.openURL(url)
}

actual fun openMapsForDirections(
    context: Any?,
    latitude: Double,
    longitude: Double,
    address: String
) {
    val url = NSURL(
        string = "http://maps.apple.com/?daddr=$latitude,$longitude"
    ) ?: return

    UIApplication.sharedApplication.openURL(url)
}

