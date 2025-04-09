package task_manager;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Scanner;
import java.util.ArrayList;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

public class main {
    public static void main(String[] args) {
        task_manager t1 = new task_manager();
        Scanner s1 = new Scanner(System.in);

        int choice;


        while (true) {

            System.out.println("\n\nEnter 1 to add a new task: ");
            System.out.println("Enter 2 to view all the tasks: ");
            System.out.println("Enter 3 to sort the tasks based on priority: ");
            System.out.println("Enter 4 to sort the tasks based on due date: ");
            System.out.println("Enter 5 to mark a task as completed: ");
            System.out.println("Enter 6 to remove a task: ");
            System.out.println("Enter 7 to Save and Exit: ");

            String file1 = "storage_file2.csv";

            System.out.println("\nEnter Your Choice: ");
            choice = s1.nextInt();
            s1.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("Input the Title: ");
                    String title = s1.nextLine();

                    System.out.println("Input the Description: ");
                    String description = s1.nextLine();

                    System.out.println("\nInput the Status(Pending , Completed) : ");
                    String status_input = s1.nextLine().toUpperCase();
                    task_manager.Status status = task_manager.Status.valueOf(status_input);

                    System.out.println("\nInput the Priority(High, Low , Medium) : ");
                    String priority_input = s1.nextLine().toUpperCase();
                    task_manager.Priority priority = task_manager.Priority.valueOf(priority_input);

                    DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    System.out.println("\nEnter the Due Date is (yyyy-MM-dd) format : ");
                    String date_byuser = s1.nextLine();

                    LocalDate date_1 = LocalDate.parse(date_byuser, df);


                    new task_manager(title, description, priority, status, date_1);

                    try (FileWriter fileWriter = new FileWriter("storage_file2.csv", true)) {
                        fileWriter.write(title + "," + description + "," + priority + "," + status + "," + date_1 + "\n");
                        fileWriter.flush();
                        System.out.println("\nTask Saved");
                    } catch (IOException e) {
                        System.out.println("Error");

                    }
                    break;


                case 2:
                    try (BufferedReader br = new BufferedReader(new FileReader(file1))) {
                        System.out.println("\nTasks are  --> \n");
                        String lines;
                        while ((lines = br.readLine()) != null) {

                            if (lines == null) {
                                System.out.println("Null");
                            }
                            System.out.println(lines + "\n");
                        }

                    } catch (IOException e) {
                        System.out.println(e);
                        e.printStackTrace();
                    }
                    break;


                case 3:
                    try (CSVReader cr = new CSVReader(new FileReader(file1))) {
                        String[] content;
                        ArrayList<task_manager> a1 = new ArrayList<task_manager>();

                        while ((content = cr.readNext()) != null) {
                            if (content.length == 5) {
                                String title1 = content[0];
                                String desc1 = content[1];
                                task_manager.Status status1 = task_manager.Status.valueOf(content[3]);
                                task_manager.Priority priority1 = task_manager.Priority.valueOf(content[2]);
                                LocalDate date1 = LocalDate.parse(content[4]);

                                a1.add(new task_manager(title1, desc1, priority1, status1, date1));
                            }
                        }


                        a1.sort(Comparator.comparing(task_manager::getPriority));
                        System.out.println("The Tasks sorted by Priority : ");

                        for (task_manager task : a1) {
                            System.out.println(
                                    "Title: " + task.getTitle() +
                                            ", Description: " + task.getDescription() +
                                            ", Priority: " + task.getPriority() +
                                            ", Status: " + task.getStatus() +
                                            ", Due Date: " + task.getDue_date());

                        }
                    } catch (IOException e) {
                        System.out.println("The error is: " + e.getMessage());
                    } catch (IllegalArgumentException | CsvValidationException e) {
                        System.out.println("Exception : " + e.getMessage());
                    }

                    break;


                //sorting by date


                case 4:
                    try (CSVReader cr = new CSVReader(new FileReader(file1))) {
                        String[] content;
                        ArrayList<task_manager> a1 = new ArrayList<task_manager>();

                        while ((content = cr.readNext()) != null) {
                            if (content.length == 5) {
                                String title1 = content[0];

                                String desc1 = content[1];
                                task_manager.Status status1 = task_manager.Status.valueOf(content[3]);
                                task_manager.Priority priority1 = task_manager.Priority.valueOf(content[2]);
                                LocalDate date1 = LocalDate.parse(content[4]);

                                a1.add(new task_manager(title1, desc1, priority1, status1, date1));
                            }
                        }


                        a1.sort(Comparator.comparing(task_manager::getDue_date));
                        System.out.println("The Tasks sorted by Due Date : ");

                        for (task_manager task : a1) {
                            System.out.println(
                                    "Title: " + task.getTitle() +
                                            ", Description: " + task.getDescription() +
                                            ", Priority: " + task.getPriority() +
                                            ", Status: " + task.getStatus() +
                                            ", Due Date: " + task.getDue_date());

                        }
                    } catch (IOException e) {
                        System.out.println("The error is: " + e.getMessage());
                    } catch (IllegalArgumentException | CsvValidationException e) {
                        System.out.println("Exception : " + e.getMessage());
                    }

                    break;

                case 5:


                    //marking task as completed
                    System.out.println("Input the Task Title : ");
                    String sel_task = s1.nextLine().trim();
                    try (CSVReader cr = new CSVReader(new FileReader(file1))) {
                        String[] content;
                        ArrayList<task_manager> a1 = new ArrayList<task_manager>();

                        while ((content = cr.readNext()) != null) {
                            if (content.length == 5) {
                                String title1 = content[0];

                                String desc1 = content[1];
                                task_manager.Status status1 = task_manager.Status.valueOf(content[3]);
                                task_manager.Priority priority1 = task_manager.Priority.valueOf(content[2]);
                                LocalDate date1 = LocalDate.parse(content[4]);

                                a1.add(new task_manager(title1, desc1, priority1, status1, date1));
                            }
                        }


                        for (task_manager task : a1) {
                            if (task.getTitle().equalsIgnoreCase(sel_task)) {
                                task.status = task_manager.Status.COMPLETED;
                                break;
                            }
                            System.out.println("\nThe task is marked as Completed " );
                        }

                        try (FileWriter fileWriter = new FileWriter("storage_file2.csv", false)) {

                            for (task_manager task : a1) {
                                fileWriter.write(task.getTitle() + "," + task.getDescription() + "," + task.getPriority() + "," + task.getStatus() + "," + task.getDue_date() + "\n");
                                fileWriter.flush();

                            }
                        } catch (IOException e) {
                            System.out.println("Error");

                        }

                    } catch (CsvValidationException | IOException e) {
                        System.out.println("Error during reading : " + e.getMessage());

                    }
                    break;

                case 6:
                    //Removing tasks

                    System.out.println("Input the Task Title : ");
                    String rem_task = s1.nextLine().trim();
                    try (CSVReader cr = new CSVReader(new FileReader(file1))) {
                        String[] content;
                        ArrayList<task_manager> a1 = new ArrayList<task_manager>();

                        while ((content = cr.readNext()) != null) {
                            if (content.length == 5) {
                                String title1 = content[0];

                                String desc1 = content[1];
                                task_manager.Status status1 = task_manager.Status.valueOf(content[3]);
                                task_manager.Priority priority1 = task_manager.Priority.valueOf(content[2]);
                                LocalDate date1 = LocalDate.parse(content[4]);

                                a1.add(new task_manager(title1, desc1, priority1, status1, date1));
                            }
                        }

                        boolean removed = a1.removeIf(task -> task.getTitle().equalsIgnoreCase(rem_task));

                        System.out.println("\nThe Task has been discarded");
                        if (removed)
                        {
                            try (FileWriter fileWriter = new FileWriter("storage_file2.csv")) {

                                for (task_manager task : a1) {
                                    fileWriter.write(task.getTitle() + "," + task.getDescription() + "," + task.getPriority() + "," + task.getStatus() + "," + task.getDue_date() + "\n");

                                }
                            } catch (IOException e) {
                                System.out.println("Error");

                            }
                        }

                    }
                    catch(IOException | CsvValidationException e )
                    {
                        System.out.println("The error is " + e.getMessage());
                    }
                    break;

                case 7 :
                    System.out.println("The Program has Exited Successfully");
                    System.exit(0);


            }
        }

    }
}