package com.example.giftapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CalendarView
import android.widget.CalendarView.OnDateChangeListener
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    // on below line we are creating
    // variables for text view and calendar view
    lateinit var dateTV: TextView
    lateinit var calendarView: CalendarView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // initializing variables of
        // list view with their ids.
//        dateTV = findViewById(R.id.idTVDate)

        calendarView = findViewById(R.id.calendarView)

        // on below line we are adding set on
        // date change listener for calendar view.
        calendarView
            .setOnDateChangeListener(
                OnDateChangeListener { view, year, month, dayOfMonth ->
                    // In this Listener we are getting values
                    // such as year, month and day of month
                    // on below line we are creating a variable
                    // in which we are adding all the variables in it.
                    val Date = (dayOfMonth.toString() + "-"
                            + (month + 1) + "-" + year)

                    // set this date in TextView for Display
                    dateTV.setText(Date)
                })
        val button1 = findViewById<Button>(R.id.button1)
        button1.setOnClickListener {
            // Get the number from the edit text and calculate the square
            val number = findViewById<EditText>(R.id.editText1).text.toString().toInt()
            val square = number * number

            // Display the result in the result view
            val resultView = findViewById<TextView>(R.id.resultView)
            resultView.text = "The square of $number is $square"
        }

    }
}