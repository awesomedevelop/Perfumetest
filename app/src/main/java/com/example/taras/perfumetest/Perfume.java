package com.example.taras.perfumetest;

import android.app.ProgressDialog;
import android.content.Intent;
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
import android.widget.Toast;

import com.example.taras.perfumetest.dbhelper.ExternalDbOpenHelper;

import java.util.ArrayList;


public class Perfume extends ActionBarActivity {
    static View.OnClickListener myOnClickListener;
    private static final String DB_NAME = "perfume.sqlite3";
    private static final String TABLE_NAME = "perfume";
    private static final String BRAND_NAME = "name";
    private static final String BRAND_IMAGE ="image";
    private SQLiteDatabase database;
    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    private static ArrayList<PerfumeData> perfumes;
    String BrandName;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      //  myOnClickListener = new MyOnClickListener(this);
        recyclerView = (RecyclerView)findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        Intent intent =  getIntent();
        BrandName = intent.getStringExtra("brand");
        ExternalDbOpenHelper dbOpenHelper = new ExternalDbOpenHelper(this, DB_NAME);
        database = dbOpenHelper.openDataBase();
        Toast.makeText(getApplication(),BrandName,Toast.LENGTH_SHORT).show();
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        new InsertDataTask().execute();

    }

    private void fillPerfumes (){
        perfumes = new ArrayList<PerfumeData>();

        Cursor brandCursor = database.query(
                TABLE_NAME,
                new String[] { BRAND_NAME,BRAND_IMAGE},
                "brand" +"="+ "'"+BrandName+"'", null, null, null,
                BRAND_NAME);
        brandCursor.moveToFirst();
        Log.i("Cursor",brandCursor.toString());


        if(!brandCursor.isAfterLast()){
            do {
                String name = brandCursor.getString(0);
                String img = "http://perfumeapp.16mb.com/rus/"+brandCursor.getString(1).trim();
                Log.i("[Image]",img);
                perfumes.add(new PerfumeData(name, img));

            } while (brandCursor.moveToNext());
        }
        brandCursor.close();
    } // Конец заполнения бд

    private class InsertDataTask extends AsyncTask<Integer,Void,Void> {
        private final ProgressDialog dialog = new ProgressDialog(
                Perfume.this);

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
            adapter = new PerfumeAdapter(Perfume.this,perfumes);
            recyclerView.setAdapter(adapter);

        }}
// Конец асинхронки









    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_perfume, menu);
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
