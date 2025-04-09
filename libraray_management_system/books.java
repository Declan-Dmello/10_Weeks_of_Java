package libraray_management_system;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;


class books {

 enum Available
 {
     YES , NO
 }

    int ISBN ;
    String title;
    String Author ;
    Available available;
    LocalDate borrowDate; // Store borrow date



    books(int ISBN , String title , String Author , Available available)
 {
     this.Author = Author;
     this.ISBN = ISBN;
     this.title = title;
     this.available  = available;
     this.borrowDate = null; // Default is null (not borrowed)

 }


    public String getTitle() {
        return title;
    }

    public int getISBN() {
        return ISBN;
    }


    public Available getAvailable() {
        return available;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAvailable(Available available) {
        this.available = available;
    }

    public void setBorrowDate(LocalDate date) {
        this.borrowDate = date;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }
}






class members{

    int member_id;
    String name;
    public ArrayList<books> borrowed_books;
    public int total_fines;


    public ArrayList<books> getBorrowed_books() {
        return borrowed_books;
    }

    public int getMember_id() {
        return member_id;
    }

    public String getName() {
        return name;
    }

    public int getTotal_fines() {
        return total_fines;
    }

    members(int member_id , String name , int total_fines , ArrayList<books> borrowed_books)
    {
        this.member_id = member_id;
        this.name = name;
        this.total_fines = total_fines;
        this.borrowed_books = borrowed_books;

    }



    public void pay_fine(int amount) {
        if (amount > total_fines) {
            System.out.println("Overpaid! Paying only ₹" + total_fines);
            total_fines = 0;
        } else {
            total_fines -= amount;
        }
    }
}


class library
{
    String name;
    private ArrayList<books> total_books;
    private ArrayList<members> all_members;

    public String getName() {
        return name;
    }

    public ArrayList<books> getTotal_books() {
        return total_books;
    }

    public ArrayList<members> getAll_members() {
        return all_members;
    }


    library(String name,ArrayList<books> total_books , ArrayList<members> all_members )
    {
        this.name = name;
        this.total_books = total_books;
        this.all_members = all_members;
    }

    public void add_book(books newBook) {
        for (books book : total_books) {
            if (book.getISBN() == newBook.getISBN()) {
                System.out.println("Book with ISBN " + newBook.getISBN() + " already exists!");
                return;
            }
        }

        total_books.add(newBook);
        System.out.println("Book added: " + newBook.getTitle());
    }



    public void remove_book() {
        System.out.println("Input the ISBN: ");
        Scanner s1 = new Scanner(System.in);

        int removal_isbn = s1.nextInt();

        Iterator<books> iterator = total_books.iterator();
        while (iterator.hasNext()) {
            books book = iterator.next();
            if (book.getISBN() == removal_isbn) {
                System.out.println("The Book is removed " + book.getTitle());
                iterator.remove();
                return;
            }
        }
        System.out.println("Book with ISBN " + removal_isbn + " not found.");
    }




    public void search_book()
    {

        System.out.println("Search by ISBN : ");
        Scanner s1 = new Scanner(System.in);

        int search_isbn = s1.nextInt();

        Iterator<books> iterator = total_books.iterator();
        while (iterator.hasNext())
        {
            books book = iterator.next();
            if (book.getISBN() ==search_isbn )
            {
                System.out.println("The Book details are  \n" +
                       "ISBN : "+book.getISBN() +
                                "\nTitle : " + book.getTitle()
                                + "\n Author : " + book.getAuthor()
                                + "\nAvailable : "+ book.getAvailable()+ "\n");
                return;
            }
        }
        System.out.println("Book with ISBN " + search_isbn + " not found.");

    }

    public void display_books()
    {
        System.out.println("The Book details are  : \n" );

        for (books book : total_books)
        {
            System.out.println("ISBN : "+book.getISBN() +
                    "\nTitle : " + book.getTitle()
                    + "\n Author : " + book.getAuthor()
                    + "\nAvailable : "+ book.getAvailable()+ "\n");

        }
    }


    public void add_member(members new_member)
    {

        for (members member : all_members)
        {
            if (member.member_id == new_member.member_id)
            {
                System.out.println("The Member ID Already Exists!");
                return ;
            }



        }
        all_members.add(new_member);


        System.out.println("New Member Added Successfully");
        System.out.println("Member name " + new_member.getName());

    }




