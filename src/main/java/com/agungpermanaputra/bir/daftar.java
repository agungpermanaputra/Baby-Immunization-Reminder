package com.agungpermanaputra.bir;

import android.database.sqlite.SQLiteOpenHelper;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class daftar extends AppCompatActivity {

    EditText editTextUserName, editTextEmail,editTextPassword;
    TextInputLayout tiUsername, tiEmail, tiPassword;
    Button btDaftar;
    sqliteHelper sqliteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar);
        sqliteHelper = new sqliteHelper(this);
        initViews();
        btDaftar = (Button) findViewById(R.id.btn_daftar);
        btDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()) {
                    String UserName = editTextUserName.getText().toString();
                    String Email = editTextEmail.getText().toString();
                    String Password = editTextPassword.getText().toString();

                    //Check in the database is there any user associated with  this email
                    if (!sqliteHelper.isEmailExists(Email)) {

                        //Email does not exist now add new user to database
                        sqliteHelper.addUser(new User(null, UserName, Email, Password));
                        Snackbar.make(btDaftar, "User created successfully! Please Login ", Snackbar.LENGTH_LONG).show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                finish();
                            }
                        }, Snackbar.LENGTH_LONG);
                    }else {

                        //Email exists with email input provided so show error user already exist
                        Snackbar.make(btDaftar, "User already exists with same email ", Snackbar.LENGTH_LONG).show();
                    }


                }
            }
        });
    }


    //this method is used to connect XML views to its Objects
    private void initViews() {
        editTextEmail = (EditText) findViewById(R.id.etEmail);
        editTextPassword = (EditText) findViewById(R.id.etPassword_daftar);
        editTextUserName = (EditText) findViewById(R.id.etUsername_daftar);
        tiUsername = (TextInputLayout) findViewById(R.id.edt_username);
        tiEmail = (TextInputLayout) findViewById(R.id.edt_email);
        tiPassword = (TextInputLayout) findViewById(R.id.edt_password);

    }

    //This method is used to validate input given by user
    public boolean validate() {
        boolean valid = false;

        //Get values from EditText fields
        String UserName = editTextUserName.getText().toString();
        String Email = editTextEmail.getText().toString();
        String Password = editTextPassword.getText().toString();

        //Handling validation for UserName field
        if (UserName.isEmpty()) {
            valid = false;
            tiUsername.setError("Please enter valid username!");
        } else {
            if (UserName.length() > 5) {
                valid = true;
                tiUsername.setError(null);
            } else {
                valid = false;
                tiUsername.setError("Username is to short!");
            }
        }

        //Handling validation for Email field
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
            valid = false;
            tiEmail.setError("Please enter valid email!");
        } else {
            valid = true;
            tiEmail.setError(null);
        }

        //Handling validation for Password field
        if (Password.isEmpty()) {
            valid = false;
            tiPassword.setError("Please enter valid password!");
        } else {
            if (Password.length() > 5) {
                valid = true;
                tiPassword.setError(null);
            } else {
                valid = false;
                tiPassword.setError("Password is to short!");
            }
        }


        return valid;
    }
}
