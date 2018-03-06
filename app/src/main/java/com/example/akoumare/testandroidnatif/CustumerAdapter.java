package com.example.akoumare.testandroidnatif;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;



/**
 * Created by akoumare on 02/03/2018.
 */

public class CustumerAdapter extends RecyclerView.Adapter<CustumerAdapter.FilmViewHolder> {

    LayoutInflater inflater;
    ListeFilms films;
    Context context;

    public class FilmViewHolder  extends RecyclerView.ViewHolder {
        public TextView monTexte;
        public ImageView monImage;

        public FilmViewHolder(View itemView) {
            super(itemView);
            monTexte =(TextView) itemView.findViewById(R.id.monTexte);
            monImage=(ImageView) itemView.findViewById(R.id.monImage);
        }
    }



    public CustumerAdapter(ListeFilms monfilm) {
        films= monfilm;
    }
    // Create new views (invoked by the layout manager)
    @Override
    public CustumerAdapter.FilmViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        context =parent.getContext();
        View v = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.listage, parent, false);
        FilmViewHolder vh = new FilmViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(FilmViewHolder ancien, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        Films film = films.getSearch().get(position);
       ancien.monTexte.setText(film.getTitle().toString());
        Picasso.with(context).load(film.getPoster().toString()).into(ancien.monImage);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return films.getSearch()==null ? 0 : films.getSearch().size();
    }

}
