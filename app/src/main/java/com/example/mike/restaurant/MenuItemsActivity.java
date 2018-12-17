package com.example.mike.restaurant;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.Serializable;

public class MenuItemsActivity extends AppCompatActivity implements Serializable {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_items);

        // create variables for image and text views
        TextView nameView = findViewById(R.id.name);
        ImageView foodImageView = findViewById(R.id.image);
        TextView descriptionView = findViewById(R.id.description);
        TextView priceView = findViewById(R.id.price);

        // get relevant menu item through intent
        Intent intent = getIntent();
        MenuItem itemClicked = (MenuItem) intent.getSerializableExtra("itemClicked");

        // set views on layout to relevant values
        nameView.setText(itemClicked.getName());
        descriptionView.setText(itemClicked.getDescription());
        Picasso.with(this).load(itemClicked.getImageUrl()).into(foodImageView);

        String priceText = "$" + Double.toString(itemClicked.getPrice());
        priceView.setText(priceText);
    }
}
