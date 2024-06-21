package hostelmanage;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;
import Controller.BookingController;
import Controller.PaymentController;
import Controller.RoomsController;
import Controller.StudentController;
import DAO.PaymentDAO;
import View.RoomsView;
import View.StudentView;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); 
        try {
            boolean exit = false;

            Connection connection = DBconnection.getConnection();

            PaymentDAO paymentDAO = new PaymentDAO(connection);

            StudentController studentController = new StudentController(connection);
            RoomsController roomsController = new RoomsController(connection);
            PaymentController paymentController = new PaymentController(connection, studentController, roomsController);

            while (!exit) {
                System.out.println("-----------------Hostel Management Login---------------");
                System.out.println("1. Admin Login");
                System.out.println("2. Hosteller Login");
                System.out.println("3. Exit");
                System.out.println("Enter your choice:");

                int choice = scanner.nextInt();
                scanner.nextLine(); 

                switch (choice) {
                    case 1:
                        System.out.println("Admin login selected.");
                        System.out.println("Enter admin username:");
                        String username = scanner.nextLine();
                        System.out.println("Enter admin password:");
                        String password = scanner.nextLine();
                        if (Admin.login(username, password, connection)) {
                        	
                            System.out.println("Admin Login Successful");
                            RoomsController roomsController1 = new RoomsController(connection);
                            BookingController bookingController = new BookingController(connection, studentController, roomsController1, paymentController);

                            RoomsView roomsView = new RoomsView(roomsController1, bookingController, studentController, scanner);
                            roomsView.displayMenu();
                        } else {
                        	
                            boolean success = Admin.insertAdmin(username, password, connection);
                            if (success) {
                                System.out.println("Admin account created successfully. Please login again.");
                            } else {
                                System.out.println("Failed to create admin account.");
                            }
                        }
                        break;
                    case 2:
                        System.out.println("Hosteller login/register selected.");
                        RoomsController roomsController2 = new RoomsController(connection);
                        BookingController bookingController2 = new BookingController(connection, studentController, roomsController2, paymentController);

                        StudentView studentView = new StudentView(studentController, roomsController2, bookingController2, paymentController, scanner, connection);
                        studentView.displayMenu();
                        break;

                    case 3:
                        System.out.println("Exiting program.");
                        exit = true;
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter 1, 2, or 3.");
                        break;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } 
    }
}
