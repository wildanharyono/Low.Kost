package com.example.haryono.lowkost;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

import static java.lang.Integer.parseInt;


public class MiniContentProvider extends ContentProvider {

    private static final String TAG = MiniContentProvider.class.getSimpleName();
    public String[] mData;

   private static UriMatcher sUriMatcher = new UriMatcher( UriMatcher.NO_MATCH);


    @Override
    public boolean onCreate() {
        initializeUriMatching();
        Context context = getContext();
        mData = context.getResources().getStringArray(R.array.words);
        return true;
    }


    private void initializeUriMatching(){
      sUriMatcher.addURI( Contract.AUTHORITY, Contract.CONTENT_PATH + "/#", 1);

       sUriMatcher.addURI( Contract.AUTHORITY, Contract.CONTENT_PATH, 0);
    }


    @Nullable
    @Override
    public String getType(Uri uri) {
        switch (sUriMatcher.match(uri)) {
            case 0:
                return Contract.MULTIPLE_RECORDS_MIME_TYPE;
            case 1:
                return Contract.SINGLE_RECORD_MIME_TYPE;
            default:
                // Alternatively, throw an exception.
                return null;
        }
    }




    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        int id = -1;
        switch (sUriMatcher.match(uri)) {
            case 0:

                id = Contract.ALL_ITEMS;
                if (selection != null){
                    id = Integer.parseInt(selectionArgs[0]);
                }
                break;

            case 1:

                id = parseInt(uri.getLastPathSegment());
                break;

            case UriMatcher.NO_MATCH:
               Log.d(TAG, "NO MATCH FOR THIS URI IN SCHEME.");
                id = -1;
                break;
            default:
                Log.d(TAG, "INVALID URI - URI NOT RECOGNZED.");
                id = -1;
        }
        Log.d(TAG, "query: " + id);
        return populateCursor(id);
    }


    private Cursor populateCursor(int id) {
        MatrixCursor cursor = new MatrixCursor(new String[] { Contract.CONTENT_PATH });
        if (id == Contract.ALL_ITEMS) {
            for (int i = 0; i < mData.length; i++) {
                String word = mData[i];
                cursor.addRow(new Object[]{word});
            }
        } else if (id >= 0) {
          String word = mData[id];
           cursor.addRow(new Object[]{word});
        }
        return cursor;
    }


    @Nullable
    @Override
   public Uri insert(Uri uri, ContentValues values) {
        Log.e(TAG, "Not implemented: insert uri: " + uri.toString());
        return null;
    }


    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        Log.e(TAG, "Not implemented: delete uri: " + uri.toString());
        return 0;
    }


    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        Log.e(TAG, "Not implemented: update uri: " + uri.toString());
        return 0;



    }

}

