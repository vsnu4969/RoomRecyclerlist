package com.example.vichu.roomrecyclerlist;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.util.List;

public class NoteListActivity extends AppCompatActivity {
NoteDatabase noteDatabase;
TextView textViewMsg;
RecyclerView recyclerView;
    private List<Note> notes;
    private NotesAdapter notesAdapter;
    private int pos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_list_activity);
        initializeVies();
        displayList();
    }

    private void displayList(){
// initialize database instance
        noteDatabase = NoteDatabase.getInstance(NoteListActivity.this);
// fetch list of notes in background thread
        new RetrieveTask(this).execute();
    }

    private static class RetrieveTask extends AsyncTask<Void,Void,List<Note>> {

        private WeakReference<NoteListActivity> activityReference;

        // only retain a weak reference to the activity
        RetrieveTask(NoteListActivity context) {
            activityReference = new WeakReference<>(context);
        }

        @Override
        protected List<Note> doInBackground(Void... voids) {
            if (activityReference.get()!=null)
                return activityReference.get().noteDatabase.getNoteDao().getAll();
            else
                return null;
        }

        @Override
        protected void onPostExecute(List<Note> notes) {
            if (notes!=null && notes.size()>0 ){
                activityReference.get().notes = notes;

                // hides empty text view
                activityReference.get().textViewMsg.setVisibility(View.GONE);

                // create and set the adapter on RecyclerView instance to display list
                activityReference.get().notesAdapter = new NotesAdapter(notes,activityReference.get());
                activityReference.get().recyclerView.setAdapter(activityReference.get().notesAdapter);
            }
        }

    }

    private void initializeVies(){
        textViewMsg = (TextView) findViewById(R.id.text_list_empty);
        // Action button to add note
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.floatingactionbar_addnote);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),NoteAddActivity.class);
                startActivity(intent);
            }
        });
        recyclerView = findViewById(R.id.recylerview_notelist);
        recyclerView.setLayoutManager(new LinearLayoutManager(NoteListActivity.this));

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
