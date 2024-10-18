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
 * Protect shared data from being accessed simultaneously by multiple threads.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class Mutex extends JoltPhysicsObject {
    // *************************************************************************
    // constructors

    /**
     * Instantiate a mutex.
     */
    public Mutex() {
        long mutexVa = createDefault();
        setVirtualAddress(mutexVa, () -> free(mutexVa));
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Acquire the mutex.
     */
    public void lock() {
        long mutexVa = va();
        lock(mutexVa);
    }

    /**
     * Attempt to acquire the mutex and immediately return {@code false} if it's
     * unavailable.
     *
     * @return {@code true} if acquired, otherwise {@code false}
     */
    public boolean tryLock() {
        long mutexVa = va();
        boolean result = tryLock(mutexVa);

        return result;
    }

    /**
     * Release the mutex.
     */
    public void unlock() {
        long mutexVa = va();
        unlock(mutexVa);
    }
    // *************************************************************************
    // native private methods

    native private static long createDefault();

    native private static void free(long mutexVa);

    native private static void lock(long mutexVa);

    native private static boolean tryLock(long mutexVa);

    native private static void unlock(long mutexVa);
}
