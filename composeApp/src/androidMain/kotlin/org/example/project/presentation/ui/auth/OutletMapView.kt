package org.example.project.presentation.ui.auth

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import kotlinx.coroutines.delay
import org.example.project.presentation.ui.outlets.OutletLocation

@Composable
actual fun OutletMapView(
    outlets: List<OutletLocation>,
    selectedOutlet: OutletLocation?,
    onOutletMarkerClick: (OutletLocation) -> Unit,
    onMapClick: () -> Unit,
) {
    val cameraPositionState = rememberCameraPositionState()

    // Show all markers by default
    LaunchedEffect(outlets) {
        if (outlets.isNotEmpty()) {
            val boundsBuilder = LatLngBounds.builder()
            outlets.forEach { outlet ->
                boundsBuilder.include(LatLng(outlet.latitude, outlet.longitude))
            }
            val bounds = boundsBuilder.build()
            // Small delay ensures map is ready before moving camera
            delay(300)
            cameraPositionState.animate(CameraUpdateFactory.newLatLngBounds(bounds, 100))
        }
    }

    // When user selects a marker from bottom sheet → move camera there
    LaunchedEffect(selectedOutlet) {
        selectedOutlet?.let {
            val target = LatLng(it.latitude, it.longitude)
            cameraPositionState.animate(
                CameraUpdateFactory.newLatLngZoom(target, 15f)
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
                    BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)
                } else {
                    BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)
                }
            )
        }
    }
}
