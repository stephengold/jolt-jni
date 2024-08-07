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

/*
 * Author: Stephen Gold
 */
#include <Jolt/Jolt.h>
#include <Jolt/Physics/Constraints/ContactConstraintManager.h>
#include "auto/com_github_stephengold_joltjni_CombineFunction.h"

using namespace JPH;
using CombineFunction = float (*)(const Body&, const SubShapeID&, const Body&, const SubShapeID&);

float averageFriction(const Body &inBody1, const SubShapeID&, const Body &inBody2, const SubShapeID&) {
    return (inBody1.GetFriction() + inBody2.GetFriction()) / 2.f;
}
float averageRestitution(const Body &inBody1, const SubShapeID&, const Body &inBody2, const SubShapeID&) {
   return (inBody1.GetRestitution() + inBody2.GetRestitution()) / 2.f;
}

float geometricMeanFriction(const Body &inBody1, const SubShapeID&, const Body &inBody2, const SubShapeID&) {
    return sqrt(inBody1.GetFriction() * inBody2.GetFriction());
}
float geometricMeanRestitution(const Body &inBody1, const SubShapeID&, const Body &inBody2, const SubShapeID&) {
   return sqrt(inBody1.GetRestitution() * inBody2.GetRestitution());
}

float harmonicMeanFriction(const Body &inBody1, const SubShapeID&, const Body &inBody2, const SubShapeID&) {
    float f1 = inBody1.GetFriction();
    float f2 = inBody2.GetFriction();
    return 2.f * f1 * f2 /(f1 + f2);
}
float harmonicMeanRestitution(const Body &inBody1, const SubShapeID&, const Body &inBody2, const SubShapeID&) {
    float f1 = inBody1.GetRestitution();
    float f2 = inBody2.GetRestitution();
    return 2.f * f1 * f2 /(f1 + f2);
}

float maxFriction(const Body &inBody1, const SubShapeID&, const Body &inBody2, const SubShapeID&) {
   return max(inBody1.GetFriction(), inBody2.GetFriction());
}
float maxRestitution(const Body &inBody1, const SubShapeID&, const Body &inBody2, const SubShapeID&) {
   return max(inBody1.GetRestitution(), inBody2.GetRestitution());
}

float minFriction(const Body &inBody1, const SubShapeID&, const Body &inBody2, const SubShapeID&) {
   return min(inBody1.GetFriction(), inBody2.GetFriction());
}
float minRestitution(const Body &inBody1, const SubShapeID&, const Body &inBody2, const SubShapeID&) {
   return min(inBody1.GetRestitution(), inBody2.GetRestitution());
}

float productFriction(const Body &inBody1, const SubShapeID&, const Body &inBody2, const SubShapeID&) {
   return inBody1.GetFriction() * inBody2.GetFriction();
}
float productRestitution(const Body &inBody1, const SubShapeID&, const Body &inBody2, const SubShapeID&) {
   return inBody1.GetRestitution() * inBody2.GetRestitution();
}

float rootMeanSquareFriction(const Body &inBody1, const SubShapeID&, const Body &inBody2, const SubShapeID&) {
    float f1 = inBody1.GetFriction();
    float f2 = inBody2.GetFriction();
    return sqrt((f1 * f1 + f2 * f2)/ 2.f);
}
float rootMeanSquareRestitution(const Body &inBody1, const SubShapeID&, const Body &inBody2, const SubShapeID&) {
    float f1 = inBody1.GetRestitution();
    float f2 = inBody2.GetRestitution();
    return sqrt((f1 * f1 + f2 * f2)/ 2.f);
}

float sumFriction(const Body &inBody1, const SubShapeID&, const Body &inBody2, const SubShapeID&) {
   return inBody1.GetFriction() + inBody2.GetFriction();
}
float sumRestitution(const Body &inBody1, const SubShapeID&, const Body &inBody2, const SubShapeID&) {
   return inBody1.GetRestitution() + inBody2.GetRestitution();
}

/*
 * Class:     com_github_stephengold_joltjni_CombineFunction
 * Method:    getAverage
 * Signature: (Z)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_CombineFunction_getAverage
  (JNIEnv *, jclass, jboolean friction) {
    if (friction) {
        return reinterpret_cast<jlong> (&averageFriction);
    } else {
        return reinterpret_cast<jlong> (&averageRestitution);
    }
}

/*
 * Class:     com_github_stephengold_joltjni_CombineFunction
 * Method:    getGeometricMean
 * Signature: (Z)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_CombineFunction_getGeometricMean
  (JNIEnv *, jclass, jboolean friction) {
    if (friction) {
        return reinterpret_cast<jlong> (&geometricMeanFriction);
    } else {
        return reinterpret_cast<jlong> (&geometricMeanRestitution);
    }
}

/*
 * Class:     com_github_stephengold_joltjni_CombineFunction
 * Method:    getHarmonicMean
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_CombineFunction_getHarmonicMean
  (JNIEnv *, jclass, jboolean friction) {
    if (friction) {
        return reinterpret_cast<jlong> (&harmonicMeanFriction);
    } else {
        return reinterpret_cast<jlong> (&harmonicMeanRestitution);
    }
}

/*
 * Class:     com_github_stephengold_joltjni_CombineFunction
 * Method:    getMax
 * Signature: (Z)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_CombineFunction_getMax
  (JNIEnv *, jclass, jboolean friction) {
    if (friction) {
        return reinterpret_cast<jlong> (&maxFriction);
    } else {
        return reinterpret_cast<jlong> (&maxRestitution);
    }
}

/*
 * Class:     com_github_stephengold_joltjni_CombineFunction
 * Method:    getMin
 * Signature: (Z)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_CombineFunction_getMin
  (JNIEnv *, jclass, jboolean friction) {
    if (friction) {
        return reinterpret_cast<jlong> (&minFriction);
    } else {
        return reinterpret_cast<jlong> (&minRestitution);
    }
}

/*
 * Class:     com_github_stephengold_joltjni_CombineFunction
 * Method:    getProduct
 * Signature: (Z)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_CombineFunction_getProduct
  (JNIEnv *, jclass, jboolean friction) {
    if (friction) {
        return reinterpret_cast<jlong> (&productFriction);
    } else {
        return reinterpret_cast<jlong> (&productRestitution);
    }
}

/*
 * Class:     com_github_stephengold_joltjni_CombineFunction
 * Method:    getRootMeanSquare
 * Signature: (Z)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_CombineFunction_getRootMeanSquare
  (JNIEnv *, jclass, jboolean friction) {
    if (friction) {
        return reinterpret_cast<jlong> (&rootMeanSquareFriction);
    } else {
        return reinterpret_cast<jlong> (&rootMeanSquareRestitution);
    }
}

/*
 * Class:     com_github_stephengold_joltjni_CombineFunction
 * Method:    getSum
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_CombineFunction_getSum
  (JNIEnv *, jclass, jboolean friction) {
    if (friction) {
        return reinterpret_cast<jlong> (&sumFriction);
    } else {
        return reinterpret_cast<jlong> (&sumRestitution);
    }
}