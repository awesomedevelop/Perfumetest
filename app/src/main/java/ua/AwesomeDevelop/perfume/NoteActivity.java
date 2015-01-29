package ua.AwesomeDevelop.perfume;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import ua.AwesomeDevelop.perfume.dbhelper.ExternalDbOpenHelper;


public class NoteActivity extends ActionBarActivity {
    private static final String DB_NAME = "perfume.sqlite3";
    private static final String TABLE_NAME = "notes";
    private static final String NOTE_NAME = "note";
    private SQLiteDatabase database;
    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    private static ArrayList<NoteData> notes;
    static View.OnClickListener myOnClickListener;
   // private static final String BRAND_IMAGE ="images";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        recyclerView = (RecyclerView) findViewById(R.id.note_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);


        getSupportActionBar().setTitle("Ноты");
        myOnClickListener = new MyOnClickListener(this);
        ExternalDbOpenHelper dbOpenHelper = new ExternalDbOpenHelper(this, DB_NAME);
        database = dbOpenHelper.getReadableDatabase();
        new InsertDataTask().execute();






    }




    private void fillNotes (){
        notes = new ArrayList<NoteData>();

        Cursor noteCursor = database.query(
                TABLE_NAME,
                new String[] { NOTE_NAME,"id"},
                null, null, null, null,
                NOTE_NAME);
       noteCursor.moveToFirst();


        if(!noteCursor.isAfterLast()){
            do {

                String name = noteCursor.getString(0);
                notes.add(new NoteData(name,false));

            } while (noteCursor.moveToNext());
        }
       noteCursor.close();
    } // Конец заполнения бд


    private void fillUpdate (String filter){
        notes = new ArrayList<NoteData>();

        Cursor noteCursor = database.query(
                TABLE_NAME,
                new String[] { NOTE_NAME,"id"},
                NOTE_NAME+ " LIKE "+"'"+filter+"%'",
                null, null, null,
                NOTE_NAME);
        noteCursor.moveToFirst();


        if(!noteCursor.isAfterLast()){
            do {

                String name = noteCursor.getString(0);
                notes.add(new NoteData(name,false));

            } while (noteCursor.moveToNext());
        }
        noteCursor.close();
    } // Конец заполнения бд


















    // Асинхронка для бд
    private class InsertDataTask extends AsyncTask<Integer,Void,Void> {
        private final ProgressDialog dialog = new ProgressDialog(
                NoteActivity.this);

        // can use UI thread here
        protected void onPreExecute() {
           // this.dialog.setMessage("Загрузка...");
           // this.dialog.show();
        }
        protected Void doInBackground(Integer...params) {
            fillNotes();


            //SystemClock.sleep(1500);
            return null;
        }
        protected void onPostExecute(final Void unused) {
            if (this.dialog.isShowing()) {
                this.dialog.dismiss();
                // setUpList();

            }
            adapter = new NoteAdapter(NoteActivity.this,notes);
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
                    = (TextView) viewHolder.itemView.findViewById(R.id.note_name);
            String selectedNote = (String) textViewName.getText();
           Toast.makeText(context, selectedNote, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(context, Perfume.class);
            intent.putExtra("note",selectedNote);
            intent.putExtra("type",2);
            context.startActivity(intent);

            overridePendingTransition(R.anim.right_in, R.anim.left_out);
        }


    }







    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_note, menu);
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        android.support.v7.widget.SearchView searchView =
                (android.support.v7.widget.SearchView) menu.findItem(R.id.action_search_note).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                fillUpdate(s);
                adapter = new NoteAdapter(NoteActivity.this,notes);
                recyclerView.setAdapter(adapter);
                return true;
            }
        });
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
