package ua.AwesomeDevelop.perfume;

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

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;

import ua.AwesomeDevelop.perfume.dbhelper.ExternalDbOpenHelper;


public class Perfume extends ActionBarActivity {
    static View.OnClickListener myOnClickListener;
    private static final String DB_NAME = "perfume.sqlite3";
    private static final String TABLE_NAME = "perfume";
    private static final String PERFUME_NAME = "name";
    private static final String BRAND_IMAGE ="image";
    private static final String PERFUME_NOTES = "notes";
    private static final String PERFUME_FAVORITE = "favorite";
    private static final String PERFUME_DESCRIPTION = "description";
    private static final String BRAND_NAME = "brand";
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

        AdView adView = (AdView)this.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice("162FA5DA79ABF08B56162454516850DC")
                .build();
        adView.loadAd(adRequest);






        myOnClickListener = new MyOnClickListener(this);
        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        Intent intent =  getIntent();

        int case_for_condition = intent.getIntExtra("type",0);

        if(case_for_condition == 1) {

            condition = "brand = " + "'" + intent.getStringExtra("brand").trim() + "'";
            setTitle(intent.getStringExtra("brand").trim());
        }
        if (case_for_condition == 2){

            condition = "notes LIKE" + "'%" + intent.getStringExtra("note").trim() + "%'";
        }



       //
        ExternalDbOpenHelper dbOpenHelper = new ExternalDbOpenHelper(this, DB_NAME);
        database = dbOpenHelper.getReadableDatabase();


        recyclerView.setItemAnimator(new DefaultItemAnimator());

        new InsertDataTask().execute();

    }

    private void fillPerfumes (){
        perfumes = new ArrayList<PerfumeData>();
        Log.i("[TEST]","1");
        Cursor perfumeCursor = database.query(
                TABLE_NAME,
                new String[] { PERFUME_NAME,BRAND_IMAGE,PERFUME_NOTES,PERFUME_DESCRIPTION,PERFUME_FAVORITE,BRAND_NAME},
                condition, null, null, null,
                PERFUME_NAME);
        Log.i("[CONDITION]",condition);
        perfumeCursor.moveToFirst();
        Log.i("Cursor", perfumeCursor.toString());


        if(!perfumeCursor.isAfterLast()){
            do {
                String name = perfumeCursor.getString(0);
                String img = "http://perfumeapp.orgfree.com/"+perfumeCursor.getString(1).trim();
                String notes = perfumeCursor.getString(2);
                String description = perfumeCursor.getString(3);
                int favorite= perfumeCursor.getInt(4);
                String brand = perfumeCursor.getString(5);

                perfumes.add(new PerfumeData(name, img, notes,description,favorite,brand));

            } while (perfumeCursor.moveToNext());
        }
        perfumeCursor.close();
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





          //  int selectedItemPosition = recyclerView.getChildPosition(v);
//            RecyclerView.ViewHolder viewHolder
//                    = recyclerView.findViewHolderForPosition(selectedItemPosition);
//            TextView textViewName
//                    = (TextView) viewHolder.itemView.findViewById(R.id.tittle_text);
//            String selectedName = (String) textViewName.getText();
//            Toast.makeText(context, selectedName, Toast.LENGTH_SHORT).show();


        }


    }






























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

       switch (id){

           case R.id.action_settings:
               return true;
           default: return super.onOptionsItemSelected(item);



       }


    }
}
