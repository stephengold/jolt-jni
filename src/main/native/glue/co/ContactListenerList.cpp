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

/*
 * Author: Stephen Gold
 */
#include "Jolt/Jolt.h"
#include "Jolt/Physics/Collision/ContactListener.h"
#include "auto/com_github_stephengold_joltjni_ContactListenerList.h"
#include "glue/glue.h"

using namespace JPH;

class ContactListenerList : ContactListener {
    std::vector<ContactListener *> mSublisteners;
public:
    void Erase(jint startIndex, jint stopIndex) {
        std::vector<ContactListener *>::iterator origin = mSublisteners.begin();
        mSublisteners.erase(origin + startIndex, origin + stopIndex);
    }
    void OnContactAdded(const Body& inBody1, const Body& inBody2,
            const ContactManifold& inManifold,
            ContactSettings& ioSettings) override {
        for (std::vector<ContactListener *>::iterator i = mSublisteners.begin();
                i != mSublisteners.end(); ++i) {
            (*i)->OnContactAdded(inBody1, inBody2, inManifold, ioSettings);
        }
    }
    void OnContactPersisted(const Body& inBody1, const Body& inBody2,
            const ContactManifold& inManifold,
            ContactSettings& ioSettings) override {
        for (std::vector<ContactListener *>::iterator i = mSublisteners.begin();
                i != mSublisteners.end(); ++i) {
            (*i)->OnContactPersisted(inBody1, inBody2, inManifold, ioSettings);
        }
    }
    void OnContactRemoved(const SubShapeIDPair& pair) override {
        for (std::vector<ContactListener *>::iterator i = mSublisteners.begin();
                i != mSublisteners.end(); ++i) {
            (*i)->OnContactRemoved(pair);
        }
    }
    ValidateResult OnContactValidate(const Body& inBody1, const Body& inBody2,
            RVec3Arg inBaseOffset,
            const CollideShapeResult& inCollisionResult) override {
        jint maxResult = 0;
        for (std::vector<ContactListener *>::iterator i = mSublisteners.begin();
                i != mSublisteners.end(); ++i) {
            ValidateResult valid = (*i)->OnContactValidate(
                    inBody1, inBody2, inBaseOffset, inCollisionResult);
            jint ordinal = (jint) valid;
            maxResult = max(maxResult, ordinal);
        }
        return (ValidateResult) maxResult;
    }
    void PushBack(ContactListener *pListener) {
        mSublisteners.push_back(pListener);
    }
};

/*
 * Class:     com_github_stephengold_joltjni_ContactListenerList
 * Method:    createDefault
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_ContactListenerList_createDefault
  BODYOF_CREATE_DEFAULT(ContactListenerList)

/*
 * Class:     com_github_stephengold_joltjni_ContactListenerList
 * Method:    erase
 * Signature: (JII)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_ContactListenerList_erase
  (JNIEnv *, jclass, jlong listVa, jint startIndex, jint stopIndex) {
    ContactListenerList * const pList
            = reinterpret_cast<ContactListenerList *> (listVa);
    pList->Erase(startIndex, stopIndex);
}

/*
 * Class:     com_github_stephengold_joltjni_ContactListenerList
 * Method:    free
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_ContactListenerList_free
  BODYOF_FREE(ContactListenerList)

/*
 * Class:     com_github_stephengold_joltjni_ContactListenerList
 * Method:    pushBack
 * Signature: (JJ)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_ContactListenerList_pushBack
  (JNIEnv *, jclass, jlong listVa, jlong listenerVa) {
    ContactListenerList * const pList
            = reinterpret_cast<ContactListenerList *> (listVa);
    ContactListener * const pListener
            = reinterpret_cast<ContactListener *> (listenerVa);
    pList->PushBack(pListener);
}