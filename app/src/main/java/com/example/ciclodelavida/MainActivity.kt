package com.example.ciclodelavida

import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
                    color = Color.Gray
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
private fun Greeting(name: String, modifier: Modifier = Modifier) {
    var _number = remember {mutableStateOf(0)}

    Text(
        text = "Numero: ${_number.value}",
        color = Color.White
    )
    Column(Modifier.fillMaxWidth().absolutePadding(10.dp, 200.dp, 10.dp, 0.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Button(
            onClick = { _number.value = (0..10).random() },
            modifier = Modifier.padding(64.dp),

            )
        {
            Image(
                painter = painterResource(id = R.drawable.baseline_numbers_24),
                contentDescription = null,
                modifier = Modifier.padding(10.dp)
            )

            Text(
                text = "Generar numero",
                color = Color.White
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CicloDeLaVidaTheme {
        Greeting1("Android")
    }
}