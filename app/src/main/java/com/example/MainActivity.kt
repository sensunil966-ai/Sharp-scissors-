package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.example.data.db.BarbershopDatabase
import com.example.data.repository.BarbershopRepository
import com.example.ui.screens.MainScreen
import com.example.ui.theme.SharpScissorsTheme
import com.example.ui.viewmodel.BarbershopViewModel

class MainActivity : ComponentActivity() {

  private val viewModel: BarbershopViewModel by viewModels {
    val database = BarbershopDatabase.getDatabase(applicationContext)
    val repository = BarbershopRepository(database.appointmentDao())
    BarbershopViewModel.provideFactory(repository)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      SharpScissorsTheme {
        MainScreen(viewModel = viewModel)
      }
    }
  }
}

