package com.example.mike.restaurant;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class CategoriesActivity extends AppCompatActivity implements CategoriesRequest.Callback {

    private ListView categoriesView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        // send api request for categories
        CategoriesRequest categoriesRequest = new CategoriesRequest(this);
        categoriesRequest.getCategories(this);

        // create listview variable show categories
        categoriesView = findViewById(R.id.categories);

        // when clicked on listview item
        categoriesView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // save category as a string
                String category = (String) parent.getItemAtPosition(position);

                // send category to MenuActivity through intent
                Intent intent = new Intent(CategoriesActivity.this, MenuActivity.class);
                intent.putExtra("category", category);
                startActivity(intent);
            }
        });


    }

    // receives list of category string from api request
    @Override
    public void gotCategories(ArrayList<String> categories) {

        // create adapter and link categories to listview
        ArrayAdapter adapter = new ArrayAdapter<>(this,R.layout.categories_row, categories);
        categoriesView.setAdapter(adapter);
    }

    // when no categories received, print error as toast
    @Override
    public void gotCategoriesError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
