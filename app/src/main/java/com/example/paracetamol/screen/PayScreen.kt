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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Group
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.paracetamol.api.data.denda.response.DendaItem
import com.example.paracetamol.component.showToast
import com.example.paracetamol.model.UserViewModel
import com.example.paracetamol.ui.theme.poppinsFamily

/**
 * Composable function for rendering the pay screen.
 * @param navController NavController for navigating to other screens.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PayScreen(
    titleA: String,
    id: String,
    navController: NavController
) {
    val context = LocalContext.current;

    val userViewModel: UserViewModel = viewModel { UserViewModel(context) }

    var proofLink by remember { mutableStateOf("") }


    // Observe the LiveData and update the local variable
    userViewModel.payDendaSuccess.observeAsState().value?.let {success ->
        if(success){
            showToast(context, "Proof successfuly sent.")
        }
    }

    val errorMessage by userViewModel.errorMessage.observeAsState()
    errorMessage?.let {
        showToast(context, it)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF2F1FA)) //
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
                text = "Send Proof",
                color = Color(0xFF15104D),
                modifier = Modifier.padding(top = 4.dp),
                fontSize = 12.sp
            )
        }
        TextField(
            value = proofLink,
            onValueChange = { proofLink = it },
            label = { Text("Proof Link") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 35.dp, bottom = 5.dp)
                .padding(horizontal = 35.dp)
                .height(55.dp),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.White,
                cursorColor = Color.Black,
                disabledLabelColor = Color(0xFFF1F8FF),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(8.dp),
            singleLine = true,
        )

        Button(
            onClick = {
                if(proofLink.isNotBlank()){
                    userViewModel.payDenda(id, proofLink)
                }else {
                    showToast(context, "Fill proof link")
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 25.dp, bottom = 16.dp)
                .padding(horizontal = 35.dp)
                .height(55.dp),
            border = BorderStroke(2.dp, Color.Black),
            colors = ButtonDefaults.elevatedButtonColors(
                contentColor = Color.White
            ),
        ) {
            Text(
                "Send",
                color = Color(0xFF15104D),
                fontSize = 16.sp,
                fontFamily = poppinsFamily,
            )
        }
    }
}



//@Composable
//@Preview(showBackground = true)
//fun PayScreenPreview() {
//    val navController = rememberNavController()
//    PayScreen(
//        "STARLIGHT 2023",
//        "12345",
//        navController = navController
//    )
//}