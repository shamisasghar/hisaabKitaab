package com.technology.hisabKitab.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.technology.hisabKitab.Model.User;
import com.technology.hisabKitab.R;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {
    Context c;
    ArrayList<User> users;


    public CustomAdapter(Context c, ArrayList<User> spacecrafts) {
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


        final User s = (User) this.getItem(position);


        nameTxt.setText(s.getFname());
        propTxt.setText(s.getLname());


        //ONITECLICK
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(c, s.getId(), Toast.LENGTH_SHORT).show();
                showdialog(s.getId(),s.getFname(),s.getLname());

            }
        });
        return convertView;
    }

    private void showdialog(final String id, String fname, String lname)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(c);
        LayoutInflater inflater =  LayoutInflater.from(c);
        final View dialogView = inflater.inflate(R.layout.dialog_update_delete, null);
        builder.setView(dialogView);
        final EditText editTextfName = (EditText) dialogView.findViewById(R.id.editTextFname);
        final EditText editTextlName = (EditText) dialogView.findViewById(R.id.editTextLname);
        final Button buttonUpdate = (Button) dialogView.findViewById(R.id.buttonUpdateArtist);
        final Button buttonDelete = (Button) dialogView.findViewById(R.id.buttonDeleteArtist);



        builder.setTitle("Updating: "+fname);
        final AlertDialog b = builder.create();
        b.show();
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fname = editTextfName.getText().toString().trim();
                String lname = editTextlName.getText().toString().trim();

                if (!TextUtils.isEmpty(fname)) {
                    updateArtist(id,fname,lname);
                    b.dismiss();
                }
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                deleteArtist(id);
                b.dismiss();
            }
        });



    }

    private boolean deleteArtist(String id) {
        //getting the specified artist reference
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("Person").child(id);

        //removing artist
        dR.removeValue();

        Toast.makeText(c, "User Deleted", Toast.LENGTH_LONG).show();

        return true;
    }

    private boolean updateArtist(String id,String Fname,String Lname) {
        //getting the specified artist reference
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("Person").child(id);

        //updating artist
        User artist = new User(id,Fname,Lname);
        dR.setValue(artist);
        Toast.makeText(c, "User Updated", Toast.LENGTH_LONG).show();
        return true;
    }

}