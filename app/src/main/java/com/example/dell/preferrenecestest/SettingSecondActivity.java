package com.example.dell.preferrenecestest;

import android.content.SharedPreferences;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import java.util.prefs.PreferenceChangeListener;

public class SettingSecondActivity extends AppCompatActivity {
///////////////////////////////////////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_second);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    /////////////////////////////////////////
    ///start of class
    public static class WeatherPrefernce extends PreferenceFragment implements Preference.OnPreferenceChangeListener{
        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            //for adding in layout of setting (xml.setting)
            addPreferencesFromResource(R.xml.setting_second_main);
            //for summary of location
            Preference location =findPreference(getString(R.string.key));
            bindPreferenceSummaryToValue(location);
            ///for summary of section
            Preference section =findPreference(getString(R.string.key2));
            bindPreferenceSummaryToValue(section);
//[kokpkkkpk
        }
        //for summary..of loaction.............................
        private  void bindPreferenceSummaryToValue(Preference preference){
            preference.setOnPreferenceChangeListener(this);
            SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(preference.getContext());
            String preferenceString=preferences.getString(preference.getKey(),"");
            onPreferenceChange(preference,preferenceString);

        }
        ///for sumaary
        @Override
        public boolean onPreferenceChange(Preference preference, Object o) {
            String StringValue=o.toString();
            preference.setSummary(StringValue);
            return true;
        }




    }
    //end class
/////////////////////////for retrun menu button arraw
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}
