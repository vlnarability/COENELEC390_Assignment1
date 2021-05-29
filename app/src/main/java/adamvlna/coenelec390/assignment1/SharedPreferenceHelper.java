package adamvlna.coenelec390.assignment1;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

public class SharedPreferenceHelper{

    private final SharedPreferences sharedPref;
    private Settings userSettings;

    private String userGSON;
    private final Gson gson = new Gson();

    public SharedPreferenceHelper(Context context){
        sharedPref = context.getSharedPreferences("User Settings", Context.MODE_PRIVATE);
    }

    //Returns the stored userSettings from SharedPreference by converting the GSON string into an object
    public Settings getUserSettings(){
        userGSON = sharedPref.getString("Settings", null);
        userSettings = gson.fromJson(userGSON, Settings.class);
        return userSettings;
    }

    //Sets the stored userSettings into SharedPreferences by storing the Object as a GSON string
    public void setUserSettings(Settings userPref){
        this.userSettings = userPref;
        SharedPreferences.Editor editor = sharedPref.edit();
        userGSON = gson.toJson(userSettings);

        editor.putString("Settings", userGSON);
        editor.apply();
    }
}
