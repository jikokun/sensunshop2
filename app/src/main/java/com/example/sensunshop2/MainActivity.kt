package com.example.sensunshop2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.lifecycleScope
import com.example.sensunshop2.data.UserPreferencesRepository
import com.example.sensunshop2.data.dataStore
import com.example.sensunshop2.ui.navigation.AppNavigation
import com.example.sensunshop2.ui.theme.SensunShop2Theme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        
        val userPreferencesRepository = UserPreferencesRepository(dataStore)
        
        setContent {
            val isOnboardingCompletedState = userPreferencesRepository.isOnboardingCompleted.collectAsState(initial = false)

            SensunShop2Theme {
                AppNavigation(
                    isOnboardingCompleted = { isOnboardingCompletedState.value },
                    onCompleteOnboarding = {
                        lifecycleScope.launch {
                            userPreferencesRepository.setOnboardingCompleted(true)
                        }
                    }
                )
            }
        }
    }
}
