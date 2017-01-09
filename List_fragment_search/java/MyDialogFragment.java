package com.example.shindemandar82.list_fragment;

import android.app.DialogFragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MyDialogFragment extends DialogFragment {

    EditText editText;
    String[] mobileArray = {"Android", "IPhone", "WindowsMobile", "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X"};
    ListView listView;
    String selectedFromList;
     //ArrayAdapter <String> adapter;
    private HighlightArrayAdapter mHighlightArrayAdapter;


    static MyDialogFragment newInstance() {
        return new MyDialogFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View dialogView = inflater.inflate(R.layout.customlayout, container, false);

        editText = (EditText) dialogView.findViewById(R.id.dialogedittext);
        listView = (ListView) dialogView.findViewById(R.id.listView);


        /*adapter = new ArrayAdapter<String>(getActivity(),R.layout.activity_listview,R.id.label, mobileArray);

        listView.setAdapter(adapter);*/
        mHighlightArrayAdapter = new HighlightArrayAdapter(getActivity(), R.layout.list_item, R.id.product_name, mobileArray);
        listView.setAdapter(mHighlightArrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                String s = String.valueOf(position);

                selectedFromList = (String) (listView.getItemAtPosition(position));
                Toast.makeText(getActivity(), selectedFromList, Toast.LENGTH_SHORT).show();

                EditDialogListener activity = (EditDialogListener) getActivity();
                activity.updateResult(selectedFromList);

                dismiss();
            }
        });

        doSearch();

        return dialogView;
    }

    private void doSearch() {

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                mHighlightArrayAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    public interface EditDialogListener {
        void updateResult(String inputText);
    }


}