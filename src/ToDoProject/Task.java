package ToDoProject;

import java.util.Date;

class Task {
    private String index;
    private final String text;
    private final String date;
    private String status;

    public Task (int index, String text, Date date, String status) {
        this.index = String.valueOf(index);
        this.text = text;
        this.date = String.valueOf(date);
        this.status = status;
    }

    public Task (String content) {
        this.index = content.substring(0, content.indexOf(" "));
        this.text = content.substring(content.indexOf(" ")+1, content.length()-14);
        this.date = content.substring(content.length()-34, content.length()-5);
        this.status = content.substring(content.length()-4);
    }

    public void setCorrectIndex (int index) {
        this.index = String.valueOf(index);
    }

    public void setStatus (String status) {
        this.status = status;
    }

    @Override
    public String toString () {
        return  index + ' ' + text + ' ' + date + ' ' + status;
    }
}
