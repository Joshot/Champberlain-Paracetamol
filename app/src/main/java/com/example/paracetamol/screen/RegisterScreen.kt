package com.example.paracetamol.screen

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.paracetamol.component.DialogUI
import com.example.paracetamol.component.showToast
import com.example.paracetamol.model.UserViewModel
import com.example.paracetamol.ui.theme.poppinsFamily

/**
 * Composable function for rendering the register screen.
 *
 * @param navController NavController for navigating to other screens.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(navController: NavController) {
    var name by remember { mutableStateOf("") }
    var nim by remember { mutableStateOf("") }
    var prodi by remember { mutableStateOf("") }
    var angkatan by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var repassword by remember { mutableStateOf("") }
    var passwordVisibility by remember { mutableStateOf(false) }

    val context = LocalContext.current

    val userViewModel: UserViewModel = viewModel { UserViewModel(context) }

    val registerSuccess by userViewModel.registerSuccess.observeAsState()
    registerSuccess?.let { success ->
        if (success) {
            Log.d("RegisterScreen", "Register Success!")
            DialogUI(
                title = "Registration Successful",
                desc = "Your account has been successfully created."
            )
        }
    }

    val errorMessage by userViewModel.errorMessage.observeAsState()
    errorMessage?.let {
        showToast(context, it)
    }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF2F1FA)) //
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF2F1FA)) //
                .padding(horizontal = 35.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                "CHAMPBERLAIN",
                fontSize = 15.sp,
                fontFamily = poppinsFamily,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = Color(0xFF15104D),
            )
            Text("Register",
                modifier = Modifier
                    .padding(bottom = 2.dp),
                fontSize = 20.sp,
                fontFamily = poppinsFamily,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
                color = Color(0xFF15104D),
            )
            Text("Register your Champberlain account",
                modifier = Modifier
                    .padding(bottom = 15.dp),
                fontSize = 12.sp,
                fontFamily = poppinsFamily,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center,
                color = Color(0xFF15104D),
            )
            // TextFields for user input
            TextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Your Name") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 2.dp, bottom = 2.dp)
                    .height(55.dp),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.White,
                    cursorColor = Color(0xFF15104D),
                    disabledLabelColor = Color(0xFFF1F8FF),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                shape = RoundedCornerShape(8.dp),
                singleLine = true,
            )

            TextField(
                value = nim,
                onValueChange = { nim = it },
                label = { Text("NIM") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 5.dp, bottom = 2.dp)
                    .height(55.dp),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.White,
                    cursorColor = Color(0xFF15104D),
                    disabledLabelColor = Color(0xFFF1F8FF),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                shape = RoundedCornerShape(8.dp),
                singleLine = true,
            )

            TextField(
                value = prodi,
                onValueChange = { prodi = it },
                label = { Text("Major") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 5.dp, bottom = 2.dp)
                    .height(55.dp),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.White,
                    cursorColor = Color(0xFF15104D),
                    disabledLabelColor = Color(0xFFF1F8FF),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                shape = RoundedCornerShape(8.dp),
                singleLine = true,
            )

            TextField(
                value = angkatan,
                onValueChange = { angkatan = it },
                label = { Text("Class Of Year") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 5.dp, bottom = 2.dp)
                    .height(55.dp),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.White,
                    cursorColor = Color(0xFF15104D),
                    disabledLabelColor = Color(0xFFF1F8FF),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                shape = RoundedCornerShape(8.dp),
                singleLine = true,
            )

            TextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 5.dp, bottom = 2.dp)
                    .height(55.dp),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.White,
                    cursorColor = Color(0xFF15104D),
                    disabledLabelColor = Color(0xFFF1F8FF),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                shape = RoundedCornerShape(8.dp),
                singleLine = true,
            )

            var showPassword by remember { mutableStateOf(value = false) }

            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 5.dp, bottom = 2.dp)
                    .height(55.dp),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.White,
                    cursorColor = Color(0xFF15104D),
                    disabledLabelColor = Color(0xFFF1F8FF),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                shape = RoundedCornerShape(8.dp),
                singleLine = true,
                visualTransformation = if (showPassword) {
                    VisualTransformation.None
                } else {
                    PasswordVisualTransformation()
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                trailingIcon = {
                    if (showPassword) {
                        IconButton(onClick = { showPassword = false }) {
                            Icon(
                                imageVector = Icons.Filled.Visibility,
                                contentDescription = "hide_password"
                            )
                        }
                    } else {
                        IconButton(
                            onClick = { showPassword = true }) {
                            Icon(
                                imageVector = Icons.Filled.VisibilityOff,
                                contentDescription = "hide_password"
                            )
                        }
                    }
                },
            )

            TextField(
                value = repassword,
                onValueChange = { repassword = it },
                label = { Text("Re-enter Password") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 5.dp, bottom = 2.dp)
                    .height(55.dp),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.White,
                    cursorColor = Color(0xFF15104D),
                    disabledLabelColor = Color(0xFFF1F8FF),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                shape = RoundedCornerShape(8.dp),
                singleLine = true,
                visualTransformation = if (showPassword) {
                    VisualTransformation.None
                } else {
                    PasswordVisualTransformation()
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                trailingIcon = {
                    if (showPassword) {
                        IconButton(onClick = { showPassword = false }) {
                            Icon(
                                imageVector = Icons.Filled.Visibility,
                                contentDescription = "hide_password"
                            )
                        }
                    } else {
                        IconButton(
                            onClick = { showPassword = true }) {
                            Icon(
                                imageVector = Icons.Filled.VisibilityOff,
                                contentDescription = "hide_password"
                            )
                        }
                    }
                },
            )

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp, bottom = 8.dp)
                    .height(55.dp),
                border = BorderStroke(2.dp, Color.Black),
                colors = ButtonDefaults.elevatedButtonColors(
                    contentColor = Color.White
                ),
                onClick = {
                    val condition = name.isNotBlank() && nim.isNotBlank() && password.isNotBlank() && repassword.isNotBlank() && email.isNotBlank() && prodi.isNotBlank() && angkatan.isNotBlank()

                    if (condition){
                        if (password == repassword) userViewModel.register(name, nim, password, email, prodi, angkatan)
                        else showToast(context, "Password and Confirmation Password do not match")
                    } else {
                        showToast(context, "Please fill all required fields.")
                    }
                }
            ) {
                Text(
                    "Sign up",
                    fontSize = 14.sp,
                    fontFamily = poppinsFamily,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center,
                    color = Color(0xFF15104D),
                )
            }

            Row(
                modifier = Modifier,
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Already have an account?",
                    fontSize = 12.sp,
                    fontFamily = poppinsFamily,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Center,
                    color = Color(0xFF15104D),
                )
                TextButton(
                    modifier = Modifier,
                    onClick = { navController.navigate(Screen.LoginScreen.route) }
                ) {
                    Text(
                        "Sign In",
                        fontSize = 12.sp,
                        fontFamily = poppinsFamily,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF15104D),
                        textDecoration = TextDecoration.Underline
                    )
                }
            }
        }
    }
}

//@Composable
//@Preview(showBackground = true)
//fun RegisterScreenPreview() {
//    val navController = rememberNavController()
//    RegisterScreen(navController = navController)
//}
