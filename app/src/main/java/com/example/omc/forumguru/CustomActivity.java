package com.example.omc.forumguru;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;

import com.parse.ParseUser;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CustomActivity extends FragmentActivity implements View.OnClickListener
{

   
    public static final TouchEffect TOUCH = new TouchEffect();

    @Override
    public void setContentView(int layoutResID)
    {
        super.setContentView(layoutResID);

    }



    public View setTouchNClick(int id)
    {

        View v = setClick(id);
        if (v != null)
            v.setOnTouchListener(TOUCH);
        return v;
    }
    public View setClick(int id)
    {

        View v = findViewById(id);
        if (v != null)
            v.setOnClickListener(this);
        return v;
    }


    @Override
    public void onClick(View v)
    {

    }



    protected FrameLayout frameLayout;


    protected ListView mDrawerList;

    protected ArrayList<Items> _items;

    protected static int position;

    private static boolean isLaunch = true;


    private DrawerLayout mDrawerLayout;


    private ActionBarDrawerToggle actionBarDrawerToggle;


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_drawer_base_layout);

        frameLayout = (FrameLayout)findViewById(R.id.content_frame);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        _items = new ArrayList<Items>();
        _items.add(new Items("Home", R.drawable.ic_home));

        _items.add(new Items("My account", R.drawable.us));
        _items.add(new Items("Forum", R.drawable.forum));
        _items.add(new Items("Mytopics ", R.drawable.mt));

        _items.add(new Items("Add topic", R.drawable.ic_post));
        _items.add(new Items("Blog", R.drawable.blog));
        _items.add(new Items("Myposts", R.drawable.myposts));

        _items.add(new Items("Compose",  R.drawable.ic_compose));
        _items.add(new Items("Gallery", R.drawable.ic_gallery));
        _items.add(new Items("Add Image",  R.drawable.add));
        _items.add(new Items("Faq", R.drawable.faq));
        _items.add(new Items("Terms and Conditions ",  R.drawable.tc));
        _items.add(new Items("Contact ",  R.drawable.cus));
        _items.add(new Items("About us ", R.drawable.aus));
        _items.add(new Items("Logout", R.drawable.logout));


        //Adding header on list view
        View header = (View)getLayoutInflater().inflate(R.layout.list_view_header_layout, null);
        mDrawerList.addHeaderView(header);

        // set up the drawer's list view with items and click listener
        mDrawerList.setAdapter(new NavigationDrawerListAdapter(this, _items));
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                openActivity(position);
            }
        });

        // enable ActionBar app icon to behave as action to toggle nav drawer



        getActionBar().setHomeAsUpIndicator(R.drawable.back_arrow);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        getActionBar().setHomeButtonEnabled(true);

        // ActionBarDrawerToggle ties together the the proper interactions between the sliding drawer and the action bar app icon
        actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,						/* host Activity */
                mDrawerLayout, 				/* DrawerLayout object */
                R.drawable.back_arrow,     /* nav drawer image to replace 'Up' caret */
                R.string.open_drawer,       /* "open drawer" description for accessibility */
                R.string.close_drawer)      /* "close drawer" description for accessibility */
        {
            @Override
            public void onDrawerClosed(View drawerView) {
                getActionBar();
                invalidateOptionsMenu();
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                getActionBar();
                invalidateOptionsMenu();
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
            }

            @Override
            public void onDrawerStateChanged(int newState) {
                super.onDrawerStateChanged(newState);
            }
        };
        mDrawerLayout.setDrawerListener(actionBarDrawerToggle);


        if(isLaunch){

            isLaunch = false;
            openActivity(1);
        }
    }


    protected void openActivity(int position) {

//		mDrawerList.setItemChecked(position, true);
//		setTitle(listArray[position]);
        mDrawerLayout.closeDrawer(mDrawerList);
        CustomActivity.position = position; //Setting currently selected position in this field so that it will be available in our child activities.

        switch (position) {
            case 0:
                //startActivity(new Intent(this,Status.class));
                break;
            case 2:
                startActivity(new Intent(this,Status.class));
                break;
            case 4:
                startActivity(new Intent(this,Myqueries.class));
                break;
            case 1:
                startActivity(new Intent(this, UserList.class));
                break;
            case 3:
                startActivity(new Intent(this,Forum.class));
                break;
            case 6:
                startActivity(new Intent(this, Posts.class));
                break;
            case 7:
                startActivity(new Intent(this, Myposts.class));
                break;
            case 8:
                startActivity(new Intent(this, Compose.class));
                break;
            case 5:
                startActivity(new Intent(this, Createtopic.class));
                break;
            case 9:
                startActivity(new Intent(this, Gallery.class));
                break;
            case 10:
                startActivity(new Intent(this, Uploadimage.class));
                break;
            case 11:
                startActivity(new Intent(this, Faq.class));
                break;
            case 12:
                startActivity(new Intent(this, terms.class));
                break;
            case 13:
                startActivity(new Intent(this, Contact.class));
                break;
            case 14:
                startActivity(new Intent(this, Aboutus.class));
                break;
            case 15:
                startActivity(new Intent(this, Loguot.class));
                break;
            default:
                break;
        }

//		Toast.makeText(this, "Selected Item Position::"+position, Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);

        return super.onPrepareOptionsMenu(menu);
    }


}
