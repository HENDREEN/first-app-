package com.example.dell.preferrenecestest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {
    public static final String BASE_WEATHER_URL = "https://openweathermap.org/data/2.5/weather?appid=b6907d289e10d714a6e88b30761fae22";
    TextView nameText;
    TextView windSpeedText;
    TextView descriptionText;
public static final String TAG="MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //.find view by id..............................................
        nameText = findViewById(R.id.name_location);
        windSpeedText = findViewById(R.id.wind_tv);
        descriptionText = findViewById(R.id.description_tv);

    }
//......................for create  and inflate menu...........................
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_bar,menu);
        return true;
    }
    //.........................................................
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.setting:
                Intent intent=new Intent(MainActivity.this,SettingSecondActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    //.................................................................

    @Override
    protected void onStart() {
        super.onStart();
        LoaderManager loaderManager=getSupportLoaderManager();
        Loader loader= loaderManager.getLoader(0);
        if(loader==null){
            loaderManager.initLoader(0,null,MainActivity.this).forceLoad();
        }else{
            loaderManager.restartLoader(0,null,MainActivity.this).forceLoad();
        }
    }

//..............on create loader.............................................
    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {

       SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        String location = sharedPreferences.getString(getString(R.string.key), "London");

        Uri baseUri = Uri.parse(BASE_WEATHER_URL);

       Uri.Builder builder = baseUri.buildUpon();

        builder.appendQueryParameter("q", location);

        builder.build();

        return new WeatherAysincTaskLoader(this,builder.toString());
    }
    //...........................................................................
    @Override
    public void onLoadFinished(Loader<String> loader, String data) {
        JSONObject root ;
        Log.i(TAG,data);
        try {
            root = new JSONObject(data);
           String name=root.getString("name");
           JSONArray weather=root.getJSONArray("weather");
           JSONObject element=weather.getJSONObject(0);
           String description=element.getString("description");
           JSONObject winds=root.getJSONObject("wind");
            double windSpeed=winds.getDouble("speed");

            //..........................................
            nameText.setText(name);
            descriptionText.setText(description);
            windSpeedText.setText(windSpeed+"");
            //..................

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
//...................................................................................
    @Override
    public void onLoaderReset(Loader<String> loader) {

    }
}
