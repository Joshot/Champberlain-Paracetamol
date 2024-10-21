package com.example.paracetamol.nav


import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.paracetamol.nav_screen.HomeScreen
import com.example.paracetamol.screen.LoginScreen
import com.example.paracetamol.nav_screen.ProfileScreen
import com.example.paracetamol.screen.RegisterScreen
import com.example.paracetamol.nav_screen.SearchScreen
import com.example.paracetamol.screen.Screen
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalContext
import com.example.paracetamol.screen.UserGroupScreen
import com.example.paracetamol.screen.CreateScreen
import com.example.paracetamol.screen.JoinScreen
import com.example.paracetamol.nav_screen.ArchiveScreen
import com.example.paracetamol.preferences.PreferenceManager
import com.example.paracetamol.screen.AdminMemberListScreen
import com.example.paracetamol.screen.AdminViewMemberScreen
import com.example.paracetamol.screen.GlobalViewModel
import com.example.paracetamol.screen.MemberGroupScreen
import com.example.paracetamol.screen.PayScreen
import com.example.paracetamol.screen.admin.AdminMemberDetailScreen
import com.example.paracetamol.screen.admin.AdminNewDendaScreen
import com.example.paracetamol.screen.admin.AdminPaidScreen
import com.example.paracetamol.screen.admin.AdminProfileUserScreen
import com.example.paracetamol.screen.admin.AdminUpdateDendaScreen

