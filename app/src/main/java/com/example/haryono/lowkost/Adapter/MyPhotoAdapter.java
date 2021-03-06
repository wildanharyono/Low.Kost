package com.example.haryono.lowkost.Adapter;

/**
 * Created by haryono on 4/2/2018.
 */


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.haryono.lowkost.Activity.AddPhotoActivity;
import com.example.haryono.lowkost.Activity.PhotoDetailEditActivity;
import com.example.haryono.lowkost.Fragment.MyPhotoFragment;
import com.example.haryono.lowkost.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

import com.example.haryono.lowkost.Activity.PhotoDetailActivity;
import com.example.haryono.lowkost.Config.Constant;
import com.example.haryono.lowkost.Model.CommentModel;
import com.example.haryono.lowkost.Model.PhotoModel;

//class adapter untuk row pada photo list
public class MyPhotoAdapter extends RecyclerView.Adapter<MyPhotoAdapter.MyViewHolder> {
    //deklarasi variable
    private List<PhotoModel> photoList;
    private Context context;
    FirebaseDataListener listener;
    private DatabaseReference database;

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
    public MyPhotoAdapter(List<PhotoModel> photoList, Context context) {
        this.photoList = photoList;
        this.context = context;
//        this.listener = (MyPhotoFragment)context;
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
        holder.tvTitle.setText(photo.getKostName());

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
                Intent in = new Intent(context, PhotoDetailEditActivity.class); //intent menuju detail photo
                in.putExtra("photoData", photo);
                context.startActivity(in);
            }
        });

        holder.cvPhoto.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                /**
                 *  Kodingan untuk tutorial Selanjutnya :p Delete dan update data
                 */
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.update_dialog);
                dialog.setTitle("Pilih Aksi");
                dialog.show();

//                Button editButton = (Button) dialog.findViewById(R.id.bt_edit_data);
                Button delButton = (Button) dialog.findViewById(R.id.bt_delete_data);

                //apabila tombol edit diklik
//                editButton.setOnClickListener(
//                        new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                dialog.dismiss();
//                                context.startActivity(AddPhotoActivity.getActIntent((Activity) context).putExtra("data", photoList.get(position)));
//                            }
//                        }
//                );
//
                //apabila tombol delete diklik
                delButton.setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                                listener.onDeleteData(photoList.get(position), position);
                            }
                        }
                );
                return true;
            }
        });
//        holder.tvTitle.setText((CharSequence) photos);
    }

    //count data
    @Override
    public int getItemCount() {
        return photoList.size();
    }

    public interface FirebaseDataListener{
        void onDeleteData(PhotoModel PhotoModel, int position);
    }


}
