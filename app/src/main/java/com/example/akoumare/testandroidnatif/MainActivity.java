package com.example.akoumare.testandroidnatif;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
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
    ListeFilms films = new ListeFilms();
    OmdbService service;
    ImageView imagedufilm;
    private RecyclerView.Adapter tableau;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    ProgressBar progres ;
    RecyclerClickListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tableau=new CustumerAdapter(films,listener);
        setContentView(R.layout.main);
        imagedufilm=(ImageView)findViewById(R.id.monImage);

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
                        films.setSearch(results);
                        tableau.notifyDataSetChanged();
                        progres.setVisibility(View.GONE);


 /*
                                Intent intent = new Intent(MainActivity.this, Details.class);
                                intent.putExtra("titre",results.get(position).getTitle());
                                intent.putExtra("annee",results.get(position).getYear());
                                intent.putExtra("genre",results.get(position).getGenre());
                                intent.putExtra("acteurs",results.get(position).getActors());
                                intent.putExtra("lien",results.get(position).getPoster());
                                startActivity(intent);*/


                    }else {
                        // Erreur serveur
                        Toast.makeText(MainActivity.this,"Film  non trouvé",Toast.LENGTH_SHORT).show();
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
