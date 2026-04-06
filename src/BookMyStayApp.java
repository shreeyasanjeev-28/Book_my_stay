import java.util.*;

/**
 * Hotel Booking Management System
 * Demonstrates FIFO booking, inventory consistency,
 * and prevention of double booking.
 * 
 * @author Shre
 * @version 1.0
 */
public class HotelBookingApp {

    // ------------------ Room Class ------------------
    static class Room {
        int roomId;
        String type;
        boolean isBooked;

        Room(int roomId, String type) {
            this.roomId = roomId;
            this.type = type;
            this.isBooked = false;
        }

        public String toString() {
            return "Room ID: " + roomId + ", Type: " + type + ", Booked: " + isBooked;
        }
    }

    // ------------------ Booking Request ------------------
    static class BookingRequest {
        String customerName;
        String roomType;

        BookingRequest(String customerName, String roomType) {
            this.customerName = customerName;
            this.roomType = roomType;
        }
    }

    // ------------------ Data Structures ------------------
    static List<Room> rooms = new ArrayList<>();
    static Queue<BookingRequest> requestQueue = new LinkedList<>();
    static Set<Integer> bookedRoomIds = new HashSet<>();

    // ------------------ Initialize Rooms ------------------
    public static void initializeRooms() {
        rooms.add(new Room(101, "Single"));
        rooms.add(new Room(102, "Single"));
        rooms.add(new Room(201, "Double"));
        rooms.add(new Room(202, "Double"));
    }

    // ------------------ Add Booking Request (FIFO) ------------------
    public static void addBookingRequest(String name, String type) {
        requestQueue.add(new BookingRequest(name, type));
        System.out.println("Request added for " + name + " (" + type + ")");
    }

    // ------------------ Process Booking ------------------
    public static void processBooking() {
        if (requestQueue.isEmpty()) {
            System.out.println("No pending booking requests.");
            return;
        }

        BookingRequest request = requestQueue.poll(); // FIFO

        for (Room room : rooms) {
            if (!room.isBooked && room.type.equalsIgnoreCase(request.roomType)) {

                // Prevent double booking
                if (!bookedRoomIds.contains(room.roomId)) {
                    room.isBooked = true;
                    bookedRoomIds.add(room.roomId);

                    System.out.println("Booking SUCCESS for " + request.customerName +
                            " -> Room " + room.roomId);
                    return;
                }
            }
        }

        System.out.println("Booking FAILED for " + request.customerName +
                " (No available " + request.roomType + " rooms)");
    }

    // ------------------ Display Rooms ------------------
    public static void displayRooms() {
        System.out.println("\n--- Room Status ---");
        for (Room room : rooms) {
            System.out.println(room);
        }
    }

    // ------------------ MAIN METHOD ------------------
    public static void main(String[] args) {

        System.out.println("===== Book My Stay App v1.0 =====");

        initializeRooms();

        // ------------------ TEST CASES ------------------

        // Test Case 1: FIFO Booking
        addBookingRequest("Alice", "Single");
        addBookingRequest("Bob", "Single");
        addBookingRequest("Charlie", "Single");

        processBooking(); // Alice
        processBooking(); // Bob
        processBooking(); // Charlie (should fail if no rooms)

        // Test Case 2: Different Room Type
        addBookingRequest("David", "Double");
        processBooking();

        // Test Case 3: No Requests
        processBooking();

        // Display final room status
        displayRooms();

        System.out.println("===== Application Finished =====");
    }
}
