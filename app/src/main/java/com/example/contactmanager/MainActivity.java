package com.example.contactmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.contactmanager.adapter.RecyclerViewAdapter;
import com.example.contactmanager.data.DatabaseHandler;
import com.example.contactmanager.model.Contact;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private ArrayList<Contact> contactArrayList;
    private ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        contactArrayList = new ArrayList<>();
        DatabaseHandler db = new DatabaseHandler(MainActivity.this);

        List<Contact> contactList = db.getAllContacts();

        for (Contact contact: contactList) {
            Log.d("MainActivity", "onCreate: " + contact.getName());
            contactArrayList.add(contact);
        }

        // set up adapter
        recyclerViewAdapter = new RecyclerViewAdapter(MainActivity.this, contactArrayList);

        recyclerView.setAdapter(recyclerViewAdapter);


        //db.addContact(new Contact("James", "2452524"));
        //db.addContact(new Contact("Greg", "870987987"));
        //db.addContact(new Contact("Helena", "750495720"));
        //db.addContact(new Contact("Carimo", "87520744"));
        //db.addContact(new Contact("Karate", "740947"));
        //db.addContact(new Contact("Silo", "45245"));
        //db.addContact(new Contact("Santos", "8969"));
        //db.addContact(new Contact("Litos", "78463"));
        //db.addContact(new Contact("Guerra", "24245334"));
        //db.addContact(new Contact("Gema", "4787856356"));
    }
}