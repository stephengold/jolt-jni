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
+ [How to add Jolt JNI to an existing project](#add)
+ [How to build Jolt JNI from source](#build)
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
+ The `mConvexRadius` member of the `BoxShapeSettings` class
  is accessed using `getConvexRadius()` and `setConvexRadius()` in Jolt JNI.

For a couple well-known Jolt Physics examples,
[line-for-line translations into Java](https://github.com/stephengold/jolt-jni/tree/master/src/test/java/testjoltjni/app)
are provided.

[Jump to the table of contents](#toc)


<a name="add"></a>
<a name="addAndroid"></a>
<a name="init"></a>

## How to add Jolt JNI to an existing project

[How to add Jolt JNI to an existing project](https://stephengold.github.io/jolt-jni-docs/jolt-jni-en/English/add.html)

[Jump to the table of contents](#toc)


<a name="build"></a>

## How to build Jolt JNI from source

[How to build Jolt JNI from source](https://stephengold.github.io/jolt-jni-docs/jolt-jni-en/English/build.html)

[Jump to the table of contents](#toc)


<a name="free"></a>

## Freeing native objects

[Freeing native memory](https://stephengold.github.io/jolt-jni-docs/jolt-jni-en/English/free.html)

[Jump to the table of contents](#toc)


<a name="links"></a>

## External links

+ [The Jolt Physics repo at GitHub](https://github.com/jrouwe/JoltPhysics)

[Jump to the table of contents](#toc)


<a name="acks"></a>

## Acknowledgments

Jolt JNI is derived from open-source software:

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

I am grateful to [GitHub], [Sonatype],
[Travis], and [MacStadium]
for providing free hosting for this project
and many other open-source projects.

I'm also grateful to my dear Holly, for keeping me sane.

If I've misattributed anything or left anyone out, please let me know, so I can
correct the situation: sgold@sonic.net

[Jump to the table of contents](#toc)


[checkstyle]: https://checkstyle.org "Checkstyle"
[firefox]: https://www.mozilla.org/en-US/firefox "Firefox"
[gcc]: https://gcc.gnu.org "GNU Compiler Collection"
[gdb]: https://www.gnu.org/software/gdb/ "GNU Project Debugger"
[git]: https://git-scm.com "Git"
[github]: https://github.com "GitHub"
[gitkraken]: https://www.gitkraken.com "GitKraken client"
[gradle]: https://gradle.org "Gradle"
[idea]: https://www.jetbrains.com/idea/ "IntelliJ IDEA"
[java]: https://en.wikipedia.org/wiki/Java_(programming_language) "Java programming language"
[jolt]: https://jrouwe.github.io/JoltPhysics "Jolt Physics"
[jvm]: https://en.wikipedia.org/wiki/Java_virtual_machine "Java Virtual Machine"
[kotlin]: https://en.wikipedia.org/wiki/Kotlin_(programming_language) "Kotlin programming language"
[license]: https://github.com/stephengold/jolt-jni/blob/master/LICENSE "Jolt-JNI license"
[llvm]: https://www.llvm.org "LLVM Compiler"
[macstadium]: https://www.macstadium.com/ "MacStadium"
[markdown]: https://daringfireball.net/projects/markdown "Markdown"
[meld]: https://meldmerge.org "Meld merge tool"
[mint]: https://linuxmint.com "Linux Mint"
[netbeans]: https://netbeans.org "NetBeans"
[project]: https://github.com/stephengold/jolt-jni "Jolt-JNI project"
[sonatype]: https://www.sonatype.com "Sonatype"
[studio]: https://developer.android.com/studio "Android Studio IDE"
[travis]: https://travis-ci.com "Travis CI"
[vhacd]: https://github.com/kmammou/v-hacd "V-HACD Library"
