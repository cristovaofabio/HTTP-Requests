package com.tecnoapp.requesthttp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private Button buttonRecover;
    private TextView textResult;
    private String urlApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonRecover = findViewById(R.id.buttonRecover);
        textResult = findViewById(R.id.textResult);

        buttonRecover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyTask task = new MyTask();
                urlApi = "https://www.abibliadigital.com.br/api/books/mt";
                task.execute(urlApi);
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

            textResult.setText(result);
        }
    }
}
