/*
Copyright (c) 2025-2026 Stephen Gold

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
 */
package testjoltjni;

import com.github.stephengold.joltjni.Jolt;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.reflect.Modifier;
import java.util.Set;
import java.util.TreeSet;

/**
 * Print the full names of Jolt JNI's public classes to a text file.
 *
 * @author Stephen Gold sgold@sonic.net
 */
final public class ListClasses {
    // *************************************************************************
    // constants

    /**
     * filesystem path to the output file
     */
    final private static String outputFilePath = "jolt-jni-classes.txt";
    // *************************************************************************
    // constructors

    /**
     * A private constructor to inhibit instantiation of this class.
     */
    private ListClasses() {
        // do nothing
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Main entry point for the ListClasses application.
     *
     * @param arguments array of command-line arguments (not null)
     */
    public static void main(String[] arguments) {
        try {
            OutputStream fileStream = new FileOutputStream(outputFilePath);
            boolean autoFlush = true;
            PrintStream stream = new PrintStream(fileStream, autoFlush);
            printNamesTo(stream);

        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
    // *************************************************************************
    // private methods

    /**
     * Add the full names of all public classes in the specified package to the
     * specified set.
     *
     * @param nameSet the set to add to (not null)
     * @param packageName the name of the package (must start with
     * "com.github.stephengold.joltjni")
     */
    private static void addPublicClassNames(
            Set<String> nameSet, String packageName) {
        Set<Class<?>> coreClasses = Jolt.listClasses(packageName);
        for (Class<?> coreClass : coreClasses) {
            String simpleName = coreClass.getSimpleName();
            if (simpleName.isBlank()) {
                continue; // skip to the next Java class
            }
            int classModifiers = coreClass.getModifiers();
            if (!Modifier.isPublic(classModifiers)) {
                continue; // skip to the next Java class
            }
            nameSet.add(packageName + "." + simpleName);
        }
    }

    /**
     * Print the full names of Jolt JNI's public classes to the specified
     * stream.
     *
     * @param stream the output stream to use (not null)
     */
    private static void printNamesTo(PrintStream stream) {
        Set<String> names = new TreeSet<>();

        // Enumerate Jolt JNI's core and enum public classes:
        addPublicClassNames(names, "com.github.stephengold.joltjni");
        addPublicClassNames(names, "com.github.stephengold.joltjni.enumerate");
        addPublicClassNames(names, "com.github.stephengold.joltjni.operator");
        addPublicClassNames(
                names, "com.github.stephengold.joltjni.streamutils");
        addPublicClassNames(names, "com.github.stephengold.joltjni.vhacd");

        for (String fullName : names) {
            stream.println(fullName);
        }
    }
}
