//package de.tum.pom16.teamtba.reservationapp.customviews;
//
//import android.app.Fragment;
//import android.database.MatrixCursor;
//import android.os.Bundle;
//import android.provider.BaseColumns;
//import android.support.v4.view.MenuItemCompat;
//import android.support.v7.widget.SearchView;
//import android.view.Menu;
//import android.view.MenuInflater;
//import android.widget.CursorAdapter;
//import android.widget.SimpleCursorAdapter;
//
//import com.annimon.stream.Collectors;
//import com.annimon.stream.Stream;
//
//import java.util.List;
//
//import de.tum.pom16.teamtba.reservationapp.R;
//import de.tum.pom16.teamtba.reservationapp.dataaccess.DataGenerator;
//import de.tum.pom16.teamtba.reservationapp.dataaccess.DataSearch;
//
///**
// * Created by evisa on 9/9/16.
// */
//public class SearchSuggestionsFragment extends Fragment {
//    private static final List<String> restaurantNamesSuggestions = Stream.of(DataGenerator.generateDummyData()).map(restaurant -> restaurant.getName()).collect(Collectors.toList());
//
//    private SimpleCursorAdapter cursorAdapter;
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        final String[] from = new String[] {"restaurantName"};
//        final int[] to = new int[] {android.R.id.text1};
//
//        cursorAdapter = new SimpleCursorAdapter(getActivity(), android.R.layout.simple_list_item_1, null, from, to, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
//    }
//
//    @Override
//    public void onActivityCreated(Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        setHasOptionsMenu(true);
//    }
//
//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        super.onCreateOptionsMenu(menu, inflater);
//        inflater.inflate(R.menu.menu_action_bar, menu);
//
//    }
//
//    @Override
//    public void onPrepareOptionsMenu(Menu menu) {
//        super.onPrepareOptionsMenu(menu);
//        SearchView searchView = (SearchView) MenuItemCompat
//                .getActionView(menu.findItem(R.id.menu_search));
//        searchView.setSuggestionsAdapter((android.support.v4.widget.CursorAdapter) cursorAdapter);
//        searchView.setIconifiedByDefault(false);
//        // Getting selected (clicked) item suggestion
//        searchView.setOnSuggestionListener(new SearchView.OnSuggestionListener() {
//            @Override
//            public boolean onSuggestionClick(int position) {
//                // Your code here
//                return true;
//            }
//
//            @Override
//            public boolean onSuggestionSelect(int position) {
//                // Your code here
//                return true;
//            }
//        });
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String s) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String s) {
//                populateAdapter(s);
//                return false;
//            }
//        });
//    }
//
//    // You must implements your logic to get data using OrmLite
//    private void populateAdapter(String query) {
//        final MatrixCursor c = new MatrixCursor(new String[]{ BaseColumns._ID, "cityName" });
//        for (int i=0; i<restaurantNamesSuggestions.size(); i++) {
//            if (restaurantNamesSuggestions.get(i).toLowerCase().startsWith(query.toLowerCase()))
//                c.addRow(new Object[] {i, restaurantNamesSuggestions.get(i)});
//        }
//        cursorAdapter.changeCursor(c);
//    }
//}
