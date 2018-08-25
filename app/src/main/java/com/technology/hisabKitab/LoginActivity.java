package com.technology.hisabKitab;

import android.content.Intent;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.technology.hisabKitab.utils.LoginUtils;

public class LoginActivity  extends AppCompatActivity {
    Button btn_sign;
    private EditText edit_email;
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        btn_sign=(Button)findViewById(R.id.button_sign);
        edit_email =(EditText)findViewById(R.id.edit_cell_number);

//        progressBar.setVisibility(View.GONE);
        if (LoginUtils.isUserLogin(getApplicationContext())) {
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        } else {
         //   Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
            btn_sign.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    submit();
                }
            });
        }

    }

    public void submit()
    {
        LoginUtils.saveUserEmail(LoginActivity.this,edit_email.getText().toString());
        LoginUtils.userLoggedIn(LoginActivity.this);
        databaseReference = FirebaseDatabase.getInstance().getReference().child(edit_email.getText().toString());
       // databaseReference.child("shamis").setValue(edit_email.getText().toString());
        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();

    }




}




