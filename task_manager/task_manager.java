package task_manager;

import java.time.LocalDate;
import java.util.Date;

public class task_manager {



    public enum Priority {

         HIGH,MEDIUM, LOW
    }


    public enum Status {
        COMPLETED ,PENDING
    }


    private String title;
    private String description;
    public Status status;
    public Priority priority;
    private LocalDate due_date;


    public Priority getPriority() {
        return priority;
    }

    public LocalDate getDue_date() {
        return due_date;
    }

    public Status getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }


    public String getTitle() {
        return title;
    }

    public task_manager() {}

    public task_manager(String title, String description, Priority priority, Status status, LocalDate due_date) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.priority = priority;
        this.due_date = due_date;

        //Very helpful but dont need it anymore.
        //System.out.println(title+""+description+""+status+""+priority+""+due_date);
    }
}
