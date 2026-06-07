package com.assuncao.ufsc.urticaria

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.assuncao.ufsc.urticaria.ui.home.HomeScreen
import com.assuncao.ufsc.urticaria.ui.theme.UrticariaTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UrticariaTheme {
                HomeScreen()
            }
        }
    }
}
