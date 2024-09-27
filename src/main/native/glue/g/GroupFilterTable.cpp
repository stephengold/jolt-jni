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
#include "Jolt/Jolt.h"
#include "Jolt/Physics/Collision/GroupFilterTable.h"
#include "auto/com_github_stephengold_joltjni_GroupFilterTable.h"
#include "auto/com_github_stephengold_joltjni_GroupFilterTableRef.h"
#include "glue/glue.h"

using namespace JPH;

IMPLEMENT_REF(GroupFilterTable,
  Java_com_github_stephengold_joltjni_GroupFilterTableRef_copy,
  Java_com_github_stephengold_joltjni_GroupFilterTableRef_createEmpty,
  Java_com_github_stephengold_joltjni_GroupFilterTableRef_free,
  Java_com_github_stephengold_joltjni_GroupFilterTableRef_getPtr)

/*
 * Class:     com_github_stephengold_joltjni_GroupFilterTable
 * Method:    createFilter
 * Signature: (I)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_GroupFilterTable_createFilter
  (JNIEnv *, jclass, jint numSubGroups) {
    GroupFilterTable * const pResult = new GroupFilterTable(numSubGroups);
    TRACE_NEW("GroupFilterTable", pResult)
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_GroupFilterTable
 * Method:    disableCollision
 * Signature: (JII)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_GroupFilterTable_disableCollision
  (JNIEnv *, jclass, jlong filterVa, jint subGroup1, jint subGroup2) {
    GroupFilterTable * const pFilter
            = reinterpret_cast<GroupFilterTable *> (filterVa);
    const CollisionGroup::SubGroupID id1
            = (CollisionGroup::SubGroupID) subGroup1;
    const CollisionGroup::SubGroupID id2
            = (CollisionGroup::SubGroupID) subGroup2;
    pFilter->DisableCollision(subGroup1, subGroup2);
}

/*
 * Class:     com_github_stephengold_joltjni_GroupFilterTable
 * Method:    enableCollision
 * Signature: (JII)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_GroupFilterTable_enableCollision
  (JNIEnv *, jclass, jlong filterVa, jint subGroup1, jint subGroup2) {
    GroupFilterTable * const pFilter
            = reinterpret_cast<GroupFilterTable *> (filterVa);
    const CollisionGroup::SubGroupID id1
            = (CollisionGroup::SubGroupID) subGroup1;
    const CollisionGroup::SubGroupID id2
            = (CollisionGroup::SubGroupID) subGroup2;
    pFilter->EnableCollision(subGroup1, subGroup2);
}

/*
 * Class:     com_github_stephengold_joltjni_GroupFilterTable
 * Method:    getRefCount
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_GroupFilterTable_getRefCount
  (JNIEnv *, jclass, jlong filterVa) {
    const GroupFilterTable * const pFilter
            = reinterpret_cast<GroupFilterTable *> (filterVa);
    const uint32 result = pFilter->GetRefCount();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_GroupFilterTable
 * Method:    setEmbedded
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_GroupFilterTable_setEmbedded
  (JNIEnv *, jclass, jlong filterVa) {
    GroupFilterTable * const pFilter
            = reinterpret_cast<GroupFilterTable *> (filterVa);
    pFilter->SetEmbedded();
}

/*
 * Class:     com_github_stephengold_joltjni_GroupFilterTable
 * Method:    toRef
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_GroupFilterTable_toRef
  (JNIEnv *, jclass, jlong filterVa) {
    GroupFilterTable * const pFilter
            = reinterpret_cast<GroupFilterTable *> (filterVa);
    Ref<GroupFilterTable> * const pResult = new Ref<GroupFilterTable>(pFilter);
    TRACE_NEW("Ref<GroupFilterTable>", pResult)
    return reinterpret_cast<jlong> (pResult);
}