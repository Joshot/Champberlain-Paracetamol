package com.example.paracetamol.screen.admin

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Group
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.paracetamol.R
import com.example.paracetamol.api.data.admin.response.AMember
import com.example.paracetamol.api.data.group.response.Member
import com.example.paracetamol.api.data.profile.Profile
import com.example.paracetamol.component.showToast
import com.example.paracetamol.model.AdminViewModel
import com.example.paracetamol.model.UserViewModel
import com.example.paracetamol.nav_screen.ProfileItem

@Composable
fun ProfileItem(aMemberData: AMember) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .background(Color.White)
            .clip(RoundedCornerShape(8.dp))
            .border(1.dp, Color.Gray.copy(alpha = 0.8f), RoundedCornerShape(8.dp))
            .padding(10.dp) // Padding untuk memberi jarak dari border ke dalam
    ) {
        Text(
            text = aMemberData.nama,
            fontSize = 16.sp,
            color = Color(0xFF15104D),
            modifier = Modifier.padding(6.dp)
        )
        Divider(color = Color.Gray.copy(alpha = 0.8f), thickness = 1.dp)
        Text(
            text = aMemberData.nim,
            fontSize = 16.sp,
            color = Color(0xFF15104D),
            modifier = Modifier.padding(6.dp)
        )
        Divider(color = Color.Gray.copy(alpha = 0.8f), thickness = 1.dp)
        Text(
            text = "${aMemberData.prodi} (${aMemberData.angkatan})",
            fontSize = 16.sp,
            color = Color(0xFF15104D),
            modifier = Modifier.padding(6.dp)
        )
        Divider(color = Color.Gray.copy(alpha = 0.8f), thickness = 1.dp)
        Text(
            text = aMemberData.email,
            fontSize = 16.sp,
            color = Color(0xFF15104D),
            modifier = Modifier.padding(6.dp)
        )
    }
}


@Composable
fun AdminProfileUserScreen(
    navController: NavController,
    id: String,
    isAdmin: Boolean,
    refKey: String
) {
    val context = LocalContext.current

    val adminViewModel: AdminViewModel = viewModel { AdminViewModel(context) }

    // Local variable to store profile data
    var aMemberData by rememberSaveable { mutableStateOf<AMember?>(null) }

    // LaunchedEffect to fetch profile data before building the UI
    LaunchedEffect(adminViewModel) {
        // Fetch profile data
        adminViewModel.getAGroupMember(id, refKey)
    }

    // Observe the LiveData and update the local variable
    adminViewModel.aMemberData.observeAsState().value?.let {
        aMemberData = it
    }

    val successKickMember by adminViewModel.successKickMember.observeAsState()
    successKickMember?.let {
        showToast(context, "Member kicked.")
    }

    val addAdminSuccess by adminViewModel.addAdminSuccess.observeAsState()
    addAdminSuccess?.let {
        showToast(context, "Member promoted to admin.")
    }

    val demoteAdminSuccess by adminViewModel.demoteAdminSuccess.observeAsState()
    demoteAdminSuccess?.let {
        showToast(context, "Admin demoted to member.")
    }

    val errorMessage by adminViewModel.errorMessage.observeAsState()
    errorMessage?.let {
        showToast(context, it)
    }

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
        }
        Text(
            text = if (isAdmin) "Admin" else "Member",
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.fillMaxWidth(),
            fontSize = 25.sp,
            color = Color(0xFF15104D),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_profile),
                    contentDescription = "Profile Image",
                    modifier = Modifier
                        .size(150.dp)
                        .align(Alignment.CenterHorizontally)
                )

                Text(
                    text = "Biodata",
                    color = Color(0xFF15104D),
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    modifier = Modifier
                        .padding(top = 8.dp, bottom = 8.dp)
                        .padding(horizontal = 4.dp)
                )

                if(aMemberData != null)
                    ProfileItem(aMemberData!!)
            }
        }

        when (isAdmin) {
            false -> {
                // Jika status adalah 0 (Member)
                Button(
                    onClick = {
                        if(aMemberData != null)
                            adminViewModel.addAdmin(aMemberData!!._id, refKey)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .padding(horizontal = 25.dp),
                    border = BorderStroke(1.dp, Color.Black),
                    colors = ButtonDefaults.elevatedButtonColors(
                        contentColor = Color.White
                    ),
                ) {
                    Text(
                        text = "Give Admin",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp),
                        color = Color(0xFF15104D),
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold
                    )
                }

                Button(
                    onClick = {
                        if(aMemberData != null)
                            adminViewModel.kickMember(refKey, aMemberData!!._id)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .padding(horizontal = 25.dp),
                    border = BorderStroke(1.dp, Color.Red),
                    colors = ButtonDefaults.elevatedButtonColors(
                        contentColor = Color.White
                    ),
                ) {
                    Text(
                        text = "Kick Member",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp),
                        color = Color(0xFF15104D),
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            true -> {
                // Jika status adalah 1 (Admin)
                Button(
                    onClick = {
                        if(aMemberData != null)
                            adminViewModel.demoteAdmin(aMemberData!!._id, refKey)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .padding(horizontal = 25.dp),
                    border = BorderStroke(1.dp, Color.Red),
                    colors = ButtonDefaults.elevatedButtonColors(
                        contentColor = Color.White
                    ),
                ) {
                    Text(
                        text = "Revoke Admin",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp),
                        color = Color(0xFF15104D),
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

    }
}

//@Composable
//@Preview(showBackground = true)
//fun AdminProfileUserScreenPreview() {
//    val navController = rememberNavController()
//    AdminProfileUserScreen(
//        "Joshua Hot Banget",
//        true,
//        navController = navController
//    )
//}