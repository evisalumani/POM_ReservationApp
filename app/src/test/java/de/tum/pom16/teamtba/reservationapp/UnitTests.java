//package de.tum.pom16.teamtba.reservationapp;
//
//import junit.framework.Assert;
//
//import org.junit.Test;
//
//import de.tum.pom16.teamtba.reservationapp.models.Table;
//
//import static org.junit.Assert.assertNull;
//import static org.junit.Assert.assertTrue;
//
///**
// * Created by evisa on 7/12/16.
// */
//public class UnitTests {
//
//    @Test
//    public void testVisitorsNumberFilter() {
//        //restaurant with 3 tables, having capacity 2, 3, 4
//        Restaurant restaurant = new Restaurant("Test restaurant", null, null, 48.137551, 11.57665, null, 0, 8, 24, 3);
//        int i = 2;
//        for (Table table : restaurant.getTables()) {
//            table.setTableCapacity(i);
//            i++;
//        }
//
//        //filter for 3 visitors
//        VisitorsNumberFilterCriteria filter = new VisitorsNumberFilterCriteria(3);
//        boolean isRestaurantValid = filter.filter(restaurant);
//        List<Table> filteredTables = filter.getTablesForVisitors(restaurant);
//
//        Assert.assertTrue(isRestaurantValid); //restaurant has tables for 3 people
//        Assert.assertNotNull(filteredTables);
//        Assert.assertEquals(2, filteredTables.size()); //exactly 2 tables
//        Assert.assertEquals(3, filteredTables.get(0).getCapacity());
//        Assert.assertEquals(4, filteredTables.get(1).getCapacity());
//    }
//
//    @Test
//    public void testCalendarEventCreation() {
//        Table table = new Table(1, 2); //id, capacity
//        table.setOpeningHour(10);
//        table.setClosingHour(13);
//        //3 opening hours with reservation status for this particular table
//        table.setReservationStatus(new boolean[] { Boolean.FALSE, Boolean.TRUE, Boolean.FALSE});
//
//        try {
//            //make a reservation on a valid time slot
//            CalendarEvent event = table.setReservation(0);
//            Assert.assertNotNull(event);
//            Assert.assertEquals(table.getTableId(), event.getTableNo());
//            Assert.assertEquals(table.getCapacity(), event.getNoOfPeople());
//            Assert.assertEquals(10, event.getStartTime());
//            Assert.assertEquals(11, event.getEndTime());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Test
//    public void testValidReservation() {
//        Table table = new Table(1, 2); //id, capacity
//        table.setOpeningHour(10);
//        table.setClosingHour(13);
//        table.setReservationStatus(new boolean[] { Boolean.FALSE, Boolean.FALSE, Boolean.FALSE});
//
//        try {
//            table.setReservation(0);
//            assertTrue("Table is not reserved at specific time, but should be", table.getReservation(0));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Test
//    public void testMultipleReservationsPossible() {
//        Table table = new Table(1, 2); //id, capacity
//        table.setOpeningHour(10);
//        table.setClosingHour(13);
//        table.setReservationStatus(new boolean[] { Boolean.FALSE, Boolean.FALSE, Boolean.FALSE});
//
//        try {
//            CalendarEvent event_one= table.setReservation(0);
//            assertTrue("Table is not reserved at specific time, but should be", table.getReservation(0));
//            CalendarEvent event_two= table.setReservation(0);
//            assertNull("Table can not be reserved multiple times at the same time-slot", event_two);
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Test
//    public void testOutOfOpeningHoursReservation() {
//        Table table = new Table(1, 2); //id, capacity
//        table.setOpeningHour(10);
//        table.setClosingHour(13);
//        table.setReservationStatus(new boolean[] { Boolean.FALSE, Boolean.FALSE, Boolean.FALSE});
//
//        CalendarEvent event = null;
//
//        try {
//            event= table.setReservation(7); //3 time-slots; 7 is out of bound
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//        }
//        finally {
//            assertNull("Table can not be reserved outside of possible time slots", event);
//        }
//    }
//}
