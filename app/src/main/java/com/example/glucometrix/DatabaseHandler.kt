package com.example.glucometrix

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.glucometrix.dataClass.DateGlucose
import com.example.glucometrix.dataClass.GlucoseData
import com.example.glucometrix.dataClass.User
import java.text.SimpleDateFormat
import java.util.*


class DatabaseHandler(context: Context): SQLiteOpenHelper(
        context,
        DATABASE_NAME,
        null,
        DATABASE_VERSION
) {
    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "GlucoMatrix"

        //table names
        private const val TABLE_USERS = "Users"
        private const val TABLE_GLUCO = "Glucoses"
        private const val TABLE_USERS_GLUCO = "Users_Gluco"
        private const val TABLE_EVENTS = "Events"

        //column names
        //common
        private const val KEY_ID = "_id"
        //Event column names
        private const val KEY_DATE_EVENT = "date"
        private const val KEY_DESC_EVENT = "description"
        //Users column names
        private const val KEY_LOGIN = "login"
        private const val KEY_PASSWORD = "password"

        //Glucoses table name
        private const val KEY_DATE = "date"
        private const val KEY_HOUR = "hour"
        private const val KEY_GLUCOSE = "glucose"
        private const val KEY_DESC = "description"
        private const val KEY_USER_ID = "user_id"

        //Users_Gluco table name
        private const val KEY_USERS_ID = "users_id"
        private const val KEY_GLUCO_ID = "gluco_id"

        private var curLogin = ""
        private var curPassw = ""
        private var curId = 1
    }

    var glucoseList = mutableListOf("120", "145",
            "101", "93",
            "168", "67",
            "113", "123")

    var descriptionList = listOf("after wake up",
            "before breakfast", "after breakfast", "before dinner",
            "after dinner", "before supper", "after supper",
            "before sleep")
    var hourList = mutableListOf("08:00",
            "10:00", "12:00", "14:00",
            "16:00", "18:00", "20:00",
            "22:00")
    var dateList = mutableListOf(
            "20.06.2021", "21.06.2021", "22.06.2021"
    )
    var dateListEvent = mutableListOf(
        "28.08", "29.08", "03.09"
    )
    var descListEvent = mutableListOf(
        "urodziny mamy", "urodziny taty", "imieniny cioci"
    )
    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_TABLE_USERS =
                ("CREATE TABLE IF NOT EXISTS $TABLE_USERS($KEY_ID INTEGER PRIMARY KEY, $KEY_LOGIN TEXT, $KEY_PASSWORD TEXT);")
        db?.execSQL(CREATE_TABLE_USERS)

        val CREATE_TABLE_GLUCO =
                ("CREATE TABLE IF NOT EXISTS $TABLE_GLUCO($KEY_ID INTEGER PRIMARY KEY, $KEY_USER_ID INTEGER, $KEY_DATE TEXT, $KEY_HOUR TEXT, $KEY_GLUCOSE INTEGER, $KEY_DESC TEXT);")
        db?.execSQL(CREATE_TABLE_GLUCO)

        val CREATE_TABLE_USER_GLUCO =
                ("CREATE TABLE IF NOT EXISTS $TABLE_USERS_GLUCO($KEY_ID INTEGER PRIMARY KEY, $KEY_USERS_ID INTEGER, $KEY_GLUCO_ID INTEGER);")
        db?.execSQL(CREATE_TABLE_USER_GLUCO)

        val CREATE_TABLE_EVENTS =
            ("CREATE TABLE IF NOT EXISTS $TABLE_EVENTS($KEY_ID INTEGER PRIMARY KEY, $KEY_USER_ID, $KEY_DATE_EVENT TEXT, $KEY_DESC_EVENT TEXT);")
        db?.execSQL(CREATE_TABLE_EVENTS)
        if (db != null) {
            createTables(db, glucoseList, descriptionList, hourList, dateList, dateListEvent, descListEvent)
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_USERS")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_GLUCO")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_USERS_GLUCO")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_EVENTS")
        onCreate(db)
    }

    fun addUser(user: User): Long {
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(KEY_LOGIN, user.getLogin())
        contentValues.put(KEY_PASSWORD, user.getPassword())

        val succes = db.insert(TABLE_USERS, null, contentValues)
        // lastId += 1
        db.close()
        return succes
    }

    fun changeUserData(field: String, value: String, id: Int) {
        val db = this.writableDatabase
        //val contentValues = ContentValues()

        if (field == "login") {
            //contentValues.put(KEY_LOGIN, value)
            val strSQL = ("UPDATE $TABLE_USERS SET $KEY_LOGIN = '$value' WHERE $KEY_ID = 1")

            db.execSQL(strSQL)
        } else if (field == "password") {
            //contentValues.put(KEY_PASSWORD, value)
            val strSQL = ("UPDATE $TABLE_USERS SET $KEY_PASSWORD = '$value' WHERE $KEY_ID = $id")

            db.execSQL(strSQL)
        }

        db.close()
    }

    @SuppressLint("SimpleDateFormat")
    fun addGlucose(glucoseData: GlucoseData): Long {
        val db = this.writableDatabase
        val sdt = SimpleDateFormat("HH:mm")
        val currentTime: String = sdt.format(Date())
        val sdf = SimpleDateFormat("dd.MM.yyyy")
        val currentDate: String = sdf.format(Date())
        val id = showID()
        val contentValues = ContentValues()
        contentValues.put(KEY_DATE, currentDate)
        contentValues.put(KEY_HOUR, currentTime)
        contentValues.put(KEY_DESC, glucoseData.getSubItemDesc())
        contentValues.put(KEY_GLUCOSE, glucoseData.getSubItemGlucose())
        contentValues.put(KEY_USER_ID, id)

        val success = db.insert(TABLE_GLUCO, null, contentValues)

        db.close()
        return success
    }
    fun addEvent(date: String, desc: String): Long {
        val db = this.writableDatabase
        val id = showID()
        val contentValues = ContentValues()
        contentValues.put(KEY_USER_ID, id)
        contentValues.put(KEY_DATE_EVENT, date)
        contentValues.put(KEY_DESC_EVENT, desc)

        val success = db.insert(TABLE_EVENTS, null, contentValues)

        db.close()
        return success
    }
    fun showEventsDate(): List<String>{
        val dateList: MutableList<String> = ArrayList()
        val db = this.readableDatabase
        val userId = getCurrId(db)
        val selectQuery1 = "SELECT $KEY_DATE_EVENT FROM $TABLE_EVENTS WHERE $KEY_USER_ID;"

        val c1: Cursor = db.rawQuery(selectQuery1, null)
        if (c1.moveToFirst()) {
            do {
                val t = c1.getString(c1.getColumnIndex(KEY_DATE_EVENT))
                dateList.add(t)
            } while (c1.moveToNext())
        }
        return dateList
    }

    fun showEventsDesc(): List<String>{
        val descList: MutableList<String> = ArrayList()
        val db = this.readableDatabase
        val userId = getCurrId(db)
        val selectQuery1 = "SELECT $KEY_DESC_EVENT FROM $TABLE_EVENTS;"

        val c1: Cursor = db.rawQuery(selectQuery1, null)
        if (c1.moveToFirst()) {
            do {
                val t = c1.getString(c1.getColumnIndex(KEY_DESC_EVENT))
                descList.add(t)
            } while (c1.moveToNext())
        }
        return descList
    }

    fun validateUser(login: String, password: String): Boolean {
        // array of columns to fetch
        val db = readableDatabase
        val columns = arrayOf<String>(KEY_ID)

        // selection criteria
        val selection = "$KEY_LOGIN = ? AND $KEY_PASSWORD = ?"

        // selection arguments
        val selectionArgs = arrayOf(login, password)

        val cursor = db.query(
                TABLE_USERS, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null
        );                      //The sort order

        val cursorCount = cursor.count;

        cursor.close();
        //db.close();
        if (cursorCount > 0) {
            curLogin = login
            curPassw = password
            curId = cursorCount
            return true;
        }

        return false;
    }

    private fun createTables(db: SQLiteDatabase, glucoList: MutableList<String>, descList: List<String>, hourList: List<String>, dateList: MutableList<String>,
    dateListEvent: MutableList<String>, descListEvent: MutableList<String>) {
        val values = ContentValues()

        for (i in 0 until dateList.size) {
            for (j in 0 until glucoList.size) {
                values.put(KEY_DATE, dateList[i])
                values.put(KEY_HOUR, hourList[j])
                values.put(KEY_GLUCOSE, glucoList[j])
                values.put(KEY_DESC, descList[j])
                values.put(KEY_USER_ID, showID())
                db.insert(TABLE_GLUCO, null, values)
            }
        }
        for(i in 0 until dateListEvent.size){
            values.put(KEY_DATE_EVENT, dateListEvent[i])
            values.put(KEY_DESC_EVENT, descListEvent[i])
            values.put(KEY_USER_ID, showID())
            db.insert(TABLE_EVENTS, null, values)
        }
    }

    @SuppressLint("Recycle")
    fun showDate(): List<DateGlucose> {
        var glucoseList: MutableList<GlucoseData> = ArrayList()
        val dateGlucoseList: MutableList<DateGlucose> = ArrayList()
        val db = this.readableDatabase
        val userId = getCurrId(db)
        val selectQuery = "SELECT $KEY_USER_ID, $KEY_DATE, $KEY_HOUR, $KEY_GLUCOSE, $KEY_DESC FROM $TABLE_GLUCO WHERE $KEY_USER_ID ='$userId' ORDER BY $KEY_DATE, $KEY_HOUR DESC;"
        val selectQuery1 = "SELECT DISTINCT $KEY_DATE FROM $TABLE_GLUCO WHERE $KEY_USER_ID ='$userId' ORDER BY $KEY_DATE DESC;"

        val c1: Cursor = db.rawQuery(selectQuery1, null)
            if (c1.moveToFirst()) {
                do {
                    val date = c1.getString(c1.getColumnIndex(KEY_DATE))
                    val c: Cursor = db.rawQuery(selectQuery, null)
                    if (c.moveToFirst()) {
                        do {
                            val date1 = c.getString(c.getColumnIndex(KEY_DATE))
                            if (date1 == date) {
                                val t = GlucoseData(
                                        c.getString(c.getColumnIndex(KEY_HOUR)),
                                        c.getString(c.getColumnIndex(KEY_GLUCOSE)),
                                        c.getString(c.getColumnIndex(KEY_DESC))
                                )
                                glucoseList.add(t)
                            }
                        } while (c.moveToNext())
                    }
                    dateGlucoseList.add(DateGlucose(c1.getString(c1.getColumnIndex(KEY_DATE)), glucoseList))
                    glucoseList = ArrayList()
                } while (c1.moveToNext())
            }

        return dateGlucoseList
    }

    fun showAvgGlucose(days: String): Double {
        var suma = 0.0
        var count = 0
        val db = this.readableDatabase
        val userId = getCurrId(db)
        val selectQuery = "SELECT $KEY_DATE, $KEY_HOUR, $KEY_GLUCOSE, $KEY_DESC FROM $TABLE_GLUCO  WHERE $KEY_USER_ID ='$userId' ORDER BY $KEY_DATE DESC;"
        val selectQuery1 = "SELECT DISTINCT $KEY_DATE FROM $TABLE_GLUCO WHERE $KEY_USER_ID ='$userId' ORDER BY $KEY_ID DESC LIMIT $days;"

        val c1: Cursor = db.rawQuery(selectQuery1, null)

        if (c1.moveToFirst()) {
            do {
                val date = c1.getString(c1.getColumnIndex(KEY_DATE))
                val c: Cursor = db.rawQuery(selectQuery, null)
                if (c.moveToFirst()) {
                    do {
                        val date1 = c.getString(c.getColumnIndex(KEY_DATE))
                        if (date1 == date) {
                            val t = c.getString(c.getColumnIndex(KEY_GLUCOSE)).toFloat()
                            suma += t;
                            count+=1
                        }
                    } while (c.moveToNext())
                }
            } while (c1.moveToNext())
        }
        return suma / count
    }
    fun showHour(): List<String> {
        var hourList: MutableList<String> = ArrayList()
        val db = this.readableDatabase
        val userId = getCurrId(db)
        val selectQuery = "SELECT $KEY_USER_ID, $KEY_DATE, $KEY_HOUR, $KEY_GLUCOSE, $KEY_DESC FROM $TABLE_GLUCO WHERE $KEY_USER_ID ='$userId' ORDER BY $KEY_DATE DESC;"
        val selectQuery1 = "SELECT DISTINCT $KEY_DATE FROM $TABLE_GLUCO WHERE $KEY_USER_ID ='$userId';"

        val c1: Cursor = db.rawQuery(selectQuery1, null)
        if (c1.moveToFirst()) {
            do {
                val date = c1.getString(c1.getColumnIndex(KEY_DATE))
                val c: Cursor = db.rawQuery(selectQuery, null)
                if (c.moveToFirst()) {
                    do {
                        val date1 = c.getString(c.getColumnIndex(KEY_DATE))
                        if (date1 == date) {
                            val t = c.getString(c.getColumnIndex(KEY_HOUR))
                            hourList.add(t)
                        }
                    } while (c.moveToNext())
                }
            } while (c1.moveToNext())
        }
        return hourList
    }
    fun showDescList(): List<String> {
        val descList: MutableList<String> = ArrayList()
        val db = this.readableDatabase
        val userId = getCurrId(db)
        val selectQuery = "SELECT $KEY_USER_ID, $KEY_DATE, $KEY_HOUR, $KEY_GLUCOSE, $KEY_DESC FROM $TABLE_GLUCO WHERE $KEY_USER_ID ='$userId' ORDER BY $KEY_DATE DESC;"
        val selectQuery1 = "SELECT DISTINCT $KEY_DATE FROM $TABLE_GLUCO WHERE $KEY_USER_ID ='$userId';"

        val c1: Cursor = db.rawQuery(selectQuery1, null)
        if (c1.moveToFirst()) {
            do {
                val date = c1.getString(c1.getColumnIndex(KEY_DATE))
                val c: Cursor = db.rawQuery(selectQuery, null)
                if (c.moveToFirst()) {
                    do {
                        val date1 = c.getString(c.getColumnIndex(KEY_DATE))
                        if (date1 == date) {
                            val t = c.getString(c.getColumnIndex(KEY_DESC))
                            descList.add(t)
                        }
                    } while (c.moveToNext())
                }
            } while (c1.moveToNext())
        }
        return descList
    }
    fun showGlucos(): List<String> {
        val glucoseList: MutableList<String> = ArrayList()
        val db = this.readableDatabase
        val userId = getCurrId(db)
        val selectQuery = "SELECT $KEY_USER_ID, $KEY_DATE, $KEY_HOUR, $KEY_GLUCOSE, $KEY_DESC FROM $TABLE_GLUCO WHERE $KEY_USER_ID ='$userId' ORDER BY $KEY_DATE DESC;"
        val selectQuery1 = "SELECT DISTINCT $KEY_DATE FROM $TABLE_GLUCO WHERE $KEY_USER_ID ='$userId';"

        val c1: Cursor = db.rawQuery(selectQuery1, null)
        if (c1.moveToFirst()) {
            do {
                val date = c1.getString(c1.getColumnIndex(KEY_DATE))
                val c: Cursor = db.rawQuery(selectQuery, null)
                if (c.moveToFirst()) {
                    do {
                        val date1 = c.getString(c.getColumnIndex(KEY_DATE))
                        if (date1 == date) {
                            val t = c.getString(c.getColumnIndex(KEY_GLUCOSE))
                            glucoseList.add(t)
                        }
                    } while (c.moveToNext())
                }
            } while (c1.moveToNext())
        }
        return glucoseList
    }
    fun showNumOfGlucos(): Int {
        val db = this.readableDatabase
        val userId = getCurrId(db)
        val selectQuery = "SELECT $KEY_USER_ID, $KEY_DATE, $KEY_HOUR, $KEY_GLUCOSE, $KEY_DESC FROM $TABLE_GLUCO WHERE $KEY_USER_ID ='$userId' ORDER BY $KEY_DATE DESC;"
        val selectQuery1 = "SELECT DISTINCT $KEY_DATE FROM $TABLE_GLUCO WHERE $KEY_USER_ID ='$userId';"
        var count = 0
        val c1: Cursor = db.rawQuery(selectQuery1, null)
        if (c1.moveToFirst()) {
            do {
                val date = c1.getString(c1.getColumnIndex(KEY_DATE))
                val c: Cursor = db.rawQuery(selectQuery, null)
                if (c.moveToFirst()) { //
                    do {
                        val date1 = c.getString(c.getColumnIndex(KEY_DATE))
                        if (date1 == date) {//
                            count +=1
                        }
                    } while (c.moveToNext())
                }
            } while (c1.moveToNext())
        }

        return count
    }

    fun showNumDays(): Int {
        val db = this.readableDatabase
        val userId = getCurrId(db)
        val selectQuery = "SELECT DISTINCT $KEY_DATE FROM $TABLE_GLUCO WHERE $KEY_USER_ID = '$userId';"
        val c: Cursor = db.rawQuery(selectQuery, null)
        c.moveToLast()
        return  c.count
    }

    fun showLogin(): String {
        return curLogin
    }

    fun showID(): Int {
        return curId
    }

    fun getCurrId(db: SQLiteDatabase?): String{
        val login = showLogin()
        val password = showPassword()
        val selectQuery2 = "SELECT $KEY_ID FROM $TABLE_USERS WHERE $KEY_LOGIN = '$login' AND $KEY_PASSWORD = '$password'"
        val c2: Cursor = db!!.rawQuery(selectQuery2, null)
        var userId = ""
        c2.moveToFirst()
        userId = c2.getInt(c2.getColumnIndex(KEY_ID)).toString()
        return userId
    }

    fun showPassword(): String {
        return curPassw
    }

}

