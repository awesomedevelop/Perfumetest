package com.example.taras.perfumetest;

import android.app.ProgressDialog;
import android.content.Context;
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
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.taras.perfumetest.dbhelper.ExternalDbOpenHelper;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {

    private static final String DB_NAME = "perfume.sqlite3";
    private static final String TABLE_NAME = "brands";
    private static final String BRAND_NAME = "brand_name";
    private static final String BRAND_ID ="_id";
    private SQLiteDatabase database;
    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    private static ArrayList<BrandData> brands;
    static View.OnClickListener myOnClickListener;
    private static ArrayList<Integer> removedItems;











    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myOnClickListener = new MyOnClickListener(this);
        recyclerView = (RecyclerView)findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);



        ExternalDbOpenHelper dbOpenHelper = new ExternalDbOpenHelper(this, DB_NAME);
        database = dbOpenHelper.openDataBase();






        recyclerView.setItemAnimator(new DefaultItemAnimator());
        new InsertDataTask().execute();
        SearchView search_text = (SearchView) findViewById(R.id.search);
        search_text.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                fillApdate(newText);
                adapter = new BrandAdapter(brands);
                recyclerView.setAdapter(adapter);
                return true;
            }
        });




        //EditText search_text = (EditText) findViewById(R.id.search);

//        search_text.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            fillApdate(s);
//                adapter = new BrandAdapter(brands);
//                recyclerView.setAdapter(adapter);
//               //adapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });

    }


   private void fillApdate(String filter){
       brands = new ArrayList<BrandData>();

       Cursor brandCursor = database.query(
               TABLE_NAME,
               new String[] { BRAND_NAME},
               BRAND_NAME+ " LIKE "+"'"+filter+"%'",
               null,
                null,null,
               BRAND_NAME);
       brandCursor.moveToFirst();


       if(!brandCursor.isAfterLast()){
           do {
               String name = brandCursor.getString(0);
               brands.add(new BrandData(name,name,R.drawable.mexx,1));
               Log.i("[APDATE NAME]",name);
           } while (brandCursor.moveToNext());
       }
       if (brands==null){
           Toast.makeText(getApplicationContext(),"Ничего не найдено",Toast.LENGTH_SHORT).show();
       }

       brandCursor.close();
   }


    private void fillBrands (){
        brands = new ArrayList<BrandData>();

        Cursor brandCursor = database.query(
                TABLE_NAME,
                new String[] { BRAND_NAME},
                null, null, null, null,
                BRAND_NAME);
        brandCursor.moveToFirst();


        if(!brandCursor.isAfterLast()){
            do {
                String name = brandCursor.getString(0);
                brands.add(new BrandData(name,name,R.drawable.mexx,1));

            } while (brandCursor.moveToNext());
        }
        brandCursor.close();
    } // Конец заполнения бд





    // Асинхронка для бд
    private class InsertDataTask extends AsyncTask<Integer,Void,Void> {
        private final ProgressDialog dialog = new ProgressDialog(
                MainActivity.this);

        // can use UI thread here
        protected void onPreExecute() {
            this.dialog.setMessage("Загрузка...");
            this.dialog.show();
        }
        protected Void doInBackground(Integer...params) {
            fillBrands();


            //SystemClock.sleep(1500);
            return null;
        }
        protected void onPostExecute(final Void unused) {
            if (this.dialog.isShowing()) {
                this.dialog.dismiss();
               // setUpList();

            }
            adapter = new BrandAdapter(brands);
            recyclerView.setAdapter(adapter);

        }}
// Конец асинхронки













    private class MyOnClickListener implements View.OnClickListener {

        private final Context context;

        private MyOnClickListener(Context context) {
            this.context = context;
        }

        @Override
        public void onClick(View v) {
           nextactivity(v);

        }

        private void nextactivity(View v){
            int selectedItemPosition = recyclerView.getChildPosition(v);
            RecyclerView.ViewHolder viewHolder
                    = recyclerView.findViewHolderForPosition(selectedItemPosition);
            TextView textViewName
                    = (TextView) viewHolder.itemView.findViewById(R.id.textViewName);
            String selectedName = (String) textViewName.getText();
            Toast.makeText(context,selectedName,Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(context, Perfume.class);
            intent.putExtra("brand",selectedName);
            context.startActivity(intent);

            overridePendingTransition(R.anim.right_in, R.anim.left_out);
        }


    }











    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }





    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id){
            case R.id.action_favorite:
                Intent intent = new Intent(getApplicationContext(),FavoriteActivity.class);
                MainActivity.this.startActivity(intent);
                return true;

            case R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
        //noinspection SimplifiableIfStatement



    }
}
