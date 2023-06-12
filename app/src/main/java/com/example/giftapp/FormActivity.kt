package com.example.giftapp

import GiftDbHelper
import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class FormActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)

        val goBackButton = findViewById<Button>(R.id.goBackButton)

        // put these into a database
        val inputGifteeName = findViewById<EditText>(R.id.inputGifteeName)
        val inputGiftName = findViewById<EditText>(R.id.inputGiftName)
        val inputGiftLink = findViewById<EditText>(R.id.inputGiftLink)

        // Gets the data repository in write mode
        val db = dbHelper.writableDatabase

        // Create a new map of values, where column names are the keys
        val values = ContentValues().apply {
            put(FeedEntry.COLUMN_NAME_TITLE, title)
            put(FeedEntry.COLUMN_NAME_SUBTITLE, subtitle)
        }

        // Insert the new row, returning the primary key value of the new row
        val newRowId = db?.insert(FeedEntry.TABLE_NAME, null, values)

        val gift = Gift(inputGifteeName.text.toString(), inputGiftName.text.toString(), inputGiftLink.text.toString())

        val values = ContentValues().apply {
            put(GiftContract.GiftEntry.COLUMN_GIFTEE_NAME, gift.gifteeName)
            put(GiftContract.GiftEntry.COLUMN_GIFT_NAME, gift.giftName)
            put(GiftContract.GiftEntry.COLUMN_GIFT_LINK, gift.giftLink)
        }

        db.insert(GiftContract.GiftEntry.TABLE_NAME, null, values)

        goBackButton.setOnClickListener {
            // Create an Intent to go back to the MainActivity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // Call finish() to close the SecondActivity and go back
        }
    }
}
