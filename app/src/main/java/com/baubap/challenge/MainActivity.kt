package com.baubap.challenge

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.baubap.challenge.presentation.home.HomeScreen
import com.baubap.challenge.presentation.login.LoginScreen
import com.baubap.challenge.presentation.navigation.HomeKey
import com.baubap.challenge.presentation.navigation.LoginKey
import com.baubap.challenge.presentation.navigation.RegisterKey
import com.baubap.challenge.presentation.register.RegisterScreen
import com.baubap.challenge.ui.theme.BaubapChallengeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BaubapChallengeTheme {
                BaubapNavigation()
            }
        }
    }
}


@Composable
fun BaubapNavigation() {
    val backStack = rememberNavBackStack(LoginKey)

    val entryProvider = entryProvider {
        entry<LoginKey> {
            LoginScreen(
                onNavigateToRegister = { backStack.add(RegisterKey) },
                onNavigateToHome = { user -> backStack.add(HomeKey(user)) }
            )
        }

        entry<HomeKey> { key ->
            HomeScreen(
                user = key.user,
                onLogout = { backStack.removeLastOrNull() }
            )
        }

        entry<RegisterKey> { key ->
            RegisterScreen(
                onNavigateToLogin = { backStack.removeLastOrNull() },
                onNavigateToHome = { newUser -> backStack.add(HomeKey(newUser)) }
            )
        }
    }

    NavDisplay(
        backStack = backStack,
        entryProvider = entryProvider,
        onBack = {
            backStack.removeLastOrNull()
        }
    )
}
