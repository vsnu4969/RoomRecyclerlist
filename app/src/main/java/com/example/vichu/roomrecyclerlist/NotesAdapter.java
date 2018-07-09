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
   private LayoutInflater inflater;
   private OnNoteItemClick onNoteItemClick;

   public NotesAdapter(List<Note> list, Context context) {
      this.list = list;
       inflater=LayoutInflater.from(context);
      this.context = context;
      this.onNoteItemClick=(OnNoteItemClick)context;
   }

   @Override
   public NotesViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
      View view=inflater.inflate(R.layout.note_list_view,parent);
      NotesViewholder viewholder=new NotesViewholder(view);
      return viewholder;
   }

   @Override
   public void onBindViewHolder(NotesViewholder holder, int position) {
       holder.textViewTitle.setText(list.get(position).getTitle());
       holder.textViewContent.setText(list.get(position).getContent());
   }

   @Override
   public int getItemCount() {
      return list.size();
   }

   public class NotesViewholder extends RecyclerView.ViewHolder implements View.OnClickListener{
       TextView textViewContent;
       TextView textViewTitle;

      public NotesViewholder(View itemView) {
         super(itemView);
          itemView.setOnClickListener(this);
          textViewContent = itemView.findViewById(R.id.item_text);
          textViewTitle = itemView.findViewById(R.id.tv_title);
      }

       @Override
       public void onClick(View view) {
onNoteItemClick.onNoteClick(getAdapterPosition());
       }
   }
   public interface OnNoteItemClick{
      void onNoteClick(int pos);
   }
}
