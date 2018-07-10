/**
 * Package : com.example.vichu.roomrecyclerlist
 * File Name: Note.java
 * Brief: Used as the Entity and the data from this class will added to the database
 * RoomRecyclerList Project is Strictly Used for Study Purpose Only.
 * Author : Vishnu Muraleedharan.
 **/

package com.example.vichu.roomrecyclerlist.RoomDatabase;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.example.vichu.roomrecyclerlist.constants.Constants;
import com.example.vichu.roomrecyclerlist.utils.DateRoomConverter;

import java.io.Serializable;
import java.util.Date;

/**
 *
 *Brief: Used as the Entity and the data from this class will added to the database
 * here class name must be annotated with @Entity
 *
 **/

@Entity(tableName = Constants.TABLE_NAME_NOTE)
public class Note implements Serializable {
    /**
     *
     *Brief:  There should be atleast one primary key present in the class
     *
     **/
    @PrimaryKey(autoGenerate = true)
    private int data_id;


    /**
     *
     *Brief:  information about the column
     *
     **/
    @TypeConverters(DateRoomConverter.class)
    private Date date;

    /**
     *
     *Brief:  information about the column
     *
     **/

    private String Content;

    /**
     *
     *Brief:  title of the table
     *
     **/
    private String title;

    public Note() {
    }

    /**
     *
     *Brief:  Constructor for the class
     *Param:  data_id,content,title
     *
     **/
    public Note(String content, String title) {
        this.Content = content;
        this.title = title;
        this.date = new Date(System.currentTimeMillis());

    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    /**

     *
     *Brief:  getter for data_id
     *
     **/
    public int getData_id() {
        return data_id;
    }

    /**
     *
     *Brief:  setter for data_id
     *
     **/

    public void setData_id(int data_id) {
        this.data_id = data_id;
    }

    /**
     *
     *Brief:  getter for content
     *
     **/
    public String getContent() {
        return Content;
    }

    /**
     *
     *Brief:  setter for content
     *
     **/
    public void setContent(String content) {
        Content = content;
    }

    /**
     *
     *Brief:  getter for title
     *
     **/
    public String getTitle() {
        return title;
    }

    /**
     *
     *Brief:  setter for title
     *
     **/
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     *
     *Brief:  in built overriden method for @Entity class where table values are checked
     *
     **/
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Note)) {
            return false;
        }
        Note note = (Note) obj;
        if (data_id != note.data_id) {
            return false;
        }
        return title != null ? title.equals(note.title) : note.title == null;
    }

    /**
     *
     *Brief:  in built overriden method for @Entity
     *
     **/


    @Override
    public int hashCode() {
        int result = data_id;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        return result;
    }

    /**
     *
     *Brief:  to_string for the corresponding variables
     *
     **/
    @Override
    public String toString() {
        return "Note {" +
                "data_id=" + data_id +
                ", Content='" + Content + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
