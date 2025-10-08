package org.example.project.presentation.navigation


import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import org.example.project.presentation.navigation.Screen.Screen
import org.example.project.presentation.ui.auth.*
import org.example.project.presentation.ui.home.*
import org.example.project.presentation.ui.profile.*
import org.example.project.presentation.ui.coupons.*
import org.example.project.presentation.ui.qr.*
import org.example.project.presentation.ui.outlets.*
import org.example.project.presentation.ui.transaction.*
import org.example.project.presentation.notfication.*
import org.example.project.presentation.ui.splash.AppSplashScreenRoute

const val UNKNOWN_DESTINATION_ROUTE = "unknown_destination"

private fun NavController.safeNavigate2(route: String, navOptions: NavOptions? = null) {
    try {
        navigate(route, navOptions)
    } catch (e: IllegalArgumentException) {
        // Handle navigation error
    }
}

@Composable
fun MyAppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    updateTopBottomAppBar: (topAppBar: Boolean, title: String, isBottomTabVisible: Boolean) -> Unit,
    startDestination: String = AuthRoutes.AuthFlow.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        // Add all navigation graphs
        addAllGraphs(navController, updateTopBottomAppBar)
    }
}

//private fun NavGraphBuilder.authenticationGraph2(
//    navController: NavHostController,
//    updateTopBottomAppBar: (Boolean, String, Boolean) -> Unit) {
//    navigation(
//        startDestination = Screen.Welcome.route,
//        route = Screen.AuthFlow.route
//    ) {
//        composable(Screen.Welcome.route) {
//            LaunchedEffect(Unit) {
//                updateTopBottomAppBar(false, "", false)
//            }
//            AppSplashScreenRoute(
//                onNavigateToLogin = {
//                    navController.navigate(Screen.Login.route) {
//                        popUpTo(Screen.Welcome.route) { inclusive = true }
//                    }
//                }
//            )
//        }
//
//        composable(Screen.Onboarding.route) {
//            LaunchedEffect(Unit) {
//                updateTopBottomAppBar(false, "", false)
//            }
//            OnboardingScreenRoute(
//                onComplete = {
//                    navController.navigate(Screen.Login.route) {
//                        popUpTo(Screen.Onboarding.route) { inclusive = true }
//                    }
//                },
//                onSkip = {
//                    navController.navigate(Screen.Login.route) {
//                        popUpTo(Screen.Onboarding.route) { inclusive = true }
//                    }
//                }
//            )
//        }
//
//        composable(Screen.Login.route) {
//            LaunchedEffect(Unit) {
//                updateTopBottomAppBar(false, "", false)
//            }
//            LoginScreenRoute(
//                onLogin = { email, password, userType ->
//                    if (userType == "customer") {
//                        navController.navigate(Screen.Home.route) {
//                            popUpTo(Screen.AuthFlow.route) { inclusive = true }
//                        }
//                    } else {
//                        navController.navigate(Screen.Dashboard.route) {
//                            popUpTo(Screen.AuthFlow.route) { inclusive = true }
//                        }
//                    }
//                },
//                onForgotPassword = {
//                    navController.navigate(Screen.ForgotPassword.route)
//                },
//                onRegister = {
//                    navController.navigate(Screen.Register.route)
//                },
//                onMerchantLogin = {
//                    navController.navigate(Screen.Login.route)
//                }
//            )
//        }
//
//        composable(Screen.Register.route) {
//            LaunchedEffect(Unit) {
//                updateTopBottomAppBar(true, "Register", false)
//            }
//            CustomerRegistrationScreenRoute(
//                onRegister = { name, email, phone, password ->
//                    navController.navigate(Screen.Home.route) {
//                        popUpTo(Screen.AuthFlow.route) { inclusive = true }
//                    }
//                },
//                onBack = {
//                    navController.popBackStack()
//                }
//            )
//        }
//
//        composable(Screen.ForgotPassword.route) {
//            LaunchedEffect(Unit) {
//                updateTopBottomAppBar(true, "Forgot Password", false)
//            }
//            ForgotPasswordScreenRoute(
//                onSendResetLink = { response ->
//                    navController.navigate(Screen.ResetPassword.route)
//                },
//                onBack = {
//                    navController.popBackStack()
//                }
//            )
//        }
//
//        composable(Screen.ResetPassword.route) {
//            LaunchedEffect(Unit) {
//                updateTopBottomAppBar(true, "Reset Password", false)
//            }
//            ResetPasswordScreenRoute(
//                navigateToLogin = {
//                    navController.navigate(Screen.Login.route) {
//                        popUpTo(Screen.AuthFlow.route) { inclusive = true }
//                    }
//                },
//                onBack = {
//                    navController.popBackStack()
//                }
//            )
//        }
//    }
//}
//
//private fun NavGraphBuilder.mainAppGraph(
//    navController: NavHostController,
//    updateTopBottomAppBar: (Boolean, String, Boolean) -> Unit
//) {
//    // Customer Home
//    composable(Screen.Home.route) {
//        LaunchedEffect(Unit) {
//            updateTopBottomAppBar(false, "Home", true)
//        }
//        CustomerHomeScreenRoute(
//            onNavigateToProfile = {
//                navController.navigate(Screen.Profile.route)
//            },
//            onNavigateToCouponDetails = { couponId ->
//                navController.navigate(Screen.CouponDetail.createRoute(couponId))
//            },
//            onNavigateToAllCoupons = {
//                navController.navigate(Screen.Coupons.route)
//            }
//        )
//    }
//
//    // Customer QR
//    composable(Screen.MyQR.route) {
//        LaunchedEffect(Unit) {
//            updateTopBottomAppBar(false, "My QR", true)
//        }
//        QRCodeDisplayScreenRoute(
//            onBack = {
//                navController.popBackStack()
//            }
//        )
//    }
//
//    // Coupons
//    composable(Screen.Coupons.route) {
//        LaunchedEffect(Unit) {
//            updateTopBottomAppBar(false, "Coupons", true)
//        }
//        CouponsScreenRoute(
//            onBack = {
//                navController.popBackStack()
//            },
//            onCouponClick = { coupon ->
//                navController.navigate(Screen.CouponDetail.createRoute(coupon.id))
//            }
//        )
//    }
//
//    // Profile
//    composable(Screen.Profile.route) {
//        LaunchedEffect(Unit) {
//            updateTopBottomAppBar(false, "Profile", true)
//        }
//        ProfileScreenRoute(
//            onEditProfile = {
//                navController.navigate(Screen.EditProfile.route)
//            },
//            onChangePassword = {
//                navController.navigate(Screen.ChangePassword.route)
//            },
//            onLogout = {
//                navController.navigate(Screen.AuthFlow.route) {
//                    popUpTo(navController.graph.startDestinationRoute?:"") { inclusive = true }
//                }
//            }
//        )
//    }
//
//    // Merchant Dashboard
//    composable(Screen.Dashboard.route) {
//        LaunchedEffect(Unit) {
//            updateTopBottomAppBar(false, "Dashboard", true)
//        }
//        MerchantDashboardRoute(
//            onNavigateToAllTransactions = {
//                navController.navigate(Screen.Transactions.route)
//            }
//        )
//    }
//
//    // Scan QR (Merchant)
//    composable(Screen.ScanQR.route) {
//        LaunchedEffect(Unit) {
//            updateTopBottomAppBar(false, "Scan QR", true)
//        }
//        QRScannerScreenRoute {
//            navController.popBackStack()
//        }
//    }
//
//    // Outlets (Merchant)
//    composable(Screen.Outlets.route) {
//        LaunchedEffect(Unit) {
//            updateTopBottomAppBar(false, "Outlets", true)
//        }
//        OutletsListScreenRoute(
//            onBack = {
//                navController.popBackStack()
//            },
//            onAddOutlet = {
//                navController.navigate(Screen.AddOutlet.route)
//            },
//            onOutletClick = { outlet ->
//                navController.navigate(Screen.OutletDetail.createRoute(outlet.id))
//            }
//        )
//    }
//
//    // Transactions (Merchant)
//    composable(Screen.Transactions.route) {
//        LaunchedEffect(Unit) {
//            updateTopBottomAppBar(false, "Transactions", true)
//        }
//        TransactionHistoryScreenRoute()
//    }
//
//    // Shared Screens
//    composable(Screen.Notifications.route) {
//        LaunchedEffect(Unit) {
//            updateTopBottomAppBar(true, "Notifications", false)
//        }
//        NotificationsScreenRoute(
//            onBack = {
//                navController.popBackStack()
//            },
//            onNotificationClick = { notification ->
//                // Handle notification click
//            }
//        )
//    }
//
//    composable(Screen.EditProfile.route) {
//        LaunchedEffect(Unit) {
//            updateTopBottomAppBar(true, "Edit Profile", false)
//        }
//        EditProfileScreenRoute(
//            onBack = {
//                navController.popBackStack()
//            }
//        )
//    }
//
//    composable(Screen.ChangePassword.route) {
//        LaunchedEffect(Unit) {
//            updateTopBottomAppBar(true, "Change Password", false)
//        }
//        ChangePasswordScreenRoute()
//    }
//
//    // Coupon Detail with parameter
//    composable(Screen.CouponDetail.route) { backStackEntry ->
//        LaunchedEffect(Unit) {
//            updateTopBottomAppBar(true, "Coupon Details", false)
//        }
//        val couponId = backStackEntry.arguments?.getString("couponId") ?: ""
//        CouponDetailScreenRout(
//            onBack = {
//                navController.popBackStack()
//            },
//            onRedeem = {
//                // Handle redeem
//                navController.popBackStack()
//            }
//        )
//    }
//
//    // Outlet Detail with parameter
//    composable(Screen.OutletDetail.route) { backStackEntry ->
//        LaunchedEffect(Unit) {
//            updateTopBottomAppBar(true, "Outlet Details", false)
//        }
//        val outletId = backStackEntry.arguments?.getString("outletId") ?: ""
//        OutletDetailScreenRoute(
//            onBack = {
//                navController.popBackStack()
//            },
//            onEdit = {
//                // Handle edit outlet
//            }
//        )
//    }
//}