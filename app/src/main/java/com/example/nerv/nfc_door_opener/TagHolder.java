package com.example.nerv.nfc_door_opener;

import android.nfc.Tag;

/**
 * Created by Nerv on 11-Sep-16.
 */
public class TagHolder
{
    public String Name;

    public Tag tag;

    public TagHolder(Tag t)
    {
        tag = t;
        Name = tag.toString().substring(0,10);
    }

    public void RunEmulation()
    {
        TagsStorage.Current = tag;
        TagEmulationService.inst().Reset();
    }
}
