package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.ContentCut
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.material.icons.outlined.AutoAwesome
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.ContentCut
import androidx.compose.material.icons.outlined.WorkspacePremium
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ui.components.BookingBottomSheet
import com.example.ui.theme.BlackBackground
import com.example.ui.theme.BlackSurface
import com.example.ui.theme.BlackSurfaceBorder
import com.example.ui.theme.GoldAccent
import com.example.ui.theme.GoldContainer
import com.example.ui.theme.GoldLight
import com.example.ui.theme.GoldPrimary
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.viewmodel.BarbershopViewModel

data class NavItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val testTag: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(viewModel: BarbershopViewModel) {
    val selectedTab by viewModel.selectedTab.collectAsStateWithLifecycle()
    val selectedCategory by viewModel.selectedCategory.collectAsStateWithLifecycle()
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    val appointments by viewModel.appointments.collectAsStateWithLifecycle()
    val showBookingSheet by viewModel.showBookingSheet.collectAsStateWithLifecycle()
    val bookingFormState by viewModel.bookingForm.collectAsStateWithLifecycle()
    val snackBarMessage by viewModel.snackBarMessage.collectAsStateWithLifecycle()

    val snackbarHostState = remember { SnackbarHostState() }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    LaunchedEffect(snackBarMessage) {
        snackBarMessage?.let { msg ->
            snackbarHostState.showSnackbar(msg)
            viewModel.clearSnackBar()
        }
    }

    val navItems = listOf(
        NavItem("Services", Icons.Filled.ContentCut, Icons.Outlined.ContentCut, "nav_services"),
        NavItem("Barbers", Icons.Filled.WorkspacePremium, Icons.Outlined.WorkspacePremium, "nav_barbers"),
        NavItem("Bookings", Icons.Filled.CalendarMonth, Icons.Outlined.CalendarMonth, "nav_bookings"),
        NavItem("Style Guide", Icons.Filled.AutoAwesome, Icons.Outlined.AutoAwesome, "nav_style_guide")
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = BlackBackground,
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState) { data ->
                Snackbar(
                    containerColor = GoldContainer,
                    contentColor = GoldLight,
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .padding(16.dp)
                        .border(1.dp, GoldPrimary, RoundedCornerShape(10.dp))
                ) {
                    Text(text = data.visuals.message, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                }
            }
        },
        bottomBar = {
            NavigationBar(
                containerColor = BlackSurface,
                tonalElevation = 8.dp,
                modifier = Modifier
                    .border(
                        width = 0.5.dp,
                        color = BlackSurfaceBorder
                    )
            ) {
                navItems.forEachIndexed { index, item ->
                    val isSelected = selectedTab == index
                    NavigationBarItem(
                        selected = isSelected,
                        onClick = { viewModel.selectTab(index) },
                        modifier = Modifier.testTag(item.testTag),
                        icon = {
                            if (index == 2 && appointments.isNotEmpty()) {
                                BadgedBox(
                                    badge = {
                                        Badge(
                                            containerColor = GoldPrimary,
                                            contentColor = Color(0xFF16130C)
                                        ) {
                                            Text(
                                                text = "${appointments.size}",
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 10.sp
                                            )
                                        }
                                    }
                                ) {
                                    Icon(
                                        imageVector = if (isSelected) item.selectedIcon else item.unselectedIcon,
                                        contentDescription = item.title
                                    )
                                }
                            } else {
                                Icon(
                                    imageVector = if (isSelected) item.selectedIcon else item.unselectedIcon,
                                    contentDescription = item.title
                                )
                            }
                        },
                        label = {
                            Text(
                                text = item.title,
                                fontSize = 11.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color(0xFF16130C),
                            selectedTextColor = GoldPrimary,
                            indicatorColor = GoldPrimary,
                            unselectedIconColor = TextMuted,
                            unselectedTextColor = TextMuted
                        )
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (selectedTab) {
                0 -> ServicesScreen(
                    services = viewModel.getFilteredServices(),
                    selectedCategory = selectedCategory,
                    searchQuery = searchQuery,
                    onCategorySelected = { viewModel.selectCategory(it) },
                    onSearchQueryChanged = { viewModel.updateSearchQuery(it) },
                    onBookService = { viewModel.openBookingForService(it) },
                    onOpenGeneralBooking = { viewModel.openCustomBooking() }
                )
                1 -> BarbersScreen(
                    barbers = viewModel.masterBarbers,
                    onBookWithBarber = { viewModel.openBookingForBarber(it) }
                )
                2 -> AppointmentsScreen(
                    appointments = appointments,
                    onOpenBooking = { viewModel.openCustomBooking() },
                    onUpdateStatus = { id, status -> viewModel.updateAppointmentStatus(id, status) },
                    onCancelAppointment = { viewModel.cancelAppointment(it) },
                    onDeleteAppointment = { viewModel.deleteAppointment(it) }
                )
                3 -> StyleGuideScreen(
                    tips = viewModel.groomingTips,
                    onBookConsultation = { viewModel.openCustomBooking() }
                )
            }
        }

        if (showBookingSheet) {
            BookingBottomSheet(
                formState = bookingFormState,
                allBarbers = viewModel.masterBarbers,
                sheetState = sheetState,
                onDismiss = { viewModel.dismissBookingSheet() },
                onBarberSelect = { viewModel.updateBookingBarber(it) },
                onDateSelect = { viewModel.updateBookingDate(it) },
                onTimeSelect = { viewModel.updateBookingTime(it) },
                onHairPrefSelect = { viewModel.updateHairPreference(it) },
                onBeardPrefSelect = { viewModel.updateBeardPreference(it) },
                onNameChange = { viewModel.updateCustomerName(it) },
                onPhoneChange = { viewModel.updateCustomerPhone(it) },
                onNotesChange = { viewModel.updateCustomerNotes(it) },
                onConfirmBooking = { viewModel.confirmBooking() }
            )
        }
    }
}
