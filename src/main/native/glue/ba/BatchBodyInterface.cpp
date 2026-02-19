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

/*
 * Author: xI-Mx-Ix
 */
#include "Jolt/Jolt.h"
#include "Jolt/Physics/Body/BodyInterface.h"
#include "Jolt/Physics/Collision/BroadPhase/BroadPhase.h"
#include "Jolt/Physics/Collision/Shape/Shape.h"
#include "Jolt/Physics/Collision/TransformedShape.h"

#include "auto/com_github_stephengold_joltjni_BatchBodyInterface.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_BatchBodyInterface
 * Method:    areActive
 * Signature: (JJILjava/nio/IntBuffer;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_BatchBodyInterface_areActive
  (JNIEnv *pEnv, jclass, jlong bodyInterfaceVa, jlong arrayVa, jint numBodies,
  jobject storeStatus) {
    const BodyInterface * const pInterface
            = reinterpret_cast<BodyInterface *> (bodyInterfaceVa);
    const BodyID * const pArray = reinterpret_cast<const BodyID *> (arrayVa);
    DIRECT_INT_BUFFER(pEnv, storeStatus, pStatus, capacityStatus);
    JPH_ASSERT(capacityStatus >= numBodies);
    for (int i = 0; i < numBodies; ++i) {
        pStatus[i] = pInterface->IsActive(pArray[i]) ? 1 : 0;
    }
}

/*
 * Class:     com_github_stephengold_joltjni_BatchBodyInterface
 * Method:    areAdded
 * Signature: (JJILjava/nio/IntBuffer;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_BatchBodyInterface_areAdded
  (JNIEnv *pEnv, jclass, jlong bodyInterfaceVa, jlong arrayVa, jint numBodies,
  jobject storeStatus) {
    const BodyInterface * const pInterface
            = reinterpret_cast<BodyInterface *> (bodyInterfaceVa);
    const BodyID * const pArray = reinterpret_cast<const BodyID *> (arrayVa);
    DIRECT_INT_BUFFER(pEnv, storeStatus, pStatus, capacityStatus);
    JPH_ASSERT(capacityStatus >= numBodies);
    for (int i = 0; i < numBodies; ++i) {
        pStatus[i] = pInterface->IsAdded(pArray[i]) ? 1 : 0;
    }
}

/*
 * Class:     com_github_stephengold_joltjni_BatchBodyInterface
 * Method:    areSensors
 * Signature: (JJILjava/nio/IntBuffer;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_BatchBodyInterface_areSensors
  (JNIEnv *pEnv, jclass, jlong bodyInterfaceVa, jlong arrayVa, jint numBodies,
  jobject storeStatus) {
    const BodyInterface * const pInterface
            = reinterpret_cast<BodyInterface *> (bodyInterfaceVa);
    const BodyID * const pArray = reinterpret_cast<const BodyID *> (arrayVa);
    DIRECT_INT_BUFFER(pEnv, storeStatus, pStatus, capacityStatus);
    JPH_ASSERT(capacityStatus >= numBodies);
    for (int i = 0; i < numBodies; ++i) {
        pStatus[i] = pInterface->IsSensor(pArray[i]) ? 1 : 0;
    }
}

/*
 * Class:     com_github_stephengold_joltjni_BatchBodyInterface
 * Method:    getAngularVelocities
 * Signature: (JJILjava/nio/FloatBuffer;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_BatchBodyInterface_getAngularVelocities
  (JNIEnv *pEnv, jclass, jlong bodyInterfaceVa, jlong arrayVa, jint numBodies,
  jobject storeVelocities) {
    const BodyInterface * const pInterface
            = reinterpret_cast<BodyInterface *> (bodyInterfaceVa);
    const BodyID * const pArray = reinterpret_cast<const BodyID *> (arrayVa);
    DIRECT_FLOAT_BUFFER(pEnv, storeVelocities, pOut, capacity);
    JPH_ASSERT(capacity >= numBodies * 3);
    for (int i = 0; i < numBodies; ++i) {
        const Vec3 result = pInterface->GetAngularVelocity(pArray[i]);
        pOut[i * 3 + 0] = result.GetX();
        pOut[i * 3 + 1] = result.GetY();
        pOut[i * 3 + 2] = result.GetZ();
    }
}

/*
 * Class:     com_github_stephengold_joltjni_BatchBodyInterface
 * Method:    getBodyTypes
 * Signature: (JJILjava/nio/IntBuffer;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_BatchBodyInterface_getBodyTypes
  (JNIEnv *pEnv, jclass, jlong bodyInterfaceVa, jlong arrayVa, jint numBodies,
  jobject storeTypes) {
    const BodyInterface * const pInterface
            = reinterpret_cast<BodyInterface *> (bodyInterfaceVa);
    const BodyID * const pArray = reinterpret_cast<const BodyID *> (arrayVa);
    DIRECT_INT_BUFFER(pEnv, storeTypes, pOut, capacity);
    JPH_ASSERT(capacity >= numBodies);
    for (int i = 0; i < numBodies; ++i) {
        pOut[i] = (jint) pInterface->GetBodyType(pArray[i]);
    }
}

/*
 * Class:     com_github_stephengold_joltjni_BatchBodyInterface
 * Method:    getCenterOfMassPositions
 * Signature: (JJILjava/nio/DoubleBuffer;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_BatchBodyInterface_getCenterOfMassPositions
  (JNIEnv *pEnv, jclass, jlong bodyInterfaceVa, jlong arrayVa, jint numBodies,
  jobject storePositions) {
    const BodyInterface * const pInterface
            = reinterpret_cast<BodyInterface *> (bodyInterfaceVa);
    const BodyID * const pArray = reinterpret_cast<const BodyID *> (arrayVa);
    DIRECT_DOUBLE_BUFFER(pEnv, storePositions, pOut, capacity);
    JPH_ASSERT(capacity >= numBodies * 3);
    for (int i = 0; i < numBodies; ++i) {
        const RVec3 result = pInterface->GetCenterOfMassPosition(pArray[i]);
        pOut[i * 3 + 0] = result.GetX();
        pOut[i * 3 + 1] = result.GetY();
        pOut[i * 3 + 2] = result.GetZ();
    }
}

/*
 * Class:     com_github_stephengold_joltjni_BatchBodyInterface
 * Method:    getCenterOfMassTransforms
 * Signature: (JJILjava/nio/DoubleBuffer;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_BatchBodyInterface_getCenterOfMassTransforms
  (JNIEnv *pEnv, jclass, jlong bodyInterfaceVa, jlong arrayVa, jint numBodies,
  jobject storeMatrices) {
    const BodyInterface * const pInterface
            = reinterpret_cast<BodyInterface *> (bodyInterfaceVa);
    const BodyID * const pArray = reinterpret_cast<const BodyID *> (arrayVa);
    DIRECT_DOUBLE_BUFFER(pEnv, storeMatrices, pOut, capacity);
    JPH_ASSERT(capacity >= numBodies * 16);
    for (int i = 0; i < numBodies; ++i) {
        RMat44 m = pInterface->GetCenterOfMassTransform(pArray[i]);
        int offset = i * 16;
        for (uint col = 0; col < 3; ++col) {
            Vec4 v = m.GetColumn4(col);
            pOut[offset + col * 4 + 0] = (jdouble) v.GetX();
            pOut[offset + col * 4 + 1] = (jdouble) v.GetY();
            pOut[offset + col * 4 + 2] = (jdouble) v.GetZ();
            pOut[offset + col * 4 + 3] = (jdouble) v.GetW();
        }
        RVec3 t = m.GetTranslation();
        pOut[offset + 12] = (jdouble) t.GetX();
        pOut[offset + 13] = (jdouble) t.GetY();
        pOut[offset + 14] = (jdouble) t.GetZ();
        pOut[offset + 15] = 1.0;
    }
}

/*
 * Class:     com_github_stephengold_joltjni_BatchBodyInterface
 * Method:    getFrictions
 * Signature: (JJILjava/nio/FloatBuffer;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_BatchBodyInterface_getFrictions
  (JNIEnv *pEnv, jclass, jlong bodyInterfaceVa, jlong arrayVa, jint numBodies,
  jobject storeFrictions) {
    const BodyInterface * const pInterface
            = reinterpret_cast<BodyInterface *> (bodyInterfaceVa);
    const BodyID * const pArray = reinterpret_cast<const BodyID *> (arrayVa);
    DIRECT_FLOAT_BUFFER(pEnv, storeFrictions, pOut, capacity);
    JPH_ASSERT(capacity >= numBodies);
    for (int i = 0; i < numBodies; ++i) {
        pOut[i] = pInterface->GetFriction(pArray[i]);
    }
}

/*
 * Class:     com_github_stephengold_joltjni_BatchBodyInterface
 * Method:    getGravityFactors
 * Signature: (JJILjava/nio/FloatBuffer;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_BatchBodyInterface_getGravityFactors
  (JNIEnv *pEnv, jclass, jlong bodyInterfaceVa, jlong arrayVa, jint numBodies,
  jobject storeFactors) {
    const BodyInterface * const pInterface
            = reinterpret_cast<BodyInterface *> (bodyInterfaceVa);
    const BodyID * const pArray = reinterpret_cast<const BodyID *> (arrayVa);
    DIRECT_FLOAT_BUFFER(pEnv, storeFactors, pOut, capacity);
    JPH_ASSERT(capacity >= numBodies);
    for (int i = 0; i < numBodies; ++i) {
        pOut[i] = pInterface->GetGravityFactor(pArray[i]);
    }
}

/*
 * Class:     com_github_stephengold_joltjni_BatchBodyInterface
 * Method:    getInverseInertias
 * Signature: (JJILjava/nio/FloatBuffer;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_BatchBodyInterface_getInverseInertias
  (JNIEnv *pEnv, jclass, jlong bodyInterfaceVa, jlong arrayVa, jint numBodies,
  jobject storeMatrices) {
    const BodyInterface * const pInterface
            = reinterpret_cast<BodyInterface *> (bodyInterfaceVa);
    const BodyID * const pArray = reinterpret_cast<const BodyID *> (arrayVa);
    DIRECT_FLOAT_BUFFER(pEnv, storeMatrices, pOut, capacity);
    JPH_ASSERT(capacity >= numBodies * 16);
    for (int i = 0; i < numBodies; ++i) {
        Mat44 m = pInterface->GetInverseInertia(pArray[i]);
        int offset = i * 16;
        for (uint col = 0; col < 4; ++col) {
            Vec4 v = m.GetColumn4(col);
            pOut[offset + col * 4 + 0] = v.GetX();
            pOut[offset + col * 4 + 1] = v.GetY();
            pOut[offset + col * 4 + 2] = v.GetZ();
            pOut[offset + col * 4 + 3] = v.GetW();
        }
    }
}

/*
 * Class:     com_github_stephengold_joltjni_BatchBodyInterface
 * Method:    getLinearVelocities
 * Signature: (JJILjava/nio/FloatBuffer;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_BatchBodyInterface_getLinearVelocities
  (JNIEnv *pEnv, jclass, jlong bodyInterfaceVa, jlong arrayVa, jint numBodies,
  jobject storeVelocities) {
    const BodyInterface * const pInterface
            = reinterpret_cast<BodyInterface *> (bodyInterfaceVa);
    const BodyID * const pArray = reinterpret_cast<const BodyID *> (arrayVa);
    DIRECT_FLOAT_BUFFER(pEnv, storeVelocities, pOut, capacity);
    JPH_ASSERT(capacity >= numBodies * 3);
    for (int i = 0; i < numBodies; ++i) {
        const Vec3 result = pInterface->GetLinearVelocity(pArray[i]);
        pOut[i * 3 + 0] = result.GetX();
        pOut[i * 3 + 1] = result.GetY();
        pOut[i * 3 + 2] = result.GetZ();
    }
}

/*
 * Class:     com_github_stephengold_joltjni_BatchBodyInterface
 * Method:    getMotionQualities
 * Signature: (JJILjava/nio/IntBuffer;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_BatchBodyInterface_getMotionQualities
  (JNIEnv *pEnv, jclass, jlong bodyInterfaceVa, jlong arrayVa, jint numBodies,
  jobject storeQualities) {
    const BodyInterface * const pInterface
            = reinterpret_cast<BodyInterface *> (bodyInterfaceVa);
    const BodyID * const pArray = reinterpret_cast<const BodyID *> (arrayVa);
    DIRECT_INT_BUFFER(pEnv, storeQualities, pOut, capacity);
    JPH_ASSERT(capacity >= numBodies);
    for (int i = 0; i < numBodies; ++i) {
        pOut[i] = (jint) pInterface->GetMotionQuality(pArray[i]);
    }
}

/*
 * Class:     com_github_stephengold_joltjni_BatchBodyInterface
 * Method:    getMotionTypes
 * Signature: (JJILjava/nio/IntBuffer;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_BatchBodyInterface_getMotionTypes
  (JNIEnv *pEnv, jclass, jlong bodyInterfaceVa, jlong arrayVa, jint numBodies,
  jobject storeTypes) {
    const BodyInterface * const pInterface
            = reinterpret_cast<BodyInterface *> (bodyInterfaceVa);
    const BodyID * const pArray = reinterpret_cast<const BodyID *> (arrayVa);
    DIRECT_INT_BUFFER(pEnv, storeTypes, pOut, capacity);
    JPH_ASSERT(capacity >= numBodies);
    for (int i = 0; i < numBodies; ++i) {
        pOut[i] = (jint) pInterface->GetMotionType(pArray[i]);
    }
}

/*
 * Class:     com_github_stephengold_joltjni_BatchBodyInterface
 * Method:    getObjectLayers
 * Signature: (JJILjava/nio/IntBuffer;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_BatchBodyInterface_getObjectLayers
  (JNIEnv *pEnv, jclass, jlong bodyInterfaceVa, jlong arrayVa, jint numBodies,
  jobject storeLayers) {
    const BodyInterface * const pInterface
            = reinterpret_cast<BodyInterface *> (bodyInterfaceVa);
    const BodyID * const pArray = reinterpret_cast<const BodyID *> (arrayVa);
    DIRECT_INT_BUFFER(pEnv, storeLayers, pOut, capacity);
    JPH_ASSERT(capacity >= numBodies);
    for (int i = 0; i < numBodies; ++i) {
        pOut[i] = (jint) pInterface->GetObjectLayer(pArray[i]);
    }
}

/*
 * Class:     com_github_stephengold_joltjni_BatchBodyInterface
 * Method:    getPositions
 * Signature: (JJILjava/nio/DoubleBuffer;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_BatchBodyInterface_getPositions
  (JNIEnv *pEnv, jclass, jlong bodyInterfaceVa, jlong arrayVa, jint numBodies,
  jobject storeLocations) {
    const BodyInterface * const pInterface
            = reinterpret_cast<BodyInterface *> (bodyInterfaceVa);
    const BodyID * const pArray = reinterpret_cast<const BodyID *> (arrayVa);
    DIRECT_DOUBLE_BUFFER(pEnv, storeLocations, pOut, capacity);
    JPH_ASSERT(capacity >= numBodies * 3);
    for (int i = 0; i < numBodies; ++i) {
        const RVec3 result = pInterface->GetPosition(pArray[i]);
        pOut[i * 3 + 0] = result.GetX();
        pOut[i * 3 + 1] = result.GetY();
        pOut[i * 3 + 2] = result.GetZ();
    }
}

/*
 * Class:     com_github_stephengold_joltjni_BatchBodyInterface
 * Method:    getRestitutions
 * Signature: (JJILjava/nio/FloatBuffer;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_BatchBodyInterface_getRestitutions
  (JNIEnv *pEnv, jclass, jlong bodyInterfaceVa, jlong arrayVa, jint numBodies,
  jobject storeRestitutions) {
    const BodyInterface * const pInterface
            = reinterpret_cast<BodyInterface *> (bodyInterfaceVa);
    const BodyID * const pArray = reinterpret_cast<const BodyID *> (arrayVa);
    DIRECT_FLOAT_BUFFER(pEnv, storeRestitutions, pOut, capacity);
    JPH_ASSERT(capacity >= numBodies);
    for (int i = 0; i < numBodies; ++i) {
        pOut[i] = pInterface->GetRestitution(pArray[i]);
    }
}

/*
 * Class:     com_github_stephengold_joltjni_BatchBodyInterface
 * Method:    getRotations
 * Signature: (JJILjava/nio/FloatBuffer;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_BatchBodyInterface_getRotations
  (JNIEnv *pEnv, jclass, jlong bodyInterfaceVa, jlong arrayVa, jint numBodies,
  jobject storeOrientations) {
    const BodyInterface * const pInterface
            = reinterpret_cast<BodyInterface *> (bodyInterfaceVa);
    const BodyID * const pArray = reinterpret_cast<const BodyID *> (arrayVa);
    DIRECT_FLOAT_BUFFER(pEnv, storeOrientations, pOut, capacity);
    JPH_ASSERT(capacity >= numBodies * 4);
    for (int i = 0; i < numBodies; ++i) {
        const Quat result = pInterface->GetRotation(pArray[i]);
        pOut[i * 4 + 0] = result.GetX();
        pOut[i * 4 + 1] = result.GetY();
        pOut[i * 4 + 2] = result.GetZ();
        pOut[i * 4 + 3] = result.GetW();
    }
}

/*
 * Class:     com_github_stephengold_joltjni_BatchBodyInterface
 * Method:    getShapes
 * Signature: (JJILjava/nio/LongBuffer;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_BatchBodyInterface_getShapes
  (JNIEnv *pEnv, jclass, jlong bodyInterfaceVa, jlong arrayVa, jint numBodies,
  jobject storeShapeVas) {
    const BodyInterface * const pInterface
            = reinterpret_cast<BodyInterface *> (bodyInterfaceVa);
    const BodyID * const pArray = reinterpret_cast<const BodyID *> (arrayVa);
    DIRECT_LONG_BUFFER(pEnv, storeShapeVas, pOut, capacity);
    JPH_ASSERT(capacity >= numBodies);
    for (int i = 0; i < numBodies; ++i) {
        ShapeRefC * const pResult = new ShapeRefC();
        TRACE_NEW("ShapeRefC", pResult)
        *pResult = pInterface->GetShape(pArray[i]);
        pOut[i] = reinterpret_cast<jlong> (pResult);
    }
}

/*
 * Class:     com_github_stephengold_joltjni_BatchBodyInterface
 * Method:    getTransformedShapes
 * Signature: (JJILjava/nio/LongBuffer;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_BatchBodyInterface_getTransformedShapes
  (JNIEnv *pEnv, jclass, jlong bodyInterfaceVa, jlong arrayVa, jint numBodies,
  jobject storeShapeVas) {
    const BodyInterface * const pInterface
            = reinterpret_cast<BodyInterface *> (bodyInterfaceVa);
    const BodyID * const pArray = reinterpret_cast<const BodyID *> (arrayVa);
    DIRECT_LONG_BUFFER(pEnv, storeShapeVas, pOut, capacity);
    JPH_ASSERT(capacity >= numBodies);
    for (int i = 0; i < numBodies; ++i) {
        TransformedShape * const pResult = new TransformedShape();
        TRACE_NEW("TransformedShape", pResult)
        *pResult = pInterface->GetTransformedShape(pArray[i]);
        pOut[i] = reinterpret_cast<jlong> (pResult);
    }
}

/*
 * Class:     com_github_stephengold_joltjni_BatchBodyInterface
 * Method:    getUserDatas
 * Signature: (JJILjava/nio/LongBuffer;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_BatchBodyInterface_getUserDatas
  (JNIEnv *pEnv, jclass, jlong bodyInterfaceVa, jlong arrayVa, jint numBodies,
  jobject storeData) {
    const BodyInterface * const pInterface
            = reinterpret_cast<BodyInterface *> (bodyInterfaceVa);
    const BodyID * const pArray = reinterpret_cast<const BodyID *> (arrayVa);
    DIRECT_LONG_BUFFER(pEnv, storeData, pOut, capacity);
    JPH_ASSERT(capacity >= numBodies);
    for (int i = 0; i < numBodies; ++i) {
        pOut[i] = pInterface->GetUserData(pArray[i]);
    }
}

/*
 * Class:     com_github_stephengold_joltjni_BatchBodyInterface
 * Method:    getUseManifoldReductions
 * Signature: (JJILjava/nio/IntBuffer;)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_BatchBodyInterface_getUseManifoldReductions
  (JNIEnv *pEnv, jclass, jlong bodyInterfaceVa, jlong arrayVa, jint numBodies,
  jobject storeStatus) {
    const BodyInterface * const pInterface
            = reinterpret_cast<BodyInterface *> (bodyInterfaceVa);
    const BodyID * const pArray = reinterpret_cast<const BodyID *> (arrayVa);
    DIRECT_INT_BUFFER(pEnv, storeStatus, pOut, capacity);
    JPH_ASSERT(capacity >= numBodies);
    for (int i = 0; i < numBodies; ++i) {
        pOut[i] = pInterface->GetUseManifoldReduction(pArray[i]) ? 1 : 0;
    }
}