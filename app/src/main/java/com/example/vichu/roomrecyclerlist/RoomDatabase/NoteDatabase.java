/**
 * Package : com.example.vichu.roomrecyclerlist
 * File Name: NoteDatabase.java
 * Brief: This calss will the database where all the required notes are saved
 * RoomRecyclerList Project is Strictly Used for Study Purpose Only
 * Author : Vishnu Muraleedharan.
 **/

package com.example.vichu.roomrecyclerlist.RoomDatabase;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.vichu.roomrecyclerlist.constants.Constants;

/**
 *
 *Brief:  @Databast annotation is used inorder to represent the entity as well as the
 * database version
 *
 **/
@Database(entities = {Note.class}, version = 2)
public abstract class NoteDatabase extends RoomDatabase {

    /**
     *
     *Brief: abstract method used to get NoteDao elements.
     *
     **/
    public abstract NoteDao noteDao();

    /**
     *
     *Brief:  static object of the NoteDatabase claass
     *
     **/
    private static NoteDatabase sNoteDatabase;

    /**
     *
     *Brief:  used to get instance of the class
     *
     **/
    public static NoteDatabase getInstance(Context context) {
        if (sNoteDatabase == null) {
            sNoteDatabase = buildInstance(context);
        }
        return sNoteDatabase;
    }

    /**
     *
     *Brief: method used to build instance and Additionally, it is good to note that Room does
     * not allow code execution on Main thread. Instead, allowMainThreadQueries is used to allow
     * the execution. However, using this is not recommended on real apps.
     *
     **/
    private static NoteDatabase buildInstance(Context context) {
        return Room
                .databaseBuilder(context, NoteDatabase.class, Constants.DB_NAME)
                .fallbackToDestructiveMigration()
                .build();
    }

    /**
     *
     *Brief: method used to clean the values or make empty the database.
     *
     **/
    public void cleanUp() {
        sNoteDatabase = null;
    }

}
