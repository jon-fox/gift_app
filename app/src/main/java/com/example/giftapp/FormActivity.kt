package com.example.giftapp

import GiftDbHelper
import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class FormActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)

        val goBackButton = findViewById<Button>(R.id.goBackButton)
        Log.i("FormActivity", "Creating Gift form information")
        goBackButton.setOnClickListener {

            // put these into a database
            val inputGifteeName = findViewById<EditText>(R.id.inputGifteeName).text.toString()
            val inputGiftName = findViewById<EditText>(R.id.inputGiftName).text.toString()
            val inputGiftOccasion = findViewById<EditText>(R.id.inputGiftOccasion).text.toString()
            val inputGiftNotes = findViewById<EditText>(R.id.inputGiftNotes).text.toString()
            val inputGiftLink = findViewById<EditText>(R.id.inputGiftLink).text.toString()
            val inputGiftAttachments = findViewById<EditText>(R.id.inputGiftAttachments).text.toString()

            if (inputGifteeName.isNotBlank() && inputGiftName.isNotBlank()) {
                // All input fields are filled, perform logging
                Log.i("FormActivity", "FormActivity Giftee Name: $inputGifteeName")
                Log.i("FormActivity", "FormActivity Gift Name: $inputGiftName")
                Log.i("FormActivity", "FormActivity Giftee Occasion: $inputGiftOccasion")
                Log.i("FormActivity", "FormActivity Gift Notes: $inputGiftNotes")
                Log.i("FormActivity", "FormActivity Gift Link: $inputGiftLink")
                Log.i("FormActivity", "FormActivity Gift Attachments: $inputGiftAttachments")

                try {
                    // Gets the data repository in write mode
                    val dbHelper = GiftDbHelper(this)

                    val db = dbHelper.writableDatabase
                    val dateValue = intent.getStringExtra("date")

                    Log.i("FormActivity", "Value of date string ${dateValue}")

                    val values = ContentValues().apply {
                        put(GiftContract.GiftEntry.COLUMN_GIFTEE_DATE, dateValue)
                        put(GiftContract.GiftEntry.COLUMN_GIFTEE_NAME, inputGifteeName)
                        put(GiftContract.GiftEntry.COLUMN_GIFT_NAME, inputGiftName)
                        put(GiftContract.GiftEntry.COLUMN_GIFT_OCCASION, inputGiftOccasion)
                        put(GiftContract.GiftEntry.COLUMN_GIFT_NOTES, inputGiftNotes)
                        put(GiftContract.GiftEntry.COLUMN_GIFT_LINK, inputGiftLink)
                        put(GiftContract.GiftEntry.COLUMN_GIFT_ATTACHMENTS, inputGiftAttachments)
                    }

                    val newRowId = db?.insert(GiftContract.GiftEntry.TABLE_NAME, null, values)

                    Log.i("FormActivity", "FormActivity Giftee Name: $inputGifteeName")
                    Log.i("FormActivity", "FormActivity Gift Name: $inputGiftName")
                    Log.i("FormActivity", "FormActivity Giftee Occasion: $inputGiftOccasion")
                    Log.i("FormActivity", "FormActivity Gift Notes: $inputGiftNotes")
                    Log.i("FormActivity", "FormActivity Gift Link: $inputGiftLink")
                    Log.i("FormActivity", "FormActivity Gift Attachments: $inputGiftAttachments")

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
                    // Add the selected date as an extra to the result intent
                    intent.putExtra("date", dateValue)
                    startActivity(intent)

                    finish() // Call finish() to close the FormActivity and go back

                } catch (e: Exception) {
                    // Handle the exception
                    e.printStackTrace()
                }
            } else {
                // Show an error or prompt the user to fill in all fields
                Toast.makeText(this, "Please fill in Giftee and Name of Gift Field :)", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
