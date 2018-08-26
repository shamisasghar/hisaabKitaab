package com.technology.hisabKitab.fragments;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.technology.hisabKitab.Model.User;
import com.technology.hisabKitab.R;
import com.technology.hisabKitab.toolbox.ToolbarListener;
import com.technology.hisabKitab.utils.LoginUtils;

public class AddPersonFragment extends Fragment implements View.OnClickListener {
    Dialog Add_person;
    EditText dialog_fname, dialog_lname;
    Button btn_submit;
    String fname, lname;
    View view;
    Context mContext;
    DatabaseReference databaseReference;
    FloatingActionButton floatingActionButton;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ToolbarListener) {
            ((ToolbarListener) context).setTitle("Add person");
        }
        mContext=context;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
//        if (!EventBus.getDefault().isRegistered(this))
//            EventBus.getDefault().register(this);
    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof ToolbarListener) {
//            ((ToolbarListener) context).setTitle("Add Person");
//        }
//    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        databaseReference = FirebaseDatabase.getInstance().getReference().child(LoginUtils.getUserEmail(getContext())).child("Person");

        view= inflater.inflate(R.layout.fragment_add_person, container, false);
        floatingActionButton=(FloatingActionButton)view.findViewById(R.id.fab_add);


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               showDialog();

            }
        });



        return view;
    }


    @Override
    public void onClick(View view)
    {



    }


    public void showDialog() {
        Add_person = new Dialog(getContext());
        Add_person.setContentView(R.layout.dialog_add_person);
        dialog_fname = (EditText) Add_person.findViewById(R.id.edittext_fname);
        dialog_lname = (EditText) Add_person.findViewById(R.id.edittext_lname);
        btn_submit = (Button) Add_person.findViewById(R.id.button_submit);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SaveData();


            }
        });


        Add_person.setCanceledOnTouchOutside(true);
        Add_person.show();
        Add_person.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Add_person.setCancelable(true);

    }

    public void SaveData() {
        fname = dialog_fname.getText().toString().trim();
        lname = dialog_lname.getText().toString().trim();
        if(TextUtils.isEmpty(fname))
        {
            Toast.makeText(getContext(), "enter first name ", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(lname))
        {
            Toast.makeText(getContext(), "enter last name ", Toast.LENGTH_SHORT).show();

        }
        else {
            String id=databaseReference.push().getKey();

            User user = new User(id,fname,lname);
            databaseReference.child(id).setValue(user);
            Toast.makeText(getContext(), "data added", Toast.LENGTH_SHORT).show();
            getActivity().finish();
        }

    }

}
