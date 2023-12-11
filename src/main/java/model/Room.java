package model;

import java.util.Objects;

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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || (getClass() != obj.getClass() && !(obj instanceof IRoom))) return false;
        Room room = (Room) obj;
        return Objects.equals(roomNumber, room.roomNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomNumber);
    }
}

class RoomTester {
    public static void main(String[] args) {
        Room room = new Room("1", 12.50, RoomType.DOUBLE);

        Room room1 = new Room("1", 0, RoomType.SINGLE);

        System.out.println(room.equals(room1));
    }
}