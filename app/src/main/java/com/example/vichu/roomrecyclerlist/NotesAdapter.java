/**
 * Package : com.example.vichu.roomrecyclerlist
 * File Name: NotesAdapter.java
 * Brief: RecyclerAdapter which is used to establish the view for the Ui
 * RoomRecyclerList Project is Strictly Used for Study Purpose Only
 * Author : Vishnu Muraleedharan.
 **/

package com.example.vichu.roomrecyclerlist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewholder> {

    private List<Note> list;
    private Context context;
    private OnNoteItemClick onNoteItemClick;

    public NotesAdapter(List<Note> list, NoteListActivity activity) {
        this.list = list;
        this.onNoteItemClick = (OnNoteItemClick) activity;
        this.context = activity;
    }

    @Override
    public NotesViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater;
        inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.note_list_view, parent, false);
        NotesViewholder viewholder = new NotesViewholder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(NotesViewholder holder, int position) {
        holder.setData(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class NotesViewholder extends RecyclerView.ViewHolder implements View.OnLongClickListener {

        private TextView textViewContent;
        private TextView textViewTitle;
        private Note note;

        public NotesViewholder(View itemView) {
            super(itemView);
            itemView.setOnLongClickListener(this);
            textViewContent = itemView.findViewById(R.id.item_text);
            textViewTitle = itemView.findViewById(R.id.tv_title);
        }

        public void setData(Note note) {
            this.note = note;
            textViewTitle.setText(note.getTitle());
            textViewContent.setText(note.getContent());
        }

        @Override
        public boolean onLongClick(View view) {
            onNoteItemClick.onNoteClick(note, getAdapterPosition());
            return true;
        }
    }

    public interface OnNoteItemClick {
        void onNoteClick(Note note, int position);
    }
}
