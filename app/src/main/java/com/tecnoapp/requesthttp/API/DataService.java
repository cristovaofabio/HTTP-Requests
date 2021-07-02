package com.tecnoapp.requesthttp.API;

import com.tecnoapp.requesthttp.Class.Photo;
import com.tecnoapp.requesthttp.Class.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

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

    //Replaces the object that I have in the server for a new object
    @PUT("/photos/{id}")
    Call<Photo> updatePhoto(@Path("id") int id, @Body Photo photo);

    //Update only fields that I want to update:
    @PATCH("/photos/{id}")
    Call<Photo> updatePhotoPatch(@Path("id") int id, @Body Photo photo);

    @DELETE("/photos/{id}")
    Call<Void> deletePhoto(@Path("id") int id);
}
