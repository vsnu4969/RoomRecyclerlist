/**
 * Package : com.example.vichu.roomrecyclerlist
 * File Name: NotesAdapter.java
 * Brief: RecyclerAdapter which is used to establish the view for the Ui
 * RoomRecyclerList Project is Strictly Used for Study Purpose Only
 * Author : Vishnu Muraleedharan.
 **/

package com.example.vichu.roomrecyclerlist;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewholder> {

    private List<Note> list;
    private OnNoteItemClick onNoteItemClick;

    public List<Note> getList() {
        return list;
    }

    public void setList(List<Note> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public NotesAdapter(NoteListActivity activity) {
        this.onNoteItemClick = activity;
    }

    @Override
    public NotesViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
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
        return list != null
                ? list.size()
                : 0;
    }

    public void removeItem(Note note) {
        int position = getPosition(note);
        list.remove(position);
        notifyItemRemoved(position);
    }

    private int getPosition(Note note) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getData_id() == note.getData_id()) {
                return i;
            }
        }
        return -1;
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
