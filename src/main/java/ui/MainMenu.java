package ui;

import api.AdminResource;
import api.HotelResource;
import model.Customer;
import model.IRoom;
import model.Reservation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

public class MainMenu {

    private static Scanner scanner = new Scanner(System.in);
    private final HotelResource hotelResource;
    private final AdminResource adminResource;
    private final Collection<String> availableRoomNumbers = new ArrayList<>();

    private String newCustomerEmail;

    public MainMenu() {
        hotelResource = HotelResource.getInstance();
        adminResource = AdminResource.getInstance();
    }


    private void displayMenuOptions() {
        System.out.println("\n1. Find and reserve a room");
        System.out.println("2. See my reservations");
        System.out.println("3. Create an account");
        System.out.println("4. Admin");
        System.out.println("5. Exit");
    }

    private int getChoice() {
        int choice;
        displayMenuOptions();
        do {
            System.out.println("\nPlease enter your choice");
            try {
                choice = scanner.nextInt();
                scanner.nextLine();

                if (choice <= 0 || choice > 5) {
                    System.out.println("\nInvalid choice. Please choose a number from the displayed menu.");
                } else {
                    break;
                }
            } catch (InputMismatchException ex) {
                scanner.nextLine();
                System.out.println("\nInvalid choice. Please choose a number from the displayed menu.");
            }
        } while (true);
        return choice;
    }

    public void performMenuAction() {
        int choice;
        do {
            choice = getChoice();
            switch (choice) {
                case 1:
                    findAndReserveRoom();
                    break;
                case 2:
                    getCustomersReservations();
                    break;
                case 3:
                    createAccount();
                    break;
                case 4:
                    getAdminMenu();
                    break;
                case 5:
                    break;
            }
        } while (choice != 5);
    }

    private void createAccount() {
        scanner = new Scanner(System.in);
        String firstName, lastName, email;

        do {
            System.out.println("Enter your first name");
            firstName = scanner.nextLine().trim();

            if (firstName.isEmpty()) {
                System.out.println("First name is required.");
            } else {
                break;
            }
        } while (true);

        do {
            System.out.println("Enter your last name");
            lastName = scanner.nextLine().trim();

            if (lastName.isEmpty()) {
                System.out.println("Last name is required.");
            } else {
                break;
            }
        } while (true);

        do {
            System.out.println("Enter your email (Format: name@domain.com)");
            email = scanner.nextLine().trim();

            if (email.isEmpty()) {
                System.out.println("Email is required.");
            } else {
                try {
                    if (hotelResource.getCustomer(email) != null) {
                        System.out.println("Customer with email " + email + " already exist.");
                    } else {
                        newCustomerEmail = email;
                        hotelResource.createCustomer(email, firstName, lastName);
                        break;
                    }
                } catch (IllegalArgumentException ex) {
                    System.out.println("Invalid email address. Email address should be of form name@domain.com.");
                }
            }
        } while (true);
    }

    private void getCustomersReservations() {
        String hasAccount = customerHasAccount();
        if (hasAccount.equalsIgnoreCase("Y")) {
            String customerEmail = getCustomerEmail();
            displayCustomersReservations(customerEmail);
        } else {
            createAccount();
            displayCustomersReservations(newCustomerEmail);
        }
    }

    private void displayCustomersReservations(String email) {
        Collection<Reservation> customerReservations = hotelResource.getCustomersReservations(email);

        if (customerReservations == null) {
            System.out.println("You have not made any reservations yet.");
        } else {
            System.out.println("\n*****My Reservations*****\n");
            for (Reservation reservation : customerReservations) {
                System.out.println(reservation);
                System.out.println();
            }
            System.out.println("\n*************************\n");
        }
    }

