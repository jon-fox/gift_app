package com.example.giftapp

import GiftDbHelper
import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class GiftInfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.gift_view_background)

        val uniqueID = intent.getStringExtra("id") ?: ""
        val dateValue = intent.getStringExtra("date") ?: ""
        val gifteeName = intent.getStringExtra("gifteeName", ) ?: ""
        val giftName = intent.getStringExtra("giftName") ?: ""
        val giftOccasion = intent.getStringExtra("giftOccasion") ?: ""
        val giftNotes = intent.getStringExtra("giftNotes") ?: ""
        val giftLink = intent.getStringExtra("giftLink") ?: ""
        val giftAttachments = intent.getStringExtra("giftAttachments") ?: ""

        val dateView = findViewById<TextView>(R.id.dateView)
        val gifteeNameView = findViewById<TextView>(R.id.gifteeNameView)
        val giftNameView = findViewById<TextView>(R.id.giftNameView)
        val giftOccasionView = findViewById<TextView>(R.id.giftOccasionView)
        val giftNotesView = findViewById<TextView>(R.id.giftNotesView)
        val giftLinkView = findViewById<TextView>(R.id.giftLinkView)
        val giftAttachmentsView = findViewById<TextView>(R.id.giftAttachmentsView)

        dateView.text = dateValue
        gifteeNameView.text = gifteeName
        giftNameView.text = giftName
        giftOccasionView.text = giftOccasion
        giftNotesView.text = giftNotes
        giftLinkView.text = giftLink
        giftAttachmentsView.text = giftAttachments
    }

    @SuppressLint("WrongViewCast")
    fun onSaveChanges(view: View) {
        val dateView = findViewById<EditText>(R.id.dateView)
        val gifteeNameView = findViewById<EditText>(R.id.gifteeNameView)
        val giftNameView = findViewById<EditText>(R.id.giftNameView)
        val giftOccasionView = findViewById<EditText>(R.id.giftOccasionView)
        val giftNotesView = findViewById<EditText>(R.id.giftNotesView)
        val giftLinkView = findViewById<EditText>(R.id.giftLinkView)
        val giftAttachmentsView = findViewById<EditText>(R.id.giftAttachmentsView)

        val updatedDateValue = dateView.text.toString()
        val updatedGifteeName = gifteeNameView.text.toString()
        val updatedGiftName = giftNameView.text.toString()
        val updatedGiftOccasion = giftOccasionView.text.toString()
        val updatedGiftNotes = giftNotesView.text.toString()
        val updatedGiftLink = giftLinkView.text.toString()
        val updatedGiftAttachments = giftAttachmentsView.text.toString()

        try {
            // Gets the data repository in write mode
            val dbHelper = GiftDbHelper(this)

            val db = dbHelper.writableDatabase
            val uniqueID = intent.getIntExtra("id", -1)
            val dateValue = intent.getStringExtra("date")

            val values = ContentValues().apply {
                put(GiftContract.GiftEntry.COLUMN_ID, uniqueID)
                put(GiftContract.GiftEntry.COLUMN_GIFTEE_DATE, updatedDateValue)
                put(GiftContract.GiftEntry.COLUMN_GIFTEE_NAME, updatedGifteeName)
                put(GiftContract.GiftEntry.COLUMN_GIFT_NAME, updatedGiftName)
                put(GiftContract.GiftEntry.COLUMN_GIFT_OCCASION, updatedGiftOccasion)
                put(GiftContract.GiftEntry.COLUMN_GIFT_NOTES, updatedGiftNotes)
                put(GiftContract.GiftEntry.COLUMN_GIFT_LINK, updatedGiftLink)
                put(GiftContract.GiftEntry.COLUMN_GIFT_ATTACHMENTS, updatedGiftAttachments)
            }

            val updateRowId = db?.update(
                GiftContract.GiftEntry.TABLE_NAME,
                values,
                "${GiftContract.GiftEntry.COLUMN_ID} = ?",
                arrayOf(uniqueID.toString())
            )

            val mutableMap = mutableMapOf<String, String>()
            if (updatedDateValue != dateView.toString()) {
                // Date value has changed
                mutableMap["dateView"] = updatedDateValue
            }

            if (updatedGifteeName != gifteeNameView.toString()) {
                // Giftee name has changed
                mutableMap["gifteeNameView"] = updatedGifteeName
            }

            if (updatedGiftName != giftNameView.toString()) {
                // Gift name has changed
                mutableMap["giftNameView"] = updatedGiftName
            }

            if (updatedGiftOccasion != giftOccasionView.toString()) {
                // Gift occasion has changed
                mutableMap["giftOccasionView"] = updatedGiftOccasion
            }

            if (updatedGiftNotes != giftNotesView.toString()) {
                // Gift notes have changed
                mutableMap["giftNotesView"] = updatedGiftNotes
            }

            if (updatedGiftLink != giftLinkView.toString()) {
                // Gift link has changed
                mutableMap["giftLinkView"] = updatedGiftLink
            }

            if (updatedGiftAttachments != giftAttachmentsView.toString()) {
                // Gift attachments have changed
                mutableMap["giftAttachmentsView"] = updatedGiftAttachments
            }

            Log.i("FormActivity", "FormActivity Giftee Name: $gifteeNameView")
            Log.i("FormActivity", "FormActivity Gift Name: $giftNameView")
            Log.i("FormActivity", "FormActivity Giftee Occasion: $giftOccasionView")
            Log.i("FormActivity", "FormActivity Gift Notes: $giftNotesView")
            Log.i("FormActivity", "FormActivity Gift Link: $giftLinkView")
            Log.i("FormActivity", "FormActivity Gift Attachments: $giftAttachmentsView")

            // Handle the result of the insert operation if needed
            if (updateRowId != -1) {
                // Insert successful
                System.out.println("SUCCESS!!!!")
            } else {
                System.out.println("BIG TIME ERROR!!!!")
                throw Exception()
            }

            // Create an Intent to go back to the MainActivity
            val intent = Intent(this, MainActivity::class.java)
            // Add the selected date as an extra to the result intent
            intent.putExtra("id", uniqueID)
            intent.putExtra("date", dateValue)
            startActivity(intent)

            finish() // Call finish() to close the FormActivity and go back

        } catch (e: Exception) {
            // Handle the exception
            e.printStackTrace()
        }
            // Save the updated values or perform any other necessary actions
    }
}