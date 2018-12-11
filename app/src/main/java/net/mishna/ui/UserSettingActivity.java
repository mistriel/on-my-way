package net.mishna.ui;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import net.mishna.R;

public class UserSettingActivity extends PreferenceActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.settings);

    }
}
