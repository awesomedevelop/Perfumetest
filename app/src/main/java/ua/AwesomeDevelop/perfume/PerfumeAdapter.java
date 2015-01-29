package ua.AwesomeDevelop.perfume;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import ua.AwesomeDevelop.perfume.dbhelper.ExternalDbOpenHelper;

/**
 * Created by Taras on 17.12.2014.
 */




public class PerfumeAdapter extends RecyclerView.Adapter<PerfumeAdapter.MyViewHolder> {

private ArrayList<PerfumeData> perfumeDataSet;
public Context mContext;
    private int lastPosition = -1;
    public static class MyViewHolder extends RecyclerView.ViewHolder  {
        TextView textName;
        TextView textNotes;
        TextView textDescription;
        TextView textBrand;
        ImageView imageIcon;
        CardView container;
        CheckBox favorite;


        public MyViewHolder(View itemView){
            super (itemView);
            this.textDescription = (TextView) itemView.findViewById(R.id.description_text);
            this.textNotes = (TextView) itemView.findViewById(R.id.note_text);
            this.textName = (TextView) itemView.findViewById(R.id.tittle_text);
            this.container = (CardView) itemView.findViewById(R.id.perfume_card);
            this.imageIcon = (ImageView) itemView.findViewById(R.id.perfumeImage);
            this.favorite = (CheckBox)itemView.findViewById(R.id.favorite);
            this.textBrand = (TextView)itemView.findViewById(R.id.brand_for_perfume);
            favorite.setTag(this);
            favorite.setOnClickListener(Perfume.myOnClickListener);
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

      //  view.setOnClickListener(Perfume.myOnClickListener);

        MyViewHolder myViewHolder = new MyViewHolder(view);

        return myViewHolder;
    }




    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {

        final TextView textViewName = holder.textName;
        TextView textViewDescription = holder.textDescription;
        ImageView imageView = holder.imageIcon;
        TextView textViewNotes = holder.textNotes;
        CheckBox favoriteBox = holder.favorite;
        TextView textViewBrand = holder.textBrand;
//        Typeface tf = Typeface.createFromAsset(mContext.getAssets(),
//                "fonts/type2.ttf");
//        textViewDescription.setTypeface(tf);

       // textViewName.setTextSize(50);
        textViewDescription.setText(Html.fromHtml(perfumeDataSet.get(listPosition).getDescription()));
        textViewNotes.setText(Html.fromHtml(perfumeDataSet.get(listPosition).getNotes()));
        textViewName.setText(perfumeDataSet.get(listPosition).getName());
        String src = perfumeDataSet.get(listPosition).getImage();
        Picasso.with(mContext).load(src).placeholder(R.drawable.placeholder).resize(500,500).into(holder.imageIcon);
        textViewBrand.setText(perfumeDataSet.get(listPosition).getBrand());
        if (perfumeDataSet.get(listPosition).getFavorite()!=1){
            favoriteBox.setChecked(false);
        }
        else favoriteBox.setChecked(true);




        setAnimation(holder.container,listPosition);
       // favoriteBox.setTag(this);



       //imageView.setImageResource(perfumeDataSet.get(listPosition).getImage());

//////нажатие фаворита
        favoriteBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // String s = String.valueOf(listPosition);
                int marked = perfumeDataSet.get(listPosition).getFavorite();
                String f = perfumeDataSet.get(listPosition).getName();
                //Toast.makeText(mContext.getApplicationContext(),f,Toast.LENGTH_LONG).show();
                updateFavorite(f,marked);
            }
        });///////нажатие фаворита













    }
////////занести фаворит в бд
    public void updateFavorite(String bran,int mark){
        String[] s = new String[] {bran};
        int marked = mark;
        SQLiteDatabase database;
        ExternalDbOpenHelper dbOpenHelper = new ExternalDbOpenHelper(mContext, "perfume.sqlite3");
        database = dbOpenHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        if(marked!=1){
        cv.put("favorite",1);}
        else cv.put("favorite",0);
        database.update("perfume",cv,"name = ?",s);


    }/////конец фаворита в бд


    private void setAnimation(View viewToAnimate, int listPosition){
        // If the bound view wasn't previously displayed on screen, it's animated
        if (listPosition > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.bottom_in);
            viewToAnimate.startAnimation(animation);
            lastPosition = listPosition;
        }
    }
    @Override
    public int getItemCount() {
        return perfumeDataSet.size();
    }





}
