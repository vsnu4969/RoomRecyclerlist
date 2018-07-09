/**
 * Package : com.example.vichu.roomrecyclerlist
 * File Name: NoteAddActivity.java
 * Brief: NoteAddActivity which is used to add and update the note list
 * RoomRecyclerList Project is Strictly Used for Study Purpose Only
 * Author : Vishnu Muraleedharan.
 **/


package com.example.vichu.roomrecyclerlist;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.vichu.roomrecyclerlist.RoomDatabase.Note;
import com.example.vichu.roomrecyclerlist.RoomDatabase.NoteDatabase;

import java.io.Serializable;
import java.lang.ref.WeakReference;

/**
 * @brief   NoteAddActivity which is used to add and update the note list.
 *
 */
public class NoteAddActivity extends AppCompatActivity {
    private EditText et_title,et_content;
    private NoteDatabase noteDatabase;
    private Note note;

    /**
     * @brief oncreate for the NoteAddActivity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_add);
        et_title = findViewById(R.id.text_note_title);
        et_content = findViewById(R.id.text_note_content);
        noteDatabase = NoteDatabase.getInstance(NoteAddActivity.this);
        Button button = findViewById(R.id.button_note_save);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // fetch data and create note object
                note = new Note(et_content.getText().toString(), et_title.getText().toString());
                // create worker thread to insert data into database
                new InsertTask(NoteAddActivity.this,note).execute();
            }
        });

    }

    /**
     * @brief setting the result for the NoteAddActivity.
     */
    private void setResult(Note note, int flag){
        setResult(flag,new Intent().putExtra("note", (Serializable) note));
        finish();
    }

    /**
     * @brief inserting note in the database using asynctask.
     */
    private static class InsertTask extends AsyncTask<Void,Void,Boolean> {

        private WeakReference<NoteAddActivity> activityReference;
        private Note note;

        // only retain a weak reference to the activity
        InsertTask(NoteAddActivity context, Note note) {
            activityReference = new WeakReference<>(context);
            this.note = note;
        }

        // doInBackground methods runs on a worker thread
        @Override
        protected Boolean doInBackground(Void... objs) {
            activityReference.get().noteDatabase.noteDao().insert(note);
            return true;
        }

        // onPostExecute runs on main thread
        @Override
        protected void onPostExecute(Boolean bool) {
            if (bool){
                activityReference.get().setResult(note,1);
            }
        }

    }

}