/**
 * Data class representing a navigation item.
 *
 * @param label The label to be displayed.
 * @param icon The icon to be displayed.
 * @param route The route to navigate to.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Navigation() {
    val navController = rememberNavController()
    val context = LocalContext.current
    var isLoggedIn by rememberSaveable{ mutableStateOf(false) }
    val sessionExist = PreferenceManager.getIsLoggedIn(context)

    if(sessionExist) isLoggedIn = true

    Scaffold(
        bottomBar = {
            if(isLoggedIn){
                NavigationBar {
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentDestination = navBackStackEntry?.destination

                    listOfNavItems.forEach { navItem ->
                        NavigationBarItem(
                            selected = currentDestination?.hierarchy?.any() {it.route == navItem.route} == true,
                            onClick = {
                                navController.navigate(navItem.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            icon = {
                                Icon(
                                    imageVector = navItem.icon,
                                    contentDescription = null
                                )
                            },
                            label = {
                                Text(text = navItem.label)
                            }
                        )
                    }
                }
            }

        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination =
                if(isLoggedIn) Screen.HomeScreen.route
                else Screen.LoginScreen.route,
            modifier = Modifier.padding(paddingValues)
        ){
            composable(route = Screen.LoginScreen.route){
                LoginScreen(navController = navController){
                    isLoggedIn = true
                }
            }
            composable(route = Screen.RegisterScreen.route){
                RegisterScreen(navController = navController)
            }
            composable(route = Screen.HomeScreen.route){
                HomeScreen(navController = navController)
            }
            composable(route = Screen.SearchScreen.route){
                SearchScreen(navController = navController)
            }
            composable(route = Screen.ArchiveScreen.route){
                ArchiveScreen(navController = navController)
            }
            composable(route = Screen.ProfileScreen.route){
                ProfileScreen()
                {
                    isLoggedIn = false
                }
            }
            composable(route = Screen.AdminUpdateDendaScreen.route + "/{title}/{refKey}/{groupID}") { navBackStackEntry ->
                val title = navBackStackEntry.arguments?.getString("title") ?: ""
                val refKey = navBackStackEntry.arguments?.getString("refKey") ?: ""
                val groupID = navBackStackEntry.arguments?.getString("groupID") ?: ""
                AdminUpdateDendaScreen(navController = navController, titleA = title, refKey = refKey, groupID = groupID)
            }
            composable(route = Screen.AdminNewDendaScreen.route + "/{title}/{refKey}/{groupID}") { navBackStackEntry ->
                val title = navBackStackEntry.arguments?.getString("title") ?: ""
                val refKey = navBackStackEntry.arguments?.getString("refKey") ?: ""
                val groupID = navBackStackEntry.arguments?.getString("groupID") ?: ""
                AdminNewDendaScreen(navController = navController, titleA = title, refKey = refKey, groupID = groupID)
            }
            composable(route = Screen.AdminMemberListScreen.route + "/{id}/{refKey}/{title}") { navBackStackEntry ->
                val id = navBackStackEntry.arguments?.getString("id") ?: ""
                val refKey = navBackStackEntry.arguments?.getString("refKey") ?: ""
                val title = navBackStackEntry.arguments?.getString("title") ?: ""
                AdminMemberListScreen(navController = navController, id = id, refKey = refKey, title = title)
            }
            composable(route = Screen.CreateScreen.route){
                CreateScreen(navController = navController)
            }
            composable(route = Screen.JoinScreen.route){
                JoinScreen(navController = navController)
            }
            composable(route = Screen.MemberGroupScreen.route + "/{title}/{refKey}") { navBackStackEntry ->
                val title = navBackStackEntry.arguments?.getString("title") ?: ""
                val refKey = navBackStackEntry.arguments?.getString("refKey") ?: ""
                MemberGroupScreen(navController = navController, title = title, refKey = refKey)
            }
            composable(route = Screen.AdminMemberDetailScreen.route + "/{id}/{name}/{namaGroup}/{groupID}/{refKey}") { navBackStackEntry ->
                val id = navBackStackEntry.arguments?.getString("id") ?: ""
                val name = navBackStackEntry.arguments?.getString("name") ?: ""
                val namaGroup = navBackStackEntry.arguments?.getString("namaGroup") ?: ""
                val groupID = navBackStackEntry.arguments?.getString("groupID") ?: ""
                val refKey = navBackStackEntry.arguments?.getString("refKey") ?: ""
                AdminMemberDetailScreen(navController = navController, id = id, name = name, namaGroup = namaGroup, groupID = groupID, refKey = refKey)
            }
            composable(route = Screen.AdminViewMemberScreen.route + "/{id}/{namaGroup}/{refKey}") { navBackStackEntry ->
                val id = navBackStackEntry.arguments?.getString("id") ?: ""
                val namaGroup = navBackStackEntry.arguments?.getString("namaGroup") ?: ""
                val refKey = navBackStackEntry.arguments?.getString("refKey") ?: ""
                AdminViewMemberScreen(navController = navController, id = id, namaGroup = namaGroup, refKey = refKey)
            }
            composable(route = Screen.UserGroupScreen.route + "/{id}/{title}/{description}/{refKey}") { navBackStackEntry ->
                val id = navBackStackEntry.arguments?.getString("id") ?: ""
                val title = navBackStackEntry.arguments?.getString("title") ?: ""
                val description = navBackStackEntry.arguments?.getString("description") ?: ""
                val refKey = navBackStackEntry.arguments?.getString("refKey") ?: ""
                UserGroupScreen(navController = navController, id = id, titleA = title, descriptionA = description, refKey = refKey)
            }
            composable(route = Screen.AdminProfileUserScreen.route + "/{id}/{isAdmin}/{refKey}") { navBackStackEntry ->
                val id = navBackStackEntry.arguments?.getString("id") ?: ""
                val isAdmin = navBackStackEntry.arguments?.getString("isAdmin") ?: ""
                val refKey = navBackStackEntry.arguments?.getString("refKey") ?: ""
                AdminProfileUserScreen(navController = navController, id = id, isAdmin = isAdmin.toBoolean(), refKey = refKey)
            }
            composable(route = Screen.PayScreen.route + "/{id}/{judulDenda}") { navBackStackEntry ->
                val id = navBackStackEntry.arguments?.getString("id") ?: ""
                val judulDenda = navBackStackEntry.arguments?.getString("judulDenda") ?: ""
                PayScreen(navController = navController, id = id, titleA = judulDenda)
            }
            composable(route = Screen.AdminPaidScreen.route + "/{title}/{dendaID}/{nama}") { navBackStackEntry ->
                val title = navBackStackEntry.arguments?.getString("title") ?: ""
                val dendaID = navBackStackEntry.arguments?.getString("dendaID") ?: ""
                val nama = navBackStackEntry.arguments?.getString("nama") ?: ""
                AdminPaidScreen(navController = navController, titleA = title, dendaID = dendaID, nama = nama)
            }

        }
    }

}
