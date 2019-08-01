package com.agungpermanaputra.bir;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class login extends AppCompatActivity {
    EditText editUSername, editPassword;
    TextInputLayout tiUsername, tiPassword;
    sqliteHelper sqliteHelper;
    Button btnmasuk, btndaftar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btndaftar = (Button) findViewById(R.id.btn_daftar);
        btndaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent daftar = new Intent(login.this, daftar.class);
                startActivity(daftar);
            }
        });

        sqliteHelper = new sqliteHelper(this);
        initViews();

        btnmasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Check user input is correct or not
                if (validate()) {

                   //Get values from EditText fields
                   String Email = editUSername.getText().toString();
                   String Password = editPassword.getText().toString();

                  //Authenticate user
                    User currentUser = sqliteHelper.Authenticate(new User(null, null, Email, Password));

                 //Check Authentication is successful or not
                   if (currentUser != null) {
                        Snackbar.make(btnmasuk, "Successfully Logged in!", Snackbar.LENGTH_LONG).show();

                        //User Logged in Successfully Launch You home screen activity
                        Intent intent = new Intent(login.this, bottom_navigasi.class);
                        startActivity(intent);
                        finish();

                    } else {

                        //User Logged in Failed
                        Snackbar.make(btnmasuk, "Failed to log in , please try again", Snackbar.LENGTH_LONG).show();

                    }
                }
            }
        });
    }

    //this method is used to connect XML views to its Objects
    private void initViews() {
        editUSername = (EditText) findViewById(R.id.etUsername);
        editPassword = (EditText) findViewById(R.id.etPassword);
        tiUsername = (TextInputLayout) findViewById(R.id.edt_username);
        tiPassword = (TextInputLayout) findViewById(R.id.edtPassword);
        btnmasuk = (Button) findViewById(R.id.btn_masuk);

    }

    //This method is used to validate input given by user
    public boolean validate() {
        boolean valid = false;

        //Get values from EditText fields
        String Email = editUSername.getText().toString();
        String Password = editPassword.getText().toString();

        //Handling validation for Email field
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
            valid = false;
            editUSername.setError("Please enter valid email!");
        } else {
            valid = true;
            editUSername.setError(null);
        }

        //Handling validation for Password field
        if (Password.isEmpty()) {
            valid = false;
            editPassword.setError("Please enter valid password!");
        } else {
            if (Password.length() > 5) {
                valid = true;
                editPassword.setError(null);
            } else {
                valid = false;
                editPassword.setError("Password is to short!");
            }
        }

        return valid;
    }

}



