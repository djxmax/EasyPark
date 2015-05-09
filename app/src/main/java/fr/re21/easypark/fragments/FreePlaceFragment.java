package fr.re21.easypark.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import fr.re21.easypark.R;

/**
 * Created by maxime on 08/05/15.
 */
public class FreePlaceFragment extends Fragment {

    private GoogleMap map;
    private SupportMapFragment mMapFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_free_place,container, false);


        mMapFragment = SupportMapFragment.newInstance();
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.free_place_map, mMapFragment);
        fragmentTransaction.commit();

        // add a marker
        map = mMapFragment.getMap();


        return view;
    }

    public void setPlace(){
        //TODO bug de recupération de la map, nullpointerexception
        /*map = mMapFragment.getMap();
        System.out.println("Coucou !");
        if (map != null) {
            map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
            //map.addMarker(new MarkerOptions().position(target));
            map.setMyLocationEnabled(true);
            CameraPosition cameraPosition = new CameraPosition.Builder().target(
                    new LatLng(48.29881172611295, 4.0776872634887695)).zoom(12).build();
            map.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        } else {
            System.out.println("youp !");
        }*/
    }

}
