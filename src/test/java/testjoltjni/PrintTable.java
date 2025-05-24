/*
Copyright (c) 2025 Stephen Gold

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
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Print a table of corresponding types to a "lexicon.adoc" file, for use in the
 * documentation website.
 *
 * @author Stephen Gold sgold@sonic.net
 */
final public class PrintTable {
    // *************************************************************************
    // fields

    /**
     * reusable builder
     */
    final private static StringBuilder builder = new StringBuilder();
    // *************************************************************************
    // constructors

    /**
     * A private constructor to inhibit instantiation of this class.
     */
    private PrintTable() {
        // do nothing
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Main entry point for the PrintTable application.
     *
     * @param arguments array of command-line arguments (not null)
     */
    public static void main(String[] arguments) {
        try {
            FileOutputStream file = new FileOutputStream("lexicon.adoc");
            boolean autoFlush = true;
            PrintStream stream = new PrintStream(file, autoFlush);
            printTableTo(stream);

        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
    // *************************************************************************
    // private methods

    /**
     * Test whether a page exists at the specified URL.
     *
     * @param url the URL to test (not null)
     * @return {@code true} if the URL returns {@code HTTP_OK}, otherwise
     * {@code false}
     * @throws IOException from HttpURLConnection
     */
    private static boolean doesPageExist(URL url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        // Request the header only, no data:
        connection.setRequestMethod("HEAD");

        // Pretend to be a browser:
        connection.setRequestProperty("User-Agent",
                "Mozilla/5.0 (Windows; U; Windows NT 6.0; en-US; rv:1.9.1.2)"
                + " Gecko/20090729 Firefox/3.5.2 (.NET CLR 3.5.30729)");
        int responseCode = connection.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK) {
            return true;

        } else {
            System.out.println(
                    "Got " + responseCode + " from " + url.toString());
            return false;
        }
    }

    /**
     * Convert a C++ identifier to a URL fragment following Doxygen conventions.
     *
     * @param cppId the identifier to convert (not null)
     * @return the filename
     */
    private static String escape(String cppId) {
        builder.setLength(0);

        for (char ch : cppId.toCharArray()) {
            if (Character.isUpperCase(ch)) {
                if (builder.length() > 0) {
                    builder.append("_");
                }
                builder.append(Character.toLowerCase(ch));
            } else {
                builder.append(ch);
            }
        }

        return builder.toString();
    }

    /**
     * Print a table of correspondences to the specified stream.
     *
     * @param stream the output stream to use (not null)
     */
    private static void printTableTo(PrintStream stream) throws IOException {
        // Print blank lines to simplify file comparisons:
        for (int i = 0; i < 14; ++i) {
            stream.println();
        }

        // Enumerate Jolt JNI's basic names and ref names:
        Set<Class> coreClasses
                = Jolt.listClasses("com.github.stephengold.joltjni");
        List<String> basicNames = new ArrayList<>();
        Set<String> refNames = new TreeSet<>();
        for (Class clas : coreClasses) {
            String name = clas.getSimpleName();
            if (name.endsWith("Ref") || name.endsWith("RefC")) {
                refNames.add(name);
            } else if (!name.isBlank()) {
                basicNames.add(name);
            }
        }
        Collections.sort(basicNames);

        // Enumerate Jolt JNI's read-only interfaces:
        Set<Class> roClasses
                = Jolt.listClasses("com.github.stephengold.joltjni.readonly");
        Set<String> roNames = new TreeSet<>();
        for (Class clas : roClasses) {
            String name = clas.getSimpleName();
            roNames.add(name);
        }

        for (String javaName : basicNames) {
            // Convert the Java class name to a hypothetical C++ identifier:
            String cppId = javaName
                    .replaceAll("\\bAa", "AA")
                    .replaceAll("Dof", "DOF")
                    .replaceAll("Id", "ID")
                    .replaceAll("Tv$", "TV")
                    .replaceAll("Wv$", "WV");

            // Access the Doxygen site for Jolt Physics via HTTPS:
            String lcFilename = escape(cppId);
            String classUrlString = String.format(
                    "https://jrouwe.github.io/JoltPhysics/class_%s.html",
                    lcFilename);
            URL classUrl = new URL(classUrlString);
            boolean classExists = doesPageExist(classUrl);
            if (classExists) {
                stream.printf("%n|{url-jolt}%s.html[JPH::%s]%n",
                        lcFilename, cppId);

            } else {
                String structUrlString = String.format(
                        "https://jrouwe.github.io/JoltPhysics/struct_%s.html",
                        lcFilename);
                URL structUrl = new URL(structUrlString);
                boolean structExists = doesPageExist(structUrl);
                if (structExists) {
                    stream.printf("%n|{url-jolt-struct}%s.html[JPH::%s]%n",
                            lcFilename, cppId);

                } else { // not a native class or struct
                    continue;
                }
            }

            stream.printf("|{url-api}/%s.html[%s]%n", javaName, javaName);

            String ro1 = "Const" + javaName;
            if (roNames.contains(ro1)) {
                stream.printf(" {url-api}/readonly/%s.html[%s]%n", ro1, ro1);
            }

            String ro2 = javaName + "Arg";
            if (roNames.contains(ro2)) {
                stream.printf(" {url-api}/readonly/%s.html[%s]%n", ro2, ro2);
            }

            String ref = javaName + "Ref";
            if (refNames.contains(ref)) {
                stream.printf(" {url-api}/%s.html[%s]%n", ref, ref);
            }

            String refc = javaName + "RefC";
            if (refNames.contains(refc)) {
                stream.printf(" {url-api}/%s.html[%s]%n", refc, refc);
            }
        }

        stream.println();
        stream.println("|===");
    }
}
