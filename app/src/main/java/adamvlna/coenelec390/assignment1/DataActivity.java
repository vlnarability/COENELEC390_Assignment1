package adamvlna.coenelec390.assignment1;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;


public class DataActivity extends AppCompatActivity{

    protected SharedPreferenceHelper sharedPrefHelper;
    protected Settings userSettings;

    protected RecyclerViewAdapter adapter;

    protected int eventAValue = 0;
    protected int eventBValue = 0;
    protected int eventCValue = 0;

    protected boolean showEventNames = true;

    protected String[] events;

    protected TextView event1TextView;
    protected TextView event2TextView;
    protected TextView event3TextView;
    protected TextView totalTextView;

    protected RecyclerView eventLogRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        sharedPrefHelper = new SharedPreferenceHelper(DataActivity.this);
        userSettings = sharedPrefHelper.getUserSettings();

        Intent intent = getIntent();
        eventAValue = intent.getIntExtra("eventACount", 0);
        eventBValue = intent.getIntExtra("eventBCount", 0);
        eventCValue = intent.getIntExtra("eventCCount", 0);
        events = intent.getStringArrayExtra("eventLog");
        setupUI();
    }

    //Adds the Option Menu to the screen
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_data, menu);
        return true;
    }

    //Sets up what each action bar item does
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        //If the user presses on the "Toggle Event Names" item
        if (item.getItemId()==R.id.action_settings){
            //Checks if userSettings exist: toggles the showEventName boolean or creates a toast message to notify that the counters must be names first
            if(userSettings!=null){
                showEventNames = !showEventNames;
                refreshUI();
            }
            else{
                Toast toast = Toast.makeText(getApplicationContext(), "Please name the counters in settings first", Toast.LENGTH_SHORT);
                toast.show();
            }

            return true;
        } else
            return super.onOptionsItemSelected(item);
    }

    //Allow the back button to return to the main activity
    @Override
    public boolean onNavigateUp(){
        finish();
        return true;
    }

    //Refreshes the UI with the updated parameters
    protected void refreshUI(){
        totalTextView.setText(getString(R.string.total_events,(eventAValue+eventBValue+eventCValue)));

        //Toggles between showing the event names or the default names and numbers
        if(showEventNames){
            event1TextView.setText(getString(R.string.event,userSettings.getButton1Name(),eventAValue));
            event2TextView.setText(getString(R.string.event,userSettings.getButton2Name(),eventBValue));
            event3TextView.setText(getString(R.string.event,userSettings.getButton3Name(),eventCValue));

            //Find & Replace of all default names with the ones stored in userSettings
            for(int i = 0; i < events.length; i++){
                events[i] = events[i].replaceAll("1", userSettings.getButton1Name()).replaceAll("2",userSettings.getButton2Name()).replaceAll("3", userSettings.getButton3Name());
            }
        } else{
            event1TextView.setText(getString(R.string.counter,1,eventAValue));
            event2TextView.setText(getString(R.string.counter,2,eventBValue));
            event3TextView.setText(getString(R.string.counter,3,eventCValue));

            //Find & Replace of all the userSetting names with the default ones
            for(int i = 0; i < events.length; i++){
                events[i] = events[i].replaceAll(userSettings.getButton1Name(),"1").replaceAll(userSettings.getButton2Name(),"2").replaceAll(userSettings.getButton3Name(), "3");
            }

        }

        //Sets up and displays the recyclerview to show previous events
        eventLogRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecyclerViewAdapter(this, events);
        eventLogRecyclerView.setAdapter(adapter);

        //Adds a divider between each element in the recyclerview
        DividerItemDecoration dividers = new DividerItemDecoration(eventLogRecyclerView.getContext(),new LinearLayoutManager(this).getOrientation());
        eventLogRecyclerView.addItemDecoration(dividers);


    }

    //Setup UI by assigning the IDs of the textview and recyclerview variables
    protected void setupUI(){
        if(getSupportActionBar()!=null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        event1TextView = findViewById(R.id.event1_textview);
        event2TextView = findViewById(R.id.event2_textview);
        event3TextView = findViewById(R.id.event3_textview);
        totalTextView = findViewById(R.id.total_textview);

        eventLogRecyclerView = findViewById(R.id.eventLog_recyclerview);

        refreshUI();
    }
}