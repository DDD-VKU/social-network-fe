package vku.ddd.social_network_fe.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import vku.ddd.social_network_fe.data.api.StompManager
import vku.ddd.social_network_fe.ui.layouts.Navigation

class MainActivity : ComponentActivity() {
    private val stompManager: StompManager
        get() = (application as MyApplication).stompManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val navController = rememberNavController()
            Navigation(navController, null, true)
        }

        lifecycleScope.launch {
            // Wait until STOMP CONNECTED
            stompManager.connectionState
                .filter { it }
                .first()

            Log.d("MainActivity", "STOMP connected, now subscribing")
            stompManager.subscribe("/user/notification") { payload ->
                Log.d("MainActivity", "Received notification: $payload")
            }
        }
    }
}
