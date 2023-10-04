package com.example.ciclodelavida

import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.ciclodelavida.ui.theme.CicloDeLaVidaTheme
import com.example.ciclodelavida.Greeting as Greeting1



class MainActivity : ComponentActivity() {

    val TAG = "Estado: "
    var isAppActive = false
    var startTime: Long = 0
    var totalTimeActive: Long = 0
    var minutos: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            totalTimeActive = savedInstanceState.getLong("totalTimeActive", 0)
        }
        setContent {
            CicloDeLaVidaTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting1("Android")
                }
            }
        }
    }

    override fun onStart(){
        super.onStart()
        Log.d(TAG,"Start")
    }

    override fun onResume(){
        super.onResume()
        if (!isAppActive) {
            isAppActive = true
            startTime = SystemClock.elapsedRealtime()
        }
        updateUI()
        Log.d(TAG,"Resume")
    }

    override fun onPause(){
        super.onPause()
        Log.d(TAG,"Pause")
        if (isAppActive) {
            isAppActive = false
            val elapsedTime = SystemClock.elapsedRealtime() - startTime
            totalTimeActive += elapsedTime
            Log.d(TAG, "Tiempo activo: ${totalTimeActive / 1000} s")
        }
    }

    override fun onDestroy(){
        super.onDestroy()
        Log.d(TAG,"Destroy")
    }

    fun updateUI(){
        minutos = ((totalTimeActive / 60000).toInt())
        Toast.makeText(this, "El tiempo activo hasta ahora es de: ${totalTimeActive / 60000}min ${(totalTimeActive / 1000) - (60 * minutos)}s", Toast.LENGTH_LONG).show()
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hola!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CicloDeLaVidaTheme {
        Greeting1("Android")
    }
}