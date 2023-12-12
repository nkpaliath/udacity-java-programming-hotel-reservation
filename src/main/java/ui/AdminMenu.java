package ui;

import api.AdminResource;
import model.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.InputMismatchException;
import java.util.Scanner;

public class AdminMenu {
    private static final Scanner scanner = new Scanner(System.in);
    public final AdminResource adminResource;

    AdminMenu() {
        adminResource = AdminResource.getInstance();
    }

    private void displayMenuOptions() {
        System.out.println("\n1. See all Customers");
        System.out.println("2. See all Rooms");
        System.out.println("3. See all Reservations");
        System.out.println("4. Add a Room");
        System.out.println("5. Back to Main Menu");
    }

    private int getChoice() {
        int choice;
        displayMenuOptions();
        do {
            System.out.println("\nPlease enter your choice");
            try {
                choice = scanner.nextInt();
                scanner.nextLine();
                System.out.println();

                if (choice <= 0 || choice > 5) {
                    throw new InputMismatchException();
                }
                break;
            } catch (InputMismatchException ex) {
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
                    getAllCustomers();
                    break;
                case 2:
                    getAllRooms();
                    break;
                case 3:
                    getAllReservations();
                    break;
                case 4:
                    addRoom();
                    break;
                case 5:
                    break;
            }
        } while (choice != 5);
    }

    private void getAllCustomers() {
        Collection<Customer> customers = adminResource.getAllCustomers();

        if (customers != null && !customers.isEmpty()) {
            System.out.println("\n*****Customers*****\n");
            for (Customer customer : customers) {
                System.out.println(customer);
                System.out.println();
            }
            System.out.println("\n*******************\n");
        } else {
            System.out.println("No customers found.");
        }
    }

    private void getAllRooms() {
        Collection<IRoom> rooms = adminResource.getAllRooms();

        if (rooms != null && !rooms.isEmpty()) {
            System.out.println("\n*****Rooms*****\n");
            for (IRoom room : rooms) {
                System.out.println(room);
                System.out.println();
            }
            System.out.println("\n***************\n");
        } else {
            System.out.println("No rooms found.");
        }
    }

    private void getAllReservations() {
        Collection<Reservation> reservations = adminResource.getAllReservations();

        if (reservations != null && !reservations.isEmpty()) {
            System.out.println("\n*****Reservations*****\n");
            adminResource.displayAllReservations();
            System.out.println("\n**********************\n");
        } else {
            System.out.println("No reservations found.");
        }
    }

    private void addRoom() {
        Collection<IRoom> rooms = new ArrayList<>();
        IRoom room;
        String roomNumber;
        String freeRoom;
        RoomType roomType;
        double roomPrice;
        String addAnotherRoom;
        do {
            do {
                int number;
                System.out.println("Enter room number");
                try {
                    number = scanner.nextInt();
                    scanner.nextLine();
                    if (number <= 0) {
                        System.out.println("Invalid room number. Room number should be a number greater than 0.");
                    } else {
                        roomNumber = String.valueOf(number);
                        break;
                    }
                } catch (InputMismatchException ex) {
                    scanner.nextLine();
                    System.out.println("Invalid room number. Room number should be a number greater than 0.");
                }
            } while (true);

            do {
                String type;
                System.out.println("Enter room type");
                System.out.println("1. Single");
                System.out.println("2. Double");

                type = scanner.nextLine().trim();

                if (!type.equals("1") && !type.equals("2")) {
                    System.out.println("Invalid room type. Please enter 1 or 2.");
                } else {
                    if (type.equals("1")) {
                        roomType = RoomType.SINGLE;
                    } else {
                        roomType = RoomType.DOUBLE;
                    }
                    break;
                }
            } while (true);

            do {
                System.out.println("Is it a free room? (Y/N)");
                freeRoom = scanner.nextLine().trim();
                if (!(freeRoom.equalsIgnoreCase("y") || freeRoom.equalsIgnoreCase("n"))) {
                    System.out.println("Invalid response. Please enter Y or N.");
                } else {
                    if (freeRoom.equalsIgnoreCase("y")) {
                        room = new FreeRoom(roomNumber, roomType);
                    } else {
                        do {
                            System.out.println("Enter per day charges for the room");
                            try {
                                roomPrice = scanner.nextDouble();
                                scanner.nextLine();
                                break;
                            } catch (InputMismatchException ex) {
                                scanner.nextLine();
                                System.out.println("Invalid price.");
                            }
                        } while (true);
                        room = new Room(roomNumber, roomPrice, roomType);
                    }
                    rooms.add(room);
                    break;
                }
            } while (true);
            do {
                System.out.println("Do you want to add another room?");
                addAnotherRoom = scanner.nextLine().trim();
                if (!(addAnotherRoom.equalsIgnoreCase("y") || addAnotherRoom.equalsIgnoreCase("n"))) {
                    System.out.println("Invalid response. Please enter Y or N.");
                } else {
                    break;
                }
            } while (true);
        } while (addAnotherRoom.equalsIgnoreCase("y"));
        adminResource.addRoom(rooms);
    }
}

class AdminMenuTester {
    public static void main(String[] args) {
        AdminMenu adminMenu = new AdminMenu();
        adminMenu.performMenuAction();
    }
}