search_bar.xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:gravity="center_vertical"
    android:orientation="horizontal">

    <ImageView
        android:id="@+id/back"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:src="@drawable/back"/>

    <EditText
        android:id="@+id/edtSearch"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_weight="1"
        android:hint="Search here"
        android:imeOptions="actionSearch"
        android:inputType="text"
        android:textColor="#2a2a2a"
        android:textColorHint="#282626"/>

</LinearLayout>

MainActivity.java

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private MenuItem mSearchAction;
    private MenuItem mSettingAction;
    private boolean isSearchOpened = false;
    private EditText Search;
    ImageView BACK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        mSearchAction = menu.findItem(R.id.action_search);
        mSettingAction = menu.findItem(R.id.action_settings);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_settings:
                return true;
            case R.id.action_search:
                handleMenuSearch();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (isSearchOpened) {
            handleMenuSearch();
            return;
        } else {
            super.onBackPressed();
        }
    }

    protected void handleMenuSearch() {
        //get the actionbar
        ActionBar action = getSupportActionBar();

        if (isSearchOpened){
            //hides the keyboard
            hideKeyboard();

            action.setBackgroundDrawable(new ColorDrawable(Color.rgb(48, 63, 159)));
            action.setDisplayShowCustomEnabled(false); //disable a custom view inside the actionbar
            action.setDisplayShowTitleEnabled(true); //show the title in the action bar

            // show menus
            showMenus(true);

            isSearchOpened = false;
        }else {
            action.setBackgroundDrawable(new ColorDrawable(Color.rgb(231, 231, 231)));
            action.setDisplayShowCustomEnabled(true); //enable it to display a
            // custom view in the action bar.
            action.setCustomView(R.layout.search_bar);//add the custom view
            action.setDisplayShowTitleEnabled(false); //hide the title

            Search = (EditText) action.getCustomView().findViewById(R.id.edtSearch); //the text editor
            BACK = (ImageView) action.getCustomView().findViewById(R.id.back); //the text editor
            BACK.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    // TODO Auto-generated method stub
                    handleMenuSearch();
                }
            });

            Search.addTextChangedListener(new TextWatcher() {

                @Override
                public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

                    Toast.makeText(MainActivity.this, arg0, Toast.LENGTH_SHORT).show();

                }

                @Override
                public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                              int arg3) {
                }

                @Override
                public void afterTextChanged(Editable arg0) {

                }
            });

            //this is a listener to do a search when the user clicks on search button
            Search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                        hideKeyboard();
                        return true;
                    }
                    return false;

                }
            });

            showKeyboard();
            // hide all menus
            showMenus(false);

            isSearchOpened = true;
        }

    }


    private void showMenus(boolean b) {

        mSearchAction.setVisible(b);
        mSettingAction.setVisible(b);

    }


    private void hideKeyboard() {

        //hides the keyboard
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(Search.getWindowToken(), 0);
    }

    private void showKeyboard() {

        Search.requestFocus();
        //open the keyboard focused in the edtSearch
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(Search, InputMethodManager.SHOW_IMPLICIT);

    }


}


menu.main.xml
<?xml version="1.0" encoding="utf-8"?>
<menu
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    tools:context=".MainActivity">

    <item
        android:id="@+id/action_settings"
        android:orderInCategory="100"
        android:title="Settings"
        app:showAsAction="never" />

    <item
        android:id="@+id/action_search"
        android:title="Search"
        android:orderInCategory="200"
        android:icon="@drawable/ic_search_black_24dp"
        app:showAsAction="ifRoom"
        />
</menu>



/////////////
// DBHelper.java
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "MyDatabase.db";

    // Table name
    private static final String TABLE_NAME1 = "tb1";

    // Table Columns names
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_CITY = "place";
    public static final String COLUMN_PHONE = "phone";

    //Create Table's
    public final String CREATE_TABLE1 = "create table " + TABLE_NAME1 +
            "(id integer primary key, name text,phone text,place text)";

    // Default constructor
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // Creating Tables
        sqLiteDatabase.execSQL(CREATE_TABLE1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // Drop older table's if existed
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME1);

        // Create tables again
        onCreate(sqLiteDatabase);

    }

    // Select Query (TODO select specific row with all columns)
    public Cursor select(String TABLE_NAME, String COLUMN_NAME, String COLUMN_VALUE) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME + " where " + COLUMN_NAME + "=" + COLUMN_VALUE, null);
        return res;
    }


    // Select Query (TODO select specific row with specific column)
    public String select(String TABLE_NAME, String SELECT_COLUMN_NAME, String WHERE_COLUMN_NAME, String WHERE_COLUMN_VALUE) {
        String str = "";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select " + SELECT_COLUMN_NAME + " from " + TABLE_NAME + " where " + WHERE_COLUMN_NAME + "=" + WHERE_COLUMN_VALUE, null);
        str = res.getString(res.getColumnIndex(SELECT_COLUMN_NAME));


        res.close();
        db.close();
        return str;
    }


    // Select Query (TODO select single column with all rows)
    public ArrayList<String> select(String TABLE_NAME, String SELECT_COLUMN_NAME) {

        ArrayList<String> array_list = new ArrayList<String>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from" + TABLE_NAME, null);
        res.moveToFirst();

        while (res.isAfterLast() == false) {
            array_list.add(res.getString(res.getColumnIndex(SELECT_COLUMN_NAME)));
            res.moveToNext();
        }


        res.close();
        db.close();
        return array_list;
    }


    // Select Query (TODO select (*) all columns with all rows  )
    public Cursor select(String TABLE_NAME) {

        SQLiteDatabase db = this.getReadableDatabase();

        return db.rawQuery("select * from" + TABLE_NAME, null);
    }


    //Insert Query
    public boolean insert(String TABLE_NAME, ContentValues contentValues) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_NAME, null, contentValues);

        db.close();
        return true;
    }


    // Update Query
    public boolean update(String TABLE_NAME, ContentValues contentValues, String COLUMN_NAME, String COLUMN_VALUE) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(TABLE_NAME, contentValues, COLUMN_NAME + " = ? ", new String[]{COLUMN_VALUE});

        db.close();

        return true;
    }

    //Delete Query
    public Integer delete(String TABLE_NAME, String COLUMN_NAME, String COLUMN_VALUE) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, COLUMN_NAME + " = ? ", new String[]{COLUMN_VALUE});
    }

    // check if a record exists so it won't insert the next time you run this code
    public boolean ifExists(String TABLE_NAME, String CHECK_COLUMN_NAME, String CHECK_COLUMN_VALUE) {

        boolean recordExists = false;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + CHECK_COLUMN_NAME + " FROM " + TABLE_NAME + " WHERE " + CHECK_COLUMN_NAME + " = '" + CHECK_COLUMN_VALUE + "'", null);

        if (cursor != null) {

            if (cursor.getCount() > 0) {
                recordExists = true;
            }
        }


        if (cursor != null) {
            cursor.close();
        }
        db.close();

        return recordExists;
    }


}


/*
TODO    Save data in ContentValues
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, "name");
        contentValues.put(COLUMN_PHONE, phone);
*/

/*
TODO    Get data from Cursor
        //SQLiteDatabase db = this.getReadableDatabase();
        //Cursor cursor = db.rawQuery("select * from" + TABLE_NAME, null);

        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            String str = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
            int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
            cursor.moveToNext();
        }
*/


/*   String sql = "";

        sql += "CREATE TABLE " + tableName;
        sql += " ( ";
        sql += fieldObjectId + " INTEGER PRIMARY KEY AUTOINCREMENT, ";
        sql += fieldObjectName + " TEXT ";
        sql += " ) ";

        db.execSQL(sql);*/



