package com.example.haryono.lowkost.Activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.features.ReturnMode;
import com.esafirm.imagepicker.model.Image;
import com.example.haryono.lowkost.Config.Constant;
import com.example.haryono.lowkost.Model.PhotoModel;
import com.example.haryono.lowkost.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddPhotoActivity extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {

    //Deklarasi View
    @BindView(R.id.btnPost) //@BindView declare sekaligus inisialisasi view dengan menggunakan library ButterKnife
            FloatingActionButton btnPost;
    @BindView(R.id.tvTitle) //@BindView declare sekaligus inisialisasi view dengan menggunakan library ButterKnife
            TextInputEditText tvTitle;
    @BindView(R.id.spinnerGenres) //@BindView declare sekaligus inisialisasi view dengan menggunakan library ButterKnife
            Spinner spinnerGenres;
    @BindView(R.id.tvPost) //@BindView declare sekaligus inisialisasi view dengan menggunakan library ButterKnife
            TextInputEditText tvPost;
    @BindView(R.id.tvHarga) //@BindView declare sekaligus inisialisasi view dengan menggunakan library ButterKnife
            TextInputEditText tvHarga;
    @BindView(R.id.tvPhone) //@BindView declare sekaligus inisialisasi view dengan menggunakan library ButterKnife
            TextInputEditText tvPhone;
    @BindView(R.id.tvLokasi) //@BindView declare sekaligus inisialisasi view dengan menggunakan library ButterKnife
            TextInputEditText tvLokasi;
    @BindView(R.id.imgPhoto) //@BindView declare sekaligus inisialisasi view dengan menggunakan library ButterKnife
            ImageView imgPhoto;
    @BindView(R.id.btnChoose) //@BindView declare sekaligus inisialisasi view dengan menggunakan library ButterKnife
            Button btnChoose;
    private StorageReference refPhotoProfile;
    private Uri photoUrl;
    private ProgressDialog pbDialog;

    private static final int MY_PERMISSION_REQUEST_CODE = 7171;
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 7172;
    private TextView txtCoordinates;
    private Button btnGetCoordinates, btnLocationUpdates;
    private boolean mRequestingLocationUpdates = false;
    private LocationRequest mLocationRequest;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;

    private static int UPDATE_INTERVAL = 5000; // SEC
    private static int FATEST_INTERVAL = 3000; // SEC
    private static int DISPLACEMENT = 10; // METERS


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (checkPlayServices()) {
                        buildGoogleApiClient();
                        createLocationRequest();
                    }
                }
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_photo);
        ButterKnife.bind(this); //Binding ButterKnife pada activity
        btnChoose.setOnClickListener(this);
        btnPost.setOnClickListener(this);
        pbDialog = new ProgressDialog(this);

        //untuk membuat splash screen fullscreen tanpa tool bar.
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        txtCoordinates = (TextView) findViewById(R.id.tvLokasi);
        btnGetCoordinates = (Button) findViewById(R.id.btnLokasi);
