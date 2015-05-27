package fr.re21.easypark;


import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

import fr.re21.easypark.fragments.FreePlaceFragment;
import fr.re21.easypark.fragments.HomeFragment;
import fr.re21.easypark.fragments.MyPlaceFragment;
import fr.re21.easypark.fragments.PoliceFragment;


public class MainActivity extends ActionBarActivity
        implements NavigationDrawerCallbacks, View.OnClickListener, OnMapReadyCallback {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private Toolbar mToolbar;
    private ArrayList<Fragment> fragmentList;
    private int fragmentPos;

    private SupportMapFragment map;
    private GoogleMap googleMap;
    private MaterialDialog mt;

    private final double lat=48.29881172611295, lng=4.0776872634887695;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fragmentList = new ArrayList<>();
        fragmentList.add(new HomeFragment());
        fragmentList.add(new FreePlaceFragment());
        fragmentList.add(new MyPlaceFragment());
        fragmentList.add(new PoliceFragment());
        fragmentPos=-1;

        setContentView(R.layout.activity_main);
        mToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(mToolbar);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.fragment_drawer);

        // Set up the drawer.
        mNavigationDrawerFragment.setup(R.id.fragment_drawer, (DrawerLayout) findViewById(R.id.drawer), mToolbar);
        // populate the navigation drawer
        mNavigationDrawerFragment.setUserData("John Doe", "johndoe@doe.com", BitmapFactory.decodeResource(getResources(), R.drawable.avatar));



    }

    public void policeSeenDialog(){
        if(mt==null) {
            mt = new MaterialDialog.Builder(this)
                    .title("Je les ai vu !")
                    .customView(R.layout.dialog_layout, false)
                    .positiveText("OK")
                    .negativeText("Annuler")
                    .positiveColorRes(R.color.myPrimaryColor)
                    .negativeColorRes(R.color.myPrimaryColor)
                    .show();
        }else{
            mt.show();
        }

       if(map == null) {
           map = (SupportMapFragment) getSupportFragmentManager()
                   .findFragmentById(R.id.police_seen_map);
           map.getMapAsync(this);
       }
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        changeFragment(position);
    }


    @Override
    public void onBackPressed() {
        if (mNavigationDrawerFragment.isDrawerOpen()){
            mNavigationDrawerFragment.closeDrawer();
        } else {
            if(fragmentPos==0){
                super.onBackPressed();
            } else {
                changeFragment(0);
                mNavigationDrawerFragment.setDrawerPosition(0);
            }
        }

    }

    @Override
    public void onClick(View v) {




    }

    public void changeDrawerPosition(int position){
        mNavigationDrawerFragment.setDrawerPosition(position);
    }

    public void changeFragment(int position){
        if(fragmentPos!=position){
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.setCustomAnimations(R.animator.slide_in, R.animator.slide_out);
            transaction.replace(R.id.container, fragmentList.get(position));
            transaction.commit();
            if(position==0){
                if(mToolbar!=null) mToolbar.setTitle(R.string.fragment_home);
            } else if(position==1){
                if(mToolbar!=null) mToolbar.setTitle(R.string.fragment_free_place);
            } else if(position==2){
                if(mToolbar!=null) mToolbar.setTitle(R.string.fragment_my_place);
            } else if(position==3){
                if(mToolbar!=null) mToolbar.setTitle(R.string.fragment_police);
            }
            fragmentPos=position;
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap=googleMap;
        googleMap.setMyLocationEnabled(true);
        googleMap.getUiSettings().setMapToolbarEnabled(false);
        googleMap.getUiSettings().setMyLocationButtonEnabled(false);

        CameraPosition cameraPosition = new CameraPosition.Builder().target(
                new LatLng(lat, lng)).zoom(16).build();
        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        CircleOptions circleOptions = new CircleOptions()
                .center(new LatLng(lat, lng))   //set center
                .radius(50)   //set radius in meters
                .fillColor(getResources().getColor(R.color.circle_solid))  //default
                .strokeColor(getResources().getColor(R.color.circle_stroke))
                .strokeWidth(5);
        googleMap.addCircle(circleOptions);
    }


    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/


}
