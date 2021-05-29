package adamvlna.coenelec390.assignment1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {

    protected TextView counter1_text;
    protected TextView counter2_text;
    protected TextView counter3_text;
    protected TextView maxCount_text;

    protected EditText counter1_edit;
    protected EditText counter2_edit;
    protected EditText counter3_edit;
    protected EditText maxCount_edit;

    protected Button save_button;

    protected boolean isEnabled = false;

    protected SharedPreferenceHelper sharedPrefHelper;

    protected View.OnClickListener saveOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            boolean allValid = false;

            String toastMessage = "";
            Toast toast;

            //Checks if editing is currently enabled, else creates a toast message to notify that there is no information to save
            if(isEnabled){
                boolean[] validArray = validateFields();

                //Checks if every field is valid
                for(boolean valid: validArray){
                    allValid= valid;
                }

                //If every field is valid, disables editing and updates the Shared Preferences with the new settings, else creates a toast message to explain why it cannot be saved
                if(allValid){

                    toastMessage = "Saved!";

                    isEnabled = false;

                    counter1_edit.setEnabled(false);
                    counter2_edit.setEnabled(false);
                    counter3_edit.setEnabled(false);
                    maxCount_edit.setEnabled(false);

                    Settings newUserSettings = new Settings(String.valueOf(counter1_edit.getText()), String.valueOf(counter2_edit.getText()), String.valueOf(counter3_edit.getText()), Integer.parseInt(String.valueOf(maxCount_edit.getText())));
                    sharedPrefHelper.setUserSettings(newUserSettings);
                }
                else{
                    if(!validArray[0]){
                        toastMessage+="\nCounter 1 can be max 20 characters\n";
                    }
                    if(!validArray[1]){
                        toastMessage+="\nCounter 2 can only max 20 characters\n";
                    }
                    if(!validArray[2]){
                        toastMessage+="\nCounter 3 can only max 20 characters\n";
                    }
                    if(!validArray[3]){
                        toastMessage+="\nMaximum must be between 5 and 200\n";
                    }
                }

            }
            else
                    toastMessage = "There is nothing to save";

            toast = Toast.makeText(getApplicationContext(), toastMessage, Toast.LENGTH_SHORT);
            toast.show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        sharedPrefHelper = new SharedPreferenceHelper(SettingsActivity.this);

        setupUI();
    }

    //Adds the Option Menu to the screen
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }

    //Sets up what each action bar item does
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if (item.getItemId()==R.id.edit){
            isEnabled = true;

            counter1_edit.setEnabled(true);
            counter2_edit.setEnabled(true);
            counter3_edit.setEnabled(true);
            maxCount_edit.setEnabled(true);

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

    //Setup UI by assigning the IDs of the textview and edittext variables and sets the edittext texts to be the ones stored in userSettings
    protected void setupUI(){
        if(getSupportActionBar()!=null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        counter1_text = findViewById(R.id.counter1_textview);
        counter2_text = findViewById(R.id.counter2_textview);
        counter3_text = findViewById(R.id.counter3_textview);
        maxCount_text = findViewById(R.id.maxCount_textview);

        counter1_edit = findViewById(R.id.counter1_edittext);
        counter2_edit = findViewById(R.id.counter2_edittext);
        counter3_edit = findViewById(R.id.counter3_edittext);
        maxCount_edit = findViewById(R.id.maxCount_edittext);

        save_button = findViewById(R.id.save_button);
        save_button.setOnClickListener(saveOnClickListener);

        Settings userSettings = sharedPrefHelper.getUserSettings();

        //If previous userSettings exist, update the edittext fields
        if(userSettings!=null){
            counter1_edit.setText(userSettings.getButton1Name());
            counter2_edit.setText(userSettings.getButton2Name());
            counter3_edit.setText(userSettings.getButton3Name());
            maxCount_edit.setText(String.valueOf(userSettings.getMaxEvents()));
        }

    }

    //Validates the EditText fields and checks if empty
    protected boolean[] validateFields(){
        boolean[] isValid = new boolean[4];
        int maxCount;

        //Checks to see if maxCount is empty
        if(!maxCount_edit.getText().toString().isEmpty()) {
            maxCount = Integer.parseInt(String.valueOf(maxCount_edit.getText()));

            //Verifies that max length is between 5 and 200
            if(maxCount>=5 && maxCount <= 200){
                isValid[3] = true;
            }
        }

        //Verifies the length of counter 1
        if(String.valueOf(counter1_edit.getText()).length()<=20 && !counter1_edit.getText().toString().isEmpty()){
            isValid[0] = true;
        }

        //Verifies the length of counter 2
        if(String.valueOf(counter2_edit.getText()).length()<=20 && !counter2_edit.getText().toString().isEmpty()){
            isValid[1] = true;
        }

        //Verifies the length of counter 3
        if(String.valueOf(counter3_edit.getText()).length()<=20 && !counter3_edit.getText().toString().isEmpty()){
            isValid[2] = true;
        }

        return isValid;
    }
}