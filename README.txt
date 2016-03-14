Hello! 

To run our project please do the following:
1. Download the zip file (if you are reading this then you have done this!)
2. Extract the zip file
3. Please make sure all following batch files are set to be executable
4. To compile the Simori-ON code, run compilecode.bat from the folder in which it is located
5. To run the Simori-ON code, run runcode.bat
6. Ensure that the two required .jar files are present
   You should find them already in the same folder as the batch files
7. To compile the JUnit tests, run compiletests.bat
8. To run the JUnit tests, run runtests.bat

Jars required for JUnit tests:
junit-4.12.jar
hamcrest-core-1.3.jar
These are included within the zip file.

The network master mode currently does not have an active gui. It works the same as any other mode, you press R4 to go into master mode. The screen will go white and then you can press okay immediatly to go back to performance mode. As soon as you pressed R4 the master mode started to probe ips to find another listening simori, and will continue to do so even after you press okay.

All files were tested on the machines in the blue room as specified in the specification.

Please note that on some blue room machines there is a local problem that causes "lock: no locks available" to appear.

Cheers,
Team H
Adam, James, Josh, Jurek and Matt
