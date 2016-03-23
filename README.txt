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

There is now not a single missing @Override tag! (According to Eclipse's optional error reporting for missing override annotations). Fields and methods are encapsulated as much as possible where appropriate. However, because Java has no concept of a subpackage, each of the folders in our project's structure is technically a different top-level package. This means that we cannot declare any of the classes as package private, as it would make it possible to unit test them from test classes in our test packages. We also rely on the usage of mock objects which inherit from many of our classes, which is why we have not declared any as final.

All files were tested on the machines in the blue room as specified in the specification.
Sadly, the blue room machines do not support Window transparency. This means that the rounded corners of the Simori-ON have a black square behind them. This also affects the splash screen.
Please note that on some blue room machines there is a local problem that causes "lock: no locks available" to appear.

See also SHOPBOYREADME.txt

Cheers,
Team H
Adam, James, Josh, Jurek and Matt
