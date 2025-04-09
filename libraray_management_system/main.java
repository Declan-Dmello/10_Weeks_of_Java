package libraray_management_system;
import java.util.ArrayList;
import java.util.Scanner;

public class main {
    public static void main(String[] args) {
        ArrayList<books> initialBooks = new ArrayList<>();
        initialBooks.add(new books(101, "The Alchemist", "Paulo Coelho", books.Available.YES));
        initialBooks.add(new books(102, "1984", "George Orwell", books.Available.YES));
        initialBooks.add(new books(103, "To Kill a Mockingbird", "Harper Lee", books.Available.YES));

        ArrayList<members> initialMembers = new ArrayList<>();
        initialMembers.add(new members(1, "Rui", 0, new ArrayList<>()));
        initialMembers.add(new members(2, "Hemant", 0, new ArrayList<>()));
        initialMembers.add(new members(3, "Judah", 500, new ArrayList<>()));

        library myLibrary = new library("The Library of Babel", initialBooks, initialMembers);

        Scanner scanner = new Scanner(System.in);
        int choice;

        // Menu Loop
        do {
            System.out.println("\nLibrary Management System Menu:");
            System.out.println("1. Add Book");
            System.out.println("2. Remove Book");
            System.out.println("3. Search Book");
            System.out.println("4. Display Books");
            System.out.println("5. Add Member");
            System.out.println("6. Remove Member");
            System.out.println("7. Display Member");
            System.out.println("8. Borrow Book");
            System.out.println("9. Return Book");
            System.out.println("10. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter ISBN: ");
                    int isbn = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    System.out.print("Enter Title: ");
                    String title = scanner.nextLine();
                    System.out.print("Enter Author: ");
                    String author = scanner.nextLine();
                    myLibrary.add_book(new books(isbn, title, author, books.Available.YES));
                    break;

                case 2:
                    myLibrary.remove_book();
                    break;

                case 3:
                    myLibrary.search_book();
                    break;

                case 4:
                    myLibrary.display_books();
                    break;
                case 5:
                    System.out.print("Enter Member ID: ");
                    int memberId = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter Member Name: ");
                    String memberName = scanner.nextLine();
                    myLibrary.add_member(new members(memberId, memberName, 0, new ArrayList<>()));
                    break;

                case 6:
                    myLibrary.remove_member();
                    break;

                case 7:
                    myLibrary.display_member();
                    break;

                case 8:
                    myLibrary.borrow_book();
                    break;

                case 9:
                    myLibrary.return_book();
                    break;

                case 10:
                    System.out.println("Exiting the Library Management System...");
                    break;

                default:
                    System.out.println("Invalid choice! Please enter a number between 1 and 9.");
            }
        } while (choice != 10);

        scanner.close();
    }
}
