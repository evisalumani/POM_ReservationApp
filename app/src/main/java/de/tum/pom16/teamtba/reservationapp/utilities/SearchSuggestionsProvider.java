package de.tum.pom16.teamtba.reservationapp.utilities;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.os.Parcelable;
import android.support.annotation.Nullable;


/**
 * Created by evisa on 9/9/16.
 */
public class SearchSuggestionsProvider extends ContentProvider{
    private static final String AUTHORITY = "de.tum.pom16.teamtba.reservationapp.utilities.SearchSuggestionsProvider";
    private static final String PATH = "RestaurantNames";
    public static final Uri RESTAURANTS_URI = Uri.parse("content://" + AUTHORITY + "/" + PATH);

    final static int RESTAURANT = 1;

    private final static UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, PATH, RESTAURANT);
    }


    @Override
    public boolean onCreate() {
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        String[] columnNames = { "_id", "restaurantName" };
        MatrixCursor cursor = new MatrixCursor(columnNames);

        if (selectionArgs != null && selectionArgs.length > 0) {
            String querySubmitted = selectionArgs[0];
            //DataSea
        }
        cursor.addRow(new String[]{"1", "Tantris"});
        cursor.addRow(new String[] {"2", "Hofbräuhaus"});

       // String querySubmitted = selectionArgs[0];

        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
