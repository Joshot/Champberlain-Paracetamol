package com.example.paracetamol.nav_screen


import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
import com.example.paracetamol.ui.theme.poppinsFamily
import androidx.compose.ui.Alignment
import com.example.paracetamol.api.data.group.response.GroupItem
import com.example.paracetamol.component.showToast
import com.example.paracetamol.screen.Screen

/**
 * Composable function to display a card item representing an archive of a group.
 *
 * @param group The GroupItem to be displayed in the card.
 * @param navController The NavController for navigating to other screens.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardArchiveItem(group: GroupItem?, navController: NavController) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 25.dp, vertical = 7.dp),
        border = BorderStroke(2.dp, Color(0xFFEAEAEA)),
        shape = RoundedCornerShape(12.dp),
        color = Color(0xFFF7F7FC),
        onClick = {
            navController.navigate("${Screen.AdminViewMemberScreen.route}/${group!!._id}/${group!!.namaGroup}/${group!!.refKey}")
        }

    ) {
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
                color = Color(0xFF919196),
                textAlign = TextAlign.Start,
            )
            Text(
                text = group!!.desc,
                fontSize = 14.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp),
                color = Color(0xFFBEBEC3),
                textAlign = TextAlign.Start,
            )
        }
    }
}

/**
 * Composable function to display archived groups in a LazyColumn.
 *
 * @param innerPadding Padding values for the content.
 * @param navController NavController for navigating to other screens.
 */
@Composable
fun ArchiveScrollContent(innerPadding: PaddingValues, navController: NavController) {
    val context = LocalContext.current

    // ViewModel for user-related operations
    val userViewModel: UserViewModel = viewModel { UserViewModel(context) }

    // Mutable state to hold the list of archived groups
    var groupList by rememberSaveable { mutableStateOf<List<GroupItem?>?>(null) }

    // Fetch the joined groups when the composable is launched
    LaunchedEffect(userViewModel) {
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

    // Display the list of archived groups in a LazyColumn
    LazyColumn(
        contentPadding = innerPadding,
        modifier = Modifier.fillMaxSize(),
    ) {
        if (groupList != null && groupList?.any { it?.status == false } == true) {
            // Filter out archived groups
            val filteredGroupList = groupList!!.filter { it?.status == false }
            items(filteredGroupList) { item ->
                CardArchiveItem(group = item, navController = navController)
            }
        } else {
            // Display a message when there are no archived groups
            item {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    Text(
                        "No Group",
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(vertical = 280.dp),
                        fontSize = 14.sp,
                        fontFamily = poppinsFamily,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        color = Color(0xFF15104D),
                    )
                }

            }
        }
    }
}

/**
 * Composable function for the Archive screen.
 *
 * @param navController NavController for navigating to other screens.
 */
@Composable
fun ArchiveScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF2F1FA)) // Background color of the screen
    ) {
        // Title text for the Archive screen
        Text(
            "Archive",
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.fillMaxWidth()
                .padding(vertical = 30.dp),
            fontSize = 25.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        )
        // Display archived groups using ArchiveScrollContent composable
        ArchiveScrollContent(innerPadding = PaddingValues(16.dp), navController = navController)
    }
}

//@Preview(showBackground = true)
//@Composable
//fun ArchiveScreenPreview() {
//    ArchiveScreen(navController = NavController(LocalContext.current))
//}
