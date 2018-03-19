package com.example.haryono.lowkost;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class Update extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_update );
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.update );
    }



    private void showUpdateDialog(String kostanid, String kostanname){

        AlertDialog.Builder dialogBulider = new AlertDialog.Builder( this );

        LayoutInflater inflater = getLayoutInflater();

        final View dialogView = inflater.inflate( R.layout.update, null );

        dialogBulider.setView( dialogView );

        final EditText editTextName = (EditText) dialogView. findViewById( R.id.editTextName );

        final EditText editTextHarga = (EditText) dialogView.findViewById( R.id.editTextharga );

        final EditText editTextFasilitas = (EditText) dialogView.findViewById( R.id.editTextfasilitas );

        final EditText editTextFasilitas = (EditText) dialogView.findViewById( R.id.editTextfasilitas );

        final Button buttonUpdate = (Button) dialogView.findViewById( R.id.buttonUpdate );

        final Spinner spinnerGender = (Spinner) dialogView.findViewById( R.id.spinnerGender );



        dialogBulider.setTitle( "Updating kostan "+kostanname );

        AlertDialog alertDialog = dialogBulider.create();
        alertDialog.show();

        buttonUpdate.setOnClickListener( new View.OnClickListener() {
                                             @Override
                                             public void onClick(View v) {

                                                 String name = editTextName.getText().toString().trim();
                                                 String gender = spinnerGender.getSelectedItem().toString();

                                                 if (TextUtils.isEmpty( name ));
                                                 editTextName.setError( "Nama Tidak Boleh Kosong" );

                                                 return;

                                             }

                                             updateKostan(kostanid, kostanname, Gender, Harga, Alamat, fasilitas);

            AlertDialog.dismiss();

                                         }

        );




    }

    private boolean updateKostan(String id, String name, String Gender, Integer harga){
        DatabaseReference databaseReference = FireDatabase.getInstance().getReference("kostan").child(id);

        Kostan kostan = new Kostan (Id, name, Gender, harga);

        databaseReference.setValue(kostan);

        Toast.makeText( this, "Kostan berhasil diupdate", Toast.LENGTH_LONG ).show();

        return true;
    }



    listViewKostan.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener) {
        @Override
        public boolean onItemLongClick<AdapterView<?> adapterView, View view, int i, long l) {

            Kostan kostan = kostan.get(i);

            showUpdateDialog( kostan.getkostanid(), kostan.getkostanName() );

            return false;


        }


    }


}
}
