package com.example.akoumare.testandroidnatif;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
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
    Button  loadFilms;
    ListeFilms films = new ListeFilms();
    OmdbService service;
    private RecyclerView.Adapter tableau;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    ProgressBar progres ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        tableau=new CustumerAdapter(films);
        setContentView(R.layout.main);

        progres = (ProgressBar)findViewById(R.id.progressBar_cyclic);
        progres.setVisibility(View.INVISIBLE);

        recyclerView = (RecyclerView)findViewById(R.id.my_recycler_view);
        //au cas ou on ne changera pas la taille du contenu du recycler
        recyclerView.setHasFixedSize(true);

        //utiliser un manager lineaire
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(tableau);



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
        loadFilms=(Button)findViewById(R.id.loadFilms);

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
                progres.setVisibility(View.VISIBLE);
                startSearch();


            }
        });

        //load films from database
        loadFilms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent repertory = new Intent(getApplicationContext(), activityRepertory.class);
                getApplicationContext().startActivity(repertory);

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
                    if(response.isSuccessful()) {
                        final List<Films> results = response.body() == null || response.body().getSearch() == null? new ArrayList<Films>() : response.body().getSearch();
                        if(response.body().getSearch() == null){
                            Toast.makeText(MainActivity.this,"Film  non trouvé",Toast.LENGTH_SHORT).show();
                        }else {
                            films.setSearch(results);
                            tableau.notifyDataSetChanged();
                        }
                        progres.setVisibility(View.GONE);
                    }

                }

                @Override
                public void onFailure(Call<ListeFilms> call, Throwable t) {
                    Toast.makeText(MainActivity.this,"error Connection",Toast.LENGTH_SHORT).show();
                }
            });



        }
        else{
            Toast.makeText(MainActivity.this,"renseignez le titre d'un film",Toast.LENGTH_SHORT).show();
        }

    }

}
