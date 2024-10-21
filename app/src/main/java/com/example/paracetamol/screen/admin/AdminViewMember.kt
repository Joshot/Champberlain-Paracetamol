package com.example.paracetamol.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Group
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.SwitchColors
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.paracetamol.api.data.admin.response.MemberDataAdmin
import com.example.paracetamol.api.data.group.response.GroupItem
import com.example.paracetamol.component.showToast
import com.example.paracetamol.model.AdminViewModel
import com.example.paracetamol.model.UserViewModel
import com.example.paracetamol.nav_screen.ArchiveScrollContent
import com.example.paracetamol.nav_screen.CardArchiveItem
import com.example.paracetamol.ui.theme.poppinsFamily
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardMemberAdmin(member: MemberDataAdmin?, navController: NavController, refKey: String) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 25.dp, vertical = 7.dp),
        border = BorderStroke(1.5f.dp, Color.Black),
        shape = RoundedCornerShape(10.dp),
        onClick = {
            navController.navigate("${Screen.AdminProfileUserScreen.route}/${member!!._id}/${member!!.is_admin.toString()}/$refKey")
        }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Kolom Kiri
            Column(
                modifier = Modifier.width(180.dp), // Lebar tetap untuk kolom kiri
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    modifier = Modifier.padding(horizontal = 10.dp),
                    text = member!!.nama,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    textAlign = TextAlign.Start,
                )
            }

            // Kolom Tengah
            Spacer(modifier = Modifier.weight(1f))

            // Kolom Kanan
            Column(
                modifier = Modifier
                    .width(180.dp)
                    .padding(end = 0.dp)
                    .padding(8.dp), // Padding di kanan diatur menjadi 0.dp
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.End
            ) {
                // Text pakai status
                val memberOrAdmin = if (member!!.is_admin) "Admin" else ""

                Text(
                    text = memberOrAdmin,
                    fontSize = 10.sp,
                    color = Color.Black,
                    textAlign = TextAlign.End,
                )
            }
        }
    }
}


@Composable
fun MemberViewScrollContentAdmin(adminMembers: List<MemberDataAdmin?>?, innerPadding: PaddingValues, navController: NavController, refKey: String) {
    LazyColumn(
        contentPadding = innerPadding,
    ) {
        if (adminMembers!!.isNotEmpty()) {
            val sortedAdminMembers = adminMembers.sortedByDescending { it?.is_admin == true }

            items(sortedAdminMembers!!) { item ->
                CardMemberAdmin(member = item, navController = navController, refKey = refKey)
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
                        "No Members",
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


@Composable
fun WaitingMembersList(waitingMembers: List<MemberDataAdmin?>?, groupRef: String) {
    val context = LocalContext.current

    val adminViewModel: AdminViewModel = viewModel { AdminViewModel (context) }

    val successArchive by adminViewModel.successArchiveGroup.observeAsState()
    successArchive?.let { success ->
        if(success) showToast(context, "Archive group success.")
    }

    val successAcceptMember by adminViewModel.successAcceptMember.observeAsState()
    successAcceptMember?.let { success ->
        if(success) showToast(context, "Member accepted.")
    }

    val successKickMember by adminViewModel.successKickMember.observeAsState()
    successKickMember?.let { success ->
        if(success) showToast(context, "Member rejected.")
    }

    val errorMessage by adminViewModel.errorMessage.observeAsState()
    errorMessage?.let {
        showToast(context, it)
    }

    Column {
        waitingMembers!!.forEach { member ->
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 40.dp, vertical = 4.dp),
                border = BorderStroke(1.5f.dp, Color(0xFF1F628E)),
                shape = RoundedCornerShape(10.dp),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = member!!.nama,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        IconButton(
                            onClick = {
                                adminViewModel.kickMember(groupRef, member._id)
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Close",
                                tint = Color.Black
                            )
                        }

                        // Accept Icon
                        IconButton(
                            onClick = {
                                adminViewModel.acceptMember(groupRef, member._id)
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = "Checkmark",
                                tint = Color.Black
                            )
                        }
                    }
                }
            }
            Spacer(Modifier.height(8.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwitchButton(
    checked: Boolean,
    onCheckedChange: ((Boolean) -> Unit)?,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    colors: SwitchColors = SwitchDefaults.colors()
) {
    androidx.compose.material3.Switch(
        checked = !checked,
        onCheckedChange = onCheckedChange,
        modifier = modifier,
        enabled = enabled,
        interactionSource = interactionSource,
        colors = colors
    )
}

@Composable
fun ArchiveCard(
    title: String,
    isArchived: Boolean,
    onArchiveToggle: (Boolean) -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 25.dp, vertical = 10.dp),
        border = BorderStroke(1.5f.dp, Color.Red),
        shape = RoundedCornerShape(10.dp),
        color = Color.White
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = "Archive?",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Text(
                    text = title,
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }

            SwitchButton(
                checked = isArchived,
                onCheckedChange = onArchiveToggle,
                modifier = Modifier.size(50.dp)
            )
        }
    }
}

@Composable
fun AdminViewMemberScreen(
    navController: NavController,
    id: String,
    namaGroup: String,
    refKey: String,
) {
    val context = LocalContext.current

    val adminViewModel: AdminViewModel = viewModel { AdminViewModel (context) }

    var allMembersDataAdmin by rememberSaveable {
        mutableStateOf<List<MemberDataAdmin?>?>(null)
    }

    var groupData by rememberSaveable { mutableStateOf<GroupItem?>(null) }

    LaunchedEffect(adminViewModel) {
        adminViewModel.getAllMembersGroupDataAdmin(refKey)
        adminViewModel.getAGroup(refKey)
    }

    // Observe the LiveData and update the local variable
    adminViewModel.allMembersData.observeAsState().value?.let{
        allMembersDataAdmin =  it
    }

    adminViewModel.groupData.observeAsState().value?.let {
        groupData = it
    }

    val successArchive by adminViewModel.successArchiveGroup.observeAsState()
    successArchive?.let { success ->
        if(success) showToast(context, "Archive group success.")
    }

    val successReactivate by adminViewModel.successReactivateGroup.observeAsState()
    successReactivate?.let { success ->
        if(success) showToast(context, "Unarchive group success.")
    }

    val errorMessage by adminViewModel.errorMessage.observeAsState()
    errorMessage?.let {
        showToast(context, it)
    }

    val adminMembers = allMembersDataAdmin?.filter { it?.is_allowed == true } ?: emptyList()
    val waitingMembers = allMembersDataAdmin?.filter { it?.is_allowed == false } ?: emptyList()


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF2F1FA)) //
//            .padding(horizontal = 16.dp)
            .padding(top = 18.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(
                onClick = { navController.navigateUp() }, // Back
                modifier = Modifier
                    .padding(start = 1.dp),
            ) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            }
//            Spacer(modifier = Modifier.weight(1f))
//            IconButton(
//                onClick = { navController.navigateUp() },
//                modifier = Modifier
//                    .padding(end = 1.dp),
//            ) {
//                Icon(Icons.Default.Group, contentDescription = "Group")
//            }
        }
        Text(
            text = namaGroup,
            fontSize = 25.sp,
            color = Color(0xFF15104D),
            fontWeight = FontWeight.Bold
        )
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = refKey,
                color = Color(0xFF15104D),
                modifier = Modifier.padding(top = 4.dp),
                fontSize = 12.sp
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 5.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Admin",
                color = Color(0xFF15104D),
                modifier = Modifier.padding(top = 4.dp),
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp
            )
        }

        MemberViewScrollContentAdmin(adminMembers = adminMembers, innerPadding = PaddingValues(16.dp), navController = navController, refKey = refKey)

        //garis
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.5f.dp)
                .padding(horizontal = 16.dp),
            color = Color(0xFF15104D),
        )

        Text(
            "Waiting Member",
            fontWeight = FontWeight.Bold,
            color = Color(0xFF15104D),
            modifier = Modifier.padding(vertical = 4.dp)

        )

        WaitingMembersList(waitingMembers = waitingMembers, groupRef = refKey)

        //garis
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.5f.dp)
                .padding(horizontal = 16.dp),
            color = Color(0xFF15104D),
        )

        if(groupData != null)
            ArchiveCard(
                title = namaGroup,
                isArchived = groupData!!.status,
                onArchiveToggle = {
                    if(groupData!!.status)
                        adminViewModel.archiveGroup(refKey)
                    else
                        adminViewModel.reactivateGroup(refKey)
                }
            )

    }
}


//@Composable
//@Preview(showBackground = true)
//fun AdminViewMemberScreenPreview() {
//    val navController = rememberNavController()
//    AdminViewMemberScreen(
//        navController = navController,
//        "1234",
//        "MAXIMA 2023",
//        "Explore The World Reach New Potentials",
//    )
//}