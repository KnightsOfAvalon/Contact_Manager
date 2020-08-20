package com.example.contactmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.contactmanager.data.DatabaseHandler;
import com.example.contactmanager.model.Contact;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DatabaseHandler db = new DatabaseHandler(MainActivity.this);

        Log.d("Count", "onCreate: " + db.getCount());

        // Create contact object first
        Contact jeremy = new Contact();
        jeremy.setName("Jeremy");
        jeremy.setPhoneNumber("2740278");

        Contact jason = new Contact();
        jason.setName("Jason");
        jason.setPhoneNumber("890879045");

        // Get one contact
        //Contact c = db.getContact(1);
        //c.setName("NewJeremy");
        //c.setPhoneNumber("2222");

        //int updateRow = db.updateContact(c);

        //Log.d("RowID", "onCreate: " + updateRow);

        //db.addContact(jason);

        // delete a contact
        //db.deleteContact(c);

        List<Contact> contactList = db.getAllContacts();

        for (Contact contact: contactList) {
            Log.d("MainActivity", "onCreate: " + contact.getId());
        }
    }
}