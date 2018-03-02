package com.example.akoumare.testandroidnatif;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends Activity  {

    EditText titre ;
    Button search;
    Films film = new Films();
    OmdbService service;
    ImageView imagedufilm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        imagedufilm=(ImageView)findViewById(R.id.monImage);
        //liste


        // logger mieux que des toast
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
// set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
// add your other interceptors …

// add logging as last interceptor
        httpClient.addInterceptor(logging);  // <-- this is the important line!

        //Appels du service
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.omdbapi.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
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


    }
    //method startSearch
    public void startSearch(){
        if(!titre.getText().toString().isEmpty()&& titre.getText().toString()!=null){
            Call<ListeFilms> call = service.searchFilms("2613acdd",titre.getText().toString());
            call.enqueue(new Callback<ListeFilms>() {
                @Override
                public void onResponse(Call<ListeFilms> call, Response<ListeFilms> response) {
                    ListView list = (ListView)findViewById(R.id.maliste);
                    ArrayAdapter<String> tableau = new ArrayAdapter<String>(list.getContext(), R.layout.listage, R.id.monTexte);
                    if(response.isSuccessful() && response.body()!= null) {
                       List<Films> results= response.body().getSearch();
                        for(int i = 0; i < results.size();i++){
                              tableau.add(results.get(i).getTitle().toString());
                        }
                      //  tableau.add(response.body().getTitle().toString() );
                       // Picasso.with(getBaseContext()).load(response.body().getPoster().toString()+"").into(imagedufilm);
                        list.setAdapter(tableau);
                        Toast.makeText(MainActivity.this,"Connection successful",Toast.LENGTH_SHORT).show();
                      //  Toast.makeText(MainActivity.this,response.body().getPoster().toString(),Toast.LENGTH_SHORT).show();
                    }else {
                        // Erreur serveur
                    }

                }

                @Override
                public void onFailure(Call<ListeFilms> call, Throwable t) {
                    Toast.makeText(MainActivity.this,"error Connection",Toast.LENGTH_SHORT).show();
                }
            });



        }
        else{
            Toast.makeText(this,"renseignez le titre d'un film",Toast.LENGTH_SHORT).show();
        }
    }
}
