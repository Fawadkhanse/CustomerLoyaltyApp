package org.example.project.presentation.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import androidx.navigation.NavType
import org.example.project.domain.models.auth.resetpassword.ForgotPasswordResponse
import org.example.project.presentation.navigation.AuthNavigationUtils.navigateToMainAppAndClearAuth
import org.example.project.presentation.ui.auth.*
import org.example.project.presentation.ui.home.*
import org.example.project.presentation.ui.profile.*
import org.example.project.presentation.ui.coupons.*
import org.example.project.presentation.ui.qr.*
import org.example.project.presentation.ui.outlets.*
import org.example.project.presentation.ui.transaction.*
import org.example.project.presentation.notfication.*
import org.example.project.presentation.ui.orders.OrdersScreen
import org.example.project.presentation.ui.splash.AppSplashScreenRoute
import org.example.project.presentation.ui.web.WebViewScreenRoute

/**
 * Authentication navigation graph
 */
fun NavGraphBuilder.authenticationGraph(
    navController: NavHostController,
    updateTopBottomAppBar: (Boolean, String, Boolean) -> Unit
) {
    navigation(
        startDestination = AuthRoutes.Welcome.route,
        route = AuthRoutes.AuthFlow.route
    ) {
        // Splash/Welcome Screen
        composable(AuthRoutes.Welcome.route) {
            LaunchedEffect(Unit) {
                updateTopBottomAppBar(false, "", false)
            }
            AppSplashScreenRoute(
                onNavigateToLogin = {
                    navController.navigate(AuthRoutes.Login.route) {
                        popUpTo(AuthRoutes.Welcome.route) { inclusive = true }
                    }
                },
                onNavigateToHome = { userRole ->
                    if (userRole.lowercase() == "customer") {
                        navController.navigateToMainAppAndClearAuth(isCustomer = true)
                    } else {
                        navController.navigateToMainAppAndClearAuth(isCustomer = false)
                    }
                },
                onNavigateToOnboarding = {
                    navController.navigate(AuthRoutes.Onboarding.route)
                }
            )
        }

        // Onboarding
        composable(AuthRoutes.Onboarding.route) {
            LaunchedEffect(Unit) {
                updateTopBottomAppBar(false, "", false)
            }
            OnboardingScreenRoute(
                onComplete = {
                    navController.navigate(AuthRoutes.Login.route) {
                        popUpTo(AuthRoutes.Onboarding.route) { inclusive = true }
                    }
                },
                onSkip = {
                    navController.navigate(AuthRoutes.Login.route) {
                        popUpTo(AuthRoutes.Onboarding.route) { inclusive = true }
                    }
                }
            )
        }

        // Login
        composable(AuthRoutes.Login.route) {
            LaunchedEffect(Unit) {
                updateTopBottomAppBar(false, "", false)
            }
            LoginScreenRoute(
                onLogin = { email, password, userType ->
                    if (userType.lowercase() == "customer") {
                        navController.navigateToMainAppAndClearAuth(isCustomer = true)
                    } else {
                        navController.navigateToMainAppAndClearAuth(isCustomer = false)
                    }
                },
                onForgotPassword = {
                    navController.navigate(AuthRoutes.ForgotPassword.route)
                },
                onRegister = {
                    navController.navigate(AuthRoutes.Register.route)
                },
                onMerchantLogin = {
                    // Could navigate to different login or same
                    navController.navigate(AuthRoutes.Login.route)
                }
            )
        }

        // Register
        composable(AuthRoutes.Register.route) {
            LaunchedEffect(Unit) {
                updateTopBottomAppBar(true, "Create Account", false)
            }
            CustomerRegistrationScreenRoute(
                onRegister = { name, email, phone, password ->
                    navController.navigateToMainAppAndClearAuth(isCustomer = true)
                },
                onBack = {
                    navController.popBackStack()
                }
                ,
                onTermsAndConditionClicked = {
                    navController.navigate(SettingsRoutes.WebView.route)
                }
            )
        }

        // Forgot Password
        composable(AuthRoutes.ForgotPassword.route) {
            LaunchedEffect(Unit) {
                updateTopBottomAppBar(true, "Forgot Password", false)
            }
            ForgotPasswordScreenRoute(
                onSendResetLink = { response ->
                    navController.navigateWithJson(
                        baseRoute =AuthRoutes.ResetPassword.createRoute(), data =response

                    )
                },
                onBack = {
                    navController.popBackStack()
                }
            )
        }


        // Reset Password
        composable(
            route = AuthRoutes.ResetPassword.route,
            arguments = listOf(navArgument("responseJson") { type = NavType.StringType })
        ) { backStackEntry ->
            val responseJson = backStackEntry.arguments?.getString("responseJson")
            val response = responseJson.decodeJson<ForgotPasswordResponse>()

            ResetPasswordScreenRoute(
                response = response,
                navigateToLogin = {
                    navController.navigate(AuthRoutes.Login.route) {
                        popUpTo(AuthRoutes.AuthFlow.route) { inclusive = true }
                    }
                },
                onBack = { navController.popBackStack() }
            )
        }

    }
}

