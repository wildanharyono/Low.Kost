package com.example.haryono.lowkost;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by haryono on 3/4/2018.
 */

public class KostanList extends ArrayAdapter<Kostan> {
    private Activity context;
    List<Kostan> kostanList;

    public KostanList(Activity context, List<Kostan> kostan) {
        super(context, R.layout.list_layout, kostan);
        this.context = context;
        this.kostanList = kostan;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_kostan_list, null, true);

        TextView textViewName = (TextView) listViewItem.findViewById(R.id.textViewName);
        TextView textViewRating = (TextView) listViewItem.findViewById(R.id.textViewRating);

        Kostan artist = kostanList.get(position);
        textViewName.setText(artist.getKostanName());
        textViewRating.setText(String.valueOf(artist.getKostanRating()));

        return listViewItem;
    }
}