package com.tecnoapp.requesthttp.API;

import com.tecnoapp.requesthttp.Class.Photo;
import com.tecnoapp.requesthttp.Class.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface DataService {

    @GET("/photos")
    Call<List<Photo>> recoverPhotos();

    @GET("/posts")
    Call<List<Post>> recoverPosts();

    @POST("/photos")
    Call<Photo> savePhotos(@Body Photo photo);

    //If you want to use XML format:
    @FormUrlEncoded
    @POST("/photos")
    Call<Photo> savePhotos(
            @Field("albumId") String albumId,
            @Field("title") String title,
            @Field("url") String url,
            @Field("thumbnailUrl") String thumbnailUrl
    );
}
