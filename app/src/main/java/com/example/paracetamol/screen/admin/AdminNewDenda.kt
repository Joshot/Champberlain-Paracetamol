package com.example.paracetamol.screen.admin

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.paracetamol.api.data.group.response.Member
import com.example.paracetamol.component.DialogUI
import com.example.paracetamol.component.showToast
import com.example.paracetamol.model.AdminViewModel
import com.example.paracetamol.model.UserViewModel
import com.example.paracetamol.screen.AdminMemberListScreen
import com.example.paracetamol.screen.Screen
import com.example.paracetamol.ui.theme.poppinsFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminNewDendaScreen(
    navController: NavController,
    titleA: String,
    refKey: String,
    groupID: String
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var due by remember { mutableStateOf("") }
    var nominal by remember { mutableStateOf("") }
    var selectedMemberId by rememberSaveable { mutableStateOf("") }
    var selectedMemberName by remember { mutableStateOf("") }
//    var mTextFieldSize by remember { mutableStateOf(Size.Zero)}

    val context = LocalContext.current

    val adminViewModel: AdminViewModel = viewModel { AdminViewModel(context) }

    var members by rememberSaveable { mutableStateOf<List<Member?>?>(null) }

    LaunchedEffect(adminViewModel) {
        adminViewModel.getMembersGroupData(refKey)
    }

    // Observe the LiveData and update the local variable
    val membersData by adminViewModel.groupMembersData.observeAsState()
    membersData?.let{
        members = membersData
    }

    val createDendaSuccess by adminViewModel.createDendaSuccess.observeAsState()
    createDendaSuccess?.let {
        showToast(context, "Fines added to this user.")
    }

    val errorMessage by adminViewModel.errorMessage.observeAsState()
    errorMessage?.let {
        showToast(context, it)
    }

    // Dropdown
    var isExpanded by remember { mutableStateOf(false) }
    val icon = if (isExpanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown


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
                    text = "Add Fine",
                    color = Color(0xFF15104D),
                    modifier = Modifier.padding(top = 4.dp),
                    fontSize = 12.sp
                )
            }
            Column(
                modifier = Modifier
                    .padding(horizontal = 25.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                TextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Title") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 35.dp, bottom = 5.dp)
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

                TextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 5.dp, bottom = 5.dp)
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

                TextField(
                    value = due,
                    onValueChange = { due = it },
                    label = { Text("Due Date") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 5.dp, bottom = 5.dp)
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

                TextField(
                    value = nominal,
                    onValueChange = { nominal = it },
                    label = { Text("Total") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 5.dp, bottom = 5.dp)
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
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                )


//                OutlinedTextField(
//                    value = selectedMemberName,
//                    onValueChange = { selectedMemberName = it },
//                    colors = TextFieldDefaults.textFieldColors(
//                        containerColor = Color(0xFFF1F8FF),
//                        cursorColor = Color.Black,
//                        disabledLabelColor = Color(0xFFF1F8FF),
//                        focusedIndicatorColor = Color.Transparent,
//                        unfocusedIndicatorColor = Color.Transparent
//                    ),
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(top = 5.dp, bottom = 5.dp)
//                        .height(55.dp)
//                        .onGloballyPositioned { coordinates ->
//                            mTextFieldSize = coordinates.size.toSize()
//                        },
//                    label = { Text("Assign") },
//                    trailingIcon = {
//                        Icon(
//                            icon,
//                            "contentDescription",
//                            Modifier.clickable { mExpanded = !mExpanded }
//                        )
//                    }
//                )
//
//                DropdownMenu(
//                    expanded = mExpanded,
//                    onDismissRequest = { mExpanded = false },
//                    modifier = Modifier
//                        .width(with(LocalDensity.current) { mTextFieldSize.width.toDp() })
//                ) {
//                    members?.forEach { member ->
//                        if (member != null) {
//                            DropdownMenuItem(onClick = {
//                                selectedMemberName = member.nama
//                                selectedMemberId = member._id
//                                mExpanded = false
//                            }) {
//                                Text(text = member.nama)
//                            }
//                        }
//                    }
//                }

                ExposedDropdownMenuBox(
                    expanded = isExpanded, 
                    onExpandedChange = {isExpanded = it},
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 5.dp, bottom = 5.dp)
                        .height(55.dp),

                ) {
                    TextField(
                        value = selectedMemberName,
                        onValueChange = {},
                        readOnly = true,
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = Color.White,
                            cursorColor = Color.Black,
                            disabledLabelColor = Color(0xFFF1F8FF),
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        label = { Text("Assign") },
                        trailingIcon = {
                            Icon(
                                icon,
                                "contentDescription",
                                Modifier.clickable { isExpanded = !isExpanded }
                            )
                        },
                        modifier = Modifier.menuAnchor()
                            .fillMaxWidth(),
                    )
                    
                    ExposedDropdownMenu(
                        expanded = isExpanded,
                        onDismissRequest = { isExpanded = false }
                    ) {
                        members?.forEach { member ->
                            if(member != null)
                                DropdownMenuItem(
                                    text = { Text(text = member!!.nama) },
                                    onClick = {
                                        selectedMemberName = member!!.nama
                                        selectedMemberId = member!!._id
                                        isExpanded = false
                                    }
                                )
                        }

                    }
                }
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, bottom = 16.dp)
                        .height(55.dp),
                    border = BorderStroke(2.dp, Color(0xFF47A7FF)),
                    colors = ButtonDefaults.elevatedButtonColors(
                        contentColor = Color.White
                    ),
                    onClick = {
                        if (title.isNotBlank() && description.isNotBlank() && nominal.isNotBlank() && due.isNotBlank() && selectedMemberId.isNotBlank()){
                            if(nominal.toIntOrNull() == null || nominal.toIntOrNull() == 0)
                                showToast(context, "Please input valid total number.")
                            else
                                adminViewModel.createDenda(title, description, due, nominal.toInt(), selectedMemberId, groupID)
                        }else{
                            showToast(context, "Please fill all fields.")
                        }
                    }
                ) {
                    Text(
                        "Add Fine",
                        fontSize = 16.sp,
                        fontFamily = poppinsFamily,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Center,
                        color = Color(0xFF15104D),
                    )
                }
            }
        }
    }
}


//@Composable
//@Preview(showBackground = true)
//fun AdminNewDendaScreenPreview() {
//    val navController = rememberNavController()
//    AdminNewDendaScreen(
//        navController = navController,
//        titleA = "MAXIMA 2023",
//        refKey = "Reach New Potentials"
//    )
//}
//
