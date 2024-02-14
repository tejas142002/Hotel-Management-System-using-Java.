package Tejas;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Final {
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "password";
    private static final int MAX_ROOMS = 10;
    private static final double STANDARD_ROOM_PRICE = 500;
    private static final double DELUXE_ROOM_PRICE = 1000;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;
        boolean loggedIn = false;

        while (!loggedIn) {
            System.out.println("Login to Hotel Management System");
            System.out.print("Username: ");
            String username = scanner.nextLine();
            System.out.print("Password: ");
            String password = scanner.nextLine();

            if (username.equals(USERNAME) && password.equals(PASSWORD)) {
                loggedIn = true;
                System.out.println("\nLogin successful!");
                break;
            } else {
                System.out.println("\nInvalid username or password. Please try again.\n");
            }
        }

        if (loggedIn) {
            // Initialize the hotel rooms
            Room[] rooms = new Room[MAX_ROOMS];
            for (int i = 0; i < rooms.length; i++) {
                if (i < 5) {
                    rooms[i] = new Room(i + 1, "Standard");
                } else {
                    rooms[i] = new Room(i + 1, "Deluxe");
                }
            }

            while (true) {
                System.out.println("\nWelcome to the Hotel Management System");
                System.out.println("=======================================");
                System.out.println("1. View All Rooms");
                System.out.println("2. Check-In");
                System.out.println("3. Check-Out");
                System.out.println("4. Occupancy Status");
                System.out.println("5. Exit");
                System.out.print("Enter your choice (1-5): ");
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character

                switch (choice) {
                    case 1:
                        viewAllRooms(rooms);
                        break;
                    case 2:
                        checkInGuest(scanner, rooms);
                        break;
                    case 3:
                        checkOutGuest(scanner, rooms);
                        break;
                    case 4:
                        displayOccupancyStatus(rooms);
                        break;
                    case 5:
                        System.out.println("\nThank you for using the Hotel Management System!");
                        scanner.close();
                        System.exit(0);
                    default:
                        System.out.println("\nInvalid choice! Please enter a number between 1-5.");
                }
            }
        }
    }

    private static void viewAllRooms(Room[] rooms) {
        System.out.println("\n------------------------");
        System.out.println("     Available Rooms     ");
        System.out.println("------------------------");
        for (Room room : rooms) {
            System.out.println(room);
        }
        System.out.println("------------------------");
    }

    private static void checkInGuest(Scanner scanner, Room[] rooms) {
        System.out.print("Enter Room Number: ");
        if (scanner.hasNextInt()) {
            int roomNumber = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            if (roomNumber >= 1 && roomNumber <= MAX_ROOMS) {
                Room room = rooms[roomNumber - 1];
                if (!room.isOccupied()) {
                    System.out.print("Enter Guest Name: ");
                    String guestName = scanner.nextLine();
                    LocalDateTime checkInDateTime = LocalDateTime.now();
                    room.checkIn(guestName, checkInDateTime);
                    System.out.println("\nGuest Checked-In Successfully!");
                    System.out.println("Guest Name: " + guestName);
                    System.out.println("Room Type: " + room.getRoomType());
                    System.out.println("Room Number: " + roomNumber);
                    System.out.println("Check-In Date: " + checkInDateTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
                } else {
                    System.out.println("\nSorry, the room is already occupied.");
                }
            } else {
                System.out.println("\nInvalid room number! Please enter a number between 1-" + MAX_ROOMS + ".");
            }
        } else {
            System.out.println("\nInvalid input! Room number must be an integer.");
            scanner.nextLine(); // Consume the invalid input
        }
    }

    private static void checkOutGuest(Scanner scanner, Room[] rooms) {
        System.out.print("Enter Room Number: ");
        if (scanner.hasNextInt()) {
            int roomNumber = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            if (roomNumber >= 1 && roomNumber <= MAX_ROOMS) {
                Room room = rooms[roomNumber - 1];
                if (room.isOccupied()) {
                    System.out.print("Enter Number of Days Stayed: ");
                    if (scanner.hasNextInt()) {
                        int numDays = scanner.nextInt();
                        scanner.nextLine(); // Consume the newline character

                        if (numDays > 0) {
                            double bill = room.checkOut(numDays);
                            LocalDateTime checkOutDateTime = LocalDateTime.now();
                            System.out.println("\nGuest Checked-Out Successfully!");
                            System.out.println("Check-Out Date: " + checkOutDateTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
                            System.out.println("Total Bill: INR" + bill);
                        } else {
                            System.out.println("\nInvalid number of days! Please enter a positive integer.");
                        }
                    } else {
                        System.out.println("\nInvalid input! Number of days stayed must be an integer.");
                        scanner.nextLine(); // Consume the invalid input
                    }
                } else {
                    System.out.println("\nSorry, the room is not occupied.");
                }
            } else {
                System.out.println("\nInvalid room number! Please enter a number between 1-" + MAX_ROOMS + ".");
            }
        } else {
            System.out.println("\nInvalid input! Room number must be an integer.");
            scanner.nextLine(); // Consume the invalid input
        }
    }

    private static void displayOccupancyStatus(Room[] rooms) {
        System.out.println("\n---------------------------");
        System.out.println("     Occupancy Status       ");
        System.out.println("---------------------------");
        for (Room room : rooms) {
            if (room.isOccupied()) {
                System.out.println("Room Number: " + room.getNumber());
                System.out.println("Guest Name: " + room.getGuestName());
                System.out.println("Room Type: " + room.getRoomType());
                System.out.println("Check-In Date and Time: " + room.getCheckInDateTime().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")));

                System.out.println("---------------------------");
            }
        }
    }

    static class Room {
        private int number;
        private boolean occupied;
        private String guestName;
        private String roomType;
        private LocalDateTime checkInDateTime;

        public Room(int number, String roomType) {
            this.number = number;
            this.roomType = roomType;
        }

        public int getNumber() {
            return number;
        }

        public boolean isOccupied() {
            return occupied;
        }

        public String getGuestName() {
            return guestName;
        }

        public String getRoomType() {
            return roomType;
        }

        public LocalDateTime getCheckInDateTime() {
            return checkInDateTime;
        }

        public void checkIn(String guestName, LocalDateTime checkInDateTime) {
            this.guestName = guestName;
            this.occupied = true;
            this.checkInDateTime = checkInDateTime;
            System.out.println("\nCheck-In Date and Time: " + checkInDateTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")));
        }

        public double checkOut(int numDays) {
            this.occupied = false;
            double roomPrice = roomType.equals("Standard") ? STANDARD_ROOM_PRICE : DELUXE_ROOM_PRICE;
            double bill = roomPrice * numDays;
            return bill;
        }

        @Override
        public String toString() {
            return "Room Number: " + number + ", Room Type: " + roomType + ", Occupied: " + (occupied ? "Yes" : "No");
        }
    }
}