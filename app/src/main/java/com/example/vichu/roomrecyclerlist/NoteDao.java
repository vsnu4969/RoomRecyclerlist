/**
* Package : com.example.vichu.roomrecyclerlist
* File Name: NoteDao.java
* Brief: This inteface represents the Dao where define all methods to access database
* RoomRecyclerList Project is Strictly Used for Study Purpose Only
* Author : Vishnu Muraleedharan.
**/

package com.example.vichu.roomrecyclerlist;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.provider.SyncStateContract;

import java.util.List;


@Dao
 public interface NoteDao   {

    @Query("SELECT * FROM "+ Constants.TABLE_NAME_NOTE)
    List<Note> getAll();

    /*
     * Insert the object in database
     * @param note, object to be inserted
     */
    @Insert
    void insert(Note note);

    /*
     * update the object in database
     * @param note, object to be updated
     */
    @Update
    void update(Note repos);

    /*
     * delete the object from database
     * @param note, object to be deleted
     */
    @Delete
    void delete(Note note);

    /*
     * delete list of objects from database
     * @param note, array of objects to be deleted
     */
    @Delete
    void delete(Note... note);      // Note... is varargs, here note is an array


}
