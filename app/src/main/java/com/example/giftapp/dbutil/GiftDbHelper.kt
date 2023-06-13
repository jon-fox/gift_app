import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns

// Step 1: Define the contract class
object GiftContract {
    object GiftEntry : BaseColumns {
        const val TABLE_NAME = "gifts"
        const val COLUMN_GIFTEE_NAME = "giftee_name"
        const val COLUMN_GIFT_NAME = "gift_name"
        const val COLUMN_GIFT_LINK = "gift_link"
    }
}

// reference
// https://developer.android.com/training/data-storage/sqlite
private const val SQL_CREATE_ENTRIES =
    "CREATE TABLE ${GiftContract.GiftEntry.TABLE_NAME} (" +
            "${GiftContract.GiftEntry.COLUMN_GIFTEE_NAME} TEXT PRIMARY KEY," +
            "${GiftContract.GiftEntry.COLUMN_GIFT_NAME} TEXT," +
            "${GiftContract.GiftEntry.COLUMN_GIFT_LINK} TEXT)"

private const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS ${GiftContract.GiftEntry.TABLE_NAME}"

class GiftDbHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_ENTRIES)
    }
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }
    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }
    companion object {
        // If you change the database schema, you must increment the database version.
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "GiftAppDB.db"
    }
}