package ua.AwesomeDevelop.perfume.dbhelper;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.preference.PreferenceManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Taras on 24.12.2014.
 */
public class ExternalDbOpenHelper extends SQLiteOpenHelper
    {
        private static final String DATABASE_NAME = "perfume.sqlite3";
        private static final int DATABASE_VERSION = 3;
        private static final String SP_KEY_DB_VER = "db_ver";
        private final Context mContext;

        public ExternalDbOpenHelper(Context context, String DB_NAME) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            mContext = context;
            initialize();
        }

        /**
         * Initializes database. Creates database if doesn't exist.
         */
        private void initialize() {
            if (databaseExists()) {
                SharedPreferences prefs = PreferenceManager
                        .getDefaultSharedPreferences(mContext);
                int dbVersion = prefs.getInt(SP_KEY_DB_VER, 1);
                if (DATABASE_VERSION != dbVersion) {
                    File dbFile = mContext.getDatabasePath(DATABASE_NAME);
                    if (!dbFile.delete()) {
                       // Log.w(TAG, "Unable to update database");
                    }
                }
            }
            if (!databaseExists()) {
                createDatabase();
            }
        }

        /**
         * Returns true if database file exists, false otherwise.
         * @return
         */
        private boolean databaseExists() {
            File dbFile = mContext.getDatabasePath(DATABASE_NAME);
            return dbFile.exists();
        }

        /**
         * Creates database by copying it from assets directory.
         */
        private void createDatabase() {
            String parentPath = mContext.getDatabasePath(DATABASE_NAME).getParent();
            String path = mContext.getDatabasePath(DATABASE_NAME).getPath();

            File file = new File(parentPath);
            if (!file.exists()) {
                if (!file.mkdir()) {
                  //  Log.w(TAG, "Unable to create database directory");
                    return;
                }
            }

            InputStream is = null;
            OutputStream os = null;
            try {
                is = mContext.getAssets().open(DATABASE_NAME);
                os = new FileOutputStream(path);

                byte[] buffer = new byte[1024];
                int length;
                while ((length = is.read(buffer)) > 0) {
                    os.write(buffer, 0, length);
                }
                os.flush();
                SharedPreferences prefs = PreferenceManager
                        .getDefaultSharedPreferences(mContext);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putInt(SP_KEY_DB_VER, DATABASE_VERSION);
                editor.commit();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (is != null) {
                    try {
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (os != null) {
                    try {
                        os.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion,
                              int newVersion) {
        }
    }