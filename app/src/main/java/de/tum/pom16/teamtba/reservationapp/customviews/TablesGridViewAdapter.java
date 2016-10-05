package de.tum.pom16.teamtba.reservationapp.customviews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import de.tum.pom16.teamtba.reservationapp.R;
import de.tum.pom16.teamtba.reservationapp.models.Table;

/**
 * Created by evisa on 10/5/16.
 */
public class TablesGridViewAdapter extends BaseAdapter {
    private Context context;
    private List<Table> tables;

    public TablesGridViewAdapter(Context context, List<Table> tables) {
        this.context = context;
        this.tables = tables;
    }

    @Override
    public int getCount() {
        return tables == null ? 0 : tables.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //get table at "position"
        Table table = tables.get(position);

        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(R.layout.grid_element_table, null);
        }

        TextView nrOfPeopleTextView = (TextView)convertView.findViewById(R.id.grid_table_nrOfPeople_textview);
        nrOfPeopleTextView.setText(table != null ? "Table for " + String.valueOf(table.getCapacity()) : "No table here");

        return convertView;
    }
}
