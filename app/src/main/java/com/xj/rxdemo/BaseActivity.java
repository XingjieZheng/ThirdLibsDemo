package com.xj.rxdemo;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.database.Cursor;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Contacts;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

/**
 * Created by xj
 * on 2016/3/23.
 */
public class BaseActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private SimpleCursorAdapter mAdapter;
    private ListView listView;

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
        return new CursorLoader(this,
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

}
