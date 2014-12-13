#Documentation of the Components in our System

Originally when we started we didn't know exactly what what motion sensor to use.  We played around with the Rotational Vector Sensor, Gyroscopic Sensor, and Accelometer.  We eventually used the Rotational Vector Sensor.


Although this assignment only asked for sound feedback in accord to the orientation of the phone, we thought adding text to indicate the orientation would be helpful us to see that our program was working properly. The UI is a simple one line label and a text field that indicate the phone's orientation.


Much of our code was built on top of the Prof Patterson tutorial.  But as you can imagined, we deviated where neccessary once we changed to the Rotational Vector Sensor.

The two methods that changed the most were the updatedUI, onCreate, and onSensorChanged.  We added a new method called playAudio and added audio files into the raw file.

The onCreate is the our mastermind method.  In it lies the onSensorChanged method that updates our rotation variables and make method calls to UI and playAudio.
