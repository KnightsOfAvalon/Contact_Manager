package com.example.contactmanager.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.view.autofill.AutofillId;

import androidx.annotation.Nullable;

import com.example.contactmanager.R;
import com.example.contactmanager.model.Contact;
import com.example.contactmanager.util.Util;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {
    public DatabaseHandler(Context context) {
        super(context, Util.DATABASE_NAME, null, Util.DATABASE_VERSION);
    }

    // We create our table here
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // SQL - Structured Query Language
        // create table_name(id, name, phone_number);
        // This just structures the table
        String CREATE_CONTACT_TABLE = "CREATE TABLE " + Util.TABLE_NAME + "("
                + Util.KEY_ID + " INTEGER PRIMARY KEY," + Util.KEY_NAME + " TEXT,"
                + Util.KEY_PHONE_NUMBER + " TEXT" + ")";

        // This creates the table
        sqLiteDatabase.execSQL(CREATE_CONTACT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // Deletes the table
        String DROP_TABLE = String.valueOf(R.string.db_drop);
        sqLiteDatabase.execSQL(DROP_TABLE, new String[]{Util.DATABASE_NAME});

        // Creates a new table
        onCreate(sqLiteDatabase);

    }

    // CRUD = Create, Read, Update, Delete

    // Add contact - The "C" (Create) in CRUD
    public void addContact(Contact contact) {
        // Retrieves writeable database
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        // Creates variable that will hold key-value pairs for database
        ContentValues values = new ContentValues();
        values.put(Util.KEY_NAME, contact.getName());
        values.put(Util.KEY_PHONE_NUMBER, contact.getPhoneNumber());

        // Inserts key-value pairs into database row
        sqLiteDatabase.insert(Util.TABLE_NAME, null, values);
        Log.d("DBHandler", "addContact: " + "item added");

        // Closes the database connection
        sqLiteDatabase.close();

    }

    // Get contact - The "R" (Read) in CRUD
    public Contact getContact(int id) {
        // Retrieves readable database
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        // Creates a cursor that queries the database
        Cursor cursor = sqLiteDatabase.query(Util.TABLE_NAME,
                new String[]{ Util.KEY_ID, Util.KEY_NAME, Util.KEY_PHONE_NUMBER},
                Util.KEY_ID + "=?",
                new String[]{String.valueOf(id)},
                null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        // Creates a new contact from info provided by the cursor
        Contact contact = new Contact();
        assert cursor != null;
        contact.setId(Integer.parseInt(cursor.getString(0)));
        contact.setName(cursor.getString(1));
        contact.setPhoneNumber(cursor.getString(2));

        // Returns the created contact
        return contact;
    }

    // Get all contacts - Part of the "R" (Read) in CRUD
    public List<Contact> getAllContacts() {
        // Retrieves readable database
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        // Instantiates a new ArrayList that will hold all of the contacts
        List<Contact> contactList = new ArrayList<>();

        // Select all contacts
        String selectAll = "SELECT * FROM " + Util.TABLE_NAME;
        Cursor cursor = sqLiteDatabase.rawQuery(selectAll, null);

        // Loop through the data in the table
        if (cursor.moveToFirst()) {
            do {
                // Creates new contacts from info provided by the cursor
                Contact contact = new Contact();
                contact.setId(Integer.parseInt(cursor.getString(0)));
                contact.setName(cursor.getString(1));
                contact.setPhoneNumber(cursor.getString(2));

                // Add contact objects to our list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        return contactList;
    }

    // Update contact - The "U" (Update) in CRUD
    public int updateContact(Contact contact) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        // Creates a variable that will hold key-value pairs for the database
        ContentValues values = new ContentValues();
        values.put(Util.KEY_NAME, contact.getName());
        values.put(Util.KEY_PHONE_NUMBER, contact.getPhoneNumber());

        // Update the row
        return sqLiteDatabase.update(Util.TABLE_NAME, values, Util.KEY_ID + "=?",
                new String[]{String.valueOf(contact.getId())});
    }

    // Delete a single contact - The "D" (Delete) in CRUD
    public void deleteContact(Contact contact) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        sqLiteDatabase.delete(Util.TABLE_NAME, Util.KEY_ID + "=?",
                new String[]{String.valueOf(contact.getId())});

        sqLiteDatabase.close();
    }

    // Get the total count of contacts in the database
    public int getCount() {
        String countQuery = "SELECT * FROM " + Util.TABLE_NAME;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(countQuery, null);

        return cursor.getCount();

    }

}
