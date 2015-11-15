import lejos.nxt.Battery;
import lejos.nxt.LCD;
import lejos.robotics.subsumption.Behavior;

/**
 * A basic NXT Debug behaviour, for testing code.
 * 
 * @author James Tapsell
 *
 */
public class Debug implements Behavior {

  // Flag for if to print debug messages for testing
  static final boolean DEBUG = true;

  // Flag for if to not move the wheels for testing
  static final boolean STATIONARY = true;

  // Makes accessing the driver more concise, it is needed for sensor readings
  private static Driver driver = BehaviourRobot.driver;

  // Current run message, as Arbitrator has no way to get _active
  private static String nowMessage = "";

  private static Behavior[] behaviors;

  private static String[] titles;

  /**
  * Constructor for debug behaviour.
  * @param newBehaviors The array of behaviours given to the arbitrator
  * @param newTitles The names to display on the screen for the behaviours
  */
  public Debug(Behavior[] newBehaviors, String[] newTitles) {
  
    // The number of behaviours, which is set once, so that the differences
    // in array size do not create glitches
    int numberOfBehaviors = newTitles.length;
  
    // Need to initialise the arrays before copying the data into them
    behaviors = new Behavior[numberOfBehaviors];
    titles = new String[numberOfBehaviors];
  
    // The arrays must be copied, because otherwise adding debug to the array
    // so it runs creates infinite recursion, causing a stack overflow
    System.arraycopy(newTitles, 0, titles, 0, numberOfBehaviors);
    System.arraycopy(newBehaviors, 0, behaviors, 0, numberOfBehaviors);
  }
  /**
  * Checks if the behaviour wants to take control of the robot.
  */
  @Override
  public boolean takeControl() {
    
    // Only print messages if in debug mode
    if (DEBUG) {
      printDebug();
    }

    // The debug behaviour never needs control
    return false;
  }

  @Override
  public void action() {
    // No action, because this behaviour never requests control
  }

  @Override
  public void suppress() {
    // No suppress, as it has no action
  }
  
  /**
   * Print a debug message about a name and a status.
   * @param name The name that has started
   * @param status The status
   */
  private static void printMessage(String name,String status) {
    nowMessage = status + name;
  }
  
  /**
   * Print a debug message saying the method has started.
   * @param name The name that has started for the message
   */
  public static void printStart(String name) {
    printMessage(name, "S");
  }
  
  /**
   * Print a debug message saying the method has stopped.
   * @param name The name that has stopped for the message
   */
  public static void printStop(String name) {
    printMessage(name,"E");
  }

  /**
  * Prints the debug messages to the LCD.
  */
  private static void printDebug() {

    // Temporary status variable
    boolean ret = true;

    // Print if the robot should be in moving mode, or desk mode
    LCD.drawString("NO-MOVE:  " + STATIONARY + "     ",0,4,STATIONARY);
    
    // Print battery level
    LCD.drawString("BATTERY:  " + Battery.getVoltageMilliVolt() + "mv", 0, 3);
   
    // See behaviours want control, draw the results
    for (int i = 0; i < behaviors.length; i++) {
    
      // Check if the behaviour wants control
      ret = behaviors[i].takeControl();
      
      // Pad the name to the strings line up
      String titlePadded = pad(titles[i] + ":",10);
      
      // Print out the result to the screen
      LCD.drawString(titlePadded + ret + "      .",0,i,ret);
    }
        
    // Print Sensor values
    LCD.drawString(pad("DISTANCE:",10) + driver.getDistance() + "     ",0,5);
    LCD.drawString(pad("SOUND:",10) + driver.getSound() + "     ",0,6);

    // Print the current now message to the screen
    LCD.drawString(pad("NOW:",7) + nowMessage + "                ",0,7);

  }
  
  private static String pad(String input,int length) {
    String ret = input;
    for (int i = input.length();i < length + 1; i++) {
      ret += " ";
    }
    
    return ret;
  }
}
