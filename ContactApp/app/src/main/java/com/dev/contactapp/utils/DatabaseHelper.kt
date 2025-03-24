package com.dev.contactapp.utils

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.dev.contactapp.models.Contact

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = """
            CREATE TABLE $TABLE_NAME (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_NAME TEXT NOT NULL,
                $COLUMN_PHONE TEXT NOT NULL
            )
        """.trimIndent()
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    companion object {
        private const val DATABASE_NAME = "ContactDatabase.db"
        private const val DATABASE_VERSION = 1
        const val TABLE_NAME = "contacts"
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
        const val COLUMN_PHONE = "phone"
    }

    fun getAllContacts(): List<Contact> {
        val contactList = mutableListOf<Contact>()
        val db: SQLiteDatabase = this.readableDatabase  // Sửa lỗi ở đây

        val cursor = db.query(TABLE_NAME, null, null, null, null, null, null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
                val name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME))
                val phone = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PHONE))
                contactList.add(Contact(id, name, phone))
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return contactList
    }

    fun CreateContact(id:Int,phone:String, name:String): Boolean {
        val db: SQLiteDatabase = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_ID, id)
        values.put(COLUMN_NAME, name)
        values.put(COLUMN_PHONE, phone)
        val newRowId = db.insert(TABLE_NAME, null, values)
        db.close()
        return newRowId > 0
    }

    fun UpdateContact(id: Int, phone: String, name: String): Boolean {
        val db: SQLiteDatabase = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_NAME, name)
        values.put(COLUMN_PHONE, phone)
        val newRowId = db.update(TABLE_NAME, values, "$COLUMN_ID = ?", arrayOf(id.toString()))
        db.close()
        return newRowId > 0
    }

    fun deleteContact(id: Int): Boolean {
        val db: SQLiteDatabase = this.writableDatabase
        val newRowId = db.delete(TABLE_NAME, "$COLUMN_ID = ?", arrayOf(id.toString()))
        db.close()
        return newRowId > 0
    }

    fun isIdExists(id: Int): Boolean {
        val db = this.readableDatabase
        val query = "SELECT id FROM contacts WHERE id = ?"
        val cursor = db.rawQuery(query, arrayOf(id.toString()))
        val exists = cursor.count > 0
        cursor.close()
        return exists
    }


}

