/*
Copyright (c) 2013-2026 Stephen Gold

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

import java.util.Locale;

/**
 * Utility methods for char sequences, strings, and collections of strings.
 *
 * @author Stephen Gold sgold@sonic.net
 */
final public class MyString {
    // *************************************************************************
    // constructors

    /**
     * A private constructor to inhibit instantiation of this class.
     */
    private MyString() {
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Replace all tab, quote, newline, and backslash characters in the
     * specified text with escape sequences.
     *
     * @param unescaped the input text to escape (not {@code null})
     * @return the escaped text (not {@code null})
     */
    public static String escape(CharSequence unescaped) {
        int length = unescaped.length();
        StringBuilder result = new StringBuilder(length + 10);

        for (int i = 0; i < length; ++i) {
            char ch = unescaped.charAt(i);
            switch (ch) {
                case '\n':
                    result.append("\\n");
                    break;

                case '\t':
                    result.append("\\t");
                    break;

                case '"':
                    result.append("\\\"");
                    break;

                case '\\':
                    result.append("\\\\");
                    break;

                default:
                    result.append(ch);
                    break;
            }
        }

        return result.toString();
    }

    /**
     * Convert the first character of the specified text to lower case.
     *
     * @param input the input text to convert (not {@code null})
     * @return the converted text (not {@code null})
     */
    public static String firstToLower(String input) {
        String result = input;
        if (!input.isEmpty()) {
            String first = input.substring(0, 1);
            first = first.toLowerCase(Locale.ROOT);
            String rest = input.substring(1);
            result = first + rest;
        }

        return result;
    }

    /**
     * Enclose the specified text in quotation marks and escape all tab, quote,
     * newline, and backslash characters.
     *
     * @param text the input text to quote
     * @return the quoted text, or "null" if the input was {@code null}
     */
    public static String quote(CharSequence text) {
        String result;
        if (text == null) {
            result = "null";
        } else {
            result = "\"" + escape(text) + "\"";
        }

        return result;
    }
}