//        btnLocationUpdates = (Button) findViewById(R.id.btnTrackLocation);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //Run-time request permission
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
            }, MY_PERMISSION_REQUEST_CODE);
        } else {
            if (checkPlayServices()) {
                buildGoogleApiClient();
                createLocationRequest();
            }
        }

        btnGetCoordinates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayLocation();
            }
        });

    }
    //method handling onClickListener
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnChoose: //tombol choose (pilih gambar)
                //ImagePicker library untuk menampilkan dialog memilih gambar pada gallery/camera
                ImagePicker.create(this)
                        .returnMode(ReturnMode.ALL) // set whether pick and / or camera action should return immediate result or not.
                        .folderMode(true) // folder mode (false by default)
                        .toolbarFolderTitle("Folder") // folder selection title
                        .toolbarImageTitle("Tap to select") // image selection title
                        .toolbarArrowColor(Color.WHITE) // Toolbar 'up' arrow color
                        .single() // single mode
                        .limit(3) // max images can be selected (99 by default)
                        .showCamera(true) // show camera or not (true by default)
                        .imageDirectory("Camera") // directory name for captured image  ("Camera" folder by default)
                        .enableLog(false) // disabling log
                        .start(); // start image picker activity with request code
                break;
            case R.id.btnPost:
                //validasi kosong
                if (tvTitle.getText().toString().isEmpty()) {
                    tvTitle.setError("Required");
                    return;
                }
                //validasi kosong
                if (tvPost.getText().toString().isEmpty()) {
                    tvPost.setError("Required");
                    return;
                }
                if (tvLokasi.getText().toString().isEmpty()) {
                    tvLokasi.setError("Required");
                    return;
                }
                //validasi gambar sudah dipilih
                if (!isPicChange) {
                    Toast.makeText(this, "Harap pilih gambar!", Toast.LENGTH_SHORT).show();
                    return;
                }

                pbDialog.setMessage("Uploading..");
                pbDialog.setIndeterminate(true);
                pbDialog.show();

                //melakukan proses update foto
                refPhotoProfile = Constant.storageRef.child("gambar/" + System.currentTimeMillis() + ".jpg"); //akses path dan filename storage di firebase untuk menyimpan gambar
                StorageReference photoImagesRef = Constant.storageRef.child("gambar/" + System.currentTimeMillis() + ".jpg");
                refPhotoProfile.getName().equals(photoImagesRef.getName());
                refPhotoProfile.getPath().equals(photoImagesRef.getPath());

                //mengambil gambar dari imageview yang sudah di set menjadi selected image sebelumnya
                imgPhoto.setDrawingCacheEnabled(true);
                imgPhoto.buildDrawingCache();
                Bitmap bitmap = imgPhoto.getDrawingCache(); //convert imageview ke bitmap
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos); //convert bitmap ke bytearray
                byte[] data = baos.toByteArray();

                UploadTask uploadTask = refPhotoProfile.putBytes(data); //upload image yang sudah dalam bentuk bytearray ke firebase storage
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                        photoUrl = taskSnapshot.getDownloadUrl(); //setelah selesai upload, ambil url gambar
                        String key = Constant.refPhoto.push().getKey(); //ambil key dari node firebase

                        //push atau insert data ke firebase database
                        Constant.refPhoto.child(key).setValue(new PhotoModel(
                                key,
                                photoUrl.toString(),
                                tvTitle.getText().toString(),
                                spinnerGenres.getSelectedItem().toString(),
                                tvPost.getText().toString(),
                                tvLokasi.getText().toString(),
                                tvHarga.getText().toString(),
                                tvPhone.getText().toString(),
                                Constant.currentUser.getEmail().split("@")[0],
                                Constant.currentUser.getEmail()));
                        pbDialog.dismiss();
                        Toast.makeText(AddPhotoActivity.this, "Uploaded!", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
                break;
        }
    }

    boolean isPicChange = false;

    //method untuk handling result dari activity lain contoh disini adalah imagepicker
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) { // jika ada data dipilih
            Image image = ImagePicker.getFirstImageOrNull(data); //ambil first image
            File imgFile = new File(image.getPath()); // dapatkan lokasi gambar yang dipilih
            if (imgFile.exists()) { //jika ditemukan
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath()); //convert file ke bitmap
                imgPhoto.setImageBitmap(myBitmap); //set imageview dengan gambar yang dipilih
                isPicChange = true; // ubah state menjadi true untuk menandakan gambar telah dipilih
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
    @Override
    protected void onResume() {
        super.onResume();
        checkPlayServices();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(mGoogleApiClient != null)
            mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient,this);
        if(mGoogleApiClient != null)
            mGoogleApiClient.disconnect();
        super.onStop();
    }


    private void displayLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {
            double latitude = mLastLocation.getLatitude();
            double longitude = mLastLocation.getLongitude();
            txtCoordinates.setText(latitude + ", " + longitude);
        } else
            txtCoordinates.setText("Couldn't get the location. Make sure location is enable on the device");

    }

    @SuppressLint("RestrictedApi")
    private void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(FATEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setSmallestDisplacement(DISPLACEMENT);

    }

    private synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();

        //Fix first time run app if permission doesn't grant yet so can't get anything
        mGoogleApiClient.connect();


    }

    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this, PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Toast.makeText(getApplicationContext(), "This device is not supported", Toast.LENGTH_LONG).show();
                finish();
            }
            return false;
        }
        return true;
    }

    private void startLocationUpdates() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
    }

    private void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient,this);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        displayLocation();
        if(mRequestingLocationUpdates)
            startLocationUpdates();
    }



    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        displayLocation();
    }
}
