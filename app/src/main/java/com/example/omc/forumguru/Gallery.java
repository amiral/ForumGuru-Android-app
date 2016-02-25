package com.example.omc.forumguru;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Gallery extends CustomActivity {

    ParseUser obj=ParseUser.getCurrentUser();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_gallery, frameLayout);
        mDrawerList.setItemChecked(position, true);

        if(obj==null) {
            startActivity(new Intent(Gallery.this, Index.class));
        }




        ParseQuery<ParseObject> imagesQuery = new ParseQuery<>("img");
        imagesQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(final List<ParseObject> images, ParseException e) {
                if (e == null) {

                    android.widget.Gallery gallery = (android.widget.Gallery) findViewById(R.id.gallery1);
                    gallery.setAdapter(new ImageAdapter(Gallery.this, images));
                    gallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                            ParseObject object = images.get(position);

                            // display the images selected
                            ImageView imageView = (ImageView) findViewById(R.id.image1);

                            Picasso.with(Gallery.this.getApplicationContext()).
                                    load(object.getParseFile("imageContent").getUrl()).
                                    noFade().into(imageView);


                        }
                    });
                } else {
                    Toast.makeText(Gallery.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

    }
    public class ImageAdapter extends BaseAdapter {
        private Context context;
        private int itemBackground;
        private List<ParseObject> mImages;

        public ImageAdapter(Context c, List<ParseObject> images) {
            context = c;
            mImages = images;

            // sets a grey background; wraps around the images
            TypedArray a = obtainStyledAttributes(R.styleable.MyGallery);
            itemBackground = a.getResourceId(R.styleable.MyGallery_android_galleryItemBackground, 0);
            a.recycle();
        }


        @Override
        public int getCount() {
            return mImages.size();
        }

        // returns the ID of an item
        public Object getItem(int position) {
            return position;
        }

        // returns the ID of an item
        public long getItemId(int position) {
            return position;
        }

        // returns an ImageView view
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView = new ImageView(context);

            ParseObject object = mImages.get(position);
            //get the image

            Picasso.with(Gallery.this.getApplicationContext()).
                    load(object.getParseFile("imageContent").getUrl()).
                    noFade().into(imageView);
            imageView.setLayoutParams(new android.widget.Gallery.LayoutParams(100, 100));
            imageView.setBackgroundResource(itemBackground);
            imageView.setLayoutParams(new android.widget.Gallery.LayoutParams(300, 450));
            return imageView;
        }


    }

    @Override
    protected void onResume() {
        super.onResume();

        ParseUser obj=ParseUser.getCurrentUser();
        if(obj==null) {
            startActivity(new Intent(Gallery.this, Login.class));
        }

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private void updateUserStatus(boolean online)
    {

        obj.put("online", online);
        obj.saveEventually();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch(id){

            case R.id.action_logout:

                updateUserStatus(false);

                Intent i=new Intent(Gallery.this,Index.class);
                startActivity(i);
                ParseUser.logOut();
                break;



        }

        return super.onOptionsItemSelected(item);
    }

}
