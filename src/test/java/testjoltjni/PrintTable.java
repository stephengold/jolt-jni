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
import java.io.OutputStream;
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
            OutputStream fileStream = new FileOutputStream("lexicon.adoc");
            boolean autoFlush = true;
            PrintStream stream = new PrintStream(fileStream, autoFlush);
            printTableTo(stream);

        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
    // *************************************************************************
    // private methods

    /**
     * Test whether a specific webpage exists in the Doxygen site for Jolt
     * Physics.
     *
     * @param section the section of the site to search in ("class",
     * "namespace", or "struct")
     * @param cppId the top-level C++ identifier to search for (not null)
     * @return {@code true} if the URL returns {@code HTTP_OK}, otherwise
     * {@code false}
     * @throws IOException from HttpURLConnection
     */
    private static boolean doesIdExist(String section, String cppId)
            throws IOException {
        String urlFragment = escape(cppId);
        String doxygenUrlString = String.format(
                "https://jrouwe.github.io/JoltPhysics/%s_%s.html",
                section, urlFragment);
        URL doxygenUrl = new URL(doxygenUrlString);
        boolean result = doesPageExist(doxygenUrl);

        return result;
    }

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

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            return true;

        } else {
            //System.out.println(
            //        "Got " + responseCode + " from " + url.toString());
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

        // Enumerate Jolt JNI's basic classes and Ref/RefC classes:
        List<String> basicJavaClasses = new ArrayList<>();
        Set<String> refClasses = new TreeSet<>();
        {
            String corePackage = "com.github.stephengold.joltjni";
            Set<Class> coreClasses = Jolt.listClasses(corePackage);
            for (Class coreClass : coreClasses) {
                String simpleName = coreClass.getSimpleName();
                if (simpleName.endsWith("Ref") || simpleName.endsWith("RefC")) {
                    refClasses.add(simpleName);
                } else if (!simpleName.isBlank()) {
                    basicJavaClasses.add(simpleName);
                }
            }
        }

        // Enumerate Jolt JNI's read-only interfaces:
        Set<String> readOnlyNames = new TreeSet<>();
        {
            String readOnlyPackage = "com.github.stephengold.joltjni.readonly";
            Set<Class> readOnlyClasses = Jolt.listClasses(readOnlyPackage);
            for (Class readOnlyClass : readOnlyClasses) {
                String simpleName = readOnlyClass.getSimpleName();
                readOnlyNames.add(simpleName);
            }
        }

        Collections.sort(basicJavaClasses);
        for (String basicJavaClass : basicJavaClasses) {
            // Convert the Java class name to a hypothetical C++ identifier:
            String cppId = basicJavaClass
                    .replaceAll("Aa", "AA")
                    .replaceAll("Bcs", "BCS")
                    .replaceAll("Dof", "DOF")
                    .replaceAll("Id", "ID")
                    .replaceAll("Tv", "TV")
                    .replaceAll("Wv", "WV");

            // Access the Doxygen site for Jolt Physics via HTTPS:
            if (doesIdExist("class", cppId)) {
                stream.printf("%n|{url-jolt}%s.html[JPH::%s]%n",
                        escape(cppId), cppId);

            } else if (doesIdExist("struct", cppId)) {
                stream.printf("%n|{url-jolt-struct}%s.html[JPH::%s]%n",
                        escape(cppId), cppId);

            } else if (doesIdExist("namespace", cppId)) {
                stream.printf("%n|{url-jolt-namespace}%s.html[%s::]%n",
                        escape(cppId), cppId);

            } else { // not a top-level class, struct, or namespace in Jolt
                System.out.println(" no native ID found for " + basicJavaClass);
                continue; // skip to the next Java class
            }

            stream.printf("|{url-api}/%s.html[%s]",
                    basicJavaClass, basicJavaClass);

            String ro1 = "Const" + basicJavaClass;
            if (readOnlyNames.contains(ro1)) {
                stream.printf(" +%n {url-api}/readonly/%s.html[%s]", ro1, ro1);
            }

            String ro2 = basicJavaClass + "Arg";
            if (readOnlyNames.contains(ro2)) {
                stream.printf(" +%n {url-api}/readonly/%s.html[%s]", ro2, ro2);
            }

            String ref = basicJavaClass + "Ref";
            if (refClasses.contains(ref)) {
                stream.printf(" +%n {url-api}/%s.html[%s]", ref, ref);
            }

            String refc = basicJavaClass + "RefC";
            if (refClasses.contains(refc)) {
                stream.printf(" +%n {url-api}/%s.html[%s]", refc, refc);
            }
            stream.println();
        }

        stream.println();
        stream.println("|===");
    }
}
