package com.example.shindemandar82.list_fragment;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements MyDialogFragment.EditDialogListener {

    String result = "";
    TextView textReturned;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textReturned = (TextView)findViewById(R.id.textreturned);

        Button buttonStartDialog = (Button)findViewById(R.id.startdialog);
        buttonStartDialog.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                //showDialog(CUSTOM_DIALOG_ID);

                DialogFragment newFragment = MyDialogFragment.newInstance();
                newFragment.show(getFragmentManager(), null);

            }});
    }

    public void updateResult(String inputText) {
        result = inputText;
        textReturned.setText(result);

    }

}