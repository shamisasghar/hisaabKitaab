package com.technology.hisabKitab.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.technology.hisabKitab.Model.AddEntery;
import com.technology.hisabKitab.Model.User;
import com.technology.hisabKitab.R;
import com.technology.hisabKitab.fragments.FireBaseHelper;
import com.technology.hisabKitab.fragments.Firebase;
import com.technology.hisabKitab.utils.AppUtils;
import com.technology.hisabKitab.utils.LoginUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class CashInfoAdapter extends RecyclerView.Adapter<CashInfoAdapter.MyViewHolder> {
    Context c;
    ArrayList<User> users;
    DatabaseReference db;
    Firebase helper;
    ArrayList<AddEntery> addEnteries;



    public CashInfoAdapter(Context c, ArrayList<User> spacecrafts,ArrayList<AddEntery> addEnteries1) {
        this.c = c;
        this.users = spacecrafts;
        this.addEnteries = addEnteries1;
    }


    @Override
    public CashInfoAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cashinfo_adapter, parent, false);
        return new CashInfoAdapter.MyViewHolder(view);
    }
    String[] selected_person;
    @Override
    public void onBindViewHolder(final CashInfoAdapter.MyViewHolder holder, final int position) {
        holder.nameTxt.setText(addEnteries.get(position).getDate());
        holder.propTxt.setText(addEnteries.get(position).getRemarks());
        holder.payed_by.setText(addEnteries.get(position).getPayed_person_name());
        holder.total_amount.setText("Rs: "+addEnteries.get(position).getTotal_amount());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });


    }

    @Override
    public int getItemCount() {
        return addEnteries.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView nameTxt ;
        TextView propTxt ;
        TextView payed_by;
        TextView total_amount;


        public MyViewHolder(View itemView) {

            super(itemView);

            nameTxt = (TextView) itemView.findViewById(R.id.fname);
            propTxt = (TextView) itemView.findViewById(R.id.lname);
            payed_by = (TextView) itemView.findViewById(R.id.txt_payed_by);
            total_amount = (TextView) itemView.findViewById(R.id.txt_total_amount);

        }
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
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference().child(LoginUtils.getUserEmail(c)).child("Person").child(id);

        //removing artist
        dR.removeValue();

        Toast.makeText(c, "User Deleted", Toast.LENGTH_LONG).show();

        return true;
    }

    private boolean updateArtist(String id,String Fname,String Lname) {
        //getting the specified artist reference
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference().child(LoginUtils.getUserEmail(c)).child("Person").child(id);

        //updating artist
        User artist = new User(id,Fname,Lname);
        dR.setValue(artist);
        Toast.makeText(c, "User Updated", Toast.LENGTH_LONG).show();
        return true;
    }


}
