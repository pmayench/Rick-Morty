package com.dam2.m08.rickmortyapp;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class CharacterDetail extends AppCompatActivity
{
    //region Variables
    public static String stringCurrent;
    public static Character model = new Character();
    private static String JSON_URL = "https://rickandmortyapi.com/api/character";
    ImageView image;
    TextView name, id, gender, status, type, specie;
    //endregion

    //region OnCreate
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.character_detail);
        getData();
        actualitzaView();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    //endregion

    //region Mètodes propis
    public void actualitzaView()
    {
        Character character = MainActivity.personatge;

        image = findViewById(R.id.imageFilm);
        name = findViewById(R.id.name);
        id = findViewById(R.id.id);
        gender = findViewById(R.id.txtGender);
        status = findViewById(R.id.status);
        type = findViewById(R.id.txtType);
        specie = findViewById(R.id.txtSpecie);

        Glide.with(RecyclerAdapter.mContext).load(character.getImage()).into(image);
        name.setText(character.getName() + " ");
        id.setText(character.getId() + " ");
        gender.setText(character.getGender() + " ");
        status.setText(character.getStatus() + " ");
        type.setText(character.getType() + " ");
        specie.setText(character.getSpecie() + " ");
    }

    public String getData()
    {
        String current = "";
        try
        {
            URL url;
            HttpURLConnection urlConnection = null;
            try
            {
                url = new URL(JSON_URL);
                urlConnection = (HttpURLConnection) url.openConnection();

                InputStream is = urlConnection.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);

                int data = isr.read();
                while(data != -1)
                {
                    current += (char) data;
                    data = isr.read();
                }
                stringCurrent = current;
                return current;
            }
            catch (MalformedURLException e) {e.printStackTrace();}
            catch (IOException e) {e.printStackTrace();}
            finally
            {
                if(urlConnection != null)
                {
                    urlConnection.disconnect();
                }
            }
        }
        catch (Exception e){e.printStackTrace();}
        stringCurrent = current;
        return current;
    }
    //endregion
}