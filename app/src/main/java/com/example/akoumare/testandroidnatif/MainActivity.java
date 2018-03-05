package com.example.akoumare.testandroidnatif;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
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
    CustumerAdapter tableau;
    private ProgressBar mProgressBar;
    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        imagedufilm=(ImageView)findViewById(R.id.monImage);
        tableau =new  CustumerAdapter(this);
         list = (ListView)findViewById(R.id.maliste);
        list.setAdapter(tableau);



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
        mProgressBar = (ProgressBar) findViewById(R.id.proBar);

        // lancer la recherche en tapant sur entrée du clavier
        titre.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    Recherche newSearch =new Recherche();
                    newSearch.execute();
                    handled = true;
                }
                return handled;
            }
        });

        // lancer la recherche depuis le button
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Recherche newSearch =new Recherche();
                newSearch.execute();
            }
        });


    }
    //method startSearch

    private class Recherche extends AsyncTask<Void,Integer,Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            int progress = 0;
            if(!titre.getText().toString().isEmpty()&& titre.getText().toString()!=null){
                Call<ListeFilms> call = service.searchFilms("2613acdd",titre.getText().toString());
                call.enqueue(new Callback<ListeFilms>() {
                    @Override
                    public void onResponse(Call<ListeFilms> call, Response<ListeFilms> response) {
                        if(response.isSuccessful() && response.body().getSearch()!= null) {
                            final List<Films> results= response.body().getSearch();
                            tableau.clear();
                            // tableau.addAll(results);
                            int progress ;
                            mProgressBar.setMax(results.size() - 2);
                            for(progress =0; progress < results.size(); progress++){
                                tableau.add(results.get(progress));
                                publishProgress(progress);
                                progress++;
                            }
                            tableau.notifyDataSetChanged();
                            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    Intent intent = new Intent(MainActivity.this, Details.class);
                                    intent.putExtra("titre",results.get(position).getTitle());
                                    intent.putExtra("annee",results.get(position).getYear());
                                    intent.putExtra("genre",results.get(position).getGenre());
                                    intent.putExtra("acteurs",results.get(position).getActors());
                                    intent.putExtra("lien",results.get(position).getPoster());
                                    startActivity(intent);
                                }
                            });

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

            return null;
        }
        @Override
        protected void onProgressUpdate(Integer... values){
            super.onProgressUpdate(values);
            // Mise à jour de la ProgressBar
            mProgressBar.setProgress(values[0]);
        }
    }

}
