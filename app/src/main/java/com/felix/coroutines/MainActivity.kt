package com.felix.coroutines

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.felix.coroutines.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private val RESULT_1 = "Result #1"

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener {
            CoroutineScope(IO).launch {
                fakeApiRequest()
            }
        }
    }

    private fun setNewText(input: String){
        val newText = binding.text.text.toString() + "\n$input"
        binding.text.text = newText
    }

    private suspend fun setTextOnMainThread(input: String){
        withContext(Main){
            setNewText(input)
        }
    }

    private suspend fun fakeApiRequest(){
        val result1 = getResult1FromApi()
        println("debug: $result1")
        setTextOnMainThread(result1)
    }

    private suspend fun getResult1FromApi(): String{
        logThread("getResult1FromApi")
        delay(1000)
        return RESULT_1
    }

    private fun logThread(methodName: String){
        println("debug: ${methodName}: ${Thread.currentThread().name}")
    }
}