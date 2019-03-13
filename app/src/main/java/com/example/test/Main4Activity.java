package com.example.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.content.Context;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;

public class Main4Activity extends AppCompatActivity {
    private String[] Taiwan = new String[] {"台北","新北","桃園","新竹","苗栗","台中","彰化","雲林","嘉義","台南","高雄","屏東","台東","花蓮","宜蘭"};
    private String[] zone = new String[]{"台北地區","新北地區"};
    private String[][] Taiwan_zone = new String[][]{{"台北地區","新北地區"},{"台北","新北","桃園","新竹","苗栗","台中","彰化","雲林","嘉義","台南","高雄","屏東","台東","花蓮","宜蘭"}};
    private Spinner spinner1;
    private Spinner spinner2;
    private Context context;
    ArrayAdapter<String> adapter;
    ArrayAdapter<String> adapter2;

    private OnItemSelectedListener selectListner = new OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            int pos = spinner1.getSelectedItemPosition();
            adapter2 = new ArrayAdapter<String>(context,android.R.layout.simple_spinner_item,Taiwan_zone[pos]);
            spinner2.setAdapter(adapter2);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        context = this;

        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, Taiwan);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1 = (Spinner) findViewById(R.id.spinner1);
        spinner1.setAdapter(adapter);
        spinner1.setOnItemSelectedListener(selectListner);

        adapter2 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, zone);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2 = (Spinner) findViewById(R.id.spinner2);
        spinner2.setAdapter(adapter2);


    }
}
