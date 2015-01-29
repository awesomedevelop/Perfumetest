package ua.AwesomeDevelop.perfume;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Taras on 22.01.2015.
 */
public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.MyViewHolder> {

    private ArrayList<NoteData> noteDataSet;
    public Context mContext;

    public static class MyViewHolder extends RecyclerView.ViewHolder  {
        TextView textNote;

        public MyViewHolder(View itemView){
            super (itemView);

            this.textNote = (TextView) itemView.findViewById(R.id.note_name);

        }



    }

    public NoteAdapter(Context context, ArrayList<NoteData> notes){
        this.noteDataSet= notes;
        mContext=context;

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_cards, parent, false);

        view.setOnClickListener(NoteActivity.myOnClickListener);

        MyViewHolder myViewHolder = new MyViewHolder(view);

        return myViewHolder;
    }




    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {

        final TextView textViewNote = holder.textNote;

        textViewNote.setText(noteDataSet.get(listPosition).getNote_name());

        Log.i("[CHECKLIST]",noteDataSet.get(listPosition).getNote_name());





    }

    @Override
    public int getItemCount() {
        return noteDataSet.size();
    }





}
