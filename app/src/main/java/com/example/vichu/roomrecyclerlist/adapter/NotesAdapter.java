/**
 * Package : com.example.vichu.roomrecyclerlist
 * File Name: NotesAdapter.java
 * Brief: RecyclerAdapter which is used to establish the view for the Ui
 * RoomRecyclerList Project is Strictly Used for Study Purpose Only
 * Author : Vishnu Muraleedharan.
 **/

package com.example.vichu.roomrecyclerlist.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.vichu.roomrecyclerlist.NoteListActivity;
import com.example.vichu.roomrecyclerlist.R;
import com.example.vichu.roomrecyclerlist.RoomDatabase.Note;
import com.example.vichu.roomrecyclerlist.utils.TraceLog;

import java.util.List;

/**
 * @brief   RecyclerAdapter which is used to establish the view for the Ui.
 *
 */
public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewholder> {

    /**
     * @brief list of the class Note entity.
     */
    private List<Note> list;

    /**
     * @brief Interface Object.
     */
    private OnNoteItemClick onNoteItemClick;

    /**
     * @brief Method which contains return type Note list.
     */
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

    /**
     * @brief creating the inflater for the recycler view.
     *
     */
    @Override
    public NotesViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.note_list_view, parent, false);
        NotesViewholder viewholder = new NotesViewholder(view);
        return viewholder;
    }

    /**
     * @brief onbind view holder, setting the elements.
     */
    @Override
    public void onBindViewHolder(NotesViewholder holder, int position) {
        holder.setData(list.get(position));
    }

    /**
     * @brief list size.
     */
    @Override
    public int getItemCount() {
        return list != null
                ? list.size()
                : 0;
    }

    /**
     * @brief remove item.
     */
    public void removeItem(Note note) {
        int position = getPosition(note);
        list.remove(position);
        notifyItemRemoved(position);
    }

    /**
     * @brief getting the curretn position when clicked in the list.
     */
    private int getPosition(Note note) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getData_id() == note.getData_id()) {
                return i;
            }
        }
        return -1;
    }

    /**
     * @brief inner class which act as the view holder.
     */
    public class NotesViewholder extends RecyclerView.ViewHolder implements View.OnLongClickListener,View.OnClickListener {

        private TextView textViewContent;
        private TextView textViewTitle;
        private TextView getTextViewDate;
        private Note note;

        public NotesViewholder(View itemView) {
            super(itemView);
            itemView.setOnLongClickListener(this);
            itemView.setOnClickListener(this);
            textViewContent = itemView.findViewById(R.id.item_text);
            textViewTitle = itemView.findViewById(R.id.tv_title);
            getTextViewDate=itemView.findViewById(R.id.tv_date);
        }

        public void setData(Note note) {
            this.note = note;
            textViewTitle.setText(note.getTitle());
            textViewContent.setText(note.getContent());
            getTextViewDate.setText(String.valueOf(note.getDate()));
        }

        @Override
        public boolean onLongClick(View view) {
            onNoteItemClick.onNoteClick(note, getAdapterPosition());
            return true;
        }

        @Override
        public void onClick(View view) {
            TraceLog.entryLog();
            onNoteItemClick.onShortClick(note, getAdapterPosition());
            TraceLog.exitLog();
        }
    }

    /**
     * @brief interface where the declaration of the method is done.
     */
    public interface OnNoteItemClick {
        void onNoteClick(Note note, int position);
        void onShortClick(Note note,int position);
    }
}
