package model;

import java.text.SimpleDateFormat;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class Reservation {
    private final Customer customer;
    private final IRoom room;
    private final Date checkInDate;
    private final Date checkOutDate;
    private double totalPrice;

    public Reservation(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
        this.customer = customer;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.totalPrice = calculateTotalPrice();
    }

    public static String formatDate(Date dateToFormat) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("M/d/yyyy");
        return simpleDateFormat.format(dateToFormat);
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

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public final String toString() {
        return "Name: " + customer.getFirstName() + " " + customer.getLastName() + "\n" +
                "Email: " + customer.getEmail() + "\n" +
                "Room number: " + room.getRoomNumber() + "\n" +
                "Room type: " + room.getRoomType() + "\n" +
                "Check-in date: " + formatDate(checkInDate) + "\n" +
                "Check-out date: " + formatDate(checkOutDate) + "\n" +
                "Total price (for " + calculateTotalDays() + " day(s)): " + totalPrice;
    }

}

class ReservationTester {
    public static void main(String[] args) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("M/d/yyyy");

        System.out.println(simpleDateFormat.format(new Date()));
    }
}