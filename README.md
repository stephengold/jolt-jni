# Jolt JNI

[The Jolt-JNI Project][project] provides
[JVM] bindings for [Jolt Physics][jolt]
and [Khaled Mamou's V-HACD Library][vhacd],
to facilitate physics simulation in JVM languages such as [Java] and [Kotlin].

Source code (in Java and C++) is provided under
[an MIT license][license].


<a name="toc"></a>

## Contents of this document

+ [Translating Jolt Physics applications into Java](#translate)
+ [How to add Jolt JNI to an existing desktop project](#add)
+ [How to add Jolt JNI to an existing Android project](#addAndroid)
+ [How to build Jolt JNI from source](#build)
+ [Initializing Jolt JNI](#init)
+ [Freeing native objects](#free)
+ [External links](#links)
+ [Acknowledgments](#acks)


<a name="translate"></a>

## Translating Jolt Physics applications into Java

There’s close correspondence between the class/method names
of Jolt Physics and Jolt JNI.
For example:

+ The `Body` class in Jolt JNI will (eventually) provide
  all the functionality of the `Body` class in Jolt Physics.
+ The `ConstBody` interface will include all the `const` methods
  of the Jolt Physics `Body` class, such as its `GetPosition()` method,
  which in Jolt JNI is called `getPosition()`.

Things become slightly more interesting when C++ templates
and public member data are involved. For instance:

+ An array of body IDs is `Array<BodyID>` in Jolt Physics;
  in Jolt JNI it’s called a `BodyIdVector`.
+ The `mConvexRadius` member of the Jolt Physics `BoxShapeSettings` class
  is accessed using `getConvexRadius()` and `setConvexRadius()` in Jolt JNI.

For a couple well-known Jolt Physics examples,
[line-for-line translations into Java](https://github.com/stephengold/jolt-jni/tree/master/src/test/java/testjoltjni/app)
are provided.

[Jump to the table of contents](#toc)


<a name="add"></a>

## How to add Jolt JNI to an existing desktop project

Desktop Jolt JNI comes pre-built as a platform-independent JVM library
plus a set of native libraries, all downloadable from Maven Central.

Current Jolt-JNI releases provide
the JVM library under 8 distinct names (artifact IDs).
They also provide 32 native libraries,
each specific to a particular platform, build type, and build flavor.

Your runtime classpath should include
a JVM library plus 1-to-6 native libraries:
a native library for each platform on which the code will run.

Build types:  use "Debug" native libraries for development and troubleshooting,
then switch to "Release" libraries for performance testing and production.

Build flavors:  use "Dp" to simulate large worlds (>1000 meters in diameter)
otherwise use "Sp".

### Gradle-built projects

Add to the project’s "build.gradle" or "build.gradle.kts" file:

    repositories {
        mavenCentral()
    }
    dependencies {
        // JVM library:
        implementation("com.github.stephengold:jolt-jni-Linux64:0.9.7")

        // native libraries:
        runtimeOnly("com.github.stephengold:jolt-jni-Linux64:0.9.7:DebugSp")
        // Native libraries for other platforms could be added.
    }

+ The "Linux64" platform name may be replaced by "Linux64_fma", "Linux_ARM32hf",
  "Linux_ARM64", "MacOSX64", "MacOSX_ARM64", "Windows64", or "Windows64_avx2".
+ The "DebugSp" classifier
  may be replaced by "DebugDp", "ReleaseSp", or "ReleaseDp".
+ For some older versions of Gradle,
  it's necessary to replace `implementation` with `compile`.

[Jump to the table of contents](#toc)


<a name="addAndroid"></a>

## How to add Jolt JNI to an existing Android project

Android Jolt JNI comes pre-built as a pair of multi-platform libraries,
one for each build type.
(The only build flavor provided is "Sp".)
Both libraries are downloadable from Maven Central
under the "jolt-jni-Android" artifact ID.

Build types:  use "Debug" native libraries for development and troubleshooting,
then switch to "Release" libraries for performance testing and production.

Add to the project’s "build.gradle" or "build.gradle.kts" file:

    repositories {
        mavenCentral()
    }
    dependencies {
        implementation("com.github.stephengold:jolt-jni:0.9.7:SpDebug@aar")
    }

+ The "SpDebug" classifier may be replaced by "SpRelease".
+ For some older versions of Gradle,
  it's necessary to replace `implementation` with `compile`.

[Jump to the table of contents](#toc)


<a name="build"></a>

## How to build Jolt JNI from source

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
3. Download and extract the Jolt-JNI source code from GitHub:
  + using [Git]:
    + `git clone https://github.com/stephengold/jolt-jni.git`
    + `cd jolt-jni`
    + `git checkout -b latest 0.9.7`
  + using a web browser:
    + browse to [the latest release][latest]
    + follow the "Source code (zip)" link at the bottom of the page
    + save the ZIP file
    + extract the contents of the saved ZIP file
    + `cd` to the extracted directory/folder
4. (optional) Edit the "gradle.properties" file to configure the build.
5. Run the [Gradle] wrapper to build desktop artifacts:
  + using Bash or Fish or PowerShell or Zsh: `./gradlew build`
  + using Windows Command Prompt: `.\gradlew build`
6. To build Android artifacts, you'll need to install [Android Studio][studio]
   and point the `ANDROID_HOME` environment variable to that installation.
7. Run the Gradle wrapper to build Android artifacts:
  + using Bash or Fish or PowerShell or Zsh: `./gradlew -b android.gradle assemble`
  + using Windows Command Prompt: `.\gradlew -b android.gradle assemble`

After a successful build,
artifacts will be found in "build/libs" (desktop) and "build/outputs/aar" (Android).

### Other tasks

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
+ using Bash or Fish or PowerShell or Zsh: `./gradlew install;./gradlew -b android.gradle install`
+ using Windows Command Prompt:
  + `.\gradlew install`
  + `.\gradlew -b android.gradle install`

You can restore the project to a pristine state:
+ using Bash or Fish or PowerShell or Zsh: `./gradlew -b android.gradle clean;./gradlew cleanAll`
+ using Windows Command Prompt:
  + `.\gradlew -b android.gradle clean`
  + `.\gradlew cleanAll`

[Jump to the table of contents](#toc)


<a name="init"></a>

## Initializing Jolt JNI

Before simulating physics, much initialization and configuration is required:

1. Extract and load a native library build for the current platform.
2. Register 2 memory allocators (default and temporary).
3. Install trace and assert callbacks.
4. Create a factory and register physics types.
5. Create a job system.
6. Define 2 kinds of collision layers (object and broadphase)
   and collision filters for each.
7. Define the mapping from object layers to broadphase layers.
8. Create, configure, and populate a physics system.

Steps 2 through 8 are also required in Jolt Physics apps, of course.
However, they look different in Java than they do in C++.
A tutorial is planned.
Until it is published, initialization procedures can be learned by example:

+ [the "HelloWorld" test app](https://github.com/stephengold/jolt-jni/blob/ec805689643794d4940b6501f4389f12d2b9b128/src/test/java/testjoltjni/app/helloworld/HelloWorld.java#L146-L249)
+ [the "TestUtils" class](https://github.com/stephengold/jolt-jni/blob/ec805689643794d4940b6501f4389f12d2b9b128/src/test/java/testjoltjni/TestUtils.java#L303-L328)

However, the first step (extract and load a native library) is different
for a standalone app that doesn't have the files already extracted
(as the Jolt-JNI tests do).

In an Android app, one can simply invoke `System.loadLibrary("joltjni")`.

To extract and load native libraries in a desktop app, I suggest using
[the jSnapLoader library](https://github.com/Electrostat-Lab/jSnapLoader).
Here's a code fragment to get you started:

    LibraryInfo info = new LibraryInfo(
        new DirectoryPath("linux/x86-64/com/github/stephengold"),
        "joltjni", DirectoryPath.USER_DIR);
    NativeBinaryLoader loader = new NativeBinaryLoader(info);
    NativeDynamicLibrary[] libraries = {
        new NativeDynamicLibrary("linux/aarch64/com/github/stephengold", PlatformPredicate.LINUX_ARM_64),
        new NativeDynamicLibrary("linux/armhf/com/github/stephengold", PlatformPredicate.LINUX_ARM_32),
        new NativeDynamicLibrary("linux/x86-64/com/github/stephengold", PlatformPredicate.LINUX_X86_64),
        new NativeDynamicLibrary("osx/aarch64/com/github/stephengold", PlatformPredicate.MACOS_ARM_64),
        new NativeDynamicLibrary("osx/x86-64/com/github/stephengold", PlatformPredicate.MACOS_X86_64),
        new NativeDynamicLibrary("windows/x86-64/com/github/stephengold", PlatformPredicate.WIN_X86_64)
    };
    loader.registerNativeLibraries(libraries).initPlatformLibrary();
    loader.setLoggingEnabled(true);
    loader.setRetryWithCleanExtraction(true);
    try {
        loader.loadLibrary(LoadingCriterion.CLEAN_EXTRACTION);
    } catch (Exception e) {
        throw new IllegalStateException("Failed to load a Jolt-JNI native library!");
    }

[Jump to the table of contents](#toc)


<a name="free"></a>

## Freeing native objects

In long-running applications,
it's important to free objects that are no longer in use,
lest the app run out of memory.

Here it's important to distinguish between JVM objects and native objects.
A handful Jolt-JNI classes are implemented entirely in Java, notably:
`Color`, `Float3`, `Plane`, `Quat`, `RVec3`, `UVec4`, `Vec3`, and `Vec4`.
Apart from these special classes,
every JVM object in Jolt JNI is an instance of `JoltPhysicsObject`,
which implies it has a corresponding native object assigned to it.

For instance, when a Jolt-JNI app instantiates a matrix using `new Mat44()`,
both a JVM object and a native object are created.
The app can't access the native object directly;
it can only invoke methods exposed by the JVM object.

While JVM objects get reclaimed automatically
(in batches, by a garbage collector) after they become unreachable,
Jolt JNI provides several mechanisms for reclaiming native objects.

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
For this reason, Jolt-JNI applications cannot directly free objects created
implicitly by Jolt Physics.

For instance, when an app invokes `getBodyLockInterface()` on a `PhysicsSystem`,
a new JVM object is returned.
However, that JVM object refers to a pre-existing native object
(the one Jolt Physics allocated while initializing the `PhysicsSystem`).
Thus, the application need not (and cannot) free the native object
separately from the `PhysicsSystem` that contains it.
On such "contained" objects, `close()` is a no-op,
because the JVM object doesn't "own" its assigned native object.

Another case where a `JoltPhysicsObject` doesn't own its assigned native object
is when the object implements reference counting.
In this case, responsibility for freeing the native object (called the "target")
is shared among other objects (called "references") that refer to it.
Jolt-JNI classes that implement reference counting
are exactly those that implement the `RefTarget` interface.
They include `BaseCharacter`, `Constraint`, `ConstraintSettings`,
`GroupFilter`, `PhysicsMaterial`, `PhysicsScene`, `Ragdoll`, `Shape`,
`ShapeSettings`, and all their subclasses.
On `RefTarget` objects, `close()` is (again) a no-op,
because the JVM object doesn't own its assigned native object.

The simplest way to create a reference is to invoke `target.toRef()`.
References are themselves instances of `JoltPhysicsObject`, of course.
(And please note that Jolt-JNI reference counting is completely orthogonal
to Java references, strong, weak, or otherwise.)

The only way to free the assigned native object of a `RefTarget`
is to decrement its reference count from one to zero.
This implies creating one or more references
and then freeing them all, either explicitly, implicitly, or automatically.

As long as a reference is active, its target cannot be freed.
Nor can a target be freed if no reference to it has been created
(because in that case its reference count is already zero).
Nor can it be freed if reference counting has been disabled
by invoking `target.setEmbedded()`.

[Jump to the table of contents](#toc)


<a name="links"></a>

## External links

+ [The Jolt Physics repo at GitHub](https://github.com/jrouwe/JoltPhysics)

[Jump to the table of contents](#toc)


<a name="acks"></a>

## Acknowledgments

The Jolt-JNI Project is derived from open-source software:

  + the [Jolt Physics][jolt] project
  + [Khaled Mamou's V-HACD Library][vhacd] for approximate convex decomposition

This project also made use of the following software tools:

  + the [Android Studio][studio], [IntelliJ IDEA][idea], and [NetBeans]
    integrated development environments
  + the [Checkstyle] tool
  + the [Firefox] web browser
  + the [GNU Compiler Collection][gcc] and [Project Debugger][gdb]
  + the [Git] revision-control system and GitK commit viewer
  + the [GitKraken] client
  + the [Gradle] build tool
  + the [Java] compiler, standard doclet, and runtime environment
  + the [Linux Mint][mint] operating system
  + the [LLVM Compiler Infrastructure][llvm]
  + the [Markdown] document-conversion tool
  + the [Meld] visual merge tool
  + Microsoft Windows and Visual Studio

I am grateful to Riccardo Balbo (aka "riccardo") for bringing
V-HACD to my attention.

I am grateful to [GitHub], [Sonatype], [AppVeyor],
[Travis], and [MacStadium]
for providing free hosting for this project
and many other open-source projects.

I'm also grateful to my dear Holly, for keeping me sane.

If I've misattributed anything or left anyone out, please let me know, so I can
correct the situation: sgold@sonic.net

[Jump to the table of contents](#toc)


[adoptium]: https://adoptium.net/releases.html "Adoptium Project"
[appveyor]: https://www.appveyor.com "AppVeyor Continuous Integration"
[checkstyle]: https://checkstyle.org "Checkstyle"
[firefox]: https://www.mozilla.org/en-US/firefox "Firefox"
[fish]: https://fishshell.com/ "Fish command-line shell"
[gcc]: https://gcc.gnu.org "GNU Compiler Collection"
[gdb]: https://www.gnu.org/software/gdb/ "GNU Project Debugger"
[git]: https://git-scm.com "Git"
[github]: https://github.com "GitHub"
[gitkraken]: https://www.gitkraken.com "GitKraken client"
[gradle]: https://gradle.org "Gradle Project"
[idea]: https://www.jetbrains.com/idea/ "IntelliJ IDEA"
[java]: https://en.wikipedia.org/wiki/Java_(programming_language) "Java programming language"
[jolt]: https://jrouwe.github.io/JoltPhysics "Jolt Physics project"
[jvm]: https://en.wikipedia.org/wiki/Java_virtual_machine "Java Virtual Machine"
[kotlin]: https://en.wikipedia.org/wiki/Kotlin_(programming_language) "Kotlin programming language"
[latest]: https://github.com/stephengold/jolt-jni/releases/latest "latest Jolt-JNI release"
[license]: https://github.com/stephengold/jolt-jni/blob/master/LICENSE "Jolt-JNI license"
[llvm]: https://www.llvm.org "LLVM Compiler"
[macstadium]: https://www.macstadium.com/ "MacStadium"
[markdown]: https://daringfireball.net/projects/markdown "Markdown Project"
[meld]: https://meldmerge.org "Meld merge tool"
[mint]: https://linuxmint.com "Linux Mint Project"
[netbeans]: https://netbeans.org "NetBeans Project"
[project]: https://github.com/stephengold/jolt-jni "Jolt-JNI Project"
[sonatype]: https://www.sonatype.com "Sonatype"
[studio]: https://developer.android.com/studio "Android Studio IDE"
[travis]: https://travis-ci.com "Travis CI"
[vhacd]: https://github.com/kmammou/v-hacd "V-HACD Library"
