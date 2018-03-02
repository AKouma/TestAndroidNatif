package com.example.akoumare.testandroidnatif;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by akoumare on 02/03/2018.
 */

public class ListeFilms {
    @SerializedName("Search")
    private List<Films> search;

    public List<Films> getSearch() {
        return search;
    }

    public void setSearch(List<Films> search) {
        this.search = search;
    }
}
