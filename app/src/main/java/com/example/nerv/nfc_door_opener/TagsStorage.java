package com.example.nerv.nfc_door_opener;

import android.nfc.Tag;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nerv on 11-Sep-16.
 */
public class TagsStorage
{
    public static final TagsStorage inst = new TagsStorage();

    public List<TagHolder> TagsList = new ArrayList<TagHolder>();

    public static Tag Current;
    public TagsStorage()
    {

    }


}
