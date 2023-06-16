package com.example.giftapp

import GiftDbHelper
import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class FormActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)

        val goBackButton = findViewById<Button>(R.id.goBackButton)
        Log.i("FormActivity","Creating GIFT form information")

        // put these into a database
        val inputGifteeName = findViewById<EditText>(R.id.inputGifteeName)
        val inputGiftName = findViewById<EditText>(R.id.inputGiftName)
        val inputGiftLink = findViewById<EditText>(R.id.inputGiftLink)

        try {
            // Gets the data repository in write mode
            val dbHelper = GiftDbHelper(this)

            val db = dbHelper.writableDatabase
            val dateValue = intent.getStringExtra("date")

            val values = ContentValues().apply {
                put(GiftContract.GiftEntry.COLUMN_GIFTEE_DATE, dateValue)
                put(GiftContract.GiftEntry.COLUMN_GIFTEE_NAME, inputGifteeName.text.toString())
                put(GiftContract.GiftEntry.COLUMN_GIFT_NAME, inputGiftName.text.toString())
                put(GiftContract.GiftEntry.COLUMN_GIFT_LINK, inputGiftLink.text.toString())
            }

            val newRowId = db?.insert(GiftContract.GiftEntry.TABLE_NAME, null, values)

//            db.insert(GiftContract.GiftEntry.TABLE_NAME, null, values)

            // Handle the result of the insert operation if needed
            if (newRowId != -1L) {
                // Insert successful
                System.out.println("SUCCESS!!!!")
            } else {
                System.out.println("BIG TIME ERROR!!!!")
            }

        } catch (e: Exception) {
            // Handle the exception
            e.printStackTrace()
        }

        goBackButton.setOnClickListener {
            // Create an Intent to go back to the MainActivity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // Call finish() to close the SecondActivity and go back
        }
    }
}
