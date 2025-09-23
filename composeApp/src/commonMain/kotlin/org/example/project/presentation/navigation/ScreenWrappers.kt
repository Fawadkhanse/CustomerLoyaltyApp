
package org.example.project.presentation.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import org.example.project.presentation.components.LoyaltyPrimaryButton
import org.example.project.presentation.components.NotificationData
import org.example.project.presentation.components.NotificationsScreen
import org.example.project.presentation.components.OutletData
import org.example.project.presentation.components.OutletDetailScreen
import org.example.project.presentation.components.Profile.EditProfileScreen
import org.example.project.presentation.components.Profile.ProfileScreen
import org.example.project.presentation.components.home.*
import org.example.project.presentation.components.qr.*
import org.example.project.presentation.components.coupons.*

import org.example.project.presentation.components.transactions.*

import org.example.project.presentation.components.auth.SetNewPasswordScreen
import org.example.project.presentation.design.LoyaltyExtendedColors
import org.example.project.presentation.navigation.Screen.Screen

// ===================
// CUSTOMER SCREENS
// ===================

@Composable
fun CustomerHomeScreenWrapper(navController: NavHostController) {
    // Sample data - replace with actual ViewModel data
    val samplePromotions = listOf(
        PromotionData("1", "Happy Hour Drinks", null, "31 Dec 2024"),
        PromotionData("2", "Taco Tuesday", null, "31 Dec 2024")
    )

    val sampleCoupons = listOf(
        CouponData("1", "Free Appetizer", "Enjoy a free appetizer with any main course", 500, "15 Nov 2024"),
        CouponData("2", "10% Off Main Course", "Get 10% off any main course", 250, "30 Nov 2024")
    )

    val sampleActivity = listOf(
        ActivityData("1", "Redeemed: Free Coffee", 100, "2h ago", "redeemed"),
        ActivityData("2", "Points Earned: Lunch Special", 25, "1d ago", "earned")
    )

    CustomerHomeScreen(
        userName = "Sarah",
        userPoints = 1250,
        tier = "Gold Member",
        promotions = samplePromotions,
        availableCoupons = sampleCoupons,
        recentActivity = sampleActivity,
        onProfileClick = { navController.navigateToScreen(Screen.Profile) },
        onCouponClick = { coupon -> navController.navigateToCouponDetail(coupon.id) },
        onViewAllCoupons = { navController.navigateToScreen(Screen.Coupons) }
    )
}

@Composable
fun CustomerQRScreen(navController: NavHostController) {
    QRCodeDisplayScreen(
        customerName = "Sarah Johnson",
        qrCodeData = "CUSTOMER_ID_12345",
        onShareQR = {
            // TODO: Implement share functionality
        },
        onDownloadQR = {
            // TODO: Implement download functionality
        }
    )
}

@Composable
fun CustomerCouponsScreen(navController: NavHostController) {
    val sampleAvailableCoupons = listOf(
        CouponData("1", "20% Off Your Next Purchase", "Get 20% off on any item in our store.", 500, "Dec 31, 2024"),
        CouponData("2", "Free Coffee", "Enjoy a free cup of our signature blend coffee.", 250, "Nov 15, 2024")
    )

    val sampleRedeemedCoupons = listOf(
        RedeemedCouponData("1", "20% off on your next purchase", "2024-03-15", "Used"),
        RedeemedCouponData("2", "Free coffee with any pastry", "2024-02-28", "Used"),
        RedeemedCouponData("3", "10% off on orders over $50", "2023-12-05", "Expired")
    )

    CouponsScreen(
        availableCoupons = sampleAvailableCoupons,
        redeemedCoupons = sampleRedeemedCoupons,
        onCouponClick = { coupon -> navController.navigateToCouponDetail(coupon.id) },
        onBack = { navController.popBackStack() }
    )
}

@Composable
fun CustomerProfileScreen(
    navController: NavHostController,
    onLogout: () -> Unit
) {
    ProfileScreen(
        name = "Sarah Johnson",
        email = "sarah.johnson@example.com",
        phone = "+1 (555) 123-4567",
        onEditProfile = { navController.navigateToScreen(Screen.EditProfile) },
        onChangePassword = { navController.navigateToScreen(Screen.ChangePassword) },
        onLogout = onLogout
    )
}

// ===================
// MERCHANT SCREENS
// ===================

@Composable
fun MerchantDashboardScreenWrapper(navController: NavHostController) {
    val sampleTransactions = listOf(
        TransactionData("1", "Eleanor Pena", 10, "Downtown", "2h ago"),
        TransactionData("2", "Cody Fisher", 15, "Uptown", "3h ago"),
        TransactionData("3", "Kristin Watson", 25, "Mall", "1d ago")
    )

    MerchantDashboardScreen(
        todaysScans = 1284,
        pointsAwarded = 8520,
        couponsRedeemed = 312,
        activeOutlets = 4,
        recentTransactions = sampleTransactions,
        onViewAllTransactions = { navController.navigateToScreen(Screen.Transactions) }
    )
}

@Composable
fun MerchantQRScanScreen(navController: NavHostController) {
    var showCustomerSheet by remember { mutableStateOf(false) }
    var scannedCustomer by remember { mutableStateOf<CustomerData?>(null) }

    QRScannerScreen(
        onQRScanned = { qrData ->
            // Simulate customer lookup from QR data
            scannedCustomer = CustomerData("Eleanor Vance", "**** 5678", 2580)
            showCustomerSheet = true
        },
        onBack = { navController.popBackStack() }
    )

    // Show bottom sheet when customer is found
    if (showCustomerSheet && scannedCustomer != null) {
        CustomerFoundBottomSheet(
            customerName = scannedCustomer!!.name,
            customerId = scannedCustomer!!.id,
            currentPoints = scannedCustomer!!.points,
            onConfirm = { points ->
                showCustomerSheet = false
                // TODO: Handle points award to customer
            },
            onCancel = { showCustomerSheet = false }
        )
    }
}

@Composable
fun MerchantOutletsScreen(navController: NavHostController) {
    val sampleOutlets = listOf(
        OutletListData("1", "Main Branch", "123 Main Street, Downtown", "+1 (555) 123-4567"),
        OutletListData("2", "Seaside Cafe", "456 Ocean Avenue, Beachfront", "+1 (555) 987-6543"),
        OutletListData("3", "Uptown Express", "789 High Street, Uptown", "+1 (555) 246-8135"),
        OutletListData("4", "The Corner Stop", "101 Maple Lane, Suburbia", "+1 (555) 369-2580")
    )

    OutletsListScreen(
        outlets = sampleOutlets,
        onBack = { navController.popBackStack() },
        onAddOutlet = { navController.navigateToScreen(Screen.AddOutlet) },
        onOutletClick = { outlet -> navController.navigateToOutletDetail(outlet.id) }
    )
}

@Composable
fun MerchantTransactionsScreen(navController: NavHostController) {
    val sampleTransactions = listOf(
        TransactionHistoryData("1", "Olivia Chen", 50, "25 Oct 2023, 10:45 AM", "awarded", "Points Awarded"),
        TransactionHistoryData("2", "Benjamin Carter", 100, "24 Oct 2023, 07:15 PM", "redeemed", "Points Redeemed"),
        TransactionHistoryData("3", "Ethan Nguyen", 25, "24 Oct 2023, 01:30 PM", "awarded", "Points Awarded"),
        TransactionHistoryData("4", "Sophia Rodriguez", 75, "23 Oct 2023, 11:00 AM", "awarded", "Points Awarded"),
        TransactionHistoryData("5", "Liam Goldberg", 500, "22 Oct 2023, 08:00 PM", "redeemed", "Points Redeemed"),
        TransactionHistoryData("6", "Ava Kim", 120, "22 Oct 2023, 03:20 PM", "awarded", "Points Awarded")
    )

    TransactionHistoryScreen(
        transactions = sampleTransactions,
        onBack = { navController.popBackStack() },
        onDateRangeFilter = {
            // TODO: Show date picker dialog
        },
        onOutletFilter = {
            // TODO: Show outlet selector dialog
        }
    )
}

