package org.example.project


import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn


@Composable
fun rememberMyAppState(
    navController: NavHostController = rememberNavController(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
): MyAppState {
    return remember(navController, coroutineScope) {
        MyAppState(navController, coroutineScope)
    }
}

@Stable
class MyAppState(
    val navController: NavHostController,
    coroutineScope: CoroutineScope,
) {
    val currentDestination: StateFlow<NavDestination?> =
        navController.currentBackStackEntryFlow
            .map { it.destination }
            .stateIn(coroutineScope, SharingStarted.WhileSubscribed(), null)

//    val appBackground: StateFlow<Color?> =
//        currentDestination.map {
//            when (it?.route) {
//                SignUpRoutes.AccountCreated.route -> Color.White
//                else -> null
//            }
//        }.onEach { delay(350) }
//            .stateIn(coroutineScope, SharingStarted.WhileSubscribed(), null)
//
//    val shouldShowBottomBar: StateFlow<Boolean> =
//        currentDestination.map {
//            when (it?.route) {
//                Screen.Home.route,
//                Screen.Profile.route,
//                Screen.Dashboard.route,
//                Screen.Coupons.route -> true
//                else -> false
//            }
//        }.stateIn(coroutineScope, SharingStarted.WhileSubscribed(), false)

    fun navigateBack() {
        navController.popBackStack()
    }

    fun navigateTo(route: String) {
        navController.navigate(route) { launchSingleTop = true }
    }

    fun navigateAndClear(route: String) {
        navController.navigate(route) {
            popUpTo(navController.graph.startDestinationRoute?:"") { inclusive = true }
            launchSingleTop = true
        }
    }
}
