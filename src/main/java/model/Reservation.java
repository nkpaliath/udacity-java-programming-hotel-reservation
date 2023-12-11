package model;

import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;

public class Reservation {
    private final Customer customer;
    private final IRoom room;
    private final Date checkInDate;
    private final Date checkOutDate;
    private final double totalPrice;

    public Reservation(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
        this.customer = customer;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.totalPrice = calculateTotalPrice();
    }

    public final Customer getCustomer() {
        return customer;
    }

    public final IRoom getRoom() {
        return room;
    }

    public final Date getCheckInDate() {
        return checkInDate;
    }

    public final Date getCheckOutDate() {
        return checkOutDate;
    }

    private double calculateTotalPrice() {
        return calculateTotalDays() * room.getRoomPrice();
    }

    private long calculateTotalDays() {
        return ChronoUnit.DAYS.between(checkInDate.toInstant(), checkOutDate.toInstant());
    }

    public final double getTotalPrice() {
        return totalPrice;
    }

    @Override
    public final String toString() {
        return "Name: " + customer.getFirstName() + " " + customer.getLastName() + "\n" +
                "Email: " + customer.getEmail() + "\n" +
                "Room number: " + room.getRoomNumber() + "\n" +
                "Room type: " + room.getRoomType() + "\n" +
                "Check-in date: " + checkInDate + "\n" +
                "Check-out date: " + checkOutDate + "\n" +
                "Total price (for " + calculateTotalDays() + " day(s)): " + totalPrice;
    }

}

class ReservationTester {
    public static void main(String[] args) {
        Customer customer = new Customer("John", "Doe", "jdoe@example.com");
        IRoom room = new Room("3", 35, RoomType.DOUBLE);
        Calendar today = Calendar.getInstance();
        Date checkInDate = today.getTime();
        today.add(Calendar.DAY_OF_MONTH, 2);
        Date checkOutDate = today.getTime();
        Reservation reservation = new Reservation(customer, room, checkInDate, checkOutDate);
        System.out.println(reservation);
    }
}