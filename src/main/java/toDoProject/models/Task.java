package toDoProject.models;

import java.util.Date;

public class Task {
    private long id;
    private final String text;
    private final String date;
    private String status;

    public Task (String text, Date date, String status) {
        this.id = 0;
        this.text = text;
        this.date = String.valueOf(date);
        this.status = status;
    }

    public Task (String content) {
        this.id = Long.parseLong(content.substring(0, content.indexOf(" ")));
        this.text = content.substring(content.indexOf(" ")+1, content.length()-35);
        this.date = content.substring(content.length()-34, content.length()-5);
        this.status = content.substring(content.length()-4);
    }

    public void setId (long id) { this.id = id; }

    public void setStatus (String status) { this.status = status; }

    public long getId ( ) { return id; }

    public String getText ( ) {
        return text;
    }

    public String getDate ( ) {
        return date;
    }

    public String getStatus ( ) {
        return status;
    }

    @Override
    public String toString () { return  text + ' ' + date + ' ' + status; }
}