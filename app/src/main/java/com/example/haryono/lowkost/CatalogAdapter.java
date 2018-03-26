package com.example.haryono.lowkost;

/**
 * Created by haryono on 3/26/2018.
 */

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class CatalogAdapter extends RecyclerView.Adapter<CatalogAdapter.ViewHolder> {

    private ArrayList<Kost> daftarBarang;
    private Context context;
    FirebaseDataListener listener;

    public CatalogAdapter(ArrayList<Kost> barangs, Context ctx){
        /**
         * Inisiasi data dan variabel yang akan digunakan
         */
        daftarBarang = barangs;
        context = ctx;
        listener = (FirebaseDataListener) ctx;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        /**
         * Inisiasi View
         * Di tutorial ini kita hanya menggunakan data String untuk tiap item
         * dan juga view nya hanyalah satu TextView
         */
        TextView tvTitle;

        ViewHolder(View v) {
            super(v);
            tvTitle = (TextView) v.findViewById(R.id.tv_namabarang);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        /**
         *  Inisiasi ViewHolder
         */
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_barang, parent, false);
        // mengeset ukuran view, margin, padding, dan parameter layout lainnya
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        /**
         *  Menampilkan data pada view
         */
        final String name = daftarBarang.get(position).getKostName();
        System.out.println("BARANG DATA one by one " + position + daftarBarang.size());
        holder.tvTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /**
                 *  Kodingan untuk tutorial <span data-mce-type="bookmark" style="display: inline-block; width: 0px; overflow: hidden; line-height: 0;" class="mce_SELRES_start"></span>Read detail data
                 */
                context.startActivity(Catalog2Activity.getActIntent((Activity) context).putExtra("kost", daftarBarang.get(position)));
            }
        });
//        holder.tvTitle.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View view) {
//                /**
//                 *  Kodingan untuk tutorial delete dan update data
//                 */
//                final Dialog dialog = new Dialog(context);
//                dialog.setContentView(R.layout.dialog_view);
//                dialog.setTitle("Pilih Aksi");
//                dialog.show();
//
//                Button editButton = (Button) dialog.findViewById(R.id.bt_edit_data);
//                Button delButton = (Button) dialog.findViewById(R.id.bt_delete_data);
//
//                //apabila tombol edit diklik
//                editButton.setOnClickListener(
//                        new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                dialog.dismiss();
//                                context.startActivity(Catalog2Activity.getActIntent((Activity) context).putExtra("data", daftarBarang.get(position)));
//                            }
//                        }
//                );
//
//                //apabila tombol delete diklik
//                delButton.setOnClickListener(
//                        new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                dialog.dismiss();
//                                listener.onDeleteData(daftarBarang.get(position), position);
//                            }
//                        }
//                );
//                return true;
//            }
//        });
         holder.tvTitle.setText(name);
//    }
    }
    @Override
    public int getItemCount() {
        /**
         * Mengembalikan jumlah item pada barang
         */
        return daftarBarang.size();
    }

    public interface FirebaseDataListener{
        void onDeleteData(Kost barang, int position);
    }
}
