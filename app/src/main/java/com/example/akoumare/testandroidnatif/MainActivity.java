package com.example.akoumare.testandroidnatif;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.content.Intent;
import android.view.View;
import android.view.KeyEvent;

public class MainActivity extends ActionBarActivity  {

    EditText titre ;
    Button Search;
    Films film = new Films();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        //liste
        ListView list = (ListView)findViewById(R.id.maliste);
        ArrayAdapter<String> tableau = new ArrayAdapter<String>(list.getContext(), R.layout.listage, R.id.monTexte);

        //Appels du service
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.omdbapi.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(OmdbService.class);


        titre =(EditText)findViewById (R.id.Nomfilm);
        search =(Button)findViewById (R.id.Chercher);

        // lancer la recherche en tapant sur entrée du clavier
        titre.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    startSearch();
                    handled = true;
                }
                return handled;
            }
        });

        // lancer la recherche depuis le button
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSearch();
            }
        });

        //method startSearch
        public void startSearch(){
            if(!titre.getText().toString().isEmpty()) && titre.getText().toString()!=null){
            all<Repo> call = service.searchFilms();
            call.enqueue(new Callback<Films>() {
                @Override
                public void onResponse(Response<Films> responses){
                    // Get result Repo from response.body()
                    Films film = new Films();for (int i =0 ; i < responses.body().size()){}}
                @Override
                public void onFailure(Throwable t) {
                    Toast.makeText(this,"error Connection",Toast.LENGTH_SHORT).show();
                }
            });
        }
        else{
            Toast.makeText(this,"renseignez le titre d'un film",Toast.LENGTH_SHORT).show();
        }
    }
    }
}
