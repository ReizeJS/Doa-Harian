package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.example.data.NetworkMonitor
import com.example.data.PreferencesRepository
import com.example.data.TimeMonitor
import com.example.ui.DoaHarianApp
import com.example.ui.DoaHarianViewModel

class MainActivity : ComponentActivity() {

  private val viewModel: DoaHarianViewModel by viewModels {
    DoaHarianViewModel.Factory(
      preferencesRepository = PreferencesRepository(applicationContext),
      networkMonitor = NetworkMonitor(applicationContext),
      timeMonitor = TimeMonitor(applicationContext),
    )
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent { DoaHarianApp(viewModel = viewModel) }
  }
}
