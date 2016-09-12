package com.example.nerv.nfc_door_opener;

import android.nfc.Tag;
import android.nfc.cardemulation.HostApduService;
import android.nfc.tech.IsoDep;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;

public class TagEmulationService extends HostApduService
{
    static  TagEmulationService Inst;
    public static TagEmulationService inst()
    {
        if(Inst!=null)
            return  Inst;
        else return new TagEmulationService();
    }

    public TagEmulationService()
    {
        Inst = this;
    }

    Tag tagToEmulate;
    public void Reset()
    {
        tagToEmulate = TagsStorage.Current;
        Toast.makeText(MainActivity.inst,"Start emulating tag "+ tagToEmulate.getId().toString(),Toast.LENGTH_SHORT).show();
    }
    @Override
    public byte[] processCommandApdu(byte[] apdu, Bundle extras) {

        IsoDep tag = IsoDep.get(tagToEmulate);
        Log.i("NFC","recieved command "+apdu.toString());
        Toast.makeText(MainActivity.inst,"recieved command "+apdu.toString(),Toast.LENGTH_LONG).show();
        byte[] result = {(byte) 0x00};
        try {
            tag.connect();
            tag.setTimeout(5000);
            result = tag.transceive(apdu);
            Toast.makeText(MainActivity.inst,"NFC Result "+ result.toString(),Toast.LENGTH_LONG).show();
            Log.i("NFC","NFC Error "+ result.toString());
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(MainActivity.inst,"NFC Error "+ e.toString(),Toast.LENGTH_LONG).show();
            Log.i("NFC","NFC Error "+ e.toString());
        }
        return  result;
    }
    @Override
    public void onDeactivated(int reason) {
        Toast.makeText(MainActivity.inst,"NFC Deactivated, reason "+ reason,Toast.LENGTH_LONG).show();
        Log.i("NFC","NFC Deactivated, reason "+ reason);
    }
}