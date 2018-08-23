package com.technology.hisabKitab.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.technology.hisabKitab.Model.AddEntery;
import com.technology.hisabKitab.R;

import java.util.ArrayList;

public class HistoryAdapter extends BaseAdapter {
    Context c;
    ArrayList<AddEntery> users;
    Dialog specfic_item;
    TextView title,remarks,total_amount,each_amount,person_included,payed_by;
    Button btn_done;

    public HistoryAdapter(Context c, ArrayList<AddEntery> spacecrafts) {
        this.c = c;
        this.users = spacecrafts;
    }

    @Override
    public int getCount() {

        return users.size();
    }

    @Override
    public Object getItem(int position) {
        return users.get(position);
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


        final AddEntery s = (AddEntery) this.getItem(position);


        nameTxt.setText(s.getDate());
       // propTxt.setText(s.getLname());


        //ONITECLICK
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showdialog(s.getDate(),s.getPayed_person_name(),s.getTotal_amount(),s.getEach_amount(),s.getSelected_person(),s.getRemarks());

            }
        });
        return convertView;
    }

    private void showdialog(final String date, String payed_person, String total_Amount,String Each_amount,String selected_person,String Remarks)
    {
        specfic_item = new Dialog(c);
        specfic_item.setContentView(R.layout.dialog_specfic_item);
        title = (TextView) specfic_item.findViewById(R.id.txt_title);
        payed_by= (TextView) specfic_item.findViewById(R.id.txt_payed_by);
        total_amount = (TextView) specfic_item.findViewById(R.id.txt_total_amount);
        each_amount = (TextView) specfic_item.findViewById(R.id.txt_each_amount);
        person_included = (TextView) specfic_item.findViewById(R.id.txt_person_included);
        remarks = (TextView) specfic_item.findViewById(R.id.txt_remarks);
        btn_done = (Button) specfic_item.findViewById(R.id.button_done);

        title.setText(date);
        payed_by.setText(payed_person);
        total_amount.setText(total_Amount);
        each_amount.setText(Each_amount);
        person_included.setText(selected_person);
        remarks.setText(Remarks);

        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                specfic_item.dismiss();
            }
        });

        specfic_item.setCanceledOnTouchOutside(true);
        specfic_item.show();
        specfic_item.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        specfic_item.setCancelable(false);

    }

//    private boolean deleteArtist(String id) {
//        //getting the specified artist reference
//        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("Person").child(id);
//
//        //removing artist
//        dR.removeValue();
//
//        Toast.makeText(c, "User Deleted", Toast.LENGTH_LONG).show();
//
//        return true;
//    }
//
//    private boolean updateArtist(String id,String Fname,String Lname) {
//        //getting the specified artist reference
//        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("Person").child(id);
//
//        //updating artist
//        User artist = new User(id,Fname,Lname);
//        dR.setValue(artist);
//        Toast.makeText(c, "User Updated", Toast.LENGTH_LONG).show();
//        return true;
//    }



}