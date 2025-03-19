package com.example.shopping

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shopping.common.TEST_ID
import com.example.shopping.presentation.navigation.App
import com.example.shopping.presentation.screens.utils.NotificationPermissionRequest
import com.example.shopping.ui.theme.ShoppingAppTheme
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONObject
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            ShoppingAppTheme {


                 Modifier.fillMaxSize().background(color = Color.Black)

                MainScreen(firebaseAuth = firebaseAuth)
                //   HomeScreenUi()

            }
        }
    }




    @Composable
    fun MainScreen(firebaseAuth: FirebaseAuth) {
        val showSplash = remember { mutableStateOf(true) }

        // Trigger splash screen delay
        LaunchedEffect(Unit) {
            Handler(Looper.getMainLooper()).postDelayed({
                showSplash.value = false
            }, 3000)
        }

        if (showSplash.value) {
            SplashScreen()
        } else {
            NotificationPermissionRequest()
            App(firebaseAuth)
        }
    }

    @Composable
    fun SplashScreen() {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.Black), // Use your splash screen color
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Icon from the drawable folder
                Image(
                    painter = painterResource(id = R.drawable.images), // Replace with your drawable icon name
                    contentDescription = "App Icon",
                    modifier = Modifier.size(300.dp) // Set the desired icon size
                )
                Spacer(modifier = Modifier.height(16.dp)) // Space between icon and text

                // Text below the icon
                BasicText(
                    text = "Welcome to the Clothing Store",
                    style = MaterialTheme.typography.titleLarge.copy(
                        color = Color.White,
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center
                    )
                )
            }
        }
    }


}