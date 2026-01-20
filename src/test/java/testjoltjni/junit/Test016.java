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
package testjoltjni.junit;

import java.nio.ByteBuffer;
import org.junit.Assert;
import org.junit.Test;
import testjoltjni.TestUtils;

/**
 * Verify functionality of the {@code TestUtils} class.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class Test016 {
    // *************************************************************************
    // new methods exposed

    /**
     * Verify that {@code TestUtils.loadFileAsBytes()} works.
     */
    @Test
    public void test016() {
        String fs = System.getProperty("file.separator");
        String path = "Assets" + fs + "Shaders" + fs + "DX"
                + fs + "FontPixelShader.hlsl";
        ByteBuffer buffer = TestUtils.loadFileAsBytes(path);

        Assert.assertNotNull(buffer);
        Assert.assertEquals(432, buffer.capacity());
        Assert.assertEquals((byte)'T', buffer.get(0));
        Assert.assertEquals((byte)'e', buffer.get(1));
        Assert.assertEquals(0x0A, buffer.get(431));
    }
}