/**
 * Customer navigation graph
 */
fun NavGraphBuilder.customerGraph(
    navController: NavHostController,
    updateTopBottomAppBar: (Boolean, String, Boolean) -> Unit
) {
    // Customer Home
    composable(CustomerRoutes.Home.route) {
        LaunchedEffect(Unit) {
            updateTopBottomAppBar(false, "Home", true)
        }
        CustomerHomeScreenRoute(
            onNavigateToProfile = {
                navController.navigate(SettingsRoutes.Profile.route)
            },
            onNavigateToCouponDetails = { couponId ->
                navController.navigate(CouponRoutes.CouponDetail.createRoute(couponId))
            },
            onNavigateToAllCoupons = {
                navController.navigate(CustomerRoutes.Coupons.route)
            },
//            onNavigateToAllActivity = {
//                navController.navigate(CustomerRoutes.Transactions.route)
//            },
            onNavigateToOrder = {
                navController.navigate(CustomerRoutes.Order.route)
            },
            onNavigateToReferral = {},
            onNavigateToOutlet = {
                navController.navigate(CustomerRoutes.Outlets.route)
            },
            onNavigateToVouchers = {
                navController.navigate(CustomerRoutes.Coupons.route)
            }
        )
    }
    composable(
        route = CouponRoutes.CouponQR.route,
        arguments = listOf(
            navArgument(RouteParams.COUPON_ID) { type = NavType.StringType },
            navArgument(RouteParams.TITLE) { type = NavType.StringType }
        )
    ) { backStackEntry ->
        LaunchedEffect(Unit) {
            updateTopBottomAppBar(true, "Coupon QR Code", false)
        }

        val couponId = backStackEntry.arguments?.getString(RouteParams.COUPON_ID) ?: ""
        val couponTitle = backStackEntry.arguments?.getString(RouteParams.COUPON_TITLE) ?: ""

        CouponQRCodeScreenRoute(
            couponId = couponId,
            couponTitle = couponTitle,
            onBack = {
                navController.popBackStack()
            }
        )
    }

    // Customer QR
    composable(CustomerRoutes.MyQR.route) {
        LaunchedEffect(Unit) {
            updateTopBottomAppBar(false, "My QR", true)
        }
        QRCodeDisplayScreenRoute(
            onBack = {
                navController.popBackStack()
            },
            onNavigateToHistory = {
                navController.navigate(CustomerRoutes.Transactions.route)
            }
        )
    }
// Customer QR
    composable(CustomerRoutes.Order.route) {
        LaunchedEffect(Unit) {
            updateTopBottomAppBar(false, "Order", true)
        }
        OrdersScreen(
            onBack = {
                navController.popBackStack()
            }
        )
    }

    // Update in NavigationGraphs.kt - customerGraph function

// Customer Coupons List
    composable(CustomerRoutes.Coupons.route) {
        LaunchedEffect(Unit) {
            updateTopBottomAppBar(false, "Coupons", true)
        }
        CouponsScreenRoute(
            onBack = {
                navController.popBackStack()
            },
            onCouponClick = { coupon ->
                navController.navigate(CouponRoutes.CouponDetail.createRoute(coupon.id))
            },
            onCouponRedeem ={
                couponId,
                couponTitle ->
                navController.navigate(CouponRoutes.CouponQR.createRoute(couponId,couponTitle))

            }
        )
    }

// Coupon Detail
    composable(
        route = CouponRoutes.CouponDetail.route,
        arguments = listOf(navArgument(RouteParams.COUPON_ID) { type = NavType.StringType })
    ) { backStackEntry ->
        LaunchedEffect(Unit) {
            updateTopBottomAppBar(true, "Coupon Details", false)
        }
        val couponId = backStackEntry.arguments?.getString(RouteParams.COUPON_ID) ?: ""

        CouponDetailScreenRoute(
            couponId = couponId,
            onBack = {
                navController.popBackStack()
            },
            onRedeem = {
                // Navigate back to coupons list after successful redemption
                navController.popBackStack()
            },
            onShowQRCode = { couponId, title ->
                navController.navigate(CouponRoutes.CouponQR.createRoute(couponId, title))
            }
        )
    }


    composable(CustomerRoutes.OutletsMap.route) {
        LaunchedEffect(Unit) {
            updateTopBottomAppBar(false, "Find Outlets", true)
        }
        OutletMapScreenRoute(
            onBack = {
                navController.popBackStack()
            }
        )
    }

    // Transactions
    composable(CustomerRoutes.Transactions.route) {
        LaunchedEffect(Unit) {
            updateTopBottomAppBar(true, "Transactions", false)
        }
        TransactionHistoryScreenRoute()
    }
    composable(CustomerRoutes.Outlets.route) {
        LaunchedEffect(Unit) {
            updateTopBottomAppBar(true, "Transactions", false)
        }
        OutletsListScreenRoute(
            onBack = {
                navController.popBackStack()
            },
            onAddOutlet = {
                // Customers can't add outlets, this might be a placeholder or mistake
                // For now, we can make it do nothing or just pop back
                navController.popBackStack()
            },
            onOutletClick = { outlet ->
                navController.navigate(OutletRoutes.OutletDetail.createRoute(outlet))
            }
        )
    }
}

/**
 * Merchant navigation graph
 */
fun NavGraphBuilder.merchantGraph(
    navController: NavHostController,
    updateTopBottomAppBar: (Boolean, String, Boolean) -> Unit
) {
    // Merchant Dashboard
    composable(MerchantRoutes.Dashboard.route) {
        LaunchedEffect(Unit) {
            updateTopBottomAppBar(false, "Dashboard", true)
        }
        MerchantDashboardRoute(
            onNavigateToAllTransactions = {
                navController.navigate(MerchantRoutes.Transactions.route)
            }
        )
    }

    // Scan QR
    composable(MerchantRoutes.ScanQR.route) {
        LaunchedEffect(Unit) {
            updateTopBottomAppBar(false, "Scan QR", true)
        }
        QRScannerScreenRoute {
            navController.popBackStack()
        }
    }

    // Outlets
    composable(MerchantRoutes.Outlets.route) {
        LaunchedEffect(Unit) {
            updateTopBottomAppBar(true, "Outlets", false)
        }
        OutletsListScreenRoute(
            onBack = {
                navController.popBackStack()
            },
            onAddOutlet = {
                navController.navigate(MerchantRoutes.AddOutlet.route)
            },
            onOutletClick = { outlet ->
                navController.navigate(OutletRoutes.OutletDetail.createRoute(outlet))
            }
        )
    }

    //
    composable(
        route = OutletRoutes.OutletDetail.route,
        arguments = listOf(navArgument(RouteParams.OUTLET_ID) { type = NavType.StringType })
    ) { backStackEntry ->
        LaunchedEffect(Unit) {
            updateTopBottomAppBar(true, "Outlet Details", false)
        }
        val outletId = backStackEntry.arguments?.getString(RouteParams.OUTLET_ID) ?: ""
        OutletDetailScreenRoute(
            onBack = {
                navController.popBackStack()
            },
            onEdit = {
                navController.navigate(OutletRoutes.EditOutlet.createRoute(outletId))
            }, outletId = outletId
        )
    }

    // Outlet Detail
    composable(
        route = OutletRoutes.EditOutlet.route,
        arguments = listOf(navArgument(RouteParams.OUTLET_ID) { type = NavType.StringType })
    ) { backStackEntry ->
        LaunchedEffect(Unit) {
            updateTopBottomAppBar(true, "Edite Outlet", false)
        }
        val outletId = backStackEntry.arguments?.getString(RouteParams.OUTLET_ID) ?: ""
        EditOutletScreenRoute(
            onBack = {
                navController.popBackStack()
            }, outletId = outletId
        )
    }


    // Add Outlet
    composable(MerchantRoutes.AddOutlet.route) {
        LaunchedEffect(Unit) {
            updateTopBottomAppBar(true, "Add Outlet", false)
        }
        // Add outlet screen would go here
        // AddOutletScreenRoute(...)
    }

    // Transactions
    composable(MerchantRoutes.Transactions.route) {
        LaunchedEffect(Unit) {
            updateTopBottomAppBar(true, "Transactions", false)
        }
        TransactionHistoryScreenRoute()
    }
}

/**
 * Settings/Profile navigation graph
 */
fun NavGraphBuilder.settingsGraph(
    navController: NavHostController,
    updateTopBottomAppBar: (Boolean, String, Boolean) -> Unit
) {
    // Profile Screen
    composable(SettingsRoutes.Profile.route) {
        LaunchedEffect(Unit) {
            updateTopBottomAppBar(false, "Profile", true)
        }
        ProfileScreenRoute(
            onEditProfile = {
                navController.navigate(SettingsRoutes.EditProfile.route)
            },
            onChangePassword = {
                navController.navigate(SettingsRoutes.ChangePassword.route)
            },
            
            onLogout = {
                navController.navigate(AuthRoutes.Login.route) {
                    popUpTo(0) { inclusive = true } // Clear entire back stack
                    launchSingleTop = true
                }
            }, onPersonalInfo = {}, onReferEarn = {}, onTermsConditions = {
                navController.navigate(SettingsRoutes.WebView.route)

            },

            onAboutUs = {
                navController.navigate(SettingsRoutes.AboutUs.route)
            },

            onFAQs = {
                navController.navigate(SettingsRoutes.FAQs.route)
            },

            onOutletInfo = {
                navController.navigate(MerchantRoutes.OutletDetail.route)
            },
        )
    }

    // Edit Profile
    composable(SettingsRoutes.EditProfile.route) {
        LaunchedEffect(Unit) {
            updateTopBottomAppBar(true, "Edit Profile", false)
        }
        EditProfileScreenRoute(
            onBack = {
                navController.popBackStack()
            }
        )
    }

    // Change Password
    composable(SettingsRoutes.ChangePassword.route) {
        LaunchedEffect(Unit) {
            updateTopBottomAppBar(true, "Change Password", false)
        }
        ChangePasswordScreenRoute(onBack = {
            navController.popBackStack()
        })
    }

    // Notifications
    composable(SettingsRoutes.Notifications.route) {
        LaunchedEffect(Unit) {
            updateTopBottomAppBar(true, "Notifications", false)
        }
        NotificationsScreenRoute(
            onBack = {
                navController.popBackStack()
            },
            onNotificationClick = { notification ->
                // Handle notification click
            }
        )
    }
    // About Us
    composable(SettingsRoutes.AboutUs.route) {
        LaunchedEffect(Unit) {
            updateTopBottomAppBar(true, "About Us", false)
        }
        AboutUsScreenRoute(
            onBack = {
                navController.popBackStack()
            }
        )
    }

    // FAQs
    composable(SettingsRoutes.FAQs.route) {
        LaunchedEffect(Unit) {
            updateTopBottomAppBar(true, "FAQs", false)
        }
        FAQsScreenRoute(
            onBack = {
                navController.popBackStack()
            }
        )
    }
    composable(SettingsRoutes.WebView.route) {
        LaunchedEffect(Unit) {
            updateTopBottomAppBar(true, "Terms And Condition", false)
        }
        WebViewScreenRoute(
            onBack = {
                navController.popBackStack()
            }
        )
    }
}

/**
 * Common screens navigation graph
 */
