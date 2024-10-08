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
package com.github.stephengold.joltjni.lambda;

/**
 * Represent a function that accepts 3 arguments and produces a result.
 *
 * @author Stephen Gold sgold@sonic.net
 * @see java.util.function.BiFunction
 *
 * @param <T> the type of the first argument
 * @param <U> the type of the 2nd argument
 * @param <V> the type of the 3rd argument
 * @param <R> the return type
 */
@FunctionalInterface
public interface TriFunction<T, U, V, R> {
    // *************************************************************************
    // new methods exposed

    /**
     * Apply the function to the specified arguments.
     *
     * @param t the first argument
     * @param u the 2nd argument
     * @param v the 3rd argument
     * @return the return value
     */
    R apply(T t, U u, V v);
}
