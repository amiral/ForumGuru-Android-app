package com.example.omc.forumguru;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.text.SimpleDateFormat;
import java.util.Date;


public class Compose extends CustomActivity {
    protected Button update,pic;
    protected ImageView mPreviewImageView;
    protected EditText status,topic;
    ParseUser currentuser=ParseUser.getCurrentUser();

    public static final int TAKE_PIC_REQUEST_CODE = 0;
    public static final int CHOOSE_PIC_REQUEST_CODE = 1;
    public static final int MEDIA_TYPE_IMAGE = 2;
    private Uri mMediaUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_compose, frameLayout);
        mDrawerList.setItemChecked(position, true);


        if(currentuser==null){
            Intent i=new Intent(Compose.this,Login.class);
            startActivity(i);
            Toast.makeText(Compose.this, "You have to login to update the post", Toast.LENGTH_SHORT).show();
        }
        else {
            status = (EditText) findViewById(R.id.c_sub);
            topic = (EditText) findViewById(R.id.status1);

            update = (Button) findViewById(R.id.updatestatus);
            pic = (Button) findViewById(R.id.test);
            mPreviewImageView=(ImageView)findViewById(R.id.imv);

            pic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //show dialog
                    AlertDialog.Builder builder = new AlertDialog.Builder(Compose.this);
                    builder.setTitle("Upload a photo");
                    builder.setPositiveButton("Upload", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //upload image
                            Intent choosePictureIntent = new Intent(Intent.ACTION_GET_CONTENT);
                            choosePictureIntent.setType("image/*");
                            startActivityForResult(choosePictureIntent, CHOOSE_PIC_REQUEST_CODE);

                        }
                    });

                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            });







            update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String text = status.getText().toString();

                    if (text.isEmpty() || topic.getText().toString().isEmpty()) {
                        AlertDialog.Builder b = new AlertDialog.Builder(Compose.this);
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
                    } else {


                        try {
                            //convert image to bytes for upload.
                            byte[] fileBytes = FileHelper.getByteArrayFromFile(Compose.this, mMediaUri);
                            if (fileBytes == null) {
                                //there was an error
                                Toast.makeText(getApplicationContext(), "There was an error. Try again!", Toast.LENGTH_LONG).show();
                            } else {

                                fileBytes = FileHelper.reduceImageForUpload(fileBytes);
                                String fileName = FileHelper.getFileName(Compose.this, mMediaUri, "image");
                                final ParseFile file = new ParseFile(fileName, fileBytes);
                                SimpleDateFormat s = new SimpleDateFormat("yyyy: MM :dd _ HH :mm : ss");
                                String currentDate = s.format(new Date());
                                ParseUser user = ParseUser.getCurrentUser();
                                String name = user.getUsername();
                                ParseObject obj = new ParseObject("status");
                                obj.put("status", text);
                                obj.put("statusby", name);
                                obj.put("image", file);
                                obj.put("detail",topic.getText().toString());
                                obj.put("date",currentDate);
                                obj.put("userid",user.getObjectId());

                                obj.saveInBackground(new SaveCallback() {
                                    @Override
                                    public void done(ParseException e) {
                                        if (e == null) {
                                            Toast.makeText(Compose.this, "Post updated", Toast.LENGTH_LONG).show();
                                            Intent i = new Intent(Compose.this, Posts.class);
                                            startActivity(i);
                                        } else {
                                            AlertDialog.Builder b = new AlertDialog.Builder(Compose.this);
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

                        } catch (Exception e1) {
                            Toast.makeText(getApplicationContext(), e1.getMessage(), Toast.LENGTH_LONG).show();
                        }

                    }
                }
            });

        }
    }


    @Override
    protected void onResume()
    {
        super.onResume();
        ParseUser obj=ParseUser.getCurrentUser();

        if(obj==null) {
            startActivity(new Intent(Compose.this, Index.class));
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private void updateUserStatus(boolean online)
    {
        currentuser.put("online", online);
        currentuser.saveEventually();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();


        switch(id){

            case R.id.action_logout:
                updateUserStatus(false);
                Intent i=new Intent(Compose.this,Index.class);
                startActivity(i);
                ParseUser.logOut();
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            if(requestCode == CHOOSE_PIC_REQUEST_CODE){
                if(data == null){
                    Toast.makeText(getApplicationContext(), "Image cannot be null!", Toast.LENGTH_LONG).show();
                }
                else{
                    mMediaUri = data.getData();
                    mPreviewImageView.setImageURI(mMediaUri);

                }
            }else {

                Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                mediaScanIntent.setData(mMediaUri);
                sendBroadcast(mediaScanIntent);

                mPreviewImageView.setImageURI(mMediaUri);

            }

        }else if(resultCode != RESULT_CANCELED){
            Toast.makeText(getApplicationContext(), "Cancelled!", Toast.LENGTH_LONG).show();
        }
    }
}
