package com.tecnoapp.requesthttp.API;

import com.tecnoapp.requesthttp.Class.Photo;
import com.tecnoapp.requesthttp.Class.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface DataService {

    @GET("/photos")
    Call<List<Photo>> recoverPhotos();

    @GET("/posts")
    Call<List<Post>> recoverPosts();
}
