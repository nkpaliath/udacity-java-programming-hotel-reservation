package model;

public class FreeRoom extends Room {
    public FreeRoom(String roomNumber, RoomType enumeration) {
        super(roomNumber, 0, enumeration);
    }
}

class FreeRoomTester {
    public static void main(String[] args) {
        FreeRoom freeRoom = new FreeRoom("1", RoomType.SINGLE);
        Room room = new Room("1", 12, RoomType.DOUBLE);

        System.out.println(room instanceof FreeRoom);
    }
}