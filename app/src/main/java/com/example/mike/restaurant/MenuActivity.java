package com.example.mike.restaurant;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MenuActivity extends AppCompatActivity implements MenuItemsRequest.Callback {

    private ListView menuItemsView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        // create listview variable for menu items
        menuItemsView = findViewById(R.id.menuItems);

        // get category string through intent
        Intent intent = getIntent();
        String category = intent.getStringExtra("category");

        // request menu items through api request
        MenuItemsRequest menuItemsRequest = new MenuItemsRequest(this);
        menuItemsRequest.getMenuItems(this, category);

        // when clicked on a menu item in listview
        menuItemsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // retrieve which item was clicked
                MenuItem itemClicked = (MenuItem) parent.getItemAtPosition(position);

                // send clicked MenuItem to MenuItemsActivity through intent
                Intent intent = new Intent(MenuActivity.this, MenuItemsActivity.class);
                intent.putExtra("itemClicked", itemClicked);
                startActivity(intent);
            }
        });
    }

    // when api request succeeded
    @Override
    public void gotMenuItems(ArrayList<MenuItem> menuItems) {

        // link menu items to listview via an adapter
        MenuItemsAdapter adapter = new MenuItemsAdapter(this, R.layout.items_row, menuItems);
        menuItemsView.setAdapter(adapter);
    }

    // when api request failed, print error message on screen
    @Override
    public void gotMenuItemsError(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }
}
