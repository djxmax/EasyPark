package fr.re21.easypark.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import fr.re21.easypark.MainActivity;
import fr.re21.easypark.R;

/**
 * Created by maxime on 08/05/15.
 */
public class HomeFragment extends Fragment implements View.OnClickListener {

    private LinearLayout freePlace, myPlace, police, policeSeen;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,container, false);

        freePlace = (LinearLayout) view.findViewById(R.id.home_button_free_place);
        myPlace = (LinearLayout) view.findViewById(R.id.home_button_my_place);
        police = (LinearLayout) view.findViewById(R.id.home_button_police);
        policeSeen = (LinearLayout) view.findViewById(R.id.home_button_police_seen);
        freePlace.setOnClickListener(this);
        myPlace.setOnClickListener(this);
        police.setOnClickListener(this);
        policeSeen.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        if (v.equals(freePlace)){
            ((MainActivity) getActivity()).changeFragment(1);
        } else if(v.equals(myPlace)){
            ((MainActivity) getActivity()).changeFragment(2);
        } else if(v.equals(police)){
            ((MainActivity) getActivity()).changeFragment(3);
        } else if(v.equals(policeSeen)){

        }
    }
}
