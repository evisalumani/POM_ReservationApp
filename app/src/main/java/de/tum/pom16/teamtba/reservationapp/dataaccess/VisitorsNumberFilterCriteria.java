package de.tum.pom16.teamtba.reservationapp.dataaccess;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import java.util.List;
import de.tum.pom16.teamtba.reservationapp.models.Restaurant;
import de.tum.pom16.teamtba.reservationapp.models.Table;

/**
 * Created by evisa on 7/12/16.
 */
public class VisitorsNumberFilterCriteria extends FilterCriteria {
    public VisitorsNumberFilterCriteria(Object criteria) {
        super(criteria);
    }

    @Override
    public boolean filter(Restaurant restaurant) {
        List<Table> fittingTables = getTablesForVisitors(restaurant);
        return (fittingTables != null && fittingTables.size() > 0);
    }

    public List<Table> getTablesForVisitors(Restaurant restaurant) {
       return Stream.of(restaurant.getTables()).filter(table -> table.getCapacity() >= (int)criteria).collect(Collectors.toList());
    }
}
