package com.example.paracetamol.nav_screen

import kotlinx.coroutines.MainScope
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.paracetamol.model.UserViewModel
import com.example.paracetamol.screen.Screen
import com.example.paracetamol.ui.theme.poppinsFamily
import androidx.compose.ui.Alignment
import com.example.paracetamol.api.data.group.response.GroupItem
import com.example.paracetamol.component.showToast
import kotlinx.coroutines.launch

/**
 * Composable function for rendering a card item representing a group.
 *
 * @param userViewModel UserViewModel for handling user-related data and actions.
 * @param group GroupItem representing the group to be displayed.
 * @param navController NavController for navigating to other screens.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardItem(
    userViewModel: UserViewModel,
    group: GroupItem?,
    navController: NavController,
) {
    val context = LocalContext.current

    // Observe error messages and show a toast if there's an error
    val errorMessage by userViewModel.errorMessage.observeAsState()
    errorMessage?.let {
        showToast(context, it)
    }

    // Surface composable representing the card item
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 25.dp, vertical = 7.dp),
        border = BorderStroke(2.dp, Color(0xFF1F628E)),
        shape = RoundedCornerShape(12.dp),
        onClick = {
            userViewModel.getInGroupStatus(group!!.refKey) { isAdmin ->
                MainScope().launch{
                    // Navigate to different screens based on user's admin status
                    if (isAdmin) {
                        navController.navigate("${Screen.AdminMemberListScreen.route}/${group!!._id}/${group.refKey}/${group.namaGroup}")
                    } else {
                        navController.navigate("${Screen.UserGroupScreen.route}/${group!!._id}/${group.namaGroup}/${group.desc}/${group.refKey}")
                    }
                }
            }
        }

    ) {
        // Column composable for arranging child composables vertically
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(22.dp),
            verticalArrangement = Arrangement.Center,

            ) {
            Text(
                text = group!!.namaGroup,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 4.dp, start = 20.dp),
                color = Color(0xFF15104D),
                textAlign = TextAlign.Start,
            )
            Text(
                text = group.desc,
                fontSize = 14.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp),
                color = Color.Black,
                textAlign = TextAlign.Start,
            )
        }
    }
}

/**
 * Composable function for rendering a scrollable list of group items.
 *
 * @param innerPadding Padding values for the content.
 * @param navController NavController for navigating to other screens.
 */
@Composable
fun ScrollContent(innerPadding: PaddingValues, navController: NavController) {
    // Access the local context
    val context = LocalContext.current

    // Initialize the UserViewModel using the viewModel composable
    val userViewModel: UserViewModel = viewModel { UserViewModel(context) }

    // Mutable state to hold the list of groups
    var groupList by rememberSaveable { mutableStateOf<List<GroupItem?>?>(null) }

    // Fetch the joined groups when the composable is launched
    LaunchedEffect(userViewModel) {
        userViewModel.getMemberID()
        userViewModel.getJoinedGroup()
    }

    // Observe the LiveData and update the local variable
    userViewModel.joinedGroups.observeAsState().value?.let {
        groupList = it
    }

    // Observe the error message and display a toast if it is not null
    val errorMessage by userViewModel.errorMessage.observeAsState()
    errorMessage?.let {
        showToast(context, it)
    }

    // Display the list of groups in a LazyColumn
    LazyColumn(
        contentPadding = innerPadding,
        modifier = Modifier.fillMaxSize(),
    ) {
        if (groupList != null && groupList?.any { it?.status == true } == true) {
            val filteredGroupList = groupList!!.filter { it?.status == true }

            items(filteredGroupList) { item ->
                CardItem(userViewModel = userViewModel, group = item, navController = navController)
            }
        } else {
            item {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    Text(
                        "No group found.",
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(vertical = 280.dp),
                        fontSize = 14.sp,
                        fontFamily = poppinsFamily,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        color = Color.Black,
                    )
                }

            }
        }
    }
}

/**
 * Composable function for rendering the home screen.
 *
 * @param navController NavController for navigating to other screens.
 */
@Composable
fun HomeScreen(navController: NavController) {
    // Column composable for arranging child composables vertically
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF2F1FA)) //
    ) {
        Text(
            "Home",
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.fillMaxWidth()
                .padding(vertical = 30.dp),
            fontSize = 25.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF15104D),
        )
//        AppToolbar(navController = navController)
        ScrollContent(innerPadding = PaddingValues(16.dp), navController = navController)
    }
}

//@Preview(showBackground = true)
//@Composable
//fun HomeScreenPreview() {
//    HomeScreen(navController = NavController(LocalContext.current))
//}
