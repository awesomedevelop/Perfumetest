package com.example.taras.perfumetest;

import android.app.ProgressDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.taras.perfumetest.dbhelper.ExternalDbOpenHelper;

import java.util.ArrayList;


public class FavoriteActivity extends ActionBarActivity {
    static View.OnClickListener myOnClickListener;
    static View.OnClickListener favoriteOnClickListener;
    private static final String DB_NAME = "perfume.sqlite3";
    private static final String TABLE_NAME = "perfume";
    private static final String BRAND_NAME = "name";
    private static final String BRAND_IMAGE ="image";
    private static final String PERFUME_NOTES = "notes";
    private static final String PERFUME_FAVORITE = "favorite";
    private static final String PERFUME_DESCRIPTION = "description";
    private SQLiteDatabase database;
    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    private static ArrayList<PerfumeData> perfumes;
    String condition;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfume);
        getSupportActionBar().setTitle("Избранное");

     //   myOnClickListener = new MyOnClickListener(this);

        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
      //  Intent intent =  getIntent();
       // condition = "brand = " +"'"+intent.getStringExtra("brand").trim()+"'";
        //  condition = "brand = 'Adidas'";
        ExternalDbOpenHelper dbOpenHelper = new ExternalDbOpenHelper(this, DB_NAME);
        database = dbOpenHelper.openDataBase();


        recyclerView.setItemAnimator(new DefaultItemAnimator());

        new InsertDataTask().execute();

    }





    private void fillPerfumes (){
        perfumes = new ArrayList<PerfumeData>();
        Log.i("[TEST]", "1");
        Cursor perfumeCursor = database.query(
                TABLE_NAME,
                new String[] { BRAND_NAME,BRAND_IMAGE,PERFUME_NOTES,PERFUME_DESCRIPTION,PERFUME_FAVORITE},
                "favorite = 1", null, null, null,
                BRAND_NAME);
      //  Log.i("[CONDITION]",condition);
        perfumeCursor.moveToFirst();
       // Log.i("Cursor", perfumeCursor.toString());


        if(!perfumeCursor.isAfterLast()){
            do {
                String name = perfumeCursor.getString(0);
                String img = "http://perfumeapp.16mb.com/rus/"+perfumeCursor.getString(1).trim();
                String notes = perfumeCursor.getString(2);
                String description = perfumeCursor.getString(3);
                int favorite= perfumeCursor.getInt(4);
                Log.i("[Image]",img);
                Log.i("[NAME]",name);
                perfumes.add(new PerfumeData(name, img, notes,description,favorite));

            } while (perfumeCursor.moveToNext());
        }
        perfumeCursor.close();
    } // Конец заполнения бд





    private class InsertDataTask extends AsyncTask<Integer,Void,Void> {
        private final ProgressDialog dialog = new ProgressDialog(
                FavoriteActivity.this);

        // can use UI thread here
        protected void onPreExecute() {
            //this.dialog.setMessage("Загрузка...");
            //  this.dialog.show();
        }
        protected Void doInBackground(Integer...params) {
            fillPerfumes();


            //SystemClock.sleep(1500);
            return null;
        }
        protected void onPostExecute(final Void unused) {
            if (this.dialog.isShowing()) {
                this.dialog.dismiss();
                // setUpList();

            }
            adapter = new PerfumeAdapter(FavoriteActivity.this,perfumes);
            recyclerView.setAdapter(adapter);


        }}
// Конец асинхронки





















    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_favorite, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
