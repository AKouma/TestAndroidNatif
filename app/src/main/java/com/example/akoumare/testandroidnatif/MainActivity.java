package com.example.akoumare.testandroidnatif;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        ListView list = (ListView)findViewById(R.id.maliste);
        ArrayAdapter<String> tableau = new ArrayAdapter<String>(list.getContext(), R.layout.ligne, R.id.monTexte);
        for (int i=0; i<10; i++) {
            tableau.add("film " + i);
        }
        list.setAdapter(tableau);
    }
}
