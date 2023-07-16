package com.example.giftapp

import FirstFragment
import GiftContract
import GiftDbHelper
import SecondFragment
import ThirdFragment
import android.annotation.SuppressLint
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Gravity
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

        val receivedIntent = intent
        if (receivedIntent.hasExtra("id")) {
            Log.i("MainActivity", "Received uniqueID from forms ${receivedIntent.getStringExtra("id")}")
            dateString = receivedIntent.getStringExtra("date") ?: dateString
            fetchGifteeButtonsInLayout(receivedIntent.getStringExtra("id"))
        }

        // initializing variables of
        // list view with their ids.
        dateTV = findViewById(R.id.idTVDate)

        val firstFragment = FirstFragment()
        val secondFragment = SecondFragment()
        val thirdFragment = ThirdFragment()

        setCurrentFragment(firstFragment)

        calendarView = findViewById(R.id.calendarView)
//        editText = findViewById(R.id.editText)
        Log.i("MainActivity", "Fetching by Current Date for Gifts!")
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
                    Log.i("MainActivity", "Fetching by Current Date for Gifts!")
                    Log.i("MainActivity", "Value of date string $dateString")
                    fetchGifteeButtonsInLayout()
                    // set this date in TextView for Display
//                    dateTV.setText(Date)
                })

        if (receivedIntent.hasExtra("date")) {
            calendarView.post {
                calendarView.setDate(parseDateString(dateString))
            }
        }

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
            when (it.itemId) {
                R.id.home -> setCurrentFragment(firstFragment)
                R.id.person -> setCurrentFragment(secondFragment)
                R.id.settings -> setCurrentFragment(thirdFragment)
            }
            true
        }
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
    }

    private fun fetchGiftsByDate() {
        TODO("Not yet implemented")
    }
//    private fun showToast(message: String) {
//        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
//    }

    @RequiresApi(Build.VERSION_CODES.N)
    @SuppressLint("Range")
    private fun updateDatabase(uniqueID: String? = null) {

    }

    @RequiresApi(Build.VERSION_CODES.N)
    @SuppressLint("Range")
    private fun fetchGifteeButtonsInLayout(uniqueID: String? = null) {
        Log.i("MainActivity", "UniqueID when entering the setGifteeButton method: $uniqueID")
        Log.i("MainActivity", "setGifteeButton")
        val linearLayout = findViewById<LinearLayout>(R.id.wish_list)
        linearLayout.removeAllViews() // Clear the existing views

        val dbHelper = GiftDbHelper(this)
        val db = dbHelper.readableDatabase

        val projection = arrayOf(
            GiftContract.GiftEntry.COLUMN_ID,
            GiftContract.GiftEntry.COLUMN_GIFTEE_NAME,
            GiftContract.GiftEntry.COLUMN_GIFT_NAME,
            GiftContract.GiftEntry.COLUMN_GIFT_OCCASION,
            GiftContract.GiftEntry.COLUMN_GIFT_NOTES,
            GiftContract.GiftEntry.COLUMN_GIFT_LINK,
            GiftContract.GiftEntry.COLUMN_GIFT_ATTACHMENTS
        )
        Log.i("MainActivity", "Value of date string $dateString")
        Log.i("MainActivity", "Value of uniqueID $uniqueID")

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

//        val inflater = LayoutInflater.from(this)

        Log.i("MainActivity", "Value of date string $dateString")
        with(cursor) {
            while (moveToNext()) {
                val uniqueID =
                    cursor.getString(cursor.getColumnIndexOrThrow(GiftContract.GiftEntry.COLUMN_ID))
                val gifteeName =
                    cursor.getString(cursor.getColumnIndexOrThrow(GiftContract.GiftEntry.COLUMN_GIFTEE_NAME))
                val giftName =
                    cursor.getString(cursor.getColumnIndexOrThrow(GiftContract.GiftEntry.COLUMN_GIFT_NAME))
                val giftOccasion =
                    cursor.getString(cursor.getColumnIndexOrThrow(GiftContract.GiftEntry.COLUMN_GIFT_OCCASION))
                val giftNotes =
                    cursor.getString(cursor.getColumnIndexOrThrow(GiftContract.GiftEntry.COLUMN_GIFT_NOTES))
                val giftLink =
                    cursor.getString(cursor.getColumnIndexOrThrow(GiftContract.GiftEntry.COLUMN_GIFT_LINK))
                val giftAttachments =
                    cursor.getString(cursor.getColumnIndexOrThrow(GiftContract.GiftEntry.COLUMN_GIFT_ATTACHMENTS))

                // Create TextViews and set their text to the retrieved values
//                val gifteeButton = Button(this@MainActivity, null, R.id.gifteeButton)
                val gifteeButton = Button(this@MainActivity)
//                val gifteeButton = inflater.inflate(R.layout.giftee_button, linearLayout, false) as Button
                gifteeButton.text = "Giftee: $gifteeName"
                gifteeButton.tag = uniqueID
                Log.i("MainActivity", "uniqueID from mainactivty button tag: ${gifteeButton.tag}")
                gifteeButton.setBackgroundResource(R.drawable.giftee_button)

                val layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
//                layoutParams.weight = 0.5f // Set the weight to occupy half the width of the screen
                layoutParams.gravity = Gravity.CENTER_HORIZONTAL // Center the button horizontally
                // taking the screen width and adjusting the button length to fit
                val displayMetrics = resources.displayMetrics
                val screenWidth = displayMetrics.widthPixels
                val buttonWidth = (screenWidth * 0.75).toInt()
                layoutParams.width = buttonWidth

                //margins between buttons
                val margin = 10
                layoutParams.setMargins(0, 5, margin, 5)

                gifteeButton.layoutParams = layoutParams

                Log.i("MainActivity", "UniqueID before being passed to giftinfoactivity: $uniqueID")
                Log.i("MainActivity", "Giftee Name: $gifteeName")
                Log.i("MainActivity", "Gift Name: $giftName")
                Log.i("MainActivity", "Gift Occasion: $giftOccasion")
                Log.i("MainActivity", "Gift Notes: $giftNotes")
                Log.i("MainActivity", "Gift Link: $giftLink")
                Log.i("MainActivity", "Gift Attachments: $giftAttachments")

                // Set click listeners for the buttons if needed
                gifteeButton.setOnClickListener {
                    // Handle button click event for gifteeButton
                    val intent = Intent(this@MainActivity, GiftInfoActivity::class.java)
                    Log.i("MainActivity", "UniqueID being passed to giftinfoactivity on button click: ${uniqueID.toString()}")
                    var uniqueIdFromButton = gifteeButton.tag as String
                    intent.putExtra("id", uniqueIdFromButton)
                    intent.putExtra("date", dateString)
                    intent.putExtra("gifteeName", gifteeName)
                    intent.putExtra("giftName", giftName)
                    intent.putExtra("giftOccasion", giftOccasion)
                    intent.putExtra("giftNotes", giftNotes)
                    intent.putExtra("giftLink", giftLink)
                    intent.putExtra("giftAttachments", giftAttachments)
                    startActivity(intent)
                }
                // Add the TextViews to your LinearLayout
                linearLayout.addView(gifteeButton)
            }
            cursor.close()
        }
    }

    private fun setCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment, fragment)
            commit()
        }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun parseDateString(dateString: String): Long {
        val format = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
        val date = format.parse(dateString)
        return date?.time ?: 0L
    }
}