@Composable
fun MerchantProfileScreen(
    navController: NavHostController,
    onLogout: () -> Unit
) {
    ProfileScreen(
        name = "John Merchant",
        email = "john.merchant@business.com",
        phone = "+1 (555) 987-6543",
        onEditProfile = { navController.navigateToScreen(Screen.EditProfile) },
        onChangePassword = { navController.navigateToScreen(Screen.ChangePassword) },
        onLogout = onLogout
    )
}

// ===================
// SHARED SCREENS
// ===================

@Composable
fun CouponDetailScreenWrapper(
    navController: NavHostController,
    couponId: String
) {
    // In real app, you would fetch coupon data by ID
    CouponDetailScreen(
        title = "20% Off Your Next Purchase",
        description = "Get 20% off on any single item in your next purchase. This offer cannot be combined with other promotions. Show this coupon at checkout to apply the discount.",
        pointsRequired = 500,
        expiryDate = "Dec 31, 2024",
        onRedeem = {
            // TODO: Handle coupon redemption
            navController.popBackStack()
        },
        onBack = { navController.popBackStack() }
    )
}

@Composable
fun OutletDetailScreenWrapper(
    navController: NavHostController,
    outletId: String
) {
    // In real app, you would fetch outlet data by ID
    val sampleOutlet = OutletData(
        id = outletId,
        name = "Main Branch",
        location = "123 Main Street, Downtown, Cityville, 12345",
        category = "Coffee Shop",
        distance = "2.3",
        phone = "+1 (555) 123-4567",
        email = "main.branch@merchant.com",
        website = "www.mainbranch.com"
    )

    OutletDetailScreen(
        outlet = sampleOutlet,
        onBack = { navController.popBackStack() },
        onEdit = {
            // TODO: Navigate to edit outlet screen
        }
    )
}

@Composable
fun NotificationsScreenWrapper(navController: NavHostController) {
    val sampleNotifications = listOf(
        NotificationData("1", "Points Updated", "Your points balance has been updated. You now have 150 points.", "points", "2h ago", false),
        NotificationData(
            "2",
            "Reward Unlocked",
            "You've earned a free coffee for your loyalty!",
            "reward",
            "1d ago",
            true
        ),
        NotificationData("3", "Exclusive Offer", "Don't miss out on our exclusive offer for members this week.", "offer", "3d ago", true)
    )

    NotificationsScreen(
        notifications = sampleNotifications,
        onBack = { navController.popBackStack() },
        onNotificationClick = { notification ->
            // TODO: Handle notification click
        }
    )
}

@Composable
fun EditProfileScreenWrapper(navController: NavHostController) {
    EditProfileScreen(
        name = "Sarah Johnson",
        phone = "+1 (555) 123-4567",
        email = "sarah.johnson@example.com",
        onSave = { name, phone, email ->
            // TODO: Save profile changes
            navController.popBackStack()
        },
        onBack = { navController.popBackStack() },
        onChangeProfilePicture = {
            // TODO: Handle profile picture change
        }
    )
}

@Composable
fun ChangePasswordScreen(navController: NavHostController) {
    SetNewPasswordScreen(
        onSetPassword = { newPassword, confirmPassword ->
            // TODO: Handle password change
            navController.popBackStack()
        },
        onBack = { navController.popBackStack() }
    )
}

@Composable
fun AddOutletScreen(navController: NavHostController) {
    // Simple placeholder - you can create a proper form
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        IconButton(onClick = { navController.popBackStack() }) {
            Icon(imageVector = AppIcons.ArrowBack, contentDescription = "Back")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Add New Outlet",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(32.dp))

        // TODO: Add form fields for outlet details
        Text(
            text = "Outlet form will go here",
            style = MaterialTheme.typography.bodyLarge,
            color = LoyaltyExtendedColors.secondaryText()
        )

        Spacer(modifier = Modifier.weight(1f))

        LoyaltyPrimaryButton(
            text = "Save Outlet",
            onClick = {
                // TODO: Save outlet
                navController.popBackStack()
            },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

// Sample data class for customer found in QR scan
data class CustomerData(
    val name: String,
    val id: String,
    val points: Int
)