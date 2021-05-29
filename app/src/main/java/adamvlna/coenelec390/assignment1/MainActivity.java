package adamvlna.coenelec390.assignment1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {

    protected SharedPreferenceHelper sharedPrefHelper;
    protected Settings userSettings;

    protected LinkedList<String> eventList = new LinkedList<>();

    protected Button settingsButton;
    protected Button eventAButton;
    protected Button eventBButton;
    protected Button eventCButton;
    protected Button dataButton;

    protected TextView totalCountTextView;

    protected int maximumValue;
    protected int eventAValue = 0;
    protected int eventBValue = 0;
    protected int eventCValue = 0;

    protected View.OnClickListener settingsOnClickListener = v -> gotoSettingsActivity();

    protected View.OnClickListener dataOnClickListener = v -> gotoDataActivity();

    //If userSettings exist, adds one to the eventA counter, adds the event to the log, and updates the total count
    //Else it goes to the SettingsActivity
    protected View.OnClickListener eventAOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(userSettings!=null){
                eventAValue++;
                totalCountTextView.setText(getString(R.string.total_count, (eventAValue+eventBValue+eventCValue)));
                addEventList("1");
            } else{
                gotoSettingsActivity();
            }
        }
    };

    //If userSettings exist, adds one to the eventB counter, adds the event to the log, and updates the total count
    //Else it goes to the SettingsActivity
    protected View.OnClickListener eventBOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(userSettings!=null){
                eventBValue++;
                totalCountTextView.setText(getString(R.string.total_count, (eventAValue+eventBValue+eventCValue)));
                addEventList("2");
            } else{
                gotoSettingsActivity();
            }
        }
    };

    //If userSettings exist, adds one to the eventC counter, adds the event to the log, and updates the total count
    //Else it goes to the SettingsActivity
    protected View.OnClickListener eventCOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(userSettings!=null){
                eventCValue++;
                totalCountTextView.setText(getString(R.string.total_count, (eventAValue+eventBValue+eventCValue)));
                addEventList("3");
            } else{
                gotoSettingsActivity();
            }
        }
    };

    //Starts SettingsActivity
    protected void gotoSettingsActivity() {
        Intent intent = new Intent(this, SettingsActivity.class);

        startActivity(intent);
    }

    //Creates intent with event counts and the log of events to pass to DataActivity
    protected void gotoDataActivity() {
        Intent intent = new Intent(this, DataActivity.class);
        intent.putExtra("eventACount", eventAValue);
        intent.putExtra("eventBCount", eventBValue);
        intent.putExtra("eventCCount", eventCValue);
        intent.putExtra("eventLog", getEventList());

        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupUI();
        setupListeners();
    }

    @Override
    protected void onStart() {
        super.onStart();

        refreshUI();
    }

    //Refreshes UI upon returning from a different activity
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        refreshUI();
    }

    //Refreshes UI to update Button names and resize the eventList
    protected void refreshUI(){
        sharedPrefHelper = new SharedPreferenceHelper(MainActivity.this);
        userSettings = sharedPrefHelper.getUserSettings();

        if (userSettings != null) {
            eventAButton.setText(userSettings.getButton1Name());
            eventBButton.setText(userSettings.getButton2Name());
            eventCButton.setText(userSettings.getButton3Name());
            maximumValue = userSettings.getMaxEvents();
            resizeEventList();
        }
    }

    //Setup UI by assigning the IDs of the buttons and textview to the previously created variables
    protected void setupUI(){
        settingsButton = findViewById(R.id.settings_button);
        eventAButton = findViewById(R.id.a_button);
        eventBButton = findViewById(R.id.b_button);
        eventCButton = findViewById(R.id.c_button);
        dataButton = findViewById(R.id.data_button);
        totalCountTextView = findViewById(R.id.count_textview);
        totalCountTextView.setText(getString(R.string.total_count, (eventAValue+eventBValue+eventCValue)));
    }

    //Setup and create the listeners for each button
    protected void setupListeners(){
        settingsButton.setOnClickListener(settingsOnClickListener);
        eventAButton.setOnClickListener(eventAOnClickListener);
        eventBButton.setOnClickListener(eventBOnClickListener);
        eventCButton.setOnClickListener(eventCOnClickListener);
        dataButton.setOnClickListener(dataOnClickListener);
    }

    //Adds the string parameter to the LinkedList eventList after running the resizeEventList method
    protected void addEventList(String eventName){
        resizeEventList();

        eventList.add(eventName);
    }

    //Returns the LinkedList eventList as a String array
    protected String[] getEventList(){

        return eventList.toArray(new String[0]);
    }

    //Removes the first node in the LinkedList until the size is less than or equal to the saved setting
    protected void resizeEventList(){
        while(eventList.size() >= userSettings.getMaxEvents())
            eventList.removeFirst();
    }
}