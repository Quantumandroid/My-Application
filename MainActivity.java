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
