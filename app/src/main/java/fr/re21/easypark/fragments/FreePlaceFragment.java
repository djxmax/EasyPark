package fr.re21.easypark.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fr.re21.easypark.R;

/**
 * Created by maxime on 08/05/15.
 */
public class FreePlaceFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_free_place,container, false);
        return view;
    }

}
