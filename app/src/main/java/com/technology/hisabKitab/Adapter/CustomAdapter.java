package com.technology.hisabKitab.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.technology.hisabKitab.Model.User;
import com.technology.hisabKitab.R;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {
    Context c;
    ArrayList<User> spacecrafts;

    public CustomAdapter(Context c, ArrayList<User> spacecrafts) {
        this.c = c;
        this.spacecrafts = spacecrafts;
    }

    @Override
    public int getCount() {
        return spacecrafts.size();
    }

    @Override
    public Object getItem(int position) {
        return spacecrafts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(c).inflate(R.layout.item_custom_adapter, parent, false);
        }

        TextView nameTxt = (TextView) convertView.findViewById(R.id.fname);
        TextView propTxt = (TextView) convertView.findViewById(R.id.lname);


        final User s = (User) this.getItem(position);

        nameTxt.setText(s.getFname());
        propTxt.setText(s.getLname());


        //ONITECLICK
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(c, s.getFname(), Toast.LENGTH_SHORT).show();
            }
        });
        return convertView;
    }
}