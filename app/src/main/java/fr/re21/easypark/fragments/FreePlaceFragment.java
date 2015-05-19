package fr.re21.easypark.fragments;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;


import fr.re21.easypark.R;

/**
 * Created by maxime on 08/05/15.
 */
public class FreePlaceFragment extends Fragment implements OnMapReadyCallback{

    private SupportMapFragment map;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_free_place,container, false);

        map = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.free_place_map);
        map.getMapAsync(this);

        return view;
    }

    //en cas de debug sur version <Lollipop
    private SupportMapFragment getMapFragment() {
        FragmentManager fm = null;

        //Log.d(TAG, "sdk: " + Build.VERSION.SDK_INT);
        //Log.d(TAG, "release: " + Build.VERSION.RELEASE);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            //Log.d(TAG, "using getFragmentManager");
            fm = getFragmentManager();
        } else {
            //Log.d(TAG, "using getChildFragmentManager");
            fm = getChildFragmentManager();
        }

        return (SupportMapFragment) fm.findFragmentById(R.id.free_place_map);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        //map.addMarker(new MarkerOptions().position(target));
        googleMap.setMyLocationEnabled(true);
        CameraPosition cameraPosition = new CameraPosition.Builder().target(
                new LatLng(48.29881172611295, 4.0776872634887695)).zoom(14).build();
        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }
}
