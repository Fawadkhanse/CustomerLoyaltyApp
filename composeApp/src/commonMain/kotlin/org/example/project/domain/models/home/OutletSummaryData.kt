package org.example.project.domain.models.home

// Data class for outlet summary
data class OutletSummaryData(
    val id: String,
    val name: String,
    val location: String,
    val isActive: Boolean,
    val scansToday: Int,
    val totalCustomers: Int
)