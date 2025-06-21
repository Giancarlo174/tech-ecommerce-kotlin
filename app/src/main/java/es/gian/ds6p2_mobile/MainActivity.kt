package es.gian.ds6p2_mobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import es.gian.ds6p2_mobile.ui.theme.ProductsScreen
import es.gian.ds6p2_mobile.ui.theme.Ds6p2mobileTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Ds6p2mobileTheme {
                ProductsScreen()
            }
        }
    }
}