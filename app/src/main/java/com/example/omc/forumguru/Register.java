package com.example.omc.forumguru;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.io.ByteArrayOutputStream;

public class Register extends Activity
{


    private EditText user;
    private EditText pwd;
    private EditText email;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);



        user = (EditText) findViewById(R.id.user);
        pwd = (EditText) findViewById(R.id.pwd);
        email = (EditText) findViewById(R.id.email);
    }

    public void onClick(View v)
    {


        String u = user.getText().toString();
        String p = pwd.getText().toString();
        String e = email.getText().toString();
        if (u.length() == 0 || p.length() == 0 || e.length() == 0)
        {
            Utils.showDialog(this, R.string.err_fields_empty);
            return;
        }
        final ProgressDialog dia = ProgressDialog.show(this, null,

                getString(R.string.alert_wait));

        final ParseUser pu = new ParseUser();
        pu.setEmail(e);
        pu.setPassword(p);
        pu.setUsername(u);
        pu.put("status","Hey i am using ForumGuru");



        pu.signUpInBackground(new SignUpCallback() {

            @Override
            public void done(ParseException e)
            {
                dia.dismiss();
                if (e == null)
                {

                    startActivity(new Intent(Register.this, push.class));
                    setResult(RESULT_OK);
                    finish();
                }
                else
                {
                    Utils.showDialog(
                            Register.this,
                            getString(R.string.err_singup) + " "
                                    + e.getMessage());
                    e.printStackTrace();
                }
            }
        });

    }
}
