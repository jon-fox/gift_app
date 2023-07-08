package com.example.giftapp

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class GiftInfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.gift_view_background)

        val dateValue = intent.getStringExtra("date")
        val gifteeName = intent.getStringExtra("gifteeName", )
        val giftName = intent.getStringExtra("giftName")
        val giftOccasion = intent.getStringExtra("giftOccasion")
        val giftNotes = intent.getStringExtra("giftNotes")
        val giftLink = intent.getStringExtra("giftLink")
        val giftAttachments = intent.getStringExtra("giftAttachments")

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
}