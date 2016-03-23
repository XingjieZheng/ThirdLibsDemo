package com.xj.rxdemo;

import android.Manifest;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Contacts;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Created by xj
 * on 2016/3/23.
 */
public class BaseActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private SimpleCursorAdapter mAdapter;
    private ListView listView;
    public static final int REQUEST_CODE_READ_CONTACT_PERMISSION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getLoaderManager().initLoader(0, null, this);

        mAdapter = new SimpleCursorAdapter(this,
                android.R.layout.simple_list_item_2, null,
                new String[]{Contacts.DISPLAY_NAME, Contacts.CONTACT_STATUS},
                new int[]{android.R.id.text1, android.R.id.text2}, 0);
    }

    @Override
    protected void onStart() {
        super.onStart();
        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(mAdapter);
    }


    @Override
    public android.content.Loader<Cursor> onCreateLoader(int id, Bundle args) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && checkSelfPermission(Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            if (shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS)) {
                Toast.makeText(this, "grant read contact permission", Toast.LENGTH_SHORT).show();
            }
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS},
                    REQUEST_CODE_READ_CONTACT_PERMISSION);
            return null;
        }

        final String[] CONTACTS_SUMMARY_PROJECTION = new String[]{
                Contacts._ID,
                Contacts.DISPLAY_NAME,
                Contacts.CONTACT_STATUS,
                Contacts.CONTACT_PRESENCE,
                Contacts.PHOTO_ID,
                Contacts.LOOKUP_KEY,
        };
        String select = "((" + Contacts.DISPLAY_NAME + " NOTNULL) AND ("
                + Contacts.HAS_PHONE_NUMBER + "=1) AND ("
                + Contacts.DISPLAY_NAME + " != '' ))";
        Log.e("select", select);
        return new

                CursorLoader(this,
                Contacts.CONTENT_URI,
                CONTACTS_SUMMARY_PROJECTION,
                select,
                null,
                Contacts.DISPLAY_NAME + " COLLATE LOCALIZED ASC");

    }

    @Override
    public void onLoadFinished(android.content.Loader<Cursor> loader, Cursor data) {
        mAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(android.content.Loader<Cursor> loader) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_CODE_READ_CONTACT_PERMISSION) {
            if (grantResults != null && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            } else {
                Toast.makeText(this, "Please agree to grant read contact permission!", Toast.LENGTH_SHORT).show();
            }

        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}
