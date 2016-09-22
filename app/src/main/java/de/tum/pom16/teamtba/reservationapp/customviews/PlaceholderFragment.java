package de.tum.pom16.teamtba.reservationapp.customviews;

/**
 * Created by evisa on 9/22/16.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.tum.pom16.teamtba.reservationapp.R;
import de.tum.pom16.teamtba.reservationapp.models.Restaurant;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    public static final String ARG_SECTION_NUMBER = "section_number";

    public PlaceholderFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static PlaceholderFragment newInstance(int sectionNumber, Restaurant restaurant) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //getArguments() returns a Bundle, defined at Fragment class
        int fragmentResource = getFragmentResource(getArguments());
        if (fragmentResource != 0) {
            View fragmentView = inflater.inflate(fragmentResource, container, false);
            return fragmentView;
        }

//            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
//            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
        return null;
    }

    private int getFragmentResource(Bundle bundle) {
        int sectionNr = bundle.getInt(PlaceholderFragment.ARG_SECTION_NUMBER);
        switch(sectionNr) {
            case 1:
                return R.layout.content_scrolling;
            case 2:
                return R.layout.fragment_restuarant_reviews;
            case 3:
                return R.layout.fragment_restaurant_reservations;
        }

        return 0;
    }
}