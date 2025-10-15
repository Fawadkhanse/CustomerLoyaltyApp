package org.example.project.presentation.ui.auth


import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import org.example.project.presentation.ui.outlets.OutletLocation

@Composable
actual fun OutletMapView(
    outlets: List<OutletLocation>,
    selectedOutlet: OutletLocation?,
    onOutletMarkerClick: (OutletLocation) -> Unit,
    onMapClick: () -> Unit,
) {
    val context = LocalContext.current

    // Calculate center and bounds from outlets
    val centerPosition = remember(outlets) {
        if (outlets.isNotEmpty()) {
            val avgLat = outlets.map { it.latitude }.average()
            val avgLng = outlets.map { it.longitude }.average()
            LatLng(avgLat, avgLng)
        } else {
            LatLng(33.6844, 73.0479) // Default: Rawalpindi
        }
    }

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(centerPosition, 12f)
    }

    // Update camera when selected outlet changes
    LaunchedEffect(selectedOutlet) {
        selectedOutlet?.let {
            cameraPositionState.animate(
                CameraUpdateFactory.newLatLngZoom(
                    LatLng(it.latitude, it.longitude),
                    15f
                )
            )
        }
    }

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
        properties = MapProperties(
            isMyLocationEnabled = false,
            mapType = MapType.NORMAL
        ),
        uiSettings = MapUiSettings(
            zoomControlsEnabled = true,
            myLocationButtonEnabled = false,
            compassEnabled = true
        ),
        onMapClick = { onMapClick() }
    ) {
        // Add markers for each outlet
        outlets.forEach { outlet ->
            val position = LatLng(outlet.latitude, outlet.longitude)
            val isSelected = selectedOutlet?.id == outlet.id

            Marker(
                state = rememberMarkerState(position = position),
                title = outlet.name,
                snippet = outlet.address,
                onClick = {
                    onOutletMarkerClick(outlet)
                    true
                },
                icon = if (isSelected) {
                    BitmapDescriptorFactory.defaultMarker(
                        BitmapDescriptorFactory.HUE_ORANGE
                    )
                } else {
                    BitmapDescriptorFactory.defaultMarker(
                        BitmapDescriptorFactory.HUE_YELLOW
                    )
                }
            )
        }
    }
}


