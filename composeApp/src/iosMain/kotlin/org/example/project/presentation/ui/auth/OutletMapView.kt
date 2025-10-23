package org.example.project.presentation.ui.auth

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.UIKitView
import org.example.project.presentation.ui.outlets.OutletLocation
import platform.CoreLocation.CLLocationCoordinate2DMake
import platform.MapKit.MKCoordinateRegionMake
import platform.MapKit.MKCoordinateSpanMake
import platform.MapKit.MKMapView
import platform.MapKit.MKPointAnnotation


@Composable
actual fun OutletMapView(
    outlets: List<OutletLocation>,
    selectedOutlet: OutletLocation?,
    onOutletMarkerClick: (OutletLocation) -> Unit,
    onMapClick: () -> Unit,

) {
    Box(modifier = Modifier.fillMaxSize()) {
        UIKitView(
            factory = {
                val mapView = MKMapView()
                mapView.setShowsUserLocation(false)

                // Calculate center
                val centerLat = outlets.map { it.latitude }.average()
                val centerLng = outlets.map { it.longitude }.average()

                val center = CLLocationCoordinate2DMake(centerLat, centerLng)
                val span = MKCoordinateSpanMake(0.1, 0.1)
                val region = MKCoordinateRegionMake(center, span)
                mapView.setRegion(region, animated = false)

                // Add annotations for outlets
                outlets.forEach { outlet ->
                    val annotation = MKPointAnnotation()
                    annotation.setCoordinate(
                        CLLocationCoordinate2DMake(outlet.latitude, outlet.longitude)
                    )
                    annotation.setTitle(outlet.name)
                    annotation.setSubtitle(outlet.address)
                    mapView.addAnnotation(annotation)
                }

                mapView
            },
            update = { mapView ->
                // Update selected annotation if needed
                selectedOutlet?.let { selected ->
                    val center = CLLocationCoordinate2DMake(selected.latitude, selected.longitude)
                    val span = MKCoordinateSpanMake(0.05, 0.05)
                    val region = MKCoordinateRegionMake(center, span)
                    mapView.setRegion(region, animated = true)
                }
            }
        )
    }
}

