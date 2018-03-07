package com.example.akoumare.testandroidnatif;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class Details extends Activity {

    ImageView image;
    ListView list;
    Button retour;
    Intent intent;
    String titre =null;
    String annee =null;
    String acteurs =null;
    String genre =null;
    String lien=null;
    ArrayAdapter tabl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details);

        image = (ImageView) findViewById(R.id.imageFill);
        list = (ListView) findViewById(R.id.detailFilm);
        retour =(Button)findViewById(R.id.retour);

        tabl =new ArrayAdapter<String>(Details.this, R.layout.info,R.id.infoFilms);
        list.setAdapter(tabl);

        retour.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               finish();
            }
        });

        intent = getIntent();
        titre = intent.getStringExtra("titre");
        annee = intent.getStringExtra("annee");
        acteurs = intent.getStringExtra("acteurs");
        genre = intent.getStringExtra("genre");
        lien= intent.getStringExtra("lien");

        Toast.makeText(this,lien,Toast.LENGTH_SHORT).show();

        Picasso.with(getBaseContext()).load(lien).into(image);

        if(titre  !=null){
            tabl.add("Titre :"+titre);
            tabl.notifyDataSetChanged();
        }


        if(annee  !=null){
            tabl.add("Année de sortie :"+annee);
            tabl.notifyDataSetChanged();
        }

        if(acteurs  !=null){
            tabl.add("Acteurs :"+acteurs);
            tabl.notifyDataSetChanged();
        }

        if(genre  !=null){
            tabl.add("Genre :"+genre);
            tabl.notifyDataSetChanged();
        }
        list.setAdapter(tabl);


    }
}
