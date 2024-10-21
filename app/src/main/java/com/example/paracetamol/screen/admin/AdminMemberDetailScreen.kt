package com.example.paracetamol.screen.admin

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
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
import com.example.paracetamol.component.showToast
import com.example.paracetamol.model.AdminViewModel
import com.example.paracetamol.model.UserViewModel
import com.example.paracetamol.screen.CardTotal
import com.example.paracetamol.screen.DendaScrollContent
import com.example.paracetamol.screen.MemberScrollContentAdmin
import com.example.paracetamol.screen.Screen
import com.example.paracetamol.ui.theme.poppinsFamily
import com.google.gson.Gson
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardDendaUser(denda: DendaItem?, navController: NavController, groupRef: String, namaGroup: String, nama: String) {
    val context = LocalContext.current

    val adminViewModel: AdminViewModel = viewModel { AdminViewModel(context) }

    // Observe the LiveData and update the local variable
    val deleteDendaSuccess by adminViewModel.deleteDendaSuccess.observeAsState()
    deleteDendaSuccess?.let {
        showToast(context, "Fines deleted.")
    }

    val errorMessage by adminViewModel.errorMessage.observeAsState()
    errorMessage?.let {
        showToast(context, it)
    }

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
                    text = denda!!.desc,
                    fontSize = 11.sp,
                    color = Color(0xFF15104D),
                    textAlign = TextAlign.Start,
                )
                Text(
                    text = "Due: ${denda!!.hari}",
                    modifier = Modifier.padding(horizontal = 10.dp),
                    fontSize = 10.sp,
                    color = Color(0xFF15104D),
                    textAlign = TextAlign.Start,
                )
            }

            // Kolom Tengah
            Spacer(modifier = Modifier.weight(1f))

            // Kolom Kanan
            Column(
                modifier = Modifier.width(180.dp)
                    .padding(end = 0.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.End
            ) {
                val formattedTotal = NumberFormat.getCurrencyInstance(Locale("id", "ID")).format(denda!!.nominal)
                Text(
                    text = "Total: $formattedTotal",
                    fontSize = 10.sp,
                    color = Color(0xFF15104D),
                    textAlign = TextAlign.End,
                )
                // Button pakai status
                val buttonText = if (denda!!.is_paid) "Done" else "Pending"
                val buttonEnabled = false

                Button(
                    onClick = {  },
                    enabled = buttonEnabled,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 5.dp, bottom = 5.dp)
                        .height(30.dp),
                    border = BorderStroke(1.dp, if (denda!!.is_paid) Color.Black else Color(0xFF1F628E)), // Warna border berdasarkan status
                    colors = ButtonDefaults.elevatedButtonColors(
                        contentColor = Color.White
                    ),
                ) {
                    Text(
                        buttonText,
                        color = if (denda!!.is_paid) Color.Black else Color.DarkGray, // Warna teks tombol berdasarkan status
                        fontSize = 10.sp,
                        fontFamily = poppinsFamily,
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth().padding(top = 2.dp),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete",
                        tint = Color.Black,
                        modifier = Modifier
                            .size(20.dp) // Ukuran untuk ikon Edit
                            .clickable {
                                adminViewModel.deleteDenda(denda._id, groupRef)
                            }
                    )

                    Spacer(modifier = Modifier.width(15.dp))

                    Icon(
                        imageVector = Icons.Default.Visibility,
                        contentDescription = "See",
                        tint = Color.Black,
                        modifier = Modifier
                            .size(20.dp) // Ukuran untuk ikon Edit
                            .clickable { navController.navigate("${Screen.AdminPaidScreen.route}/$namaGroup/${denda._id}/$nama")}
                    )
                }

            }
        }
    }
}



@Composable
fun DendaUserScrollContent(id: String, groupID: String, innerPadding: PaddingValues, navController: NavController, groupRef: String, namaGroup: String, nama: String) {
    val context = LocalContext.current

    val userViewModel: UserViewModel = viewModel { UserViewModel(context) }

    var dendaDatas by rememberSaveable { mutableStateOf<List<DendaItem?>?>(null) }

    LaunchedEffect(userViewModel){
        userViewModel.getAllSelfDenda(true, id, groupID)
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
                CardDendaUser(denda = item, navController = navController, groupRef = groupRef, namaGroup = namaGroup, nama = nama)
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
fun AdminMemberDetailScreen(
    navController: NavController,
    id: String,
    name: String,
    namaGroup: String,
    groupID: String,
    refKey: String,
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
                text = "Fine Information",
                color = Color(0xFF15104D),
                modifier = Modifier.padding(top = 4.dp),
                fontSize = 12.sp
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = name,
                color = Color(0xFF15104D),
                modifier = Modifier.padding(top = 4.dp),
                fontSize = 12.sp
            )
        }
        DendaUserScrollContent(id = id, innerPadding = PaddingValues(16.dp), navController = navController, groupID = groupID, groupRef = refKey, namaGroup = namaGroup, nama = name)
    }
}


//@Composable
//@Preview(showBackground = true)
//fun AdminMemberDetailScreenPreview() {
//    val navController = rememberNavController()
//    AdminMemberDetailScreen(
//        "123",
//        "Joshua",
//        "MAXIMA 2023",
//        navController = navController
//    )
//}