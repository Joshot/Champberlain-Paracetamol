package com.example.paracetamol.nav_screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
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
import com.example.paracetamol.R
import com.example.paracetamol.api.data.profile.Profile
import com.example.paracetamol.component.showToast
import com.example.paracetamol.model.UserViewModel

/**
 * Composable function for rendering the profile screen.
 *
 * @param onLoggedOut Callback for when the user logs out.
 */
@Composable
fun ProfileScreen(onLoggedOut:() -> Unit) {
    val context = LocalContext.current

    // Create an instance of the UserViewModel
    val userViewModel: UserViewModel = viewModel { UserViewModel(context) }

    // Local variable to store profile data
    var profileData by rememberSaveable { mutableStateOf<Profile?>(null) }

    // LaunchedEffect to fetch profile data before building the UI
    LaunchedEffect(userViewModel) {
        // Fetch profile data
        userViewModel.getProfile()
    }

    // Observe the LiveData and update the local variable
    userViewModel.profileData.observeAsState().value?.let {
        profileData = it
    }

    val errorMessage by userViewModel.errorMessage.observeAsState()
    errorMessage?.let {
        showToast(context, it)
    }

    Box(
        modifier = Modifier.fillMaxSize()
            .background(Color(0xFFF2F1FA)),
        contentAlignment = Alignment.TopCenter,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF2F1FA)) //
                .padding(top = 35.dp, start = 16.dp, end = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Profile",
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.fillMaxWidth(),
                color = Color(0xFF15104D),
                fontSize = 25.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFF2F1FA)) //
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .background(Color(0xFFF2F1FA)) //
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
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = Color(0xFF15104D),
                        modifier = Modifier
                            .padding(top = 8.dp, bottom = 8.dp)
                            .padding(horizontal = 4.dp)
                    )

                    if(profileData != null){
                        ProfileItem(
                            profileData!!.nama,
                            profileData!!.nim,
                            profileData!!.prodi + " (" + profileData!!.angkatan + ")",
                            profileData!!.email
                        )
                    }

                }
            }

            Button(
                onClick = {
                    onLoggedOut.invoke()
                    userViewModel.logout()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(25.dp),
                border = BorderStroke(1.dp, Color.Black),
                colors = ButtonDefaults.elevatedButtonColors(
                    contentColor = Color.White
                ),
            ) {
                Text(
                    text = "Logout",
                    color = Color(0xFF15104D)
                )
            }
        }
    }
}

/**
 * Composable function for rendering a profile item.
 *
 * @param name Name of the user.
 * @param nim NIM of the user.
 * @param major Major of the user.
 * @param email Email of the user.
 */
@Composable
fun ProfileItem(name: String, nim: String, major: String, email: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .background(Color.White)
            .clip(RoundedCornerShape(8.dp))
            .border(1.dp, Color.Gray.copy(alpha = 0.8f), RoundedCornerShape(8.dp))
            .padding(10.dp)
    ) {
        Text(
            text = name,
            fontSize = 16.sp,
            color = Color(0xFF15104D),
            modifier = Modifier.padding(6.dp)
        )
        Divider(color = Color.Gray.copy(alpha = 0.8f), thickness = 1.dp)
        Text(
            text = nim,
            fontSize = 16.sp,
            color = Color(0xFF15104D),
            modifier = Modifier.padding(6.dp)
        )
        Divider(color = Color.Gray.copy(alpha = 0.8f), thickness = 1.dp)
        Text(
            text = major,
            fontSize = 16.sp,
            color = Color(0xFF15104D),
            modifier = Modifier.padding(6.dp)
        )
        Divider(color = Color.Gray.copy(alpha = 0.8f), thickness = 1.dp)
        Text(
            text = email,
            fontSize = 16.sp,
            color = Color(0xFF15104D),
            modifier = Modifier.padding(6.dp)
        )
    }
}

//@Composable
//@Preview(showBackground = true)
//fun ProfileScreenPreview() {
//    ProfileScreen(onLoggedOut = {})
//}
