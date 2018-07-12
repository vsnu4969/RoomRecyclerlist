/**
 * Package : com.example.vichu.roomrecyclerlist
 * File Name: NoteListActivity.java
 * Brief: NoteListActivity which is used to show the note list
 * RoomRecyclerList Project is Strictly Used for Study Purpose Only
 * Author : Vishnu Muraleedharan.
 **/

package com.example.vichu.roomrecyclerlist;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vichu.roomrecyclerlist.RoomDatabase.Note;
import com.example.vichu.roomrecyclerlist.RoomDatabase.NoteDatabase;
import com.example.vichu.roomrecyclerlist.adapter.NotesAdapter;
import com.example.vichu.roomrecyclerlist.utils.TraceLog;

import java.util.List;

/**
 * @brief NoteListActivity which is used to show the note list.
 */
public class NoteListActivity extends AppCompatActivity implements NotesAdapter.OnNoteItemClick {
    private EditText et_title, et_content;
    Note note;
    Button button;
    LinearLayout linearLayout;
    private TextView textViewMsg;
    private RecyclerView recyclerView;
    private List<Note> notes;
    private List<Note> deleteNotes;
    private NotesAdapter notesAdapter;

    /**
     * @brief oncreate for NoteListActivity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_list_activity);
        linearLayout = findViewById(R.id.linear_recycler_list_no_note);
        initUI();
    }

    /**
     * @brief onresume for NoteListActivity.
     */
    @Override
    protected void onResume() {
        super.onResume();
        TraceLog.entryLog();
        displayList();
        TraceLog.exitLog();
    }

    /**
     * @brief retreive data from the Room database and shows it using asynctask.
     */
    private void displayList() {
        new RetrieveTask(this).execute();
    }

    @Override
    public void onNoteClick(Note note, int clickedPosition) {
        RemoveTask removeTask = new RemoveTask(this);
        removeTask.execute(note);
    }

    @Override
    public void onShortClick(Note note, int position) {
        Intent intent = new Intent(this, NoteAddActivity.class);
        intent.putExtra("current_object", note);
        startActivity(intent);
    }

    private class RetrieveTask extends AsyncTask<Void, Void, List<Note>> {


        private Context context;

        RetrieveTask(Context context) {
            this.context = context;
        }

        @Override
        protected List<Note> doInBackground(Void... voids) {
            TraceLog.entryLog();
            NoteDatabase noteDatabase = NoteDatabase.getInstance(context);
            TraceLog.exitLog();
            return noteDatabase.noteDao().getAll();
        }

        @Override
        protected void onPostExecute(List<Note> notes) {

            TraceLog.entryLog();
            updateList(notes);
            TraceLog.exitLog();

        }

    }

    /**
     * @brief updating the list when no element vs when there is an element present.
     */
    private void updateList(List<Note> notes) {
        TraceLog.entryLog();
        TraceLog.log(Log.INFO, "List", "size : "
                + (notes != null ? notes.size() : 0));
        if (notes != null && !notes.isEmpty()) {
            linearLayout.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            notesAdapter.setList(notes);
            deleteNotes = notes;
        } else {
            recyclerView.setVisibility(View.GONE);
            linearLayout.setVisibility(View.VISIBLE);
        }
        TraceLog.exitLog();
    }

    /**
     * @brief remove data from the Room database and shows it using asynctask.
     */
    private class RemoveTask extends AsyncTask<Note, Void, Note> {

        private NoteDatabase noteDatabase;
        private Context context;

        RemoveTask(Context context) {
            this.context = context;
            noteDatabase = NoteDatabase.getInstance(context);
        }

        @Override
        protected Note doInBackground(Note... notes) {
            TraceLog.entryLog();
            Note note = notes[0];
            noteDatabase.noteDao().delete(note);
            TraceLog.exitLog();
            return note;
        }

        @Override
        protected void onPostExecute(Note note) {
            TraceLog.entryLog();
            notesAdapter.removeItem(note);
            List<Note> list = notesAdapter.getList();
            if (list == null || list.isEmpty())
                updateList(null);
            TraceLog.exitLog();
        }
    }

    /**
     * @brief initialising Ui by setting recycler view.
     */
    private void initUI() {
        TraceLog.entryLog();
        final NoteDatabase noteDatabase = NoteDatabase.getInstance(this);
        textViewMsg = findViewById(R.id.text_list_empty);
        et_title = findViewById(R.id.text_note_title);
        et_content = findViewById(R.id.text_note_content);
        Button button = findViewById(R.id.button_note_save);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // fetch data and create note object
                note = new Note(et_content.getText().toString(), et_title.getText().toString());
                // create worker thread to insert data into database
                //noteDatabase.noteDao().insert(note);
                insertNote(note);
                //displayList();
            }
        });

        recyclerView = findViewById(R.id.recylerview_notelist);
        recyclerView.setLayoutManager(new LinearLayoutManager(NoteListActivity.this));
        notesAdapter = new NotesAdapter(NoteListActivity.this);
        recyclerView.setAdapter(notesAdapter);
        TraceLog.exitLog();
    }

    private void insertNote(Note note) {

        new InsertNote(this, note).execute();
    }

    /**
     * @brief setting the menu inflater.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * @brief setting the item selected in the menu.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {

            case R.id.action_about:
                Toast.makeText(this, "Vishnu Muraleedharan/nQuEST GLOBAL", Toast.LENGTH_LONG).show();
                break;
            case R.id.action_delete_all:
                DeleteAll();

        }

        return super.onOptionsItemSelected(item);
    }

    private void DeleteAll() {

//        NoteDatabase noteDatabase=NoteDatabase.getInstance(this);
//        if(noteDatabase.noteDao().getCount()> 0) {
//            noteDatabase.noteDao().deleteAll();
        deleteAllNotes();
//            displayList();
//            Toast.makeText(this, " All notes Deleted", Toast.LENGTH_SHORT).show();
    }

    private void deleteAllNotes() {
        new DeleteAllNotes(this).execute();
    }
//        else{
//            Toast.makeText(this, " Nothing to Delete.", Toast.LENGTH_SHORT).show();
//
//        }

    /**
     * @brief asynctask for insert new note.
     */
    private class InsertNote extends AsyncTask<Void, Void, Void> {
        Context context;
        Note note;

        public InsertNote(Context context, Note note) {
            this.context = context;
            this.note = note;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            TraceLog.entryLog();
            NoteDatabase noteDatabase = NoteDatabase.getInstance(context);
            noteDatabase.noteDao().insert(note);
            TraceLog.exitLog();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            TraceLog.entryLog();
            displayList();
            TraceLog.exitLog();
        }
    }

    /**
     * @brief AsyncTask class for delete all notes.
     */
    private class DeleteAllNotes extends AsyncTask<Void, String, Void> {

        private Context context;

        public DeleteAllNotes(Context context) {
            this.context = context;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            TraceLog.entryLog();
            NoteDatabase noteDatabase = NoteDatabase.getInstance(context);
            if (noteDatabase.noteDao().getCount() > 0) {
                noteDatabase.noteDao().deleteAll();
                publishProgress("All notes Deleted");
            } else {
                publishProgress("Nothing to Delete.");
            }
            TraceLog.exitLog();
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            Toast.makeText(context, values[0], Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            TraceLog.entryLog();
            displayList();
            TraceLog.exitLog();
        }
    }
}
