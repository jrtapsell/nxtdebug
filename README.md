This is a debug behaviour for leJOS NXT
The constructor needs a behaviour array and a title array

<hr>

At the moment it displays the Sound and Distance values

At the moment it only displays a maximum of 4 behaviours

<hr>

TO ADD:
 * Values for all sensors
 * Scrolling behaviour list to show more than 4 at once

<hr>

A basic usage example
```java
// Trundle is the default and moves forward
Behavior trundle = new Trundle();
behaviors[0] = trundle;

// Backup moves back if the robot is too near a wall
Behavior backup = new Backup();
behaviors[1] = backup;

// Clap stops for claps
Behavior clap = new Clap();
behaviors[2] = clap;
    
// Debug prints out the debug variables
String[] titles = new String[]{"TRUNDLE","BACKUP","CLAP"};
Behavior debug = new Debug(behaviors, titles);
behaviors[3] = debug;
```
