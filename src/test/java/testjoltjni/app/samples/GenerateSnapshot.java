/*
Copyright (c) 2026 Stephen Gold

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
package testjoltjni.app.samples;

import com.github.stephengold.joltjni.Jolt;
import com.github.stephengold.joltjni.PhysicsSceneRef;
import com.github.stephengold.joltjni.StreamOut;
import com.github.stephengold.joltjni.StreamOutWrapper;
import com.github.stephengold.joltjni.std.OfStream;
import testjoltjni.MyString;
import testjoltjni.TestUtils;
import testjoltjni.app.samples.general.LoadSaveSceneTest;

/**
 * Console app to re-generate one of the snapshot files read by
 * LoadSnapshotTest.
 * <p>
 * There are 2 such files:<ul>
 * <li>"snapshot.bin", which contains single-precision location vectors and</li>
 * <li>"snapshot-dp.bin", which contains double-precision location vectors.</li>
 * </ul>
 *
 * @author Stephen Gold sgold@sonic.net
 */
final public class GenerateSnapshot {
    // *************************************************************************
    // new methods exposed

    /**
     * Main entry point for the GenerateSnapshot application.
     *
     * @param arguments array of command-line arguments (not {@code null})
     */
    public static void main(String... arguments) {
        TestUtils.loadNativeLibrary();
        TestUtils.initializeNativeLibrary();

        // Determine the filename:
        String fileName = "snapshot.bin";
        if (Jolt.isDoublePrecision()) {
            fileName = "snapshot-dp.bin";
        }

        // Open the snapshot file for output:
        int mode = StreamOutWrapper.out()
                | StreamOutWrapper.binary() | StreamOutWrapper.trunc();
        OfStream ofStream = new OfStream(fileName, mode);
        StreamOut streamOut = new StreamOutWrapper(ofStream);

        // Generate an interesting scene:
        PhysicsSceneRef scene = LoadSaveSceneTest.sCreateScene();

        // Write a snapshot of the scene:
        boolean saveShapes = true;
        boolean saveGroupFilter = true;
        scene.saveBinaryState(streamOut, saveShapes, saveGroupFilter);
        ofStream.closeStream();
        System.out.printf("Wrote the file %s%n", MyString.quote(fileName));
    }
}
