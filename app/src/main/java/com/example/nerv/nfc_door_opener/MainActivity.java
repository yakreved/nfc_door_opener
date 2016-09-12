package com.example.nerv.nfc_door_opener;

import android.app.PendingIntent;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{
    public static MainActivity inst;


    NfcAdapter adapter;
    private PendingIntent mPendingIntent;
    ListView tagsListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        adapter = NfcAdapter.getDefaultAdapter(this);
        mPendingIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        //adapter.enableForegroundDispatch(this,mPendingIntent,null,null);
        tagsListView = (ListView) findViewById(R.id.tagsList);
        tagsListView.setAdapter(new TagsListAdapter(this, TagsStorage.inst.TagsList));
        inst= this;
        Intent serviceIntent = new Intent(this,TagEmulationService.class);
        startService(serviceIntent);
    }

    @Override
    protected void onNewIntent(Intent intent){
        processIntent(intent);
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (adapter != null) {
            adapter.enableForegroundDispatch(this,mPendingIntent,null,null);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (adapter != null) {
            adapter.disableForegroundDispatch(this);
        }
    }

    private void processIntent(Intent intent) {
        String action = intent.getAction();
        Log.d("NFC",action);
        Toast.makeText(this,action,Toast.LENGTH_LONG).show();
        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(action))
        {
            Tag tagFromIntent = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            TagsStorage.inst.TagsList.add(new TagHolder(tagFromIntent));
            if(tagsListView!=null)
                tagsListView.setAdapter(new TagsListAdapter(this, TagsStorage.inst.TagsList));
            Toast.makeText(this, tagFromIntent.toString(),Toast.LENGTH_LONG).show();
        }
          if( NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)
            || NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action))

            {
                Tag tagFromIntent = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
                Parcelable[] rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
                Toast.makeText(this, rawMsgs[0].toString(),Toast.LENGTH_LONG).show();
            }
    }

    public void OnScan()
    {

    }
}
