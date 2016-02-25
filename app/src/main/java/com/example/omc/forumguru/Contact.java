package com.example.omc.forumguru;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class Contact extends CustomActivity {

    protected Button update;
    protected EditText sub,mes;
    final ParseUser currentuser=ParseUser.getCurrentUser();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_contact, frameLayout);
        mDrawerList.setItemChecked(position, true);
        if(currentuser==null){
            Intent i=new Intent(Contact.this,Index.class);
            startActivity(i);
            Toast.makeText(Contact.this, "You have to login to update the post", Toast.LENGTH_SHORT).show();
        }
        else {
            sub = (EditText) findViewById(R.id.c_sub);
            mes = (EditText) findViewById(R.id.c_mes);

            update = (Button) findViewById(R.id.c_submit);

            update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String text = sub.getText().toString();
                    String text1 = mes.getText().toString();

                    if (text.isEmpty() || text1.isEmpty()) {
                        AlertDialog.Builder b = new AlertDialog.Builder(Contact.this);
                        b.setMessage("Please fill all the fields");
                        b.setTitle("Oops!!!");
                        b.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                        AlertDialog alert = b.create();
                        alert.show();
                    }
                    else {

                        ParseObject obj = new ParseObject("contact");
                        obj.put("sub", text);

                        obj.put("mes",text1);
                        obj.put("userid",currentuser.getObjectId());

                        obj.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                if (e == null) {
                                    Toast.makeText(Contact.this, "Successfully submitted", Toast.LENGTH_LONG).show();
                                    Intent i = new Intent(Contact.this, UserList.class);
                                    startActivity(i);
                                } else {
                                    AlertDialog.Builder b = new AlertDialog.Builder(Contact.this);
                                    b.setMessage(e.getMessage());
                                    b.setTitle("Sorry");
                                    b.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });

                                    AlertDialog alert = b.create();
                                    alert.show();
                                }
                            }
                        });

                    }

                    }
                    });


        }

    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch(id){

            case R.id.action_logout:

                updateUserStatus(false);

                Intent i=new Intent(Contact.this,Index.class);
                startActivity(i);
                ParseUser.logOut();
                break;



        }

        return super.onOptionsItemSelected(item);
    }
    private void updateUserStatus(boolean online)
    {

        currentuser.put("online", online);
        currentuser.saveEventually();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        ParseUser obj=ParseUser.getCurrentUser();
        if(obj==null) {
            startActivity(new Intent(Contact.this, Index.class));
        }

    }


}
