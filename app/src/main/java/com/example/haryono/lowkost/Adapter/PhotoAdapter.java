package com.example.haryono.lowkost.Adapter;

/**
 * Created by haryono on 4/2/2018.
 */


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.haryono.lowkost.Activity.PhotoDetailActivity;
import com.example.haryono.lowkost.Config.Constant;
import com.example.haryono.lowkost.Model.CommentModel;
import com.example.haryono.lowkost.Model.PhotoModel;
import com.example.haryono.lowkost.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

//class adapter untuk row pada photo list
public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.MyViewHolder> {
    //deklarasi variable
    private List<PhotoModel> photoList;
    private Context context;

    //class viewholder untuk declare dan inisialisasi views pada row yang digunakan
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName, tvDesc, tvTitle, tvComment;
        public ImageView imgPhoto;
        public CardView cvPhoto;

        public MyViewHolder(View view) {
            super(view);
            imgPhoto = (ImageView) view.findViewById(R.id.imgPhoto);
            tvName = (TextView) view.findViewById(R.id.tvNama);
            tvDesc = (TextView) view.findViewById(R.id.tvDeskripsi);
            tvTitle = (TextView) view.findViewById(R.id.tvTitle);
            tvComment = (TextView) view.findViewById(R.id.tvComment);
            cvPhoto = (CardView) view.findViewById(R.id.cvPhoto);
        }
    }

    //konstruktor untuk menerima data yang dikirimkan dari activity ke adapter
    public PhotoAdapter(List<PhotoModel> photoList, Context context) {
        this.photoList = photoList;
        this.context = context;
    }

    //create ke layout row yang dipilih
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_photo, parent, false);

        return new MyViewHolder(itemView);
    }

    int comment = 0;

    //binding antara data yang didapatkan ke dalam views
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final PhotoModel photo = photoList.get(position);
        holder.tvDesc.setText(photo.getDesc());
        holder.tvName.setText(photo.getName());
        holder.tvTitle.setText(photo.getTitle());

        //mengambil data jumlah komentar setiap photo
        Constant.refPhoto.child(photo.getKey()).child("commentList")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        comment = 0;
                        // This method is called once with the initial value and again
                        // whenever data at this location is updated.

                        for (final DataSnapshot ds : dataSnapshot.getChildren()) {
                            CommentModel model = ds.getValue(CommentModel.class);
                            comment++;
                        }
                        holder.tvComment.setText(comment + "");
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                        Log.w("", "Failed to read value.", error.toException());
                        //showProgress(false);
                    }
                });

        Picasso.get().load(photo.getImage_url()).into(holder.imgPhoto); //load gambar dengan picasso
        holder.cvPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(context, PhotoDetailActivity.class); //intent menuju detail photo
                in.putExtra("photoData", photo);
                context.startActivity(in);
            }
        });

        holder.cvPhoto.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.dialog_view);
                dialog.show();

                Button shareDesc = (Button) dialog.findViewById(R.id.bt_share_desc);
                shareDesc.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        PhotoModel poto = photoList.get(position);
                        Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        intent.setType("image/*");
                        String title = "Nama kos: ";
                        String body = "Status: ";
                        Uri uri = Uri.parse(poto.getImage_url());
                        intent.putExtra(Intent.EXTRA_TEXT, title + poto.getTitle() + "\n" +
                                body + poto.getDesc());
                        intent.putExtra(Intent.EXTRA_STREAM, uri + poto.getImage_url());
                        context.startActivity(Intent.createChooser(intent, "Share Using"));
                        dialog.dismiss();
                    }
                });
//                return true;

                Button shareImg = (Button) dialog.findViewById(R.id.bt_share_img);
                shareImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        PhotoModel poto = photoList.get(position);
                        Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                        intent.setType("image/*");
                        intent.putExtra(Intent.EXTRA_STREAM, poto.getImage_url().hashCode());
                        context.startActivity(Intent.createChooser(intent, "Share Using"));
                        dialog.dismiss();

                    }
                });
                return true;

            }
        });
    }


    //count data
    @Override
    public int getItemCount() {
        return photoList.size();
    }
}