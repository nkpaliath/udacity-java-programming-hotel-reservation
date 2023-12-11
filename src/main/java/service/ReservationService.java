package service;

import model.Customer;
import model.IRoom;
import model.Reservation;

import java.util.*;

public class ReservationService {
    private static ReservationService instance;
    private final Map<String, List<Reservation>> reservations = new HashMap<>();
    private final Collection<IRoom> rooms = new HashSet<>();

    private boolean isRecommendation;
    private Date recommendedCheckInDate;
    private Date recommendedCheckOutDate;

    private ReservationService() {
    }

    public static ReservationService getInstance() {
        if (instance == null) {
            instance = new ReservationService();
        }

        return instance;
    }

    public void addRoom(IRoom room) {
        if (!rooms.contains(room)) {
            rooms.add(room);
        } else {
            throw new IllegalArgumentException("Room already exists.");
        }
    }

    public Collection<IRoom> getAllRooms() {
        return rooms;
    }

    public IRoom getARoom(String roomNumber) {
        for (IRoom room : rooms) {
            if (room.getRoomNumber().equals(roomNumber)) {
                return room;
            }
        }
        return null;
    }

    public Reservation reservation(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
        Reservation newReservation = new Reservation(customer, room, checkInDate, checkOutDate);
        if (reservations.get(customer.getEmail()) != null) {
            reservations.get(customer.getEmail()).add(newReservation);
        } else {
            List<Reservation> customerReservations = new ArrayList<>();
            customerReservations.add(newReservation);
            reservations.put(customer.getEmail(), customerReservations);
        }
        return newReservation;
    }

    private Collection<IRoom> getBookedRooms(Date checkInDate, Date checkOutDate) {
        Collection<IRoom> bookedRooms = new HashSet<>();
        for (List<Reservation> allReservations : reservations.values()) {
            for (Reservation reservation : allReservations) {
                if (checkInDate.compareTo(reservation.getCheckInDate()) >= 0 && checkInDate.compareTo(reservation.getCheckOutDate()) < 0) {
                    bookedRooms.add(reservation.getRoom());
                } else if (checkOutDate.compareTo(reservation.getCheckInDate()) >= 0 && checkOutDate.compareTo(reservation.getCheckOutDate()) <= 0) {
                    bookedRooms.add(reservation.getRoom());
                } else if (reservation.getCheckInDate().after(checkInDate) && reservation.getCheckInDate().before(checkOutDate)) {
                    bookedRooms.add(reservation.getRoom());
                } else if (reservation.getCheckOutDate().after(checkInDate) && reservation.getCheckOutDate().before(checkOutDate)) {
                    bookedRooms.add(reservation.getRoom());
                }
            }
        }
        return bookedRooms;
    }

    public Collection<IRoom> findRooms(Date checkInDate, Date checkOutDate) {
        isRecommendation = false;
        if (reservations.isEmpty()) {
            return rooms;
        } else {
            Collection<IRoom> bookedRooms = getBookedRooms(checkInDate, checkOutDate);
            if (bookedRooms.isEmpty()) {
                return rooms;
            } else {
                HashSet<IRoom> availableRooms = new HashSet<>(rooms);
                availableRooms.removeAll(bookedRooms);
                if (availableRooms.isEmpty()) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(checkInDate);
                    calendar.add(Calendar.DAY_OF_MONTH, 7);
                    Date recommendCheckInDate = calendar.getTime();

                    calendar.setTime(checkOutDate);
                    calendar.add(Calendar.DAY_OF_MONTH, 7);
                    Date recommendCheckOutDate = calendar.getTime();

                    bookedRooms = getBookedRooms(recommendCheckInDate, recommendCheckOutDate);
                    if (bookedRooms.isEmpty()) {
                        isRecommendation = true;
                        this.recommendedCheckInDate = recommendCheckInDate;
                        this.recommendedCheckOutDate = recommendCheckOutDate;
                        return rooms;
                    }

                    availableRooms = new HashSet<>(rooms);
                    availableRooms.removeAll(bookedRooms);

                    if (!availableRooms.isEmpty()) {
                        isRecommendation = true;
                        this.recommendedCheckInDate = recommendCheckInDate;
                        this.recommendedCheckOutDate = recommendCheckOutDate;
                        return availableRooms;
                    }
                    return null;
                } else {
                    return availableRooms;
                }
            }
        }
    }

    public Collection<Reservation> getCustomersReservation(Customer customer) {
        return reservations.get(customer.getEmail());
    }

    public Collection<Reservation> getAllReservations() {
        List<Reservation> allReservations = new ArrayList<>();
        for (List<Reservation> customerReservations : reservations.values()) {
            allReservations.addAll(customerReservations);
        }

        return allReservations;
    }

    public void printAllReservations() {
        for (Reservation reservation : getAllReservations()) {
            System.out.println(reservation);
            System.out.println();
        }
    }

    public boolean isRecommendation() {
        return isRecommendation;
    }

    public Date getRecommendedCheckInDate() {
        return recommendedCheckInDate;
    }

    public Date getRecommendedCheckOutDate() {
        return recommendedCheckOutDate;
    }

    public String getFormattedDate(Date dateToFormat) {
        return Reservation.formatDate(dateToFormat);
    }
}
