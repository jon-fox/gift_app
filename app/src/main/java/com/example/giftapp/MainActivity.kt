package com.example.giftapp

import FirstFragment
import GiftContract
import GiftDbHelper
import SecondFragment
import ThirdFragment
import android.annotation.SuppressLint
import android.content.Intent
import android.database.Cursor
import android.icu.text.SimpleDateFormat
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.*
import android.widget.CalendarView.OnDateChangeListener
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.*


class MainActivity : AppCompatActivity() {
    // on below line we are creating
    // variables for text view and calendar view
    lateinit var dateTV: TextView
    lateinit var calendarView: CalendarView
//    private lateinit var editText: EditText

    companion object {
        @RequiresApi(Build.VERSION_CODES.N)
        var dateString: String = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format(Date())
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // initializing variables of
        // list view with their ids.
        dateTV = findViewById(R.id.idTVDate)

        val firstFragment=FirstFragment()
        val secondFragment=SecondFragment()
        val thirdFragment=ThirdFragment()

        setCurrentFragment(firstFragment)

        calendarView = findViewById(R.id.calendarView)
//        editText = findViewById(R.id.editText)
        Log.i("MainActivity","Fetching by Current Date for Gifts!")
        fetchDataFromDatabase()
        // on below line we are adding set on
        // date change listener for calendar view.
        calendarView
            .setOnDateChangeListener(
                OnDateChangeListener { view, year, month, dayOfMonth ->
                    // In this Listener we are getting values
                    // such as year, month and day of month
                    // on below line we are creating a variable
                    // in which we are adding all the variables in it.
                    val Date = "${month + 1}/$dayOfMonth/$year"
                    dateString = Date
                    Log.i("MainActivity","Fetching by Current Date for Gifts!")
                    fetchDataFromDatabase()
                    // set this date in TextView for Display
//                    dateTV.setText(Date)
                })

        val plusImageView: Button = findViewById(R.id.plusImageView)
        plusImageView.setOnClickListener {
//            val currentDate = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())
//            dateTV.text = currentDate
            // Create an Intent to launch the formActivity
            val intent = Intent(this, FormActivity::class.java)
            Log.i("MainActivity", "Value of date string $dateString")
            intent.putExtra("date", dateString)
            startActivity(intent)
        }
        //        val plusImageView: Button = findViewById(R.id.plusImageView)
        // Apply additional customizations to the plusImageView if required
        //        val bottomNavigationView = (R.id.bottomNavigationView)
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottomNavigationView)


        bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.home->setCurrentFragment(firstFragment)
                R.id.person->setCurrentFragment(secondFragment)
                R.id.settings->setCurrentFragment(thirdFragment)
            }
            true
        }
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
    }
//    private fun showToast(message: String) {
//        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
//    }
    private fun setCurrentFragment(fragment: Fragment)=
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment,fragment)
            commit()
        }
    @RequiresApi(Build.VERSION_CODES.N)
    @SuppressLint("Range")
    private fun fetchDataFromDatabase() {
        Log.i("MainActivity", "Fetching from database")
        val linearLayout = findViewById<LinearLayout>(R.id.wish_list)
        linearLayout.removeAllViews() // Clear the existing views

        val dbHelper = GiftDbHelper(this)
        val db = dbHelper.readableDatabase

        if (dateString?.isEmpty() == true) {
            Log.i("MainActivity", "Date string is empty")
            // Handle the case when dateString is empty.
            // You can display an error message or perform any required action here.
            return
        }

        val projection = arrayOf(
            GiftContract.GiftEntry.COLUMN_GIFTEE_NAME,
            GiftContract.GiftEntry.COLUMN_GIFT_NAME,
            GiftContract.GiftEntry.COLUMN_GIFT_LINK
        )

        val selection = "${GiftContract.GiftEntry.COLUMN_GIFTEE_DATE} = ?"
        val selectionArgs = arrayOf(dateString)

        val cursor = db.query(
            GiftContract.GiftEntry.TABLE_NAME,
            projection,
            selection,
            selectionArgs,
            null,
            null,
            null
        )

        with(cursor) {
            while (moveToNext()) {
                val gifteeName =
                    cursor.getString(cursor.getColumnIndexOrThrow(GiftContract.GiftEntry.COLUMN_GIFTEE_NAME))
                val giftName =
                    cursor.getString(cursor.getColumnIndexOrThrow(GiftContract.GiftEntry.COLUMN_GIFT_NAME))
                val giftLink =
                    cursor.getString(cursor.getColumnIndexOrThrow(GiftContract.GiftEntry.COLUMN_GIFT_LINK))

                // Create TextViews and set their text to the retrieved values
                val gifteeNameTextView = TextView(this@MainActivity)
                gifteeNameTextView.text = "Giftee Name: $gifteeName"

                val giftNameTextView = TextView(this@MainActivity)
                giftNameTextView.text = "Gift Name: $giftName"

                val giftLinkTextView = TextView(this@MainActivity)
                giftLinkTextView.text = "Gift Link: $giftLink"

                Log.i("MainActivity", "Giftee Name: $gifteeName")
                Log.i("MainActivity", "Gift Name: $giftName")
                Log.i("MainActivity", "Gift Link: $giftLink")

                // Add the TextViews to your LinearLayout
                linearLayout.addView(gifteeNameTextView)
                linearLayout.addView(giftNameTextView)
                linearLayout.addView(giftLinkTextView)

            }
            cursor.close()
        }
    }
}

