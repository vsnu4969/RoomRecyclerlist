package com.example.vichu.roomrecyclerlist;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class NoteListActivity extends AppCompatActivity implements NotesAdapter.OnNoteItemClick {

    private TextView textViewMsg;
    private RecyclerView recyclerView;
    private List<Note> notes;
    private NotesAdapter notesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_list_activity);
        initUI();
    }

    @Override
    protected void onResume() {
        super.onResume();
        TraceLog.entryLog();
        displayList();
        TraceLog.exitLog();
    }

    private void displayList() {
        new RetrieveTask(this).execute();
    }

    @Override
    public void onNoteClick(Note note, int clickedPosition) {
        RemoveTask removeTask = new RemoveTask(this);
        removeTask.execute(note);
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
<<<<<<< HEAD
            TraceLog.entryLog();
            updateList(notes);
            TraceLog.exitLog();
=======
            if (notes != null && notes.size() > 0) {
                textViewMsg.setVisibility(View.GONE);
                notesAdapter = new NotesAdapter(notes, NoteListActivity.this);
                recyclerView.setAdapter(notesAdapter);
                recyclerView.setVisibility(View.VISIBLE);
            } else {
                recyclerView.setVisibility(View.GONE);
                textViewMsg.setVisibility(View.VISIBLE);
            }
>>>>>>> 755ec43d4092309b7c15bd75fe0301f072a64692
        }

    }

    private void updateList(List<Note> notes) {
        TraceLog.entryLog();
        TraceLog.log(Log.INFO, "List", "size : "
                + (notes != null ? notes.size() : 0));
        if (notes != null && !notes.isEmpty()) {
            textViewMsg.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            notesAdapter.setList(notes);
        } else {
            recyclerView.setVisibility(View.GONE);
            textViewMsg.setVisibility(View.VISIBLE);
        }
        TraceLog.exitLog();
    }

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

    private void initUI() {
        TraceLog.entryLog();
        textViewMsg = findViewById(R.id.text_list_empty);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.floatingactionbar_addnote);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), NoteAddActivity.class);
                startActivity(intent);
            }
        });
        recyclerView = findViewById(R.id.recylerview_notelist);
        recyclerView.setLayoutManager(new LinearLayoutManager(NoteListActivity.this));
        notesAdapter = new NotesAdapter(NoteListActivity.this);
        recyclerView.setAdapter(notesAdapter);
        TraceLog.exitLog();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_about) {
            Toast.makeText(this, "Vishnu Muraleedharan/nQuEST GLOBAL", Toast.LENGTH_LONG).show();
        }

        return super.onOptionsItemSelected(item);
    }
}
