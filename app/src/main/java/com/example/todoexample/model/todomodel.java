package com.example.todoexample.model;

public class todomodel {
    private int id, status; //status is a bool - 0/1
    private String task;
    private String date_due;
    //each tasks has 3 attributes, id, status, and task (name)

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getDate_due() {return date_due;}

    public void setDate_due(String date_due) { this.date_due = date_due; }
}
