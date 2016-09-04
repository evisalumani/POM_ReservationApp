package de.tum.pom16.teamtba.reservationapp.customviews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import de.tum.pom16.teamtba.reservationapp.R;
import de.tum.pom16.teamtba.reservationapp.models.Table;

/**
 * Created by gani on 23/06/2016.
 */
public class TableReservationAdapter extends ArrayAdapter<Table> {
    //model
    List<Table> tables;

    public TableReservationAdapter(Context context, List<Table> tables) {
        super(context, R.layout.reservation_row, tables);
        this.tables = tables;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View filterResultRowView = inflater.inflate(R.layout.reservation_row, parent, false);

        //model
        Table table = getItem(position);

        //custom row view UI
        TextView noOfPersons = (TextView)filterResultRowView.findViewById(R.id.reservation_noOfPersons);
        TextView tableNo = (TextView)filterResultRowView.findViewById(R.id.reservation_tableNo);

        //set UI elements
        noOfPersons.setText("Capacity: " + String.valueOf(table.getCapacity()) + " person(s)");

        tableNo.setText("Table #" + String.valueOf(table.getTableId()));

        return filterResultRowView;
    }

    public void refreshTables(List<Table> tables) {
        this.tables.clear();
        this.tables.addAll(tables);
        notifyDataSetChanged();
    }

}
