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
package testjoltjni.junit;

import com.github.stephengold.joltjni.BodyCreationSettings;
import com.github.stephengold.joltjni.Quat;
import com.github.stephengold.joltjni.RVec3;
import com.github.stephengold.joltjni.StaticCompoundShapeSettings;
import com.github.stephengold.joltjni.enumerate.EMotionType;
import com.github.stephengold.joltjni.vhacd.ConvexHull;
import com.github.stephengold.joltjni.vhacd.Decomposer;
import com.github.stephengold.joltjni.vhacd.Parameters;
import com.github.stephengold.joltjni.vhacd.ProgressListener;
import java.util.Collection;
import org.junit.Assert;
import org.junit.Test;
import testjoltjni.TestUtils;
import testjoltjni.app.samples.Layers;

/**
 * Automated JUnit4 tests for V-HACD.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class Test009 {
    // *************************************************************************
    // constants

    /**
     * vertex locations for an L-shaped mesh
     */
    final private static float[] locationArray = {
        0f, 0f, 0f,
        2f, 0f, 0f,
        2f, 1f, 0f,
        1f, 1f, 0f,
        1f, 3f, 0f,
        0f, 3f, 0f,
        0f, 0f, 1f,
        2f, 0f, 1f,
        2f, 1f, 1f,
        1f, 1f, 1f,
        1f, 3f, 1f,
        0f, 3f, 1f
    };

    /**
     * vertex indices for an L-shaped mesh of triangles
     */
    final private static int[] indexArray = {
        0, 1, 7, 0, 7, 6,
        0, 6, 11, 0, 11, 5,
        4, 5, 11, 4, 11, 10,
        3, 4, 10, 3, 10, 9,
        2, 3, 9, 2, 9, 8,
        1, 2, 8, 1, 8, 7,
        0, 3, 2, 0, 2, 1,
        0, 5, 4, 0, 4, 3,
        6, 8, 9, 6, 7, 8,
        6, 10, 11, 6, 9, 10
    };
    // *************************************************************************
    // fields

    /**
     * track the overall progress of the V-HACD algorithm (as a percentage)
     */
    private double progressPercent;
    // *************************************************************************
    // new methods exposed

    /**
     * Using V-HACD, construct a body with a compound collision shape that
     * approximates an L-shaped mesh.
     */
    @Test
    public void test009() {
        TestUtils.loadNativeLibrary();
        TestUtils.initializeNativeLibrary();

        ProgressListener listener = (double overallPercent, double sp,
                double op, String sn, String on) -> {
            Test009.this.progressPercent = overallPercent;
        };
        Decomposer decomposer = new Decomposer().addProgressListener(listener);

        // Decompose the triangle mesh into a 3 hulls:
        Parameters parameters = new Parameters();
        Collection<ConvexHull> hulls
                = decomposer.decompose(locationArray, indexArray, parameters);
        Assert.assertEquals(3, hulls.size());
        Assert.assertEquals(90., progressPercent, 0.);

        // Construct shape- and body settings:
        StaticCompoundShapeSettings compoundSettings
                = new StaticCompoundShapeSettings();
        compoundSettings.addHulls(hulls);
        BodyCreationSettings bcs = new BodyCreationSettings(
                compoundSettings, RVec3.sZero(), Quat.sIdentity(),
                EMotionType.Dynamic, Layers.MOVING);

        TestUtils.testClose(bcs, parameters, decomposer);
        System.gc();
    }
}
