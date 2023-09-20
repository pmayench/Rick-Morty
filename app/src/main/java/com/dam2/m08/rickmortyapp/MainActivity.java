package com.dam2.m08.rickmortyapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener
{
    //region VARIABLES
    public static boolean hihaFiltre = false;
    private static int count = 1;
    private RecyclerAdapter adapter;
    public static String JSON_URL = "https://rickandmortyapi.com/api/character";

    public static Character personatge = new Character();
    public static Info infoStatic = new Info();
    List<Character> listaCharacter;
    RecyclerView recyclerView;
    Button previous, next, reset;
    ImageButton settings;
    SearchView txtBuscar;
    //endregion

    //region ONCREATE
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtBuscar = findViewById(R.id.search);
        listaCharacter = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerView);

        GetData getData = new GetData();
        getData.execute();

        //region ACCIONS dels Botons
        previous = findViewById(R.id.btnPrevious);
        next = findViewById(R.id.btnNext);
        reset = findViewById(R.id.btnReset);
        settings = findViewById(R.id.btnSettings);

        previous.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(infoStatic.prev != "null")
                {
                    JSON_URL = infoStatic.prev;
                    changeActivity(v, MainActivity.class);
                }
            }
        });

        next.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(infoStatic.next != "null")
                {
                    JSON_URL = infoStatic.next;
                    changeActivity(v, MainActivity.class);
                }
            }
        });

        reset.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(JSON_URL != "https://rickandmortyapi.com/api/character" || hihaFiltre)
                {
                    JSON_URL = "https://rickandmortyapi.com/api/character";
                    changeActivity(v, MainActivity.class);
                    hihaFiltre = false;
                    Toast.makeText(getApplicationContext(), "Search has been reset", Toast.LENGTH_SHORT).show();
                }
            }
        });

        settings.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                changeActivity(v, Filter.class);
            }
        });

        //Dispara funcio implementada SearchView
        txtBuscar.setOnQueryTextListener(this);
        //endregion
    }

    private void PutDataIntoRecyclerView(List<Character> characterList)
    {
        RecyclerAdapter adaptery = new RecyclerAdapter(this, characterList);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));

        adaptery.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                personatge = characterList.get(recyclerView.getChildAdapterPosition(view));
                //Miro quin id estic guardant
                //Toast.makeText(getApplicationContext(), characterList.get(recyclerView.getChildAdapterPosition(view)).getName() + ", ID: " + characterList.get(recyclerView.getChildAdapterPosition(view)).getId(), Toast.LENGTH_SHORT).show();
                changeActivity(view, CharacterDetail.class);
            }
        });
        recyclerView.setAdapter(adaptery);
    }

    public void changeActivity(View v, Class tClass)
    {
        Intent myIntent = new Intent(this, tClass);
        this.startActivity(myIntent);
        if(tClass != MainActivity.class)
        {
            overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
        }
    }
    //endregion

    //region Implementacions SEARCHVIEW
    @Override
    public boolean onQueryTextSubmit(String query) {return false;}
    @Override
    public boolean onQueryTextChange(String newText)
    {
        adapter = new RecyclerAdapter(getApplicationContext(), listaCharacter);
        adapter.filtrado(newText);
        PutDataIntoRecyclerView(listaCharacter);
        return false;
    }
    //endregion

    //region Peticions GET de l'API
    public class GetData extends AsyncTask<String, String, String>
    {
        @Override
        protected String doInBackground(String... strings)
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
            return current;
        }

        @Override
        protected void onPostExecute(String s)
        {
            try
            {
                JSONObject jsonCompleto = new JSONObject(s);
                JSONObject jsonInfo = jsonCompleto.getJSONObject("info");
                Info info = new Info();
                info.setCount(jsonInfo.getInt("count"));
                info.setPages(jsonInfo.getInt("pages"));

                try { info.setNext(jsonInfo.getString("next")); }
                catch (JSONException je){info.setNext("null");}

                try { info.setPrev(jsonInfo.getString("prev"));}
                catch (JSONException je){info.setPrev("null");}
                infoStatic = info;

                JSONArray jsonArray = jsonCompleto.getJSONArray("results");
                for(int i = 0; i < jsonArray.length(); i++)
                {
                    JSONObject jsonResults = jsonArray.getJSONObject(i);

                    Character model = new Character();
                    model.setId(jsonResults.getInt("id"));
                    model.setName(jsonResults.getString("name"));
                    model.setStatus(jsonResults.getString("status"));
                    model.setType(jsonResults.getString("type"));
                    model.setGender(jsonResults.getString("gender"));
                    model.setImage(jsonResults.getString("image"));
                    model.setSpecie(jsonResults.getString("species"));

                    listaCharacter.add(model);
                }
            }
            catch (JSONException e) {e.printStackTrace();}
            PutDataIntoRecyclerView(listaCharacter);
        }
    }
    //endregion
}