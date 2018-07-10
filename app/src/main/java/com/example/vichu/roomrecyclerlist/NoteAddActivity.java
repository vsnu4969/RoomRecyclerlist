/**
 * Package : com.example.vichu.roomrecyclerlist
 * File Name: NoteAddActivity.java
 * Brief: NoteAddActivity which is used to add and update the note list
 * RoomRecyclerList Project is Strictly Used for Study Purpose Only
 * Author : Vishnu Muraleedharan.
 **/


package com.example.vichu.roomrecyclerlist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.vichu.roomrecyclerlist.RoomDatabase.Note;
import com.example.vichu.roomrecyclerlist.RoomDatabase.NoteDatabase;

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
        Intent intent=getIntent();
        note = (Note) intent.getSerializableExtra("current_object");
        Button button = findViewById(R.id.button_note_save);
        et_title.setText(note.getTitle());
        et_content.setText(note.getContent());
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // fetch data and create note object
                note.setTitle(et_title.getText().toString());
                note.setContent(et_content.getText().toString());
                updateNote(note);
                }
        });
    }

    /**
     * @brief Updating the note and gettin gbackto the note list.
     */
    private void updateNote(Note note) {
        noteDatabase.noteDao().update(note);
        this.finish();
    }
}
