package com.example.paracetamol.screen

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Group
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.paracetamol.api.data.admin.response.MemberWithDenda
import com.example.paracetamol.api.data.denda.response.DendaItem
import com.example.paracetamol.api.data.group.response.Member
import com.example.paracetamol.component.showToast
import com.example.paracetamol.model.AdminViewModel
import com.example.paracetamol.model.UserViewModel
import com.example.paracetamol.ui.theme.poppinsFamily
import kotlinx.coroutines.flow.asStateFlow
import java.text.NumberFormat
import java.util.Locale


class GlobalViewModel : ViewModel() {
    var totalDendaGroup by mutableStateOf(0)
        private set

    fun updateTotalDendaGroup(value: Int) {
            totalDendaGroup = value
    }
}

@Composable
fun CardTotal(globalViewModel: GlobalViewModel) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 25.dp, vertical = 7.dp),
        border = BorderStroke(1.5f.dp, Color.Red),
        shape = RoundedCornerShape(10.dp),
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
                    text = "Total :",
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
                val formattedTotal = NumberFormat.getCurrencyInstance(Locale("id", "ID")).format(globalViewModel.totalDendaGroup)
                Text(
                    text = formattedTotal,
                    fontSize = 10.sp,
                    color = Color.Black.copy(alpha = 0.5f),
                    textAlign = TextAlign.End,
                )
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardMemberAdmin(
    member: MemberWithDenda?,
    navController: NavController,
    title: String,
    refKey: String,
    groupID: String,
    globalViewModel: GlobalViewModel
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 25.dp, vertical = 7.dp),
        border = BorderStroke(1.5f.dp, Color.Black),
        shape = RoundedCornerShape(10.dp),
        onClick = {
            navController.navigate("${Screen.AdminMemberDetailScreen.route}/${member!!.id}/${member!!.name}/$title/$groupID/$refKey")
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
                    text = member!!.name,
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
                val formattedTotal = NumberFormat.getCurrencyInstance(Locale("id", "ID")).format(member!!.nominal)
                Text(
                    text = "Total: $formattedTotal",
                    fontSize = 10.sp,
                    //jadi putih
                    color = Color.Red,
                    textAlign = TextAlign.End,
                )
            }
        }
    }
}


@Composable
fun MemberScrollContentAdmin(id: String, refKey: String, innerPadding: PaddingValues, navController: NavController,   globalViewModel: GlobalViewModel, title: String) {
    val context = LocalContext.current

    val adminViewModel: AdminViewModel = viewModel { AdminViewModel(context) }

    var members by rememberSaveable { mutableStateOf<List<MemberWithDenda?>?>(null) }


    LaunchedEffect(adminViewModel) {
        adminViewModel.getGroupMemberWithDenda(refKey)
    }

    // Observe the LiveData and update the local variable
    val groupMemberWithDenda by adminViewModel.groupMemberWithDenda.observeAsState()
    groupMemberWithDenda?.let{
        members = it
    }

    LaunchedEffect(groupMemberWithDenda) {
        groupMemberWithDenda?.let {
            globalViewModel.updateTotalDendaGroup(0) // Clear the total before recomputing
            it.forEach { item ->
                globalViewModel.updateTotalDendaGroup(globalViewModel.totalDendaGroup + item!!.nominal)
            }
        }
    }

    val errorMessage by adminViewModel.errorMessage.observeAsState()
    errorMessage?.let {
        showToast(context, it)
    }

    LazyColumn(
        contentPadding = innerPadding,
        modifier = Modifier.fillMaxSize(),
    ) {
        if (members != null) {
            items(members!!) { item ->
                CardMemberAdmin(member = item, navController = navController, title = title, refKey = refKey, groupID = id, globalViewModel = globalViewModel, )
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
fun AdminMemberListScreen(
    navController: NavController,
    id: String,
    refKey: String,
    title: String,
) {
    // Use viewModel to create or retrieve the GlobalViewModel
    val globalViewModel: GlobalViewModel = viewModel()

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF2F1FA)) //
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
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFF2F1FA)) //
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
                        navController.navigate("${Screen.AdminViewMemberScreen.route}/$id/$title/$refKey")
                    },
                    modifier = Modifier
                        .padding(end = 1.dp),
                ) {
                    Icon(Icons.Default.Group, contentDescription = "Group")
                }
            }
            Text(
                text = title,
                fontSize = 25.sp,
                color = Color(0xFF15104D),
                fontWeight = FontWeight.Bold
            )
            Row(
                modifier = Modifier
                    .background(Color(0xFFF2F1FA)) //
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

            CardTotal(globalViewModel)

            //Floating Button
            val fabSize = 56.dp
            val fabMargin = 16.dp

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.BottomEnd
            ) {

                MemberScrollContentAdmin(id = id, refKey = refKey, innerPadding = PaddingValues(16.dp), navController = navController, globalViewModel = globalViewModel, title = title)

                FloatingActionButton(
                    onClick = { navController.navigate("${Screen.AdminNewDendaScreen.route}/$title/$refKey/$id")},
                    modifier = Modifier
                        .padding(fabMargin)
                        .size(fabSize)
                        .shadow(8.dp, CircleShape)
                        .background(Color.White)
                        .align(Alignment.BottomEnd),
                    content = {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = null,
                                tint = Color.Black
                            )
                        }
                    }
                )
            }

        }

    }

}


//@Composable
//@Preview(showBackground = true)
//fun AdminMemberListScreenPreview() {
//    val navController = rememberNavController()
//    val globalViewModel = GlobalViewModel()
//    AdminMemberListScreen(
//        navController = navController,
//        "123",
//        "123",
//        "MAXIMA 2023",
//        globalViewModel = globalViewModel
//    )
//}