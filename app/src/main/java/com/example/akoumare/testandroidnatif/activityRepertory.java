package com.example.akoumare.testandroidnatif;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
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

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class activityRepertory extends Activity {


    ListeFilms films = new ListeFilms();
    private RecyclerView.Adapter tab;
    private RecyclerView recyView;
    private RecyclerView.LayoutManager layManager;
    Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.repertory);

        back =(Button)findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Realm realm = Realm.getDefaultInstance();
        RealmQuery<Films> query =realm.where(Films.class);
        RealmResults<Films> res =query.findAll();
        films.setSearch(res);
       // realmToFIlm(res,films);
      //  realm.commitTransaction();

        tab=new repertoryAdapter(films);
        tab.notifyDataSetChanged();
        recyView = (RecyclerView)findViewById(R.id.my_recycler_view_secondaire);
        //au cas ou on ne changera pas la taille du contenu du recycler
        recyView.setHasFixedSize(true);

        //utiliser un manager lineaire
        layManager = new LinearLayoutManager(this);
        recyView.setLayoutManager(layManager);

        recyView.setAdapter(tab);

    }

    private void realmToFIlm(RealmResults<Films> res, ListeFilms films) {
        ArrayList<Films> lesFilms =new ArrayList<>();
        for(Films obj : res){
            Films fil = new Films();
            fil.setActors(obj.getActors());
            fil.setGenre(obj.getGenre());
            fil.setPoster(obj.getPoster());
            fil.setTitle(obj.getTitle());
            lesFilms.add(fil);
        }
        films.setSearch(lesFilms);

    }
}
