package com.example.giftapp

import GiftDbHelper
import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class FormActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)

        val goBackButton = findViewById<Button>(R.id.goBackButton)
        Log.i("FormActivity", "Creating GIFT form information")
        goBackButton.setOnClickListener {

            // put these into a database
            val inputGifteeName = findViewById<EditText>(R.id.inputGifteeName).text.toString()
            val inputGiftName = findViewById<EditText>(R.id.inputGiftName).text.toString()
            val inputGiftLink = findViewById<EditText>(R.id.inputGiftLink).text.toString()

            if (inputGifteeName.isNotBlank() && inputGiftName.isNotBlank() && inputGiftLink.isNotBlank()) {
                // All input fields are filled, perform logging
                Log.i("FormActivity", "FormActivity Giftee Name: $inputGifteeName")
                Log.i("FormActivity", "FormActivity Gift Name: $inputGiftName")
                Log.i("FormActivity", "FormActivity Gift Link: $inputGiftLink")

                try {
                    // Gets the data repository in write mode
                    val dbHelper = GiftDbHelper(this)

                    val db = dbHelper.writableDatabase
                    val dateValue = intent.getStringExtra("date")

                    val values = ContentValues().apply {
                        put(GiftContract.GiftEntry.COLUMN_GIFTEE_DATE, dateValue)
                        put(GiftContract.GiftEntry.COLUMN_GIFTEE_NAME, inputGifteeName)
                        put(GiftContract.GiftEntry.COLUMN_GIFT_NAME, inputGiftName)
                        put(GiftContract.GiftEntry.COLUMN_GIFT_LINK, inputGiftLink)
                    }

                    val newRowId = db?.insert(GiftContract.GiftEntry.TABLE_NAME, null, values)

                    Log.i("FormActivity", "FormActivity Giftee Name: $inputGifteeName")
                    Log.i("FormActivity", "FormActivity Gift Name: $inputGiftName")
                    Log.i("FormActivity", "FormActivity Gift Link: $inputGiftLink")

                    // Handle the result of the insert operation if needed
                    if (newRowId != -1L) {
                        // Insert successful
                        System.out.println("SUCCESS!!!!")
                    } else {
                        System.out.println("BIG TIME ERROR!!!!")
                        throw Exception()
                    }


                    // Create an Intent to go back to the MainActivity
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish() // Call finish() to close the FormActivity and go back

                } catch (e: Exception) {
                    // Handle the exception
                    e.printStackTrace()
                }
            } else {
                // Show an error or prompt the user to fill in all fields
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
