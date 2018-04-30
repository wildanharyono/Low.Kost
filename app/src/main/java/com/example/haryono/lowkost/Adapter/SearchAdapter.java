package com.example.haryono.lowkost.Adapter;



import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.haryono.lowkost.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Dushyant Mainwal on 29-Oct-17.
 */

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {
    Context context;
    ArrayList<String> kostNameList;
    ArrayList<String> kostGenreList;
    ArrayList<String> userList;
    ArrayList<String> image_url;
    ArrayList<String> kostPriceList;
    ArrayList<String> kostPhoneList;
//    ArrayList<String> profilePicList;

    class SearchViewHolder extends RecyclerView.ViewHolder {
        //   ImageView profileImage;
        TextView kostName, user_name, username;
        ImageView profileImage;
        public ImageView imgPhoto;

        public SearchViewHolder(View itemView) {
            super(itemView);
            imgPhoto = (ImageView) itemView.findViewById(R.id.imgPhoto);
            kostName = (TextView) itemView.findViewById(R.id.tvTitle);
            user_name = (TextView) itemView.findViewById(R.id.tvDeskripsi);
            username = (TextView) itemView.findViewById(R.id.tvNama);
        }
    }

    public SearchAdapter(Context context, ArrayList<String> kostNameList, ArrayList<String> kostGenreList, ArrayList<String> userList, ArrayList<String> image_url, ArrayList<String> kostPriceList, ArrayList<String> kostPhoneList) {
        this.context = context;
        this.kostNameList = kostNameList;
        this.kostGenreList = kostGenreList;
        this.userList = userList;
        this.image_url = image_url;
        this.kostPriceList = kostPriceList;
        this.kostPhoneList = kostPhoneList;
//        this.profilePicList = profilePicList;
    }

    @Override
    public SearchAdapter.SearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_search_item, parent, false);
        return new SearchAdapter.SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SearchViewHolder holder, int position) {
        holder.kostName.setText(kostNameList.get(position));
        holder.user_name.setText(kostGenreList.get(position));
        holder.username.setText(userList.get(position));
//         Glide.with(context).load(image_url.get(position)).asBitmap().placeholder(R.mipmap.ic_launcher_round).into(holder.profileImage);
        Picasso.get().load(image_url.get(position)).into(holder.imgPhoto); //load gambar dengan picasso
        holder.kostName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Full Name Clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return kostNameList.size();
    }
}
