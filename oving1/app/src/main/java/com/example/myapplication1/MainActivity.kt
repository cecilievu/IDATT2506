package com.example.myapplication1

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.w("lifecycle", "onCreate")
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
        Log.d("lifecycle", "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("lifecycle", "onResume")

    }

    override fun onPause() {
        super.onPause()
        Log.d("lifecycle", "onPause")

    }

    override fun onStop() {
        super.onStop()
        Log.d("lifecycle", "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e("lifecycle", "onDestroy")
    }
}