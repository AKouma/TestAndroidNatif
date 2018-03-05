package com.example.akoumare.testandroidnatif;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by akoumare on 02/03/2018.
 */

public class CustumerAdapter extends ArrayAdapter<Films> {

    LayoutInflater inflater;

    public CustumerAdapter(Context context) {
        super(context, 0);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //si la vue n'a pas été créée
      if(convertView==null){
          convertView = LayoutInflater.from(getContext()).inflate(R.layout.listage,parent,false);
      }


      FilmViewHolder viewHolder =(FilmViewHolder) convertView.getTag();
      if(viewHolder==null){
          viewHolder = new FilmViewHolder();
          viewHolder.monTexte =(TextView) convertView.findViewById(R.id.monTexte);
          viewHolder.monImage =(ImageView) convertView.findViewById(R.id.monImage);
          convertView.setTag(viewHolder);
      }

        Films film = getItem(position);
        viewHolder.monTexte.setText(film.getTitle().toString());
        Picasso.with(getContext()).load(film.getPoster().toString()).into(viewHolder.monImage);

        return convertView;
    }
}
