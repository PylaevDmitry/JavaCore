package ToDoProject.Models;

import java.util.Date;

public class Task {
    private int id;
    private final String text;
    private final String date;
    private String status;

    public Task (int id, String text, Date date, String status) {
        this.id = id;
        this.text = text;
        this.date = String.valueOf(date);
        this.status = status;
    }

    public Task (String content) {
        this.id = Integer.parseInt(content.substring(0, content.indexOf(" ")));
        this.text = content.substring(content.indexOf(" ")+1, content.length()-35);
        this.date = content.substring(content.length()-34, content.length()-5);
        this.status = content.substring(content.length()-4);
    }

    public void setStatus (String status) { this.status = status; }

    public int getId ( ) {
        return id;
    }

    public String getText ( ) {
        return text;
    }

    public String getDate ( ) {
        return date;
    }

    public String getStatus ( ) {
        return status;
    }

    public void setId (int id) {
        this.id = id;
    }

    @Override
    public String toString () { return  String.valueOf(id) + ' ' + text + ' ' + date + ' ' + status; }
}