package com.example.taras.perfume;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.example.taras.perfume.R.id;

/**
 * Created by Taras on 17.12.2014.
 */




public class BrandAdapter extends RecyclerView.Adapter<BrandAdapter.MyViewHolder> {

private ArrayList<BrandData> brandDataSet;
    public Context mContext;

    public static class MyViewHolder extends RecyclerView.ViewHolder  {
        TextView textName;
        TextView textEmail;
        ImageView imageIcon;

        public MyViewHolder(View itemView){
            super (itemView);

            this.textName = (TextView) itemView.findViewById(id.textViewName);
            //this.textEmail = (TextView) itemView.findViewById(id.textViewEmail);
            this.imageIcon = (ImageView) itemView.findViewById(id.imageView);


        }



    }

    public BrandAdapter (Context context,ArrayList<BrandData> brands){
        this.brandDataSet= brands;
        mContext=context;

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
      //  TextView textViewEmail = holder.textEmail;
        ImageView imageView = holder.imageIcon;

        textViewName.setText(brandDataSet.get(listPosition).getName());
       // textViewEmail.setText(brandDataSet.get(listPosition).getEmail());
        String src = brandDataSet.get(listPosition).getImage();
        Picasso.with(mContext).load(src).resize(300,200).into(imageView);




    }

    @Override
    public int getItemCount() {
        return brandDataSet.size();
    }





}
