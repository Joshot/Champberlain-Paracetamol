package com.example.paracetamol.nav

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Archive
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.paracetamol.screen.Screen

// Data class representing items in a navigation menu
data class NavItem(
    val icon: ImageVector,  // Icon associated with the navigation item
    val label: String,      // Label or text for the navigation item
    val route: String       // Route or destination associated with the navigation item
)

// List of navigation items used in the application

val listOfNavItems = listOf(
    NavItem(
        icon = Icons.Default.Home,
        label = "Home",
        route = Screen.HomeScreen.route
    ) ,
    NavItem(
        icon = Icons.Default.Search,
        label = "Search",
        route = Screen.SearchScreen.route
    ),
    NavItem(
        icon = Icons.Default.Archive,
        label = "Archive",
        route = Screen.ArchiveScreen.route
    ),
    NavItem(
        icon = Icons.Default.Person,
        label = "Profile",
        route = Screen.ProfileScreen.route
    ),
)