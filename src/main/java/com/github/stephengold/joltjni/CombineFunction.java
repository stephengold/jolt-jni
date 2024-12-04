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
package com.github.stephengold.joltjni;

/**
 * Combine the restitution or friction of 2 bodies. (native type:
 * ContactConstraintManager::CombineFunction)
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class CombineFunction extends JoltPhysicsObject {
    // *************************************************************************
    // constructors

    /**
     * Instantiate a function with the specified virtual address assigned but
     * not owned.
     *
     * @param functionVa the virtual address to assign (not zero)
     */
    CombineFunction(long functionVa) {
        setVirtualAddress(functionVa);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Create a function that calculates {@code (f1 + f2)/2}.
     *
     * @param friction {@code true} to combine frictions, {@code false} to
     * combine restitutions
     * @return a new object
     */
    public static CombineFunction average(boolean friction) {
        long functionVa = getAverage(friction);
        CombineFunction result = new CombineFunction(functionVa);

        return result;
    }

    /**
     * Create a function that calculates {@code sqrt(f1 * f2)}.
     *
     * @param friction {@code true} to combine frictions, {@code false} to
     * combine restitutions
     * @return a new object
     */
    public static CombineFunction geometricMean(boolean friction) {
        long functionVa = getGeometricMean(friction);
        CombineFunction result = new CombineFunction(functionVa);

        return result;
    }

    /**
     * Create a function that calculates {@code 2 * f1 * f2/(f1 + f2)}.
     *
     * @param friction {@code true} to combine frictions, {@code false} to
     * combine restitutions
     * @return a new object
     */
    public static CombineFunction harmonicMean(boolean friction) {
        long functionVa = getHarmonicMean(friction);
        CombineFunction result = new CombineFunction(functionVa);

        return result;
    }

    /**
     * Create a function that calculates {@code max(f1, f2)}.
     *
     * @param friction {@code true} to combine frictions, {@code false} to
     * combine restitutions
     * @return a new object
     */
    public static CombineFunction max(boolean friction) {
        long functionVa = getMax(friction);
        CombineFunction result = new CombineFunction(functionVa);

        return result;
    }

    /**
     * Create a function that calculates {@code min(f1, f2)}.
     *
     * @param friction {@code true} to combine frictions, {@code false} to
     * combine restitutions
     * @return a new object
     */
    public static CombineFunction min(boolean friction) {
        long functionVa = getMin(friction);
        CombineFunction result = new CombineFunction(functionVa);

        return result;
    }

    /**
     * Create a function that calculates {@code f1 * f2}.
     *
     * @param friction {@code true} to combine frictions, {@code false} to
     * combine restitutions
     * @return a new object
     */
    public static CombineFunction product(boolean friction) {
        long functionVa = getProduct(friction);
        CombineFunction result = new CombineFunction(functionVa);

        return result;
    }

    /**
     * Create a function that calculates {@code sqrt((f1*f1 + f2*f2)/2)}.
     *
     * @param friction {@code true} to combine frictions, {@code false} to
     * combine restitutions
     * @return a new object
     */
    public static CombineFunction rootMeanSquare(boolean friction) {
        long functionVa = getRootMeanSquare(friction);
        CombineFunction result = new CombineFunction(functionVa);

        return result;
    }

    /**
     * Create a function that calculates {@code f1 + f2}.
     *
     * @param friction {@code true} to combine frictions, {@code false} to
     * combine restitutions
     * @return a new object
     */
    public static CombineFunction sum(boolean friction) {
        long functionVa = getSum(friction);
        CombineFunction result = new CombineFunction(functionVa);

        return result;
    }
    // *************************************************************************
    // native private methods

    native private static long getAverage(boolean friction);

    native private static long getGeometricMean(boolean friction);

    native private static long getHarmonicMean(boolean friction);

    native private static long getMax(boolean friction);

    native private static long getMin(boolean friction);

    native private static long getProduct(boolean friction);

    native private static long getRootMeanSquare(boolean friction);

    native private static long getSum(boolean friction);
}
