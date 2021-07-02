package com.tecnoapp.requesthttp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.tecnoapp.requesthttp.API.BibleService;
import com.tecnoapp.requesthttp.API.DataService;
import com.tecnoapp.requesthttp.Class.Book;
import com.tecnoapp.requesthttp.Class.Photo;
import com.tecnoapp.requesthttp.Class.Verse;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private Button buttonRecover;
    private TextView textResult;
    private String urlApi;
    private Retrofit retrofit;
    private List<Photo> listPhotos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonRecover = findViewById(R.id.buttonRecover);
        textResult = findViewById(R.id.textResult);
        retrofit = new Retrofit.Builder()
                //.baseUrl("https://www.abibliadigital.com.br/api/verses/")
                .baseUrl("https://jsonplaceholder.typicode.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        buttonRecover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //recoverRequest();
                //recoverListRequest();
                //savePhoto();
                //updatePhoto();
                deletePhoto();

                /*
                MyTask task = new MyTask();
                urlApi = "https://www.abibliadigital.com.br/api/verses/nvi/pv/1/5";
                task.execute(urlApi); */
            }
        });
    }

    private void deletePhoto(){
        DataService dataService = retrofit.create(DataService.class);
        Call<Void> call = dataService.deletePhoto(2);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()){
                    textResult.setText("STATUS: "+response.code());
                }

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }


    private void updatePhoto(){
        DataService dataService = retrofit.create(DataService.class);
        Photo photo = new Photo(
                "1",
                null,
                "socialmedia.com",
                "blabla");

        //Call<Photo> call = dataService.updatePhoto(2,photo);
        Call<Photo> call = dataService.updatePhotoPatch(2,photo);

        call.enqueue(new Callback<Photo>() {
            @Override
            public void onResponse(Call<Photo> call, Response<Photo> response) {
                if (response.isSuccessful()){
                    Photo photoResponse = response.body();
                    //Check if dates are update in SERVER
                    textResult.setText(
                            "CODE: "+response.code()+"\n"+
                            "ID: "+photoResponse.getId()+"\n"+
                            "TITLE: "+photoResponse.getTitle()+"\n"+
                            "URL: "+photoResponse.getUrl());
                }
            }

            @Override
            public void onFailure(Call<Photo> call, Throwable t) {

            }
        });
    }


    private void savePhoto(){
        Photo photo = new Photo(
                "1",
                "My parents",
                "socialmedia.com",
                "blabla");

        DataService dataService = retrofit.create(DataService.class);
        Call<Photo> call = dataService.savePhotos(photo);
        //for XML file:
        //Call<Photo> call = dataService.savePhotos("1","My parents","socialmedia.com","blabla");

        call.enqueue(new Callback<Photo>() {
            @Override
            public void onResponse(Call<Photo> call, Response<Photo> response) {

                if (response.isSuccessful()){
                    Photo photoResponse = response.body();
                    //Check dates save in SERVER
                    textResult.setText(
                            "CODE: "+response.code()+"\n"+
                            "ID: "+photoResponse.getId()+"\n"+
                            "TITLE: "+photoResponse.getTitle());
                }

            }

            @Override
            public void onFailure(Call<Photo> call, Throwable t) {

            }
        });


    }


    private void recoverListRequest(){
        DataService dataService = retrofit.create(DataService.class);
        Call<List<Photo>> listCall = dataService.recoverPhotos();

        listCall.enqueue(new Callback<List<Photo>>() {
            @Override
            public void onResponse(Call<List<Photo>> call, Response<List<Photo>> response) {
                if (response.isSuccessful()){
                    listPhotos = response.body();

                    for (int i=0;i<listPhotos.size();i++){
                        Photo photo = listPhotos.get(i);
                        /*
                        Show the photos here!
                         */
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Photo>> call, Throwable t) {

            }
        });
    }


    private void recoverRequest(){
        BibleService bibleService = retrofit.create(BibleService.class);
        Call<Verse> call = bibleService.recoverVerse("nvi","pv",1,5);

        call.enqueue(new Callback<Verse>() {
            @Override
            public void onResponse(Call<Verse> call, Response<Verse> response) {
                if (response.isSuccessful()){
                    Verse verse = response.body();
                    Book book = verse.getBook();
                    textResult.setText(verse.getText()+"\n"
                            +book.getName()+" "
                            +verse.getChapter()+":"
                            +verse.getNumber());
                }
            }

            @Override
            public void onFailure(Call<Verse> call, Throwable t) {

            }
        });
    }


    class MyTask extends AsyncTask<String,Void,String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            String stringUrl = strings[0];
            InputStream inputStream=null;
            InputStreamReader inputStreamReader =null;
            BufferedReader reader =null;
            StringBuffer buffer = null;

            try {
                URL url = new URL(stringUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                inputStream = connection.getInputStream(); //Recover data in Bytes
                inputStreamReader = new InputStreamReader(inputStream); //Decode Bytes to characters
                reader = new BufferedReader(inputStreamReader); //To read characters
                buffer = new StringBuffer();

                String line="";

                while ((line = reader.readLine())!=null){
                    buffer.append(line);
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return buffer.toString();
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            String name = null;
            String verse = null;
            String book = null;

            try {
                //Recover verse:
                JSONObject jsonObject = new JSONObject(result);
                verse = jsonObject.getString("text");
                //Recover book name:
                book = jsonObject.getString("book"); //Get new JSON
                JSONObject jsonObject2 = new JSONObject(book);
                name = jsonObject2.getString("name");

            } catch (JSONException e) {
                e.printStackTrace();
            }

            textResult.setText("Verse: "+verse+"\nBook name: "+name);
        }
    }
}
