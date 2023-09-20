package com.dam2.m08.rickmortyapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Filter extends AppCompatActivity
{
    //region Variables
    MainActivity main = new MainActivity();
    Button apply;
    //endregion

    //region OnCreate
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_filter);

        apply = findViewById(R.id.btnApply);

        //Declarar Local Storage
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String recieved_data = preferences.getString("key", null);

        apply.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick( View v )
            {
                main.hihaFiltre = true;

                //Guardar Local Storage
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("key", main.JSON_URL);
                editor.apply();
                tornaEnrere(v);
            }
        });
    }
    //endregion

    //region Mètodes Propis
    public void actualitzaSettings()
    {
        RadioButton alive = findViewById(R.id.rbAlive);
        RadioButton dead = findViewById(R.id.rbDead);
        RadioButton unknownStatus = findViewById(R.id.rbUnknownStatus);

        RadioButton female = findViewById(R.id.rbFemale);
        RadioButton male = findViewById(R.id.rbMale);
        RadioButton genderless = findViewById(R.id.rbGenderless);
        RadioButton unknownGender = findViewById(R.id.rbUnknownGender);

        if(alive.isChecked())
        {main.JSON_URL = "https://rickandmortyapi.com/api/character?status=alive";}

        else if (dead.isChecked())
        {main.JSON_URL = "https://rickandmortyapi.com/api/character?status=dead";}

        else if (unknownStatus.isChecked())
        {main.JSON_URL = "https://rickandmortyapi.com/api/character?status=unknown";}

        if(female.isChecked())
        {
            if(!unknownStatus.isChecked() && !alive.isChecked() && !dead.isChecked())
            {main.JSON_URL = "https://rickandmortyapi.com/api/character?gender=female";}

            if(unknownStatus.isChecked())
            {main.JSON_URL = "https://rickandmortyapi.com/api/character?status=unknown&gender=female";}

            if(dead.isChecked())
            {main.JSON_URL = "https://rickandmortyapi.com/api/character?status=dead&gender=female";}

            if(alive.isChecked())
            {main.JSON_URL = "https://rickandmortyapi.com/api/character?status=alive&gender=female";}
        }

        else if(male.isChecked())
        {
            if(!unknownStatus.isChecked() && !alive.isChecked() && !dead.isChecked())
            {main.JSON_URL = "https://rickandmortyapi.com/api/character?gender=male";}

            if(unknownStatus.isChecked())
            {main.JSON_URL = "https://rickandmortyapi.com/api/character?status=unknown&gender=male";}

            if(dead.isChecked())
            {main.JSON_URL = "https://rickandmortyapi.com/api/character?status=dead&gender=male";}

            if(alive.isChecked())
            {main.JSON_URL = "https://rickandmortyapi.com/api/character?status=alive&gender=male";}
        }

        else if(genderless.isChecked())
        {
            if(!unknownStatus.isChecked() && !alive.isChecked() && !dead.isChecked())
            {main.JSON_URL = "https://rickandmortyapi.com/api/character?gender=genderless";}

            if(unknownStatus.isChecked())
            {main.JSON_URL = "https://rickandmortyapi.com/api/character?status=unknown&gender=genderless";}

            if(dead.isChecked())
            {main.JSON_URL = "https://rickandmortyapi.com/api/character?status=dead&gender=genderless";}

            if(alive.isChecked())
            {main.JSON_URL = "https://rickandmortyapi.com/api/character?status=alive&gender=genderless";}
        }

        else if(unknownGender.isChecked())
        {
            if(!unknownStatus.isChecked() && !alive.isChecked() && !dead.isChecked())
            {main.JSON_URL = "https://rickandmortyapi.com/api/character?gender=unknown";}

            if(unknownStatus.isChecked())
            {main.JSON_URL = "https://rickandmortyapi.com/api/character?status=unknown&gender=unknown";}

            if(dead.isChecked())
            {main.JSON_URL = "https://rickandmortyapi.com/api/character?status=dead&gender=unknown";}

            if(alive.isChecked())
            {main.JSON_URL = "https://rickandmortyapi.com/api/character?status=alive&gender=unknown";}
        }
    }

    public void tornaEnrere(View v)
    {
        actualitzaSettings();
        this.finish();
        Intent myIntent = new Intent(this, MainActivity.class);
        this.startActivity(myIntent);
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }
    //endregion
}
