package com.example.taras.perfumetest;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.example.taras.perfumetest.R.id;

/**
 * Created by Taras on 17.12.2014.
 */




public class PerfumeAdapter extends RecyclerView.Adapter<PerfumeAdapter.MyViewHolder> {

private ArrayList<PerfumeData> perfumeDataSet;
public Context mContext;

    public static class MyViewHolder extends RecyclerView.ViewHolder  {
        TextView textName;

        ImageView imageIcon;

        public MyViewHolder(View itemView){
            super (itemView);

            this.textName = (TextView) itemView.findViewById(id.note_text);

            this.imageIcon = (ImageView) itemView.findViewById(id.perfumeImage);


        }



    }

    public PerfumeAdapter(Context context,ArrayList<PerfumeData> perfumes){
        this.perfumeDataSet= perfumes;
        mContext=context;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.perfume_cards, parent, false);

        view.setOnClickListener(Perfume.myOnClickListener);

        MyViewHolder myViewHolder = new MyViewHolder(view);

        return myViewHolder;
    }




    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {

        TextView textViewName = holder.textName;

        ImageView imageView = holder.imageIcon;

        textViewName.setText(perfumeDataSet.get(listPosition).getName());
        String src = perfumeDataSet.get(listPosition).getImage();
        Picasso.with(mContext).load(src).resize(500,500).into(holder.imageIcon);

       //imageView.setImageResource(perfumeDataSet.get(listPosition).getImage());
    }

    @Override
    public int getItemCount() {
        return perfumeDataSet.size();
    }





}
