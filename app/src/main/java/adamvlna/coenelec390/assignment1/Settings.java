package adamvlna.coenelec390.assignment1;

public class Settings {

    private final String button1Name;
    private final String button2Name;
    private final String button3Name;

    private final int maxEvents;

    //Constructor for user settings
    public Settings(String button1Name, String button2Name, String button3Name, int maxEvents){
        this.button1Name = button1Name;
        this.button2Name = button2Name;
        this.button3Name = button3Name;
        this.maxEvents = maxEvents;
    }

    //Returns Button 1's name
    public String getButton1Name() {
        return button1Name;
    }
    //Returns Button 2's name
    public String getButton2Name() {
        return button2Name;
    }
    //Returns Button 3's name
    public String getButton3Name() {
        return button3Name;
    }
    //Returns the number of max events
    public int getMaxEvents() {
        return maxEvents;
    }
}
