package com.example.nfctest

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class NfcCardReaderActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cardreader)
    }

    fun openReadCityCardActivity() {
        startActivity(Intent(this, MainActivity::class.java))
    }
}