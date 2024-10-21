package com.example.paracetamol.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Group
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.paracetamol.api.data.denda.response.DendaItem
import com.example.paracetamol.api.data.group.response.GroupItem
import com.example.paracetamol.component.showToast
import com.example.paracetamol.model.UserViewModel
import com.example.paracetamol.nav_screen.ArchiveScrollContent
import com.example.paracetamol.nav_screen.CardArchiveItem
import com.example.paracetamol.ui.theme.poppinsFamily
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Composable function for rendering the user group screen.
 *
 * @param navController NavController for navigating to other screens.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardDenda(titleA: String, denda: DendaItem?, navController: NavController) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 25.dp, vertical = 7.dp),
        border = BorderStroke(2.dp, Color.Black),
        shape = RoundedCornerShape(12.dp),
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
                    text = denda!!.title,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF15104D),
                    textAlign = TextAlign.Start,
                )
                Text(
                    modifier = Modifier.padding(horizontal = 10.dp),
                    text = denda.desc,
                    fontSize = 11.sp,
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
                    .padding(end = 0.dp), // Padding di kanan diatur menjadi 0.dp
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.End
            ) {
                val formattedTotal = NumberFormat.getCurrencyInstance(Locale("id", "ID")).format(denda!!.nominal)
                Text(
                    text = "Total: $formattedTotal",
                    fontSize = 10.sp,
                    color = Color.Black.copy(alpha = 0.7f),
                    textAlign = TextAlign.End,
                )
                // Button pakai status
                val buttonText = if (denda!!.is_paid) "Edit" else "Pay"
                val buttonEnabled = true

                Button(
                    onClick = {
                        navController.navigate("${Screen.PayScreen.route}/${denda!!._id}/${denda.title}")
                    },
                    enabled = buttonEnabled,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 5.dp, bottom = 5.dp)
                        .height(30.dp),
                    border = BorderStroke(1.dp, if (denda.is_paid) Color.Black else Color.Red), // Warna border berdasarkan status
                    colors = ButtonDefaults.elevatedButtonColors(
                        contentColor = Color.White
                    ),
                ) {
                    Text(
                        buttonText,
                        color = if (denda.is_paid) Color.Black else Color.DarkGray, // Warna teks tombol berdasarkan status
                        fontSize = 10.sp,
                        fontFamily = poppinsFamily,
                    )
                }
                Text(
                    text = "Due: ${denda!!.hari}",
                    fontSize = 10.sp,
                    color = Color.Black,
                    textAlign = TextAlign.End,
                )
            }
        }
    }
}



@Composable
fun DendaScrollContent(id: String, titleA: String, innerPadding: PaddingValues, navController: NavController) {
    val context = LocalContext.current

    val userViewModel: UserViewModel = viewModel { UserViewModel(context) }

    var dendaDatas by rememberSaveable { mutableStateOf<List<DendaItem?>?>(null) }

    LaunchedEffect(userViewModel){
        userViewModel.getAllSelfDenda(false, null, id)
    }

    // Observe the LiveData and update the local variable
    userViewModel.dendas.observeAsState().value?.let {
        dendaDatas = it
    }

    val errorMessage by userViewModel.errorMessage.observeAsState()
    errorMessage?.let {
        showToast(context, it)
    }

    LazyColumn(
        contentPadding = innerPadding,
        modifier = Modifier.fillMaxSize(),
    ) {
        if (dendaDatas != null) {
            items(dendaDatas!!) { item ->
                CardDenda(titleA = titleA, denda = item, navController = navController)
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
                        "No Fines",
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
fun UserGroupScreen(
    id: String,
    titleA: String,
    descriptionA: String,
    refKey: String,
    navController: NavController
) {
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
            Spacer(modifier = Modifier.weight(1f))
            IconButton(
                onClick = {
                    navController.navigate("${Screen.MemberGroupScreen.route}/$titleA/$refKey")
                },
                modifier = Modifier
                    .padding(end = 1.dp),
            ) {
            Icon(Icons.Default.Group, contentDescription = "Group")
        }
        }
        Text(
            text = titleA,
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
                text = descriptionA,
                color = Color(0xFF15104D),
                modifier = Modifier.padding(top = 4.dp),
                fontSize = 12.sp
            )
        }
        DendaScrollContent(id = id, titleA = titleA, innerPadding = PaddingValues(16.dp), navController = navController)
    }
}



//@Composable
//@Preview(showBackground = true)
//fun UserGroupScreenPreview() {
//    val navController = rememberNavController()
//    UserGroupScreen(
//        "12345",
//        "MAXIMA 2023",
//        "Explore The World Reach New Potentials",
//        navController = navController
//    )
//}