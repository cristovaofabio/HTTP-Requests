package com.tecnoapp.requesthttp.API;

import com.tecnoapp.requesthttp.Class.Verse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface BibleService {

    @GET("{version}/{abbrev}/{chapter}/{number}")
    Call<Verse> recoverVerse(@Path("version") String version,
                             @Path("abbrev") String abbrev,
                             @Path("chapter") int chapter,
                             @Path("number") int number);

}