    private void findAndReserveRoom() {
        if (adminResource.getAllRooms().isEmpty()) {
            System.out.println("Currently reservations are not allowed.");
            return;
        }
        Date checkInDate;
        Date checkOutDate;
        String tryAgain = "n";
        scanner = new Scanner(System.in);

        do {
            do {
                try {
                    System.out.println("Enter check-in date (MM/DD/YYYY)");
                    checkInDate = validateDateFormat(scanner.nextLine().trim());
                    if (checkInDate.before(getCurrentDate())) {
                        System.out.println("Invalid date. Check-in date cannot be older than current date.");
                    } else {
                        break;
                    }
                } catch (DateTimeParseException | ParseException ex) {
                    System.out.println("Invalid date format. Please enter check-in date in MM/DD/YYYY format.");
                }
            } while (true);

            do {
                try {
                    System.out.println("Enter check-out date (MM/DD/YYYY)");
                    checkOutDate = validateDateFormat(scanner.nextLine().trim());

                    if (checkOutDate.before(checkInDate) || checkOutDate.equals(checkInDate)) {
                        System.out.println("Invalid date. Check-out date should be any date after the check-in date.");
                    } else {
                        break;
                    }
                } catch (DateTimeParseException | ParseException ex) {
                    System.out.println("Invalid date format. Please enter check-out date in MM/DD/YYYY format.");
                }
            } while (true);

            Collection<IRoom> rooms = hotelResource.findARoom(checkInDate, checkOutDate);

            if (rooms == null || rooms.isEmpty()) {
                System.out.println("Sorry! No rooms available for the specified dates.");
                System.out.println("Would you like to try for some other dates? (Y/N)");

                do {
                    tryAgain = scanner.nextLine().trim();

                    if (!(tryAgain.equalsIgnoreCase("y") || tryAgain.equalsIgnoreCase("n"))) {
                        System.out.println("Invalid response. Please enter Y or N.");
                    } else {
                        break;
                    }
                } while (true);
            } else {
                String bookARoom;

                if (hotelResource.isRecommendationRooms()) {
                    checkInDate = hotelResource.getRecommndedCheckInDate();
                    checkOutDate = hotelResource.getRecommnededCheckOutDate();
                    System.out.println("Sorry! No rooms available for the selected dates.");
                    System.out.println("We have the following recommended dates and rooms");
                    System.out.println("\n*****Recommended Dates and Rooms*****\n");
                    System.out.println("Recommended Dates: From " + hotelResource.getFormattedDate(checkInDate) +
                            " to " + hotelResource.getFormattedDate(checkOutDate));
                    System.out.println();
                    System.out.println("Recommended Rooms");
                } else {
                    System.out.println("\n*****Available Rooms*****\n");
                }

                displayAvailableRooms(rooms);

                do {
                    System.out.println("Would you like to reserve a room? (Y/N)");
                    bookARoom = scanner.nextLine().trim();
                    if (!(bookARoom.equalsIgnoreCase("y") || bookARoom.equalsIgnoreCase("n"))) {
                        System.out.println("Invalid response. Please enter Y or N.");
                    } else {
                        break;
                    }
                } while (true);

                if (bookARoom.equalsIgnoreCase("y")) {
                    String hasAccount = customerHasAccount();

                    if (hasAccount.equalsIgnoreCase("Y")) {
                        String customerEmail = getCustomerEmail();
                        bookARoom(rooms, customerEmail, checkInDate, checkOutDate);
                    } else {
                        createAccount();
                        bookARoom(rooms, newCustomerEmail, checkInDate, checkOutDate);
                    }
                }
            }
        } while (tryAgain.equalsIgnoreCase("y"));
    }

    private void bookARoom(Collection<IRoom> rooms, String customerEmail, Date checkInDate, Date checkOutDate) {
        String roomNumber;
        do {
            System.out.println("Enter the room number that you want to reserve");
            roomNumber = scanner.nextLine().trim();

            if (!availableRoomNumbers.contains(roomNumber)) {
                System.out.println("Invalid room number. Please choose a room number from the below list.");
                System.out.println("\n*****Available Rooms*****\n");
                displayAvailableRooms(rooms);
            } else {
                break;
            }
        } while (true);
        Reservation reservation = hotelResource.bookARoom(customerEmail, hotelResource.getRoom(roomNumber), checkInDate, checkOutDate);

        System.out.println("\n*****Reservation Details*****\n");
        System.out.println(reservation);
        System.out.println("\n*****************************\n");
    }

    private String customerHasAccount() {
        String hasAccount;
        do {
            System.out.println("Do you have an account? (Y/N)");
            hasAccount = scanner.nextLine().trim();
            if (!(hasAccount.equalsIgnoreCase("y") || hasAccount.equalsIgnoreCase("n"))) {
                System.out.println("Invalid response. Please enter Y or N.");
            } else {
                break;
            }
        } while (true);
        return hasAccount;
    }

    private String getCustomerEmail() {
        String customerEmail;
        do {
            System.out.println("Enter your email");
            customerEmail = scanner.nextLine().trim();
            Customer customer = hotelResource.getCustomer(customerEmail);

            if (customer == null) {
                System.out.println("Email not found. Please try again or create an account");

                String choice;

                do {
                    System.out.println("1. Try again");
                    System.out.println("2. Create an account");
                    choice = scanner.nextLine().trim();

                    if (!(choice.equals("1") || choice.equals("2"))) {
                        System.out.println("Invalid choice. Please enter 1 or 2.");
                    } else {
                        break;
                    }
                } while (true);

                if (choice.equals("2")) {
                    createAccount();
                    break;
                }

            } else {
                break;
            }
        } while (true);
        return customerEmail;
    }

    private void displayAvailableRooms(Collection<IRoom> rooms) {
        availableRoomNumbers.clear();
        for (IRoom room : rooms) {
            availableRoomNumbers.add(room.getRoomNumber());
            System.out.println(room);
            System.out.println();
        }
        System.out.println("\n*************************\n");
    }

    private Date validateDateFormat(String dateToValidate) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("M/d/yyyy");
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("M/d/yyyy");

        return simpleDateFormat
                .parse(
                        LocalDate
                                .parse(dateToValidate, dateTimeFormatter)
                                .format(dateTimeFormatter)
                );
    }

    private Date getCurrentDate() {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("M/d/yyyy");
            String curDate = simpleDateFormat.format(new Date());
            return simpleDateFormat.parse(curDate);
        } catch (ParseException ex) {
            return new Date();
        }
    }

    private void getAdminMenu() {
        AdminMenu adminMenu = new AdminMenu();
        adminMenu.performMenuAction();
    }

}

class MainMenuTester {
    public static void main(String[] args) {
        MainMenu mainMenu = new MainMenu();

        mainMenu.performMenuAction();


    }
}