/*
Copyright (c) 2024 Stephen Gold

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
package testjoltjni.app.testframework;

import java.io.*;

/**
 * A Java translation of the Jolt Physics {@code ReadData} utility.
 * <p>
 * Compare with the original by Jorrit Rouwe at
 * https://github.com/jrouwe/JoltPhysics/blob/master/TestFramework/Utils/ReadData.cpp
 */
public class ReadData {

    /**
     * Read the contents of the named binary file.
     *
     * @param inFileName the name of the file to read (not null)
     * @return a new array containing file content
     */
    public static float[] ReadData(String inFileName) {
        int numFloats = 0;
        try (InputStream byteStream = new FileInputStream(inFileName); DataInputStream dataStream = new DataInputStream(byteStream)) {
            while (true) {
                dataStream.readFloat();
                ++numFloats;
            }
        } catch (IOException exception) {
            if (!(exception instanceof EOFException)) {
                throw new RuntimeException(exception);
            }
        }
        float[] data = new float[numFloats];

        int floatIndex = 0;
        try (InputStream byteStream = new FileInputStream(inFileName); DataInputStream dataStream = new DataInputStream(byteStream)) {
            while (true) {
                data[floatIndex] = dataStream.readFloat();
                ++floatIndex;
            }
        } catch (IOException exception) {
            if (!(exception instanceof EOFException)) {
                throw new RuntimeException(exception);
            }
        }

        return data;
    }
}
