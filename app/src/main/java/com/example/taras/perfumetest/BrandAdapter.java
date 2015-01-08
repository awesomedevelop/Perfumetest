package com.example.taras.perfumetest;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import static com.example.taras.perfumetest.R.id;

/**
 * Created by Taras on 17.12.2014.
 */




public class BrandAdapter extends RecyclerView.Adapter<BrandAdapter.MyViewHolder> {

private ArrayList<BrandData> brandDataSet;


    public static class MyViewHolder extends RecyclerView.ViewHolder  {
        TextView textName;
        TextView textEmail;
        ImageView imageIcon;

        public MyViewHolder(View itemView){
            super (itemView);

            this.textName = (TextView) itemView.findViewById(id.textViewName);
            this.textEmail = (TextView) itemView.findViewById(id.textViewEmail);
            this.imageIcon = (ImageView) itemView.findViewById(id.imageView);


        }



    }

    public BrandAdapter (ArrayList<BrandData> brands){
        this.brandDataSet= brands;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cards_layout, parent, false);

        view.setOnClickListener(MainActivity.myOnClickListener);

        MyViewHolder myViewHolder = new MyViewHolder(view);

        return myViewHolder;
    }




    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {

        TextView textViewName = holder.textName;
        TextView textViewEmail = holder.textEmail;
        ImageView imageView = holder.imageIcon;

        textViewName.setText(brandDataSet.get(listPosition).getName());
        textViewEmail.setText(brandDataSet.get(listPosition).getEmail());
        imageView.setImageResource(brandDataSet.get(listPosition).getImage());




    }

    @Override
    public int getItemCount() {
        return brandDataSet.size();
    }





}
