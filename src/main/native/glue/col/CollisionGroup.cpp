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
#include "Jolt/Physics/Collision/CollisionGroup.h"
#include "auto/com_github_stephengold_joltjni_CollisionGroup.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_CollisionGroup
 * Method:    createDefault
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_CollisionGroup_createDefault
  (JNIEnv *, jclass) {
    CollisionGroup * const pResult = new CollisionGroup();
    TRACE_NEW("CollisionGroup", pResult)
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_CollisionGroup
 * Method:    createGroup
 * Signature: (JII)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_CollisionGroup_createGroup
  (JNIEnv *, jclass, jlong filterVa, jint groupId, jint subGroupId) {
    const GroupFilter * const pFilter
            = reinterpret_cast<GroupFilter *> (filterVa);
    const CollisionGroup::GroupID gid = (CollisionGroup::GroupID) groupId;
    const CollisionGroup::SubGroupID sgid
            = (CollisionGroup::SubGroupID) subGroupId;
    CollisionGroup * const pResult = new CollisionGroup(pFilter, gid, sgid);
    TRACE_NEW("CollisionGroup", pResult)
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_CollisionGroup
 * Method:    free
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_CollisionGroup_free
  (JNIEnv *, jclass, jlong groupVa) {
    CollisionGroup * const pGroup
            = reinterpret_cast<CollisionGroup *> (groupVa);
    TRACE_DELETE("CollisionGroup", pGroup)
    delete pGroup;
}

/*
 * Class:     com_github_stephengold_joltjni_CollisionGroup
 * Method:    getGroupFilter
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_CollisionGroup_getGroupFilter
  (JNIEnv *, jclass, jlong groupVa) {
    CollisionGroup * const pGroup
            = reinterpret_cast<CollisionGroup *> (groupVa);
    const GroupFilter * const pResult = pGroup->GetGroupFilter();
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_CollisionGroup
 * Method:    getGroupId
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_CollisionGroup_getGroupId
  (JNIEnv *, jclass, jlong groupVa) {
    const CollisionGroup * const pGroup
            = reinterpret_cast<CollisionGroup *> (groupVa);
    const CollisionGroup::GroupID result = pGroup->GetGroupID();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_CollisionGroup
 * Method:    getSubGroupId
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_CollisionGroup_getSubGroupId
  (JNIEnv *, jclass, jlong groupVa) {
    const CollisionGroup * const pGroup
            = reinterpret_cast<CollisionGroup *> (groupVa);
    const CollisionGroup::SubGroupID result = pGroup->GetSubGroupID();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_CollisionGroup
 * Method:    setGroupFilter
 * Signature: (JJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_CollisionGroup_setGroupFilter
  (JNIEnv *, jclass, jlong groupVa, jlong filterVa) {
    CollisionGroup * const pGroup
            = reinterpret_cast<CollisionGroup *> (groupVa);
    const GroupFilter * const pFilter
            = reinterpret_cast<GroupFilter *> (filterVa);
    pGroup->SetGroupFilter(pFilter);
}

/*
 * Class:     com_github_stephengold_joltjni_CollisionGroup
 * Method:    setGroupId
 * Signature: (JI)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_CollisionGroup_setGroupId
  (JNIEnv *, jclass, jlong groupVa, jint groupId) {
    CollisionGroup * const pGroup
            = reinterpret_cast<CollisionGroup *> (groupVa);
    const CollisionGroup::GroupID gid = (CollisionGroup::GroupID) groupId;
    pGroup->SetGroupID(gid);
}

/*
 * Class:     com_github_stephengold_joltjni_CollisionGroup
 * Method:    setSubGroupId
 * Signature: (JI)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_CollisionGroup_setSubGroupId
  (JNIEnv *, jclass, jlong groupVa, jint subGroupId) {
    CollisionGroup * const pGroup
            = reinterpret_cast<CollisionGroup *> (groupVa);
    const CollisionGroup::SubGroupID sgid
            = (CollisionGroup::SubGroupID) subGroupId;
    pGroup->SetSubGroupID(sgid);
}