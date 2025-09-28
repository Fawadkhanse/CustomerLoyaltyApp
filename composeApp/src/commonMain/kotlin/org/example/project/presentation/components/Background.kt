package org.example.project.presentation.components

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color


@Composable
fun MyAppBackground(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    //ChangeStatusBarIconColor()
    Surface(
        modifier = modifier
    ) {
        //Add any other composable here
        CompositionLocalProvider {
            content()
        }
    }
}
@Composable
fun ChangeStatusBarIconColor(color: Color = Color.Transparent, darkIcon: Boolean = false){
//    val systemUiController = remmSystemUiController()
//    // Set the status bar color and icons color
//    systemUiController.setStatusBarColor(
//        color = color, // Change this to the desired background color
//        darkIcons = darkIcon      // True for dark icons, False for light icons
//    )
}
