# jolt-jni

[JVM](https://en.wikipedia.org/wiki/Java_virtual_machine) bindings
for [Jolt Physics](https://jrouwe.github.io/JoltPhysics)

**currently under construction**

Jolt-jni provides a thin layer atop Jolt Physics,
to facilitate physics simulation in JVM languages such as Java and Kotlin.

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
        implementation("com.github.stephengold:jolt-jni-Linux64:0.5.0") // JVM library
        runtimeOnly("com.github.stephengold:jolt-jni-Linux64:0.5.0:DebugSp") // native library for Linux
        // (Native libraries for other platforms could go here.)
    }

+ The "Linux64" platform name may be replaced by "Linux_ARM32hf", "Linux_ARM64",
  "MacOSX64", "MacOSX_ARM64", or "Windows64".
+ The "DebugSp" classifier
  may be replaced by "DebugDp", "ReleaseSp", or "ReleaseDp".

## External links

+ [The JoltPhysics repo at GitHub](https://github.com/jrouwe/JoltPhysics)
