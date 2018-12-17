package com.example.mike.restaurant;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MenuItemsAdapter extends ArrayAdapter<MenuItem> {
    // create empty array list to store Menu Items
    private ArrayList<MenuItem> menuItems;

    // constructor for this adapter
    public MenuItemsAdapter(@NonNull Context context, int resource, @NonNull ArrayList<MenuItem> menuItems) {
        super(context, resource, menuItems);
        this.menuItems = menuItems;
    }

    // link item information to row in list view
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.items_row, parent, false);
        }

        // variables for image and text views
        TextView itemName = convertView.findViewById(R.id.name);
        TextView itemPrice = convertView.findViewById(R.id.price);
        ImageView itemImage = convertView.findViewById(R.id.image);

        // set information on row
        itemName.setText(menuItems.get(position).getName());
        String priceText = "$" + Double.toString(menuItems.get(position).getPrice());
        itemPrice.setText(priceText);
        Picasso.with(getContext()).load(menuItems.get(position).getImageUrl()).into(itemImage);

        return convertView;
    }
}
