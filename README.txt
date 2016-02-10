Hello! 

To run our project please do the following:
1. Download the zip file (if you are reading this then you have done this!)
2. Extract the zip file
3. Please make sure all following batch files are set to be executable
4. To compile the Simori code, run compilecode.bat from the folder in which it is located
5. To run the Simori code, run runcode.bat
6. Place the two required jar files in the same folder as the batch files
7. To compile the JUnit tests, run compiletests.bat
8. To run the JUnit tests, run runtests.bat


Please Note: Some of our unit tests use GUI-related mock objects with uninstantiated fields,
and this can lead to NullPointerExceptions during the JUnit testing if the GUIs which pop up
are interacted with  in any way. Even having the mouse inside the window when the tests start
can cause an issue!
As a result please put the mouse on the other side of the screen.
Also please avoiding touching the computer whilst the tests run!

Jars required for JUnit tests:
junit-4.12.jar
hamcrest-core-1.3.jar
These are included within the zip file.

All files were tested on the machines in the blue room as specified in the specification.

Cheers,
Team H
Josh, Adam, Matt, Jurek and James
