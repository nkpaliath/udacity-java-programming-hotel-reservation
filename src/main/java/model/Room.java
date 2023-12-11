package model;

public class Room implements IRoom {
    private final String roomNumber;
    private final double price;
    private final RoomType enumeration;

    public Room(String roomNumber, double price, RoomType enumeration) {
        this.roomNumber = roomNumber;
        this.price = price;
        this.enumeration = enumeration;
    }

    @Override
    public final String getRoomNumber() {
        return roomNumber;
    }

    @Override
    public final Double getRoomPrice() {
        return price;
    }

    @Override
    public final RoomType getRoomType() {
        return enumeration;
    }

    @Override
    public final boolean isFree() {
        return price == 0;
    }

    @Override
    public final String toString() {
        return "Number: " + roomNumber + " Type: " + enumeration + " Price: " + price;
    }
}

class RoomTester {
    public static void main(String[] args) {
        Room room = new Room("2", 12.50, RoomType.DOUBLE);

        System.out.println(room);
    }
}