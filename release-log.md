# release log for the jolt-jni project

## Version 0.2.0 released on 9 July 2024

For development only. Not for production use.

+ Added many classes and methods.
+ Made the automated testing more systematic.
+ Worked around deadlocks in 2 destructors when running on Windows.
+ Solved a `SIGTRAP` in ConvexHullShape.cpp .
+ Shortened the names of 30 glue files.
+ Re-organized the test sourcecode into 3 new packages.
+ Modified `Shape.newShape()` to accept a zero argument.
+ Reduced the recommended number of threads when there are >9 CPUs.
+ Corrected the return type of `ConvexHullShapeSettings.createShape()` .

## Version 0.1.10 released on 3 July 2024

Initial baseline pre-release, for development only. Not for production use.