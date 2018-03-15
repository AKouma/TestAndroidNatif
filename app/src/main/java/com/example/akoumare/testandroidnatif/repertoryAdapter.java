package com.example.akoumare.testandroidnatif;

/**
 * Created by akoumare on 15/03/2018.
 */

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import io.realm.Realm;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import io.realm.Realm;


/**
 * Created by akoumare on 02/03/2018.
 */

public class repertoryAdapter extends RecyclerView.Adapter<repertoryAdapter.FilmViewHolder> {

    ListeFilms films;
    Context context;
    Films film;

    private static final int DIALOG_ALERT = 10;

    public class FilmViewHolder  extends RecyclerView.ViewHolder  {
        public TextView monTexte;
        public ImageView monImage;

        public FilmViewHolder(View itemView) {
            super(itemView);
            monTexte =(TextView) itemView.findViewById(R.id.monTexte);
            monImage=(ImageView) itemView.findViewById(R.id.monImage);

        }

    }


    public repertoryAdapter(ListeFilms monfilm) {
        films= monfilm;
    }
    // Create new views (invoked by the layout manager)
    @Override
    public repertoryAdapter.FilmViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        context =parent.getContext();
        View v = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.listage, parent, false);
        FilmViewHolder vh = new FilmViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final FilmViewHolder ancien, final int position) {

        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        film = films.getSearch().get(position);
        ancien.monTexte.setText(film.getTitle().toString());
        Picasso.with(context).load(film.getPoster().toString()).into(ancien.monImage);

        //OnClickListener methods with recyclerview
        ancien.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Context c = view.getContext();
                Intent intent = new Intent(c, Details.class);
                intent.putExtra("titre",film.getTitle());
                intent.putExtra("annee",film.getYear());
                intent.putExtra("genre",film.getGenre());
                intent.putExtra("acteurs",film.getActors());
                intent.putExtra("lien",film.getPoster());
                c.startActivity(intent);

            }
        });

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return films.getSearch()==null ? 0 : films.getSearch().size();
    }

}

