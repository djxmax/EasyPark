package fr.re21.easypark.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import fr.re21.easypark.R;

/**
 * Created by maxime on 08/05/15.
 */
public class MyPlaceFragment extends Fragment implements View.OnClickListener, OnMapReadyCallback, GoogleMap.OnMarkerClickListener, GoogleMap.OnMapClickListener {

    private SupportMapFragment map;
    private GoogleMap googleMap;
    private SlidingUpPanelLayout slidingPaneLayout;
    private LinearLayout slidingContainer;
    private TextView slidingTitle, slidingPlace, slidingAddr, slidingPrice;
    private FloatingActionButton slidingFab, positionFab;

    private final double lat=48.297053 , lng=4.076061;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_place,container, false);

        slidingPaneLayout = (SlidingUpPanelLayout) view.findViewById(R.id.my_place_sliding_layout);
        slidingPaneLayout.setTouchEnabled(false);
        slidingContainer = (LinearLayout) view.findViewById(R.id.my_place_sliding_container);
        slidingTitle = (TextView) view.findViewById(R.id.my_place_sliding_title);
        slidingPlace = (TextView) view.findViewById(R.id.my_place_sliding_place);
        slidingAddr = (TextView) view.findViewById(R.id.my_place_sliding_addr);
        slidingPrice = (TextView) view.findViewById(R.id.my_place_price);
        slidingFab= (FloatingActionButton) view.findViewById(R.id.my_place_sliding_fab);
        positionFab= (FloatingActionButton) view.findViewById(R.id.my_place_position_fab);
        slidingFab.setOnClickListener(this);
        positionFab.setOnClickListener(this);

        map = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.my_place_map);
        map.getMapAsync(this);

        showPanel(false);
        return view;
    }

    @Override
    public void onClick(View view) {
        if(view.equals(positionFab) && googleMap!=null){
            double lat = googleMap.getMyLocation().getLatitude();
            double lng = googleMap.getMyLocation().getLongitude();
            CameraUpdate center=
                    CameraUpdateFactory.newLatLng(new LatLng(lat,
                            lng));
            googleMap.animateCamera(center);
        } else if(view.equals(slidingFab)){

            Uri gmmIntentUri = Uri.parse("google.navigation:q="+lat+","+lng+"&mode=w");
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            startActivity(mapIntent);

        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        showPanel(false);
        this.googleMap=googleMap;
        googleMap.setMyLocationEnabled(true);
        googleMap.setOnMarkerClickListener(this);
        googleMap.setOnMapClickListener(this);
        googleMap.getUiSettings().setMapToolbarEnabled(false);
        googleMap.getUiSettings().setMyLocationButtonEnabled(false);

        CameraPosition cameraPosition = new CameraPosition.Builder().target(
                new LatLng(lat, lng)).zoom(18).build();
        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        MarkerOptions marker = new MarkerOptions()
                .position(new LatLng(lat, lng))
                .title("Ma Place")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
        googleMap.addMarker(marker);

        slidingTitle.setText("Ma place");
        slidingPlace.setText("10 minutes restantes");
        slidingAddr.setText("25 rue Emile Zola, 10000 Troyes");

        slidingContainer.setBackgroundResource(R.color.minute_stop_dark);
        slidingFab.setColorNormalResId(R.color.minute_stop_light);
        slidingFab.setColorPressedResId(R.color.minute_stop_dark);
        showPanel(true);
    }

    public void showPanel(boolean show){
        if(show==true){
            slidingPaneLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
            slidingFab.setVisibility(View.VISIBLE);
        } else {
            slidingPaneLayout.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
            slidingFab.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        showPanel(true);
        return false;
    }

    @Override
    public void onMapClick(LatLng latLng) {
        showPanel(false);

    }
}
