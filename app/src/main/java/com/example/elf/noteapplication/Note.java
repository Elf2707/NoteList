package com.example.elf.noteapplication;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Andrey on 29.01.2015.
 */
public class Note implements Serializable {


    public Note(String title, String note, Date data) {
        this.title = title;
        this.note = note;
        this.data = data;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getFormatDate(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        return dateFormat.format( this.data );
    }

    private String title = "";
    private String note = "";
    private Date data = null;
}
