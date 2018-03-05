package com.example.haryono.lowkost;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.haryono.lowkost.Kost;
import com.example.haryono.lowkost.R;

import java.util.List;

/**
 * Created by haryono on 3/4/2018.
 */

public class KostList extends ArrayAdapter<Kost> {
    private Activity context;
    //get array data dari class Kost
    List<Kost> kostList;

    public KostList(Activity context, List<Kost> artists) {
        super(context, R.layout.list_layout, artists);
        this.context = context;
        this.kostList = artists;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.list_layout, null, true);

        TextView textViewName = (TextView) listViewItem.findViewById(R.id.textViewName);
        TextView textViewGenre = (TextView) listViewItem.findViewById(R.id.textViewGenre);

        Kost artist = kostList.get(position);
        textViewName.setText(artist.getArtistName());
        textViewGenre.setText(artist.getArtistGenre());

        return listViewItem;
    }
}