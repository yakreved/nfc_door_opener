package com.example.nerv.nfc_door_opener;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.nerv.nfc_door_opener.MainActivity;
import com.example.nerv.nfc_door_opener.R;
import com.example.nerv.nfc_door_opener.TagHolder;

import java.util.List;

/**
 * Created by Nerv on 11-Sep-16.
 */
public class TagsListAdapter extends BaseAdapter
{
    List<TagHolder> tagsList;
    Context context;
    private static LayoutInflater inflater=null;

    public TagsListAdapter(MainActivity activity, List<TagHolder> TagsList)
    {
        tagsList = TagsList;
        context = activity;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return tagsList.size();
    }

    @Override
    public Object getItem(int position) {
        return tagsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return tagsList.indexOf(position);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        View rowView = inflater.inflate(R.layout.sample_tag_in_list, null);
        TextView nameText = (TextView) rowView.findViewById(R.id.name);
        Button RunButton = (Button)rowView.findViewById(R.id.RunButton);
        Button DeleteButton = (Button)rowView.findViewById(R.id.DeleteButton);

        nameText.setText(tagsList.get(position).Name);

        RunButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tagsList.get(position).RunEmulation();
            }
        });

        DeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tagsList.remove(position);
            }
        });
        return rowView;
    }
}
