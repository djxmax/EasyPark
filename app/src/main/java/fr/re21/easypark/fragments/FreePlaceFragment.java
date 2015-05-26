package fr.re21.easypark.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;


import android.support.v4.widget.SlidingPaneLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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


import java.util.ArrayList;

import fr.re21.easypark.R;
import fr.re21.easypark.customInterface.ServerResponseInterface;
import fr.re21.easypark.entity.ClosedParking;
import fr.re21.easypark.entity.EntityList;

/**
 * Created by maxime on 08/05/15.
 */
public class FreePlaceFragment extends Fragment implements OnMapReadyCallback, ServerResponseInterface, GoogleMap.OnMarkerClickListener, GoogleMap.OnMapClickListener, View.OnClickListener {

    private SupportMapFragment map;
    private GoogleMap googleMap;
    private SlidingUpPanelLayout slidingPaneLayout;
    private ArrayList<Marker> closedParkingMarkerList;
    private LinearLayout slidingContainer;
    private TextView slidingTitle, slidingPlace, slidingAddr, slidingHourWeek, slidingHourSunday, privateParking;
    private FloatingActionButton slidingFab, positionFab;
    private ImageView cardPay, cashPay, coinsPay;
    private ClosedParking closedParking;


    private final double lat=48.29881172611295, lng=4.0776872634887695;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_free_place,container, false);

        slidingPaneLayout = (SlidingUpPanelLayout) view.findViewById(R.id.free_place_sliding_layout);

        slidingContainer = (LinearLayout) view.findViewById(R.id.free_place_sliding_container);
        slidingTitle = (TextView) view.findViewById(R.id.free_place_sliding_title);
        slidingPlace = (TextView) view.findViewById(R.id.free_place_sliding_place);
        slidingAddr = (TextView) view.findViewById(R.id.free_place_sliding_addr);
        slidingFab= (FloatingActionButton) view.findViewById(R.id.free_place_sliding_fab);
        positionFab= (FloatingActionButton) view.findViewById(R.id.free_place_position_fab);
        slidingFab.setOnClickListener(this);
        positionFab.setOnClickListener(this);
        slidingHourWeek = (TextView) view.findViewById(R.id.free_place_week_hour);
        slidingHourSunday = (TextView) view.findViewById(R.id.free_place_sunday_hour);
        privateParking = (TextView) view.findViewById(R.id.free_place_private);

        cardPay = (ImageView) view.findViewById(R.id.free_place_card);
        cashPay = (ImageView) view.findViewById(R.id.free_place_cash);
        coinsPay = (ImageView) view.findViewById(R.id.free_place_coins);

        map = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.free_place_map);
        map.getMapAsync(this);

        showPanel(false);
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
        showPanel(false);
        this.googleMap=googleMap;
        googleMap.setMyLocationEnabled(true);
        googleMap.setOnMarkerClickListener(this);
        googleMap.setOnMapClickListener(this);
        googleMap.getUiSettings().setMapToolbarEnabled(false);
        googleMap.getUiSettings().setMyLocationButtonEnabled(false);

        CameraPosition cameraPosition = new CameraPosition.Builder().target(
                new LatLng(lat, lng)).zoom(8).build();
        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        CameraUpdate zoom=CameraUpdateFactory.zoomTo(14);
        googleMap.animateCamera(zoom);




        ClosedParking.getCloseParkingList(EntityList.closedParkingList, this, getActivity());
    }

    private void addMarkerList(ArrayList<ClosedParking> closedParkingArrayList){
        closedParkingMarkerList = new ArrayList<>();
        for(ClosedParking parking : closedParkingArrayList){
            MarkerOptions marker = new MarkerOptions()
                    .position(new LatLng(parking.getLatitude(), parking.getLongitude()))
                    .title(parking.getName())
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
            closedParkingMarkerList.add(googleMap.addMarker(marker));


        }
    }

    @Override
    public void onEventCompleted(int method, String type) {
        if(method==ClosedParking.GET && type==ClosedParking.TYPE){
            addMarkerList(EntityList.closedParkingList);
        }
    }

    @Override
    public void onEventFailed(int method, String type) {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {

        closedParking = null;
        for(ClosedParking parking : EntityList.closedParkingList){
            if(parking.getName().equals(marker.getTitle())){
                closedParking=parking;
                System.out.println(marker.getTitle());
                break;
            }
        }
        if(closedParking!=null){
            slidingContainer.setBackgroundResource(R.color.closed_parking_dark);
            slidingFab.setColorNormalResId(R.color.closed_parking_light);
            slidingFab.setColorPressedResId(R.color.closed_parking_dark);
            slidingTitle.setText(closedParking.getName());
            slidingPlace.setText(closedParking.getRemainPlace() + "/" + closedParking.getTotalPlace() + " Places Libre");
            slidingAddr.setText(closedParking.getAdresse()+" "+closedParking.getZipCode()+" "+closedParking.getCity());
            slidingHourWeek.setText(closedParking.getWeekHour());
            slidingHourSunday.setText(closedParking.getSundayHour());
            if(closedParking.isPrivatePark()==false){
                privateParking.setVisibility(View.GONE);
            } else {
                privateParking.setVisibility(View.VISIBLE);
            }
            if(closedParking.isCard()==false){
                cardPay.setVisibility(View.GONE);
            } else {
                cardPay.setVisibility(View.VISIBLE);
            }
            if(closedParking.isCash()==false){
                cashPay.setVisibility(View.GONE);
            } else {
                cashPay.setVisibility(View.VISIBLE);
            }
            if(closedParking.isCoins()==false){
                coinsPay.setVisibility(View.GONE);
            } else {
                coinsPay.setVisibility(View.VISIBLE);
            }
        }
        showPanel(true);
        return false;
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
    public void onMapClick(LatLng latLng) {
        showPanel(false);
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
            if(closedParking!=null){
                Uri gmmIntentUri = Uri.parse("google.navigation:q="+closedParking.getLatitude()+","+closedParking.getLongitude());
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        }

    }
}