fun NavGraphBuilder.commonScreensGraph(
    navController: NavHostController,
    updateTopBottomAppBar: (Boolean, String, Boolean) -> Unit
) {
    // WebView Screen
    composable(
        route = CommonRoutes.WebView.route,
        arguments = listOf(
            navArgument(RouteParams.URL) { type = NavType.StringType },
            navArgument(RouteParams.TITLE) { type = NavType.StringType }
        )
    ) { backStackEntry ->
        val title = backStackEntry.arguments?.getString(RouteParams.TITLE) ?: ""
        val url = backStackEntry.arguments?.getString(RouteParams.URL) ?: ""

        LaunchedEffect(Unit) {
            updateTopBottomAppBar(true, title, false)
        }

        // WebViewScreenRoute would go here
        // WebViewScreenRoute(
        //     title = title,
        //     url = url.replace("*", "/"), // Decode slashes
        //     onBack = { navController.popBackStack() }
        // )
    }

    // OTP Screen
    composable(
        route = CommonRoutes.OTP.route,
        arguments = listOf(
            navArgument(RouteParams.MOBILE_NUMBER) { type = NavType.StringType },
            navArgument(RouteParams.DEST_ROUTE) { type = NavType.StringType },
            navArgument(RouteParams.CONFIGURATION) { type = NavType.StringType }
        )
    ) { backStackEntry ->
        LaunchedEffect(Unit) {
            updateTopBottomAppBar(true, "Verify OTP", false)
        }

        val mobileNumber = backStackEntry.arguments?.getString(RouteParams.MOBILE_NUMBER) ?: ""
        val destRoute = backStackEntry.arguments?.getString(RouteParams.DEST_ROUTE) ?: ""
        val configuration = backStackEntry.arguments?.getString(RouteParams.CONFIGURATION) ?: ""

        // OTPScreenRoute would go here
        // OtpScreenRoute(
        //     mobileNumber = mobileNumber,
        //     destRoute = destRoute,
        //     configuration = configuration,
        //     onOtpVerified = {
        //         navController.navigate(destRoute) {
        //             popUpTo(CommonRoutes.OTP.route) { inclusive = true }
        //         }
        //     },
        //     onBack = { navController.popBackStack() }
        // )
    }
}

/**
 * QR Flow navigation graph
 */
fun NavGraphBuilder.qrFlowGraph(
    navController: NavHostController,
    updateTopBottomAppBar: (Boolean, String, Boolean) -> Unit
) {
    navigation(
        startDestination = QRRoutes.ScanQR.route,
        route = QRRoutes.QRFlow.route
    ) {
        // QR Scanner
        composable(QRRoutes.ScanQR.route) {
            LaunchedEffect(Unit) {
                updateTopBottomAppBar(true, "Scan QR Code", false)
            }
            QRScannerScreenRoute {
                navController.popBackStack()
            }
        }

        // My QR Code
        composable(QRRoutes.MyQR.route) {
            LaunchedEffect(Unit) {
                updateTopBottomAppBar(true, "My QR Code", false)
            }
            QRCodeDisplayScreenRoute(
                onBack = { navController.popBackStack() },
                onNavigateToHistory = {
                    navController.navigate(CustomerRoutes.Transactions.route)
                }
            )
        }

        // QR Result
        composable(QRRoutes.QRResult.route) {
            LaunchedEffect(Unit) {
                updateTopBottomAppBar(true, "QR Result", false)
            }
            // QR Result screen would go here
        }
        composable(
            route = QRRoutes.CouponQR.route,
            arguments = listOf(
                navArgument(RouteParams.COUPON_ID) { type = NavType.StringType },
                navArgument(RouteParams.COUPON_TITLE) { type = NavType.StringType }
            )
        ) { backStackEntry ->
            LaunchedEffect(Unit) {
                updateTopBottomAppBar(true, "Coupon QR Code", false)
            }

            val couponId = backStackEntry.arguments?.getString(RouteParams.COUPON_ID) ?: ""
            val couponTitle = backStackEntry.arguments?.getString(RouteParams.COUPON_TITLE) ?: ""

            CouponQRCodeScreenRoute(
                couponId = couponId,
                couponTitle = couponTitle,
                onBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}



/**
 * Extension functions for easier graph usage
 */
fun NavGraphBuilder.addAllGraphs(
    navController: NavHostController,
    updateTopBottomAppBar: (Boolean, String, Boolean) -> Unit
) {
    authenticationGraph(navController, updateTopBottomAppBar)
    customerGraph(navController, updateTopBottomAppBar)
    merchantGraph(navController, updateTopBottomAppBar)
    settingsGraph(navController, updateTopBottomAppBar)
    commonScreensGraph(navController, updateTopBottomAppBar)
    qrFlowGraph(navController, updateTopBottomAppBar)
}