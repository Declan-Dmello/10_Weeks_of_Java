package address_book_manager;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.regex.*;

public class Address_book {

    private String name;
    private String address;
    private String phone_no;
    private String email;


    public Address_book(){}
    public Address_book(String name, String address, String phone_no, String email) {
        this.name = name;
        this.address = address;
        this.phone_no = phone_no;
        this.email = email;
    }

    public void display() {
        System.out.println("Name: " + name);
        System.out.println("Address: " + address);
        System.out.println("Phone_no: " + phone_no);
        System.out.println("Email: " + email);
        System.out.println("-------------------");
    }

    public void processing() {
        ArrayList<Address_book> contact = new ArrayList<>();
        Scanner s1 = new Scanner(System.in);
        Scanner s2 = new Scanner(System.in);
        System.out.println("The Address Book!");
        System.out.println("----------------------------");

        while (true) {
            System.out.println("1 For Insertion");
            System.out.println("2 For Display");
            System.out.println("3 For Removal");
            System.out.println("4 to Exit\n");
            System.out.print("Input your choice: ");
            int var = s2.nextInt();

            switch (var) {
                case 1: {
                    System.out.print("Insert The Name: ");
                    String name = s1.nextLine();
                    System.out.print("Insert The Address: ");
                    String address = s1.nextLine();

                    String phone_no;
                    while (true) {
                        System.out.print("Insert The Phone Number: ");
                        String non_validated_ph = s1.nextLine();
                        if (non_validated_ph.matches("^[0-9]{10}$")) {
                            phone_no = non_validated_ph;
                            break;
                        } else {
                            System.out.println("\nInvalid Phone Number Try Again\n");
                        }
                    }

                    String email;
                    while (true) {
                        System.out.print("Insert The Email: ");
                        String non_validated_email = s1.nextLine();
                        if (non_validated_email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
                            email = non_validated_email;
                            break;
                        } else {
                            System.out.println("\nNot A Valid Email!!! Try Again\n");
                        }
                    }

                    contact.add(new Address_book(name, address, phone_no, email));
                    System.out.println("\nRecord Added Successfully\n");
                }
                break;

                case 2: {
                    for (int i = 0; i < contact.size(); i++) {
                        System.out.println("\nValues at key " + (i+1) + " are: ");
                        contact.get(i).display();
                    }
                    System.out.println("\nThe Records have been displayed successfully\n");
                }
                break;

                case 3: {
                    System.out.print("\nInput the key to remove the record: ");
                    int key = s2.nextInt();

                    contact.remove(key-1);
                    System.out.println("\nThe Records have been removed successfully\n");
                }
                break;

                case 4: {
                    System.out.println("\nThe Program exited successfully\n");
                    System.exit(0);
                }
                break;
            }
        }
    }
}
