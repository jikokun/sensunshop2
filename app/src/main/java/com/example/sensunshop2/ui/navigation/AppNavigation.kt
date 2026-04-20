package com.example.sensunshop2.ui.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.sensunshop2.ui.screens.HomeScreen
import com.example.sensunshop2.ui.screens.OnboardingScreen
import com.example.sensunshop2.ui.screens.SplashScreen
import com.example.sensunshop2.ui.screens.LoginScreen
import com.example.sensunshop2.ui.screens.AdminDashboardScreen

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Onboarding : Screen("onboarding")
    object Home : Screen("home")
    object Login : Screen("login")
    object AdminDashboard : Screen("admin_dashboard")
}

@Composable
fun AppNavigation(
    isOnboardingCompleted: () -> Boolean,
    onCompleteOnboarding: () -> Unit
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route,
        enterTransition = { fadeIn(animationSpec = tween(300)) },
        exitTransition = { fadeOut(animationSpec = tween(300)) },
        popEnterTransition = { fadeIn(animationSpec = tween(300)) },
        popExitTransition = { fadeOut(animationSpec = tween(300)) }
    ) {
        composable(Screen.Splash.route) {
            SplashScreen(
                onNavigateToNext = {
                    val destination = if (isOnboardingCompleted()) Screen.Home.route else Screen.Onboarding.route
                    navController.navigate(destination) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                }
            )
        }
        composable(Screen.Onboarding.route) {
            OnboardingScreen(
                onFinishOnboarding = {
                    onCompleteOnboarding()
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Onboarding.route) { inclusive = true }
                    }
                }
            )
        }
        composable(Screen.Home.route) {
            HomeScreen(
                onNavigateToAdmin = {
                    navController.navigate(Screen.Login.route)
                }
            )
        }
        composable(Screen.Login.route) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Screen.AdminDashboard.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                onBack = {
                    navController.popBackStack()
                }
            )
        }
        composable(Screen.AdminDashboard.route) {
            AdminDashboardScreen(
                onLogout = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.AdminDashboard.route) { inclusive = true }
                    }
                }
            )
        }
    }
}
