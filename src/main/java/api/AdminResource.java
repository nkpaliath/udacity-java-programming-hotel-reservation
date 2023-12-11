package api;

import model.Customer;
import model.IRoom;
import model.Reservation;
import service.CustomerService;
import service.ReservationService;

import java.util.Collection;

public class AdminResource {
    private static AdminResource instance;
    private final CustomerService customerService;
    private final ReservationService reservationService;

    private AdminResource() {
        this.customerService = CustomerService.getInstance();
        this.reservationService = ReservationService.getInstance();
    }

    public static AdminResource getInstance() {
        if (instance == null) {
            instance = new AdminResource();
        }

        return instance;
    }

    public Customer getCustomer(String email) {
        return customerService.getCustomer(email);
    }

    public void addRoom(Collection<IRoom> rooms) {
        for (IRoom room : rooms) {
            try {
                reservationService.addRoom(room);
            } catch (IllegalArgumentException ex) {
                System.out.println("Room " + room.getRoomNumber() + " already added and will not be added again.");
            }
        }
    }

    public Collection<IRoom> getAllRooms() {
        return reservationService.getAllRooms();
    }

    public Collection<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    public Collection<Reservation> getAllReservations() {
        return reservationService.getAllReservations();
    }

    public void displayAllReservations() {
        reservationService.printAllReservations();
    }
}
