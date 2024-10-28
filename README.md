# jolt-jni

[The Jolt-jni Project][project] provides
[JVM] bindings for [Jolt Physics][jolt],
to facilitate physics simulation in JVM languages such as [Java] and [Kotlin].

Source code is provided under
[an MIT license][license].


## Translating Jolt Physics applications into Java

There’s close correspondence between the class/method names
of Jolt Physics and jolt-jni.
For example:

+ The `Body` class in jolt-jni will (eventually) provide
  all the functionality of the `Body` class in Jolt Physics.
+ The `ConstBody` interface will include all the `const` methods
  of the Jolt Physics `Body` class, such as its `GetPosition()` method,
  which in jolt-jni is called `getPosition()`.

Things become slightly more interesting when C++ templates
and public member data are involved. For instance:

+ An array of body IDs is `Array<BodyID>` in Jolt Physics;
  in jolt-jni it’s called a `BodyIdVector`.
+ The `mConvexRadius` member of the Jolt Physics `BoxShapeSettings` class
  is accessed using `getConvexRadius()` and `setConvexRadius()` in jolt-jni.

For a couple well-known Jolt Physics examples,
[line-for-line translations into Java](https://github.com/stephengold/jolt-jni/tree/master/src/test/java/testjoltjni/app)
are provided.


## How to add jolt-jni to an existing JVM project

Jolt-jni comes pre-built as a platform-independent JVM library
plus a set of native libraries, all downloadable from Maven Central.

Current jolt-jni releases provide
the JVM library under 6 distinct names (artifact IDs).
They also provide 24 native libraries,
each specific to a particular platform, build type, and build flavor.

Your runtime classpath should include
a JVM library plus 1-to-6 native libraries:
a native library for each platform on which the code will run.

### Gradle-built projects

Add to the project’s "build.gradle" or "build.gradle.kts" file:

    repositories {
        mavenCentral()
    }
    dependencies {
        implementation("com.github.stephengold:jolt-jni-Linux64:0.9.0") // JVM library
        runtimeOnly("com.github.stephengold:jolt-jni-Linux64:0.9.0:DebugSp") // native library for Linux
        // (Native libraries for other platforms could go here.)
    }

+ The "Linux64" platform name may be replaced by "Linux_ARM32hf", "Linux_ARM64",
  "MacOSX64", "MacOSX_ARM64", or "Windows64".
+ The "DebugSp" classifier
  may be replaced by "DebugDp", "ReleaseSp", or "ReleaseDp".


## How to build jolt-jni from source

### Initial build

1. Install a [Java Development Kit (JDK)][adoptium],
   if you don't already have one.
2. Point the `JAVA_HOME` environment variable to your JDK installation:
   (In other words, set it to the path of a directory/folder
   containing a "bin" that contains a Java executable.
   That path might look something like
   "C:\Program Files\Eclipse Adoptium\jdk-17.0.3.7-hotspot"
   or "/usr/lib/jvm/java-17-openjdk-amd64/" or
   "/Library/Java/JavaVirtualMachines/zulu-17.jdk/Contents/Home" .)
  + using Bash or Zsh: `export JAVA_HOME="` *path to installation* `"`
  + using [Fish]: `set -g JAVA_HOME "` *path to installation* `"`
  + using Windows Command Prompt: `set JAVA_HOME="` *path to installation* `"`
  + using PowerShell: `$env:JAVA_HOME = '` *path to installation* `'`
3. Download and extract the jolt-jni source code from GitHub:
  + using [Git]:
    + `git clone https://github.com/stephengold/jolt-jni.git`
    + `cd jolt-jni`
    + `git checkout -b latest 0.9.0`
 + using a web browser:
    + browse to [the latest release][latest]
    + follow the "Source code (zip)" link at the bottom of the page
    + save the ZIP file
    + extract the contents of the saved ZIP file
    + `cd` to the extracted directory/folder
4. (optional) Edit the "gradle.properties" file to configure the build.
5. Run the [Gradle] wrapper:
  + using Bash or Fish or PowerShell or Zsh: `./gradlew build`
  + using Windows Command Prompt: `.\gradlew build`

After a successful build,
artifacts will be found in "build/libs".

You can run the "hello world" example app:
+ using Bash or Fish or PowerShell or Zsh: `./gradlew runHelloWorld`
+ using Windows Command Prompt: `.\gradlew runHelloWorld`

You can run various scenes in the "performance test" example app:
+ the ConvexVsMesh scene:
  + using Bash or Fish or PowerShell or Zsh: `./gradlew runConvexVsMesh`
  + using Windows Command Prompt: `.\gradlew runConvexVsMesh`
+ the Pyramid scene:
  + using Bash or Fish or PowerShell or Zsh: `./gradlew runPyramid`
  + using Windows Command Prompt: `.\gradlew runPyramid`
+ the Ragdoll scene:
  + using Bash or Fish or PowerShell or Zsh: `./gradlew runRagdoll`
  + using Windows Command Prompt: `.\gradlew runRagdoll`

You can install the artifacts to your local Maven repository:
+ using Bash or Fish or PowerShell or Zsh: `./gradlew install`
+ using Windows Command Prompt: `.\gradlew install`

You can restore the project to a pristine state:
+ using Bash or Fish or PowerShell or Zsh: `./gradlew cleanAll`
+ using Windows Command Prompt: `.\gradlew cleanAll`


## Freeing native objects

In long-running applications,
it's important to free objects that are no longer in use,
lest the app run out of memory.

Here it's important to distinguish between JVM objects and native objects.
A handful jolt-jni classes are implemented entirely in Java, notably:
`Color`, `Float3`, `Plane`, `Quat`, `RVec3`, `UVec4`, `Vec3`, and `Vec4`.
Apart from these special classes,
every JVM object in jolt-jni is an instance of `JoltPhysicsObject`,
which implies it has a corresponding native object assigned to it.

For instance, when a jolt-jni app instantiates a matrix using `new Mat44()`,
both a JVM object and a native object are created.
The app can't access the native object directly;
it can only invoke methods exposed by the JVM object.

While JVM objects get reclaimed automatically
(in batches, by a garbage collector) after they become unreachable,
jolt-jni provides several mechanisms for reclaiming native objects.

In the simple case of a matrix instantiated using `matrix = new Mat44()`,
the native object can be freed:
+ explicitly using `matrix.close()`,
+ implicitly by `AutoCloseable`
  (at the end of the `try` block in which the matrix was instantiated), or
+ automatically when the corresponding JVM object is reclaimed
  (provided a cleaning task has been started
  by invoking `JoltPhysicsObject.startCleaner()`).

In native code, convention dictates that when a class allocates memory,
it assumes responsibility for freeing it.
For this reason, jolt-jni applications cannot free objects created
implicitly by Jolt Physics.

For instance, when an app invokes `getBodyLockInterface()` on a `PhysicsSystem`,
a new JVM object is returned.
However, that JVM object refers to a pre-existing native object
(the one Jolt Physics allocated while initializing the `PhysicsSystem`).
Thus the application need not (and cannot) free the native object
separately from the `PhysicsSystem` that contains it.
On such "contained" objects, `close()` is a no-op,
because the JVM object doesn't "own" its assigned native object.

Another case where a `JoltPhysicsObject` doesn't own its assigned native object
is when the object implements reference counting.
In this case, responsibility for freeing the native object (called the "target")
is shared among other objects (called "references") that refer to it.
Jolt-jni classes that implement reference counting
are exactly those that implement the `RefTarget` interface.
They include `BaseCharacter`, `Constraint`, `ConstraintSettings`,
`GroupFilter`, `PhysicsMaterial`, `PhysicsScene`, `Ragdoll`, `Shape`,
`ShapeSettings`, `VehicleController`, and all their subclasses.
On `RefTarget` objects, `close()` is (again) a no-op,
because the JVM object doesn't own its assigned native object.

The simplest way to create a reference is to invoke `target.toRef()`.
References are themselves instances of `JoltPhysicsObject`, of course.
(And please note that jolt-jni reference counting is completely orthogonal
to Java references, strong, weak, or otherwise.)

The only way to free the assigned native object of a `RefTarget`
is to decrement its reference count from one to zero.
This implies creating one or more references
and then freeing them all, either explicitly, implicitly, or automatically.

As long as a reference is active, its target cannot be freed.
Nor can a target be freed if no reference to it has been created
(because in that case its reference count is already zero).
Nor can it be freed if reference counting is disabled
by invoking `target.setEmbedded()`.


## External links

+ [The Jolt Physics repo at GitHub](https://github.com/jrouwe/JoltPhysics)


[adoptium]: https://adoptium.net/releases.html "Adoptium Project"
[fish]: https://fishshell.com/ "Fish command-line shell"
[git]: https://git-scm.com "Git"
[github]: https://github.com "GitHub"
[gradle]: https://gradle.org "Gradle Project"
[java]: https://en.wikipedia.org/wiki/Java_(programming_language) "Java programming language"
[jolt]: https://jrouwe.github.io/JoltPhysics "Jolt Physics project"
[jvm]: https://en.wikipedia.org/wiki/Java_virtual_machine "Java Virtual Machine"
[kotlin]: https://en.wikipedia.org/wiki/Kotlin_(programming_language) "Kotlin programming language"
[latest]: https://github.com/stephengold/jolt-jni/releases/latest "latest jolt-jni release"
[license]: https://github.com/stephengold/jolt-jni/blob/master/LICENSE "jolt-jni license"
[project]: https://github.com/stephengold/jolt-jni "Jolt-jni Project"