    public void remove_member()
    {

        Scanner s1 = new Scanner(System.in);
        System.out.println("Input the Member ID : ");

        int removal_id = s1.nextInt();
        Iterator<members> iterator = all_members.iterator();
        while (iterator.hasNext())
        {
            members member = iterator.next();
            if (member.member_id == removal_id)
            {
                System.out.println("\nThe Member with ID " + member.getMember_id() + "is removed \n");
                iterator.remove();
                return;
            }



        }
        System.out.println("Member with ID "+ removal_id+ " Not Found!");


    }




    public void display_member()
    {
        for (members member : all_members)
        {

                System.out.println("\nMember Name : " + member.getName()
                + " Total Fines "  +member.getTotal_fines());


                System.out.println("Books Borrowed: ");

                if (member.getBorrowed_books().isEmpty()) {
                    System.out.println("None");
                } else {
                    for (books book : member.getBorrowed_books()) {
                        System.out.println("- " + book.getTitle());
                    }
                }

        }

    }




    public void borrow_book() {
        Scanner s1 = new Scanner(System.in);

        // Get Book ISBN
        System.out.println("\nInput Book ISBN: ");
        int borrow_book_isbn = s1.nextInt();

        // Get Member ID
        System.out.println("\nInput Member ID: ");
        int borrow_member_id = s1.nextInt();

        // Find the book
        books selectedBook = null;
        for (books book : total_books) {
            if (book.ISBN == borrow_book_isbn) {
                selectedBook = book;
                break;
            }
        }

        if (selectedBook == null) {
            System.out.println("Book with ISBN " + borrow_book_isbn + " does not exist.");
            return;
        }

        // Check if the book is available
        if (selectedBook.getAvailable() == books.Available.NO) {
            System.out.println("The Book is Not Available.");
            return;
        }

        // Find the member
        members selectedMember = null;
        for (members member : all_members) {
            if (member.member_id == borrow_member_id) {
                selectedMember = member;
                break;
            }
        }

        if (selectedMember == null) {
            System.out.println("Member with ID " + borrow_member_id + " not found.");
            return;
        }

        // Borrow the book
        selectedMember.borrowed_books.add(selectedBook);
        selectedBook.setAvailable(books.Available.NO);
        selectedBook.setBorrowDate(LocalDate.now()); // Set current date as borrow date


        System.out.println("Book '" + selectedBook.getTitle() + "' borrowed by " + selectedMember.getName());
    }






    public void return_book() {
        Scanner s1 = new Scanner(System.in);

        System.out.println("Hey, so you want to return your book?");
        System.out.println("\nInput Member ID: ");
        int borrow_member_id = s1.nextInt();

        members selectedMember = null;
        for (members member : all_members) {
            if (member.member_id == borrow_member_id) {
                selectedMember = member;
                break;
            }
        }

        if (selectedMember == null) {
            System.out.println("Member with ID " + borrow_member_id + " not found.");
            return;
        }

        System.out.println("Input the ISBN of the book to be returned.");
        int return_book_isbn = s1.nextInt();

        books selectedBook = null;
        for (books book : selectedMember.borrowed_books) {
            if (book.ISBN == return_book_isbn) {
                selectedBook = book;
                break;
            }
        }

        if (selectedBook == null) {
            System.out.println("Book with ISBN " + return_book_isbn + " was not borrowed by the member.");
            return;
        }

        LocalDate borrowDate = selectedBook.getBorrowDate();
        LocalDate returnDate = LocalDate.now();
        long daysBorrowed = ChronoUnit.DAYS.between(borrowDate, returnDate);

        int allowedDays = 14;  // Allowed borrowing period
        int finePerDay = 10;   // Fine per extra day
        int fine = 0;

        if (daysBorrowed > allowedDays) {
            fine = (int) (daysBorrowed - allowedDays) * finePerDay;
            selectedMember.total_fines += fine;
        }

        selectedMember.borrowed_books.remove(selectedBook);
        selectedBook.setAvailable(books.Available.YES);
        selectedBook.setBorrowDate(null); // Reset borrow date

        System.out.println("Book '" + selectedBook.getTitle() + "' returned by " + selectedMember.getName());
        if (fine > 0) {
            System.out.println("Late Fee: ₹" + fine);
            System.out.println("Updated Total Fine for " + selectedMember.getName() + ": ₹" + selectedMember.total_fines);
        }
    }


}

