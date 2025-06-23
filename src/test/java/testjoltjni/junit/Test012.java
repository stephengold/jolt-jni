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

import com.github.stephengold.joltjni.BcsResult;
import com.github.stephengold.joltjni.BodyCreationSettings;
import com.github.stephengold.joltjni.BoxShapeSettings;
import com.github.stephengold.joltjni.CapsuleShapeSettings;
import com.github.stephengold.joltjni.Color;
import com.github.stephengold.joltjni.ConstraintSettingsRef;
import com.github.stephengold.joltjni.ConvexHullShapeSettings;
import com.github.stephengold.joltjni.CylinderShapeSettings;
import com.github.stephengold.joltjni.Edge;
import com.github.stephengold.joltjni.EmptyShapeSettings;
import com.github.stephengold.joltjni.GroupFilterRef;
import com.github.stephengold.joltjni.GroupFilterResult;
import com.github.stephengold.joltjni.GroupFilterTable;
import com.github.stephengold.joltjni.GroupFilterTableRef;
import com.github.stephengold.joltjni.HeightFieldShapeSettings;
import com.github.stephengold.joltjni.MeshShapeSettings;
import com.github.stephengold.joltjni.MutableCompoundShapeSettings;
import com.github.stephengold.joltjni.ObjectStreamIn;
import com.github.stephengold.joltjni.ObjectStreamOut;
import com.github.stephengold.joltjni.OffsetCenterOfMassShapeSettings;
import com.github.stephengold.joltjni.PhysicsMaterial;
import com.github.stephengold.joltjni.PhysicsMaterialRef;
import com.github.stephengold.joltjni.PhysicsMaterialResult;
import com.github.stephengold.joltjni.PhysicsMaterialSimple;
import com.github.stephengold.joltjni.PlaneShapeSettings;
import com.github.stephengold.joltjni.Quat;
import com.github.stephengold.joltjni.RotatedTranslatedShapeSettings;
import com.github.stephengold.joltjni.SbcsResult;
import com.github.stephengold.joltjni.ScaledShapeSettings;
import com.github.stephengold.joltjni.SettingsResult;
import com.github.stephengold.joltjni.ShapeResult;
import com.github.stephengold.joltjni.ShapeSettings;
import com.github.stephengold.joltjni.ShapeSettingsRef;
import com.github.stephengold.joltjni.SoftBodyCreationSettings;
import com.github.stephengold.joltjni.SoftBodySharedSettings;
import com.github.stephengold.joltjni.SoftBodySharedSettingsRef;
import com.github.stephengold.joltjni.SphereShapeSettings;
import com.github.stephengold.joltjni.SpringSettings;
import com.github.stephengold.joltjni.StaticCompoundShapeSettings;
import com.github.stephengold.joltjni.StreamIn;
import com.github.stephengold.joltjni.StreamInWrapper;
import com.github.stephengold.joltjni.StreamOut;
import com.github.stephengold.joltjni.StreamOutWrapper;
import com.github.stephengold.joltjni.TaperedCapsuleShapeSettings;
import com.github.stephengold.joltjni.TaperedCylinderShapeSettings;
import com.github.stephengold.joltjni.TrackedVehicleControllerSettings;
import com.github.stephengold.joltjni.TriangleShapeSettings;
import com.github.stephengold.joltjni.Vec3;
import com.github.stephengold.joltjni.VehicleAntiRollBar;
import com.github.stephengold.joltjni.VehicleConstraintSettings;
import com.github.stephengold.joltjni.VehicleControllerSettingsRef;
import com.github.stephengold.joltjni.VehicleDifferentialSettings;
import com.github.stephengold.joltjni.Vertex;
import com.github.stephengold.joltjni.WheelSettingsTv;
import com.github.stephengold.joltjni.WheelSettingsWv;
import com.github.stephengold.joltjni.WheeledVehicleControllerSettings;
import com.github.stephengold.joltjni.enumerate.ESpringMode;
import com.github.stephengold.joltjni.enumerate.EStreamType;
import com.github.stephengold.joltjni.readonly.ConstBodyCreationSettings;
import com.github.stephengold.joltjni.readonly.ConstGroupFilter;
import com.github.stephengold.joltjni.readonly.ConstJoltPhysicsObject;
import com.github.stephengold.joltjni.readonly.ConstPhysicsMaterial;
import com.github.stephengold.joltjni.readonly.ConstShapeSettings;
import com.github.stephengold.joltjni.readonly.ConstSoftBodyCreationSettings;
import com.github.stephengold.joltjni.readonly.ConstSoftBodySharedSettings;
import com.github.stephengold.joltjni.readonly.ConstVehicleConstraintSettings;
import com.github.stephengold.joltjni.readonly.ConstVehicleControllerSettings;
import com.github.stephengold.joltjni.readonly.ConstVertex;
import com.github.stephengold.joltjni.readonly.ConstWheelSettings;
import com.github.stephengold.joltjni.std.StringStream;
import com.github.stephengold.joltjni.streamutils.IdToGroupFilterMap;
import com.github.stephengold.joltjni.streamutils.IdToMaterialMap;
import com.github.stephengold.joltjni.streamutils.IdToShapeMap;
import com.github.stephengold.joltjni.streamutils.IdToSharedSettingsMap;
import com.github.stephengold.joltjni.streamutils.MaterialToIdMap;
import com.github.stephengold.joltjni.streamutils.SharedSettingsToIdMap;
import org.junit.Assert;
import org.junit.Test;
import testjoltjni.TestUtils;

/**
 * Automated JUnit4 tests for serialization, de-serialization, and copying of
 * settings and related objects.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class Test012 {
    // *************************************************************************
    // new methods exposed

    /**
     * Test serialization, de-serialization, and copying of settings and related
     * objects.
     */
    @Test
    public void test012() {
        TestUtils.loadNativeLibrary();
        TestUtils.initializeNativeLibrary();

        testBodyCreationSettings();
        testGroupFilterTable();
        testPhysicsMaterial();
        testShapeSettings();
        testSoftBodyCreationSettings();
        testSoftBodySharedSettings();
        testSpringSettings();
        testTrackedVehicleControllerSettings();
        testVehicleAntiRollBar();
        testVehicleConstraintSettings();
        testWheeledVehicleControllerSettings();
        testWheelSettingsTv();
        testWheelSettingsWv();

        TestUtils.cleanup();
    }
    // *************************************************************************
    // private methods

    /**
     * De-serialize a body-creation settings object from the specified data
     * using {@code restoreBinaryState()}.
     *
     * @param serialData the data to de-serialize (not null, unaffected)
     * @return a new settings object
     */
    private static BodyCreationSettings dcBodyCreationSettings(
            String serialData) {
        StringStream ss = new StringStream(serialData);
        StreamIn streamIn = new StreamInWrapper(ss);

        BodyCreationSettings result = new BodyCreationSettings();
        result.restoreBinaryState(streamIn);

        TestUtils.testClose(streamIn, ss);
        return result;
    }

    /**
     * De-serialize a group-filter from the specified data using
     * {@code sRestoreFromBinaryState()}.
     *
     * @param serialData the data to de-serialize (not null, unaffected)
     * @return a new counted reference
     */
    private static GroupFilterRef dcGroupFilter(String serialData) {
        StringStream ss = new StringStream(serialData);
        StreamIn streamIn = new StreamInWrapper(ss);

        GroupFilterResult filterResult
                = GroupFilterTable.sRestoreFromBinaryState(streamIn);
        assert !filterResult.hasError() : filterResult.getError();
        GroupFilterRef result = filterResult.get();

        TestUtils.testClose(streamIn, ss);
        return result;
    }

    /**
     * De-serialize a soft-body creation-settings object from the specified data
     * using {@code sRestoreFromBinaryState()}.
     *
     * @param serialData the data to de-serialize (not null, unaffected)
     * @return a new counted reference
     */
    private static PhysicsMaterialRef dcPhysicsMaterial(String serialData) {
        StringStream ss = new StringStream(serialData);
        StreamIn streamIn = new StreamInWrapper(ss);

        PhysicsMaterialResult pmResult
                = PhysicsMaterial.sRestoreFromBinaryState(streamIn);
        assert !pmResult.hasError() : pmResult.getError();
        PhysicsMaterialRef result = pmResult.get();

        TestUtils.testClose(streamIn, ss);
        return result;
    }

    /**
     * De-serialize a soft-body creation-settings object from the specified data
     * using {@code restoreBinaryState()}.
     *
     * @param serialData the data to de-serialize (not null, unaffected)
     * @return a new settings object
     */
    private static ConstSoftBodyCreationSettings
            dcSoftBodyCreationSettings(String serialData) {
        StringStream ss = new StringStream(serialData);
        StreamIn streamIn = new StreamInWrapper(ss);

        SoftBodyCreationSettings result = new SoftBodyCreationSettings();
        result.restoreBinaryState(streamIn);

        TestUtils.testClose(streamIn, ss);
        return result;
    }

    /**
     * De-serialize a soft-body shared-settings object from the specified data
     * using {@code restoreBinaryState()}.
     *
     * @param serialData the data to de-serialize (not null, unaffected)
     * @return a new settings object
     */
    private static ConstSoftBodySharedSettings dcSoftBodySharedSettings(
            String serialData) {
        StringStream ss = new StringStream(serialData);
        StreamIn streamIn = new StreamInWrapper(ss);

        SoftBodySharedSettings result = new SoftBodySharedSettings();
        result.restoreBinaryState(streamIn);

        TestUtils.testClose(streamIn, ss);
        return result;
    }

    /**
     * De-serialize a wheeled-vehicle controller-settings object from the
     * specified data using {@code restoreBinaryState()}.
     *
     * @param serialData the data to de-serialize (not null, unaffected)
     * @return a new settings object
     */
    private static WheeledVehicleControllerSettings
            dcWheeledVehicleControllerSettings(String serialData) {
        StringStream ss = new StringStream(serialData);
        StreamIn streamIn = new StreamInWrapper(ss);

        WheeledVehicleControllerSettings result
                = new WheeledVehicleControllerSettings();
        result.restoreBinaryState(streamIn);

        TestUtils.testClose(streamIn, ss);
        return result;
    }

    /**
     * De-serialize a tracked-vehicle wheel-settings object from the specified
     * data using {@code restoreBinaryState()}.
     *
     * @param serialData the data to de-serialize (not null, unaffected)
     * @return a new settings object
     */
    private static WheelSettingsTv dcWheelSettingsTv(String serialData) {
        StringStream ss = new StringStream(serialData);
        StreamIn streamIn = new StreamInWrapper(ss);

        WheelSettingsTv result = new WheelSettingsTv();
        result.restoreBinaryState(streamIn);

        TestUtils.testClose(streamIn, ss);
        return result;
    }

    /**
     * De-serialize a wheeled-vehicle wheel-settings object from the specified
     * data using {@code restoreBinaryState()}.
     *
     * @param serialData the data to de-serialize (not null, unaffected)
     * @return a new settings object
     */
    private static WheelSettingsWv dcWheelSettingsWv(String serialData) {
        StringStream ss = new StringStream(serialData);
        StreamIn streamIn = new StreamInWrapper(ss);

        WheelSettingsWv result = new WheelSettingsWv();
        result.restoreBinaryState(streamIn);

        TestUtils.testClose(streamIn, ss);
        return result;
    }

    /**
     * De-serialize a body-creation settings object from the specified data
     * using {@code ObjectStreamIn}.
     *
     * @param serialData the data to de-serialize (not null, unaffected)
     * @return a new settings object
     */
    private static BodyCreationSettings drBodyCreationSettings(
            String serialData) {
        StringStream ss = new StringStream(serialData);
        BodyCreationSettings[] storeBcs = new BodyCreationSettings[1];
        boolean success = ObjectStreamIn.sReadObject(ss, storeBcs);
        assert success;
        BodyCreationSettings result = storeBcs[0];

        TestUtils.testClose(ss);
        return result;
    }

    /**
     * De-serialize a vehicle-constraint settings object from the specified data
     * using {@code ObjectStreamIn}.
     *
     * @param serialData the data to de-serialize (not null, unaffected)
     * @return a new counted reference
     */
    private static ConstraintSettingsRef drConstraintSettings(
            String serialData) {
        StringStream ss = new StringStream(serialData);
        ConstraintSettingsRef result = new ConstraintSettingsRef();
        boolean success = ObjectStreamIn.sReadObject(ss, result);
        assert success;

        TestUtils.testClose(ss);
        return result;
    }

    /**
     * De-serialize a group filter from the specified data using
     * {@code ObjectStreamIn}.
     *
     * @param serialData the data to de-serialize (not null, unaffected)
     * @return a new counted reference
     */
    private static GroupFilterTableRef drGroupFilterTable(String serialData) {
        StringStream ss = new StringStream(serialData);
        GroupFilterTableRef result = new GroupFilterTableRef();
        boolean success = ObjectStreamIn.sReadObject(ss, result);
        assert success;

        TestUtils.testClose(ss);
        return result;
    }

    /**
     * De-serialize a material from the specified data using
     * {@code ObjectStreamIn}.
     *
     * @param serialData the data to de-serialize (not null, unaffected)
     * @return a new counted reference
     */
    private static PhysicsMaterialRef drPhysicsMaterial(String serialData) {
        StringStream ss = new StringStream(serialData);
        PhysicsMaterialRef result = new PhysicsMaterialRef();
        boolean success = ObjectStreamIn.sReadObject(ss, result);
        assert success;

        TestUtils.testClose(ss);
        return result;
    }

    /**
     * De-serialize a shape-settings object from the specified data using
     * {@code ObjectStreamIn}.
     *
     * @param serialData the data to de-serialize (not null, unaffected)
     * @return a new settings object
     */
    private static ShapeSettingsRef drShapeSettings(String serialData) {
        StringStream ss = new StringStream(serialData);
        ShapeSettingsRef result = new ShapeSettingsRef();
        boolean success = ObjectStreamIn.sReadObject(ss, result);
        assert success;

        TestUtils.testClose(ss);
        return result;
    }

    /**
     * De-serialize a soft-body creation settings object from the specified data
     * using {@code ObjectStreamIn}.
     *
     * @param serialData the data to de-serialize (not null, unaffected)
     * @return a new settings object
     */
    private static SoftBodyCreationSettings drSoftBodyCreationSettings(
            String serialData) {
        StringStream ss = new StringStream(serialData);
        SoftBodyCreationSettings[] storeSbcs = new SoftBodyCreationSettings[1];
        boolean success = ObjectStreamIn.sReadObject(ss, storeSbcs);
        assert success;
        SoftBodyCreationSettings result = storeSbcs[0];

        TestUtils.testClose(ss);
        return result;
    }

    /**
     * De-serialize a soft-body shared settings object from the specified data
     * using {@code ObjectStreamIn}.
     *
     * @param serialData the data to de-serialize (not null, unaffected)
     * @return a new settings object
     */
    private static SoftBodySharedSettingsRef drSoftBodySharedSettings(
            String serialData) {
        StringStream ss = new StringStream(serialData);
        SoftBodySharedSettingsRef result = new SoftBodySharedSettingsRef();
        boolean success = ObjectStreamIn.sReadObject(ss, result);
        assert success;

        TestUtils.testClose(ss);
        return result;
    }

    /**
     * De-serialize a wheeled-vehicle controller-settings object from the
     * specified data using {@code ObjectStreamIn}.
     *
     * @param serialData the data to de-serialize (not null, unaffected)
     * @return a new settings object
     */
    private static VehicleControllerSettingsRef
            drWheeledVehicleControllerSettings(String serialData) {
        StringStream ss = new StringStream(serialData);
        VehicleControllerSettingsRef result
                = new VehicleControllerSettingsRef();
        boolean success = ObjectStreamIn.sReadObject(ss, result);
        assert success;

        TestUtils.testClose(ss);
        return result;
    }

    /**
     * De-serialize a body-creation settings object from the specified data
     * using {@code sRestoreWithChildren()}.
     *
     * @param serialData the data to de-serialize (not null, unaffected)
     * @return a new settings object
     */
    private static BodyCreationSettings dwcBodyCreationSettings(
            String serialData) {
        StringStream ss = new StringStream(serialData);
        StreamIn streamIn = new StreamInWrapper(ss);

        IdToShapeMap shapeMap = new IdToShapeMap();
        IdToMaterialMap materialMap = new IdToMaterialMap();
        IdToGroupFilterMap filterMap = new IdToGroupFilterMap();
        BcsResult bcsResult = BodyCreationSettings.sRestoreWithChildren(
                streamIn, shapeMap, materialMap, filterMap);
        assert !bcsResult.hasError() : bcsResult.getError();
        BodyCreationSettings result = bcsResult.get();

        TestUtils.testClose(streamIn, ss);
        return result;
    }

    /**
     * De-serialize a soft-body creation settings object from the specified data
     * using {@code sRestoreWithChildren()}.
     *
     * @param serialData the data to de-serialize (not null, unaffected)
     * @return a new settings object
     */
    private static SoftBodyCreationSettings dwcSoftBodyCreationSettings(
            String serialData) {
        StringStream ss = new StringStream(serialData);
        StreamIn streamIn = new StreamInWrapper(ss);

        IdToSharedSettingsMap sharedSettingsMap = new IdToSharedSettingsMap();
        IdToMaterialMap materialMap = new IdToMaterialMap();
        IdToGroupFilterMap filterMap = new IdToGroupFilterMap();
        SbcsResult sbcsResult = SoftBodyCreationSettings.sRestoreWithChildren(
                streamIn, sharedSettingsMap, materialMap, filterMap);
        assert !sbcsResult.hasError() : sbcsResult.getError();
        SoftBodyCreationSettings result = sbcsResult.get();

        TestUtils.testClose(streamIn, ss);
        return result;
    }

    /**
     * De-serialize a soft-body creation settings object from the specified data
     * using {@code sRestoreWithMaterials()}.
     *
     * @param serialData the data to de-serialize (not null, unaffected)
     * @return a new counted reference
     */
    private static SoftBodySharedSettingsRef dwmSoftBodySharedSettings(
            String serialData) {
        StringStream ss = new StringStream(serialData);
        StreamIn streamIn = new StreamInWrapper(ss);

        IdToSharedSettingsMap sharedSettingsMap = new IdToSharedSettingsMap();
        IdToMaterialMap materialMap = new IdToMaterialMap();
        SettingsResult sbssResult
                = SoftBodySharedSettings.sRestoreWithMaterials(
                        streamIn, sharedSettingsMap, materialMap);
        assert !sbssResult.hasError() : sbssResult.getError();
        SoftBodySharedSettingsRef result = sbssResult.get();

        TestUtils.testClose(streamIn, ss);
        return result;
    }

    /**
     * Serialize the specified body-creation settings using
     * {@code saveBinaryState()}.
     *
     * @param bcs the settings to serialize (not null, unaffected)
     * @return serialized data
     */
    private static String serializeCooked(ConstBodyCreationSettings bcs) {
        StringStream ss = new StringStream();
        StreamOut streamOut = new StreamOutWrapper(ss);

        bcs.saveBinaryState(streamOut);
        String result = ss.str();

        TestUtils.testClose(streamOut, ss);
        return result;
    }

    /**
     * Serialize the specified group filter using {@code saveBinaryState()}.
     *
     * @param filter the filter to serialize (not null, unaffected)
     * @return serialized data
     */
    private static String serializeCooked(ConstGroupFilter filter) {
        StringStream ss = new StringStream();
        StreamOut streamOut = new StreamOutWrapper(ss);

        filter.saveBinaryState(streamOut);
        String result = ss.str();

        TestUtils.testClose(streamOut, ss);
        return result;
    }

    /**
     * Serialize the specified material using {@code saveBinaryState()}.
     *
     * @param material the material to serialize (not null, unaffected)
     * @return serialized data
     */
    private static String serializeCooked(ConstPhysicsMaterial material) {
        StringStream ss = new StringStream();
        StreamOut streamOut = new StreamOutWrapper(ss);

        material.saveBinaryState(streamOut);
        String result = ss.str();

        TestUtils.testClose(streamOut, ss);
        return result;
    }

    /**
     * Serialize the specified soft-body creation settings using
     * {@code saveBinaryState()}.
     *
     * @param sbcs the settings to serialize (not null, unaffected)
     * @return serialized data
     */
    private static String serializeCooked(ConstSoftBodyCreationSettings sbcs) {
        StringStream ss = new StringStream();
        StreamOut streamOut = new StreamOutWrapper(ss);

        sbcs.saveBinaryState(streamOut);
        String result = ss.str();

        TestUtils.testClose(streamOut, ss);
        return result;
    }

    /**
     * Serialize the specified soft-body shared settings using
     * {@code saveBinaryState()}.
     *
     * @param sbss the settings to serialize (not null, unaffected)
     * @return serialized data
     */
    private static String serializeCooked(ConstSoftBodySharedSettings sbss) {
        StringStream ss = new StringStream();
        StreamOut streamOut = new StreamOutWrapper(ss);

        sbss.saveBinaryState(streamOut);
        String result = ss.str();

        TestUtils.testClose(streamOut, ss);
        return result;
    }

    /**
     * Serialize the specified controller settings using
     * {@code saveBinaryState()}.
     *
     * @param settings the settings to serialize (not null, unaffected)
     * @return serialized data
     */
    private static String serializeCooked(
            ConstVehicleControllerSettings settings) {
        StringStream ss = new StringStream();
        StreamOut streamOut = new StreamOutWrapper(ss);

        settings.saveBinaryState(streamOut);
        String result = ss.str();

        TestUtils.testClose(streamOut, ss);
        return result;
    }

    /**
     * Serialize the specified wheel settings using {@code saveBinaryState()}.
     *
     * @param settings the settings to serialize (not null, unaffected)
     * @return serialized data
     */
    private static String serializeCooked(ConstWheelSettings settings) {
        StringStream ss = new StringStream();
        StreamOut streamOut = new StreamOutWrapper(ss);

        settings.saveBinaryState(streamOut);
        String result = ss.str();

        TestUtils.testClose(streamOut, ss);
        return result;
    }

    /**
     * Serialize the specified object using {@code ObjectStreamOut}.
     *
     * @param writeObj the object to serialize (not null, uncooked, unaffected)
     * @return serialized data
     */
    private static String serializeRaw(ConstJoltPhysicsObject writeObj) {
        StringStream ss = new StringStream();
        boolean success = ObjectStreamOut.sWriteObject(
                ss, EStreamType.Binary, writeObj);
        assert success;
        String result = ss.str();
        TestUtils.testClose(ss);
        return result;
    }

    /**
     * Serialize the specified body-creation settings using
     * {@code saveWithChildren()}.
     *
     * @param bcs the settings to serialize (not null, unaffected)
     * @return serialized data
     */
    private static String serializeWithChildren(ConstBodyCreationSettings bcs) {
        StringStream ss = new StringStream();
        StreamOut streamOut = new StreamOutWrapper(ss);

        bcs.saveWithChildren(streamOut, null, null, null);
        String result = ss.str();

        TestUtils.testClose(streamOut, ss);
        return result;
    }

    /**
     * Serialize the specified soft-body creation settings using
     * {@code saveWithChildren()}.
     *
     * @param sbcs the settings to serialize (not null, unaffected)
     * @return serialized data
     */
    private static String serializeWithChildren(
            ConstSoftBodyCreationSettings sbcs) {
        StringStream ss = new StringStream();
        StreamOut streamOut = new StreamOutWrapper(ss);

        sbcs.saveWithChildren(streamOut, null, null, null);
        String result = ss.str();

        TestUtils.testClose(streamOut, ss);
        return result;
    }

    /**
     * Serialize the specified soft-body shared settings using
     * {@code saveWithMaterials()}.
     *
     * @param sbss the settings to serialize (not null, unaffected)
     * @return serialized data
     */
    private static String serializeWithMaterials(
            ConstSoftBodySharedSettings sbss) {
        StringStream ss = new StringStream();
        StreamOut streamOut = new StreamOutWrapper(ss);

        sbss.saveWithMaterials(
                streamOut, new SharedSettingsToIdMap(), new MaterialToIdMap());
        String result = ss.str();

        TestUtils.testClose(streamOut, ss);
        return result;
    }

    /**
     * Test replication of {@code BodyCreationSettings} objects.
     */
    private static void testBodyCreationSettings() {
        float radius = 9f;
        ConstShapeSettings shapeSettings = new SphereShapeSettings(radius);
        BodyCreationSettings bcs = new BodyCreationSettings()
                .setShapeSettings(shapeSettings);

        { // serialize and then deserialize raw settings using object streams:
            String serialData = serializeRaw(bcs);
            ConstBodyCreationSettings bcsCopy
                    = drBodyCreationSettings(serialData);

            Assert.assertNotEquals(bcs.targetVa(), bcsCopy.targetVa());
            Equivalent.bodyCreationSettings(bcs, bcsCopy);
            TestUtils.testClose(bcsCopy);
        }

        { // test the copy constructor on uncooked settings:
            ConstBodyCreationSettings bcsCopy = new BodyCreationSettings(bcs);

            Assert.assertNotEquals(bcs.targetVa(), bcsCopy.targetVa());
            Equivalent.bodyCreationSettings(bcs, bcsCopy);
            TestUtils.testClose(bcsCopy);
        }

        ShapeResult sr = bcs.convertShapeSettings(); // cook the settings
        assert !sr.hasError();
        assert sr.isValid();
        Assert.assertNull(bcs.getShapeSettings());
        Assert.assertEquals(sr.get().targetVa(), bcs.getShape().targetVa());

        { // serialize and then deserialize binary state:
            String serialData = serializeCooked(bcs);
            ConstBodyCreationSettings bcsCopy
                    = dcBodyCreationSettings(serialData);

            Assert.assertNotEquals(bcs.targetVa(), bcsCopy.targetVa());
            Equivalent.bodyCreationSettings(bcs, bcsCopy);
            TestUtils.testClose(bcsCopy);
        }

        { // serialize and then deserialize using with-children methods:
            String serialData = serializeWithChildren(bcs);
            ConstBodyCreationSettings bcsCopy
                    = dwcBodyCreationSettings(serialData);

            Assert.assertNotEquals(bcs.targetVa(), bcsCopy.targetVa());
            Equivalent.bodyCreationSettings(bcs, bcsCopy);
            TestUtils.testClose(bcsCopy);
        }

        { // test the copy constructor on cooked settings:
            ConstBodyCreationSettings bcsCopy = new BodyCreationSettings(bcs);

            Assert.assertNotEquals(bcs.targetVa(), bcsCopy.targetVa());
            Equivalent.bodyCreationSettings(bcs, bcsCopy);
            TestUtils.testClose(bcsCopy);
        }

        TestUtils.testClose(bcs, shapeSettings);
    }

    /**
     * Test replication of {@code GroupFilterTable} objects.
     */
    private static void testGroupFilterTable() {
        GroupFilterTable filter = new GroupFilterTable(2);
        filter.disableCollision(0, 1);

        { // serialize and then deserialize binary state:
            String serialData = serializeCooked(filter);
            GroupFilterRef refCopy = dcGroupFilter(serialData);
            GroupFilterTable filterCopy = refCopy.getPtrAsTable();

            Assert.assertNotEquals(filter.targetVa(), filterCopy.targetVa());
            Equivalent.groupFilter(filter, filterCopy);
            TestUtils.testClose(filterCopy);
        }

        { // serialize and then deserialize using object streams:
            String serialData = serializeRaw(filter);
            GroupFilterTableRef refCopy = drGroupFilterTable(serialData);
            GroupFilterTable filterCopy = refCopy.getPtr();

            Assert.assertNotEquals(filter.targetVa(), filterCopy.targetVa());
            Equivalent.groupFilter(filter, filterCopy);
            TestUtils.testClose(filterCopy);
        }

        { // copy constructor:
            GroupFilterTable filterCopy = new GroupFilterTable(filter);

            Assert.assertNotEquals(filter.targetVa(), filterCopy.targetVa());
            Equivalent.groupFilter(filter, filterCopy);
            TestUtils.testClose(filterCopy);
        }

        TestUtils.testClose(filter);
    }

    /**
     * Test replication of {@code PhysicsMaterial} objects.
     */
    private static void testPhysicsMaterial() {
        PhysicsMaterialSimple material
                = new PhysicsMaterialSimple("name", Color.sOrange);

        { // serialize and then deserialize binary state:
            String serialData = serializeCooked(material);
            PhysicsMaterialRef refCopy = dcPhysicsMaterial(serialData);
            PhysicsMaterialSimple materialCopy = refCopy.getPtrAsSimple();

            Assert.assertNotEquals(
                    material.targetVa(), materialCopy.targetVa());
            Equivalent.physicsMaterial(material, materialCopy);
            TestUtils.testClose(materialCopy);
        }

        { // serialize and then deserialize using object streams:
            String serialData = serializeRaw(material);
            PhysicsMaterialRef refCopy = drPhysicsMaterial(serialData);
            PhysicsMaterialSimple materialCopy
                    = (PhysicsMaterialSimple) refCopy.getPtrAsSimple();

            Assert.assertNotEquals(
                    material.targetVa(), materialCopy.targetVa());
            Equivalent.physicsMaterial(material, materialCopy);
            TestUtils.testClose(materialCopy);
        }

        { // copy constructor:
            PhysicsMaterialSimple materialCopy
                    = new PhysicsMaterialSimple(material);

            Assert.assertNotEquals(
                    material.targetVa(), materialCopy.targetVa());
            Equivalent.physicsMaterial(material, materialCopy);
            TestUtils.testClose(materialCopy);
        }

        TestUtils.testClose(material);
    }

    /**
     * Test replication of {@code ShapeSettings} objects.
     */
    private static void testShapeSettings() {
        BoxShapeSettings box = new BoxShapeSettings(1f, 2f, 3f);
        testShapeSettings(box);

        CapsuleShapeSettings capsule = new CapsuleShapeSettings(1f, 2f);
        testShapeSettings(capsule);

        ConvexHullShapeSettings convexHull = new ConvexHullShapeSettings(
                new Vec3(1f, 2f, 3f), new Vec3(4f, 5f, 6f), new Vec3());
        testShapeSettings(convexHull);

        CylinderShapeSettings cylinder = new CylinderShapeSettings(1f, 2f);
        testShapeSettings(cylinder);

        EmptyShapeSettings empty = new EmptyShapeSettings();
        testShapeSettings(empty);

        HeightFieldShapeSettings heightField = new HeightFieldShapeSettings();
        testShapeSettings(heightField);

        MeshShapeSettings mesh = new MeshShapeSettings();
        testShapeSettings(mesh);

        OffsetCenterOfMassShapeSettings ocom
                = new OffsetCenterOfMassShapeSettings(
                        new Vec3(1f, 2f, 3f), box);
        testShapeSettings(ocom);

        MutableCompoundShapeSettings mutableCompound
                = new MutableCompoundShapeSettings();
        testShapeSettings(mutableCompound);

        PlaneShapeSettings plane = new PlaneShapeSettings();
        testShapeSettings(plane);

        RotatedTranslatedShapeSettings rotated
                = new RotatedTranslatedShapeSettings(new Vec3(1f, 2f, 3f),
                        new Quat(0.6f, -0.8f, 0f, 0f), box);
        testShapeSettings(rotated);

        ScaledShapeSettings scaled
                = new ScaledShapeSettings(box, new Vec3(1f, 2f, 3f));
        testShapeSettings(scaled);

        SphereShapeSettings sphere = new SphereShapeSettings(9f);
        testShapeSettings(sphere);

        StaticCompoundShapeSettings staticCompound
                = new StaticCompoundShapeSettings();
        testShapeSettings(staticCompound);

        TaperedCapsuleShapeSettings taperedCapsule
                = new TaperedCapsuleShapeSettings(3f, 2f, 1f);
        testShapeSettings(taperedCapsule);

        TaperedCylinderShapeSettings taperedCylinder
                = new TaperedCylinderShapeSettings(3f, 2f, 1f);
        testShapeSettings(taperedCylinder);

        TriangleShapeSettings triangle = new TriangleShapeSettings();
        testShapeSettings(triangle);

        TestUtils.testClose(triangle, taperedCylinder, taperedCapsule,
                staticCompound, sphere, scaled, rotated, plane, ocom,
                mutableCompound, mesh, heightField, empty, cylinder, convexHull,
                capsule, box);
    }

    /**
     * Test replication of the specified {@code ShapeSettings} object.
     *
     * @param ss the object to test
     */
    private static void testShapeSettings(ConstShapeSettings ss) {
        { // cloneShapeSettings() method:
            ConstShapeSettings ssCopy = ShapeSettings.cloneShapeSettings(ss);

            Assert.assertNotEquals(ss.targetVa(), ssCopy.targetVa());
            Equivalent.shapeSettings(ss, ssCopy);
            TestUtils.testClose(ssCopy);
        }

        { // serialize and then deserialize using object streams:
            String serialData = serializeRaw(ss);
            ShapeSettingsRef refCopy = drShapeSettings(serialData);
            ConstShapeSettings ssCopy = refCopy.getPtr();

            Assert.assertNotEquals(ss.targetVa(), ssCopy.targetVa());
            Equivalent.shapeSettings(ss, ssCopy);
            TestUtils.testClose(ssCopy);
        }
    }

    /**
     * Test replication of {@code SoftBodyCreationSettings} objects.
     */
    private static void testSoftBodyCreationSettings() {
        SoftBodyCreationSettings sbcs = new SoftBodyCreationSettings();

        { // serialize and then deserialize binary state:
            String serialData = serializeCooked(sbcs);
            ConstSoftBodyCreationSettings sbcsCopy
                    = dcSoftBodyCreationSettings(serialData);

            Assert.assertNotEquals(sbcs.targetVa(), sbcsCopy.targetVa());
            Equivalent.softBodyCreationSettings(sbcs, sbcsCopy);
            TestUtils.testClose(sbcsCopy);
        }

        { // serialize and then deserialize using with-children methods:
            String serialData = serializeWithChildren(sbcs);
            ConstSoftBodyCreationSettings sbcsCopy
                    = dwcSoftBodyCreationSettings(serialData);

            Assert.assertNotEquals(sbcs.targetVa(), sbcsCopy.targetVa());
            Equivalent.softBodyCreationSettings(sbcs, sbcsCopy);
            TestUtils.testClose(sbcsCopy);
        }

        { // serialize and then deserialize using object streams:
            String serialData = serializeRaw(sbcs);
            ConstSoftBodyCreationSettings sbcsCopy
                    = drSoftBodyCreationSettings(serialData);

            Assert.assertNotEquals(sbcs.targetVa(), sbcsCopy.targetVa());
            Equivalent.softBodyCreationSettings(sbcs, sbcsCopy);
            TestUtils.testClose(sbcsCopy);
        }

        { // copy constructor:
            ConstSoftBodyCreationSettings sbcsCopy
                    = new SoftBodyCreationSettings(sbcs);

            Assert.assertNotEquals(sbcs.targetVa(), sbcsCopy.targetVa());
            Equivalent.softBodyCreationSettings(sbcs, sbcsCopy);
            TestUtils.testClose(sbcsCopy);
        }

        TestUtils.testClose(sbcs);
    }

    /**
     * Test replication of {@code SoftBodySharedSettings} objects.
     */
    private static void testSoftBodySharedSettings() {
        SoftBodySharedSettings sbss = new SoftBodySharedSettings();
        ConstVertex v0 = new Vertex().setPosition(1f, 2f, 3f);
        sbss.addVertex(v0);
        ConstVertex v1 = new Vertex().setPosition(4f, 5f, 6f);
        sbss.addVertex(v1);
        Edge edge = new Edge()
                .setCompliance(7f)
                .setVertex(0, 1)
                .setVertex(1, 0);
        sbss.addEdgeConstraint(edge);

        { // serialize and then deserialize binary state:
            String serialData = serializeCooked(sbss);
            ConstSoftBodySharedSettings sbssCopy
                    = dcSoftBodySharedSettings(serialData);

            Assert.assertNotEquals(sbss.targetVa(), sbssCopy.targetVa());
            Equivalent.softBodySharedSettings(sbss, sbssCopy);
            TestUtils.testClose(sbssCopy);
        }

        { // serialize and then deserialize using with-materials methods:
            String serialData = serializeWithMaterials(sbss);
            SoftBodySharedSettingsRef sbssCopy
                    = dwmSoftBodySharedSettings(serialData);

            Assert.assertNotEquals(sbss.targetVa(), sbssCopy.targetVa());
            Equivalent.softBodySharedSettings(sbss, sbssCopy);
            TestUtils.testClose(sbssCopy);
        }

        { // serialize and then deserialize using object streams:
            String serialData = serializeRaw(sbss);
            SoftBodySharedSettingsRef sbssCopy
                    = drSoftBodySharedSettings(serialData);

            Assert.assertNotEquals(sbss.targetVa(), sbssCopy.targetVa());
            Equivalent.softBodySharedSettings(sbss, sbssCopy);
            TestUtils.testClose(sbssCopy);
        }

        { // copy constructor:
            ConstSoftBodySharedSettings sbssCopy
                    = new SoftBodySharedSettings(sbss);

            Assert.assertNotEquals(sbss.targetVa(), sbssCopy.targetVa());
            Equivalent.softBodySharedSettings(sbss, sbssCopy);
            TestUtils.testClose(sbssCopy);
        }

        TestUtils.testClose(sbss);
    }

    /**
     * Test replication of {@code SpringSettings} objects.
     */
    private static void testSpringSettings() {
        SpringSettings settings = new SpringSettings()
                .setDamping(2f)
                .setMode(ESpringMode.StiffnessAndDamping)
                .setStiffness(345f);

        { // copy constructor:
            SpringSettings settingsCopy = new SpringSettings(settings);

            Assert.assertNotEquals(
                    settings.targetVa(), settingsCopy.targetVa());
            Equivalent.springSettings(settings, settingsCopy);
            TestUtils.testClose(settingsCopy);
        }

        TestUtils.testClose(settings);
    }

    /**
     * Test replication of {@code TrackedVehicleControllerSettings} objects.
     */
    private static void testTrackedVehicleControllerSettings() {
        TrackedVehicleControllerSettings settings
                = new TrackedVehicleControllerSettings();

        { // copy constructor:
            TrackedVehicleControllerSettings settingsCopy
                    = new TrackedVehicleControllerSettings(settings);

            Assert.assertNotEquals(
                    settings.targetVa(), settingsCopy.targetVa());
            Equivalent.trackedVehicleControllerSettings(
                    settings, settingsCopy);
            TestUtils.testClose(settingsCopy);
        }

        TestUtils.testClose(settings);
    }

    /**
     * Test replication of {@code VehicleAntiRollBar} objects.
     */
    private static void testVehicleAntiRollBar() {
        VehicleAntiRollBar bar = new VehicleAntiRollBar()
                .setLeftWheel(1)
                .setRightWheel(2)
                .setStiffness(345f);

        { // copy constructor:
            VehicleAntiRollBar barCopy = new VehicleAntiRollBar(bar);

            Assert.assertNotEquals(bar.targetVa(), barCopy.targetVa());
            Equivalent.antiRollBar(bar, barCopy);
            TestUtils.testClose(barCopy);
        }

        TestUtils.testClose(bar);
    }

    /**
     * Test replication of {@code VehicleConstraintSettings} objects.
     */
    private static void testVehicleConstraintSettings() {
        // Configure 4 wheels, 2 in the front (for steering) and 2 in the rear:
        WheelSettingsWv[] wheels = new WheelSettingsWv[4];
        for (int i = 0; i < 4; ++i) {
            wheels[i] = new WheelSettingsWv();
        }
        wheels[0].setPosition(new Vec3(-1f, 0f, +2f)); // left front
        wheels[1].setPosition(new Vec3(+1f, 0f, +2f));
        wheels[2].setPosition(new Vec3(-1f, 0f, -2f));
        wheels[3].setPosition(new Vec3(+1f, 0f, -2f)); // right rear

        // The rear wheels aren't steerable:
        wheels[2].setMaxSteerAngle(0f);
        wheels[3].setMaxSteerAngle(0f);
        /*
         * Configure a controller with a single differential,
         * for rear-wheel drive:
         */
        WheeledVehicleControllerSettings wvcs
                = new WheeledVehicleControllerSettings();
        wvcs.setNumDifferentials(1);
        VehicleDifferentialSettings vds = wvcs.getDifferential(0);
        vds.setLeftWheel(2);
        vds.setRightWheel(3);

        VehicleConstraintSettings vcs = new VehicleConstraintSettings();
        vcs.addWheels(wheels);
        vcs.setController(wvcs);

        { // serialize and then deserialize using object streams:
            String serialData = serializeRaw(vcs);
            ConstraintSettingsRef copyRef = drConstraintSettings(serialData);
            ConstVehicleConstraintSettings vcsCopy
                    = (ConstVehicleConstraintSettings) copyRef.getPtr();

            Assert.assertNotEquals(vcs.targetVa(), vcsCopy.targetVa());
            Equivalent.vehicleConstraintSettings(vcs, vcsCopy);
            TestUtils.testClose(vcsCopy, copyRef);
        }

        { // copy constructor:
            ConstVehicleConstraintSettings vcsCopy
                    = new VehicleConstraintSettings(vcs);

            Assert.assertNotEquals(vcs.targetVa(), vcsCopy.targetVa());
            Equivalent.vehicleConstraintSettings(vcs, vcsCopy);
            TestUtils.testClose(vcsCopy);
        }

        TestUtils.testClose(wheels);
        TestUtils.testClose(vcs, wvcs);
    }

    /**
     * Test replication of {@code VehicleControllerSettings} objects.
     */
    private static void testWheeledVehicleControllerSettings() {
        WheeledVehicleControllerSettings settings
                = new WheeledVehicleControllerSettings();

        { // serialize and then deserialize binary state:
            String serialData = serializeCooked(settings);
            WheeledVehicleControllerSettings settingsCopy
                    = dcWheeledVehicleControllerSettings(serialData);

            Assert.assertNotEquals(
                    settings.targetVa(), settingsCopy.targetVa());
            Equivalent.vehicleControllerSettings(settings, settingsCopy);
            TestUtils.testClose(settingsCopy);
        }

        { // serialize and then deserialize using object streams:
            String serialData = serializeRaw(settings);
            VehicleControllerSettingsRef copyRef
                    = drWheeledVehicleControllerSettings(serialData);
            WheeledVehicleControllerSettings settingsCopy
                    = (WheeledVehicleControllerSettings) copyRef.getPtr();

            Assert.assertNotEquals(
                    settings.targetVa(), settingsCopy.targetVa());
            Equivalent.vehicleControllerSettings(settings, settingsCopy);
            TestUtils.testClose(settingsCopy);
        }

        { // copy constructor:
            WheeledVehicleControllerSettings settingsCopy
                    = new WheeledVehicleControllerSettings(settings);

            Assert.assertNotEquals(
                    settings.targetVa(), settingsCopy.targetVa());
            Equivalent.vehicleControllerSettings(settings, settingsCopy);
            TestUtils.testClose(settingsCopy);
        }
    }

    /**
     * Test replication of {@code WheelSettingsTv} objects.
     */
    private static void testWheelSettingsTv() {
        WheelSettingsTv wheel = new WheelSettingsTv();
        wheel.setEnableSuspensionForcePoint(true);
        wheel.setPosition(new Vec3(1f, 0f, -2f));

        { // serialize and then deserialize binary state:
            String serialData = serializeCooked(wheel);
            WheelSettingsTv wheelCopy = dcWheelSettingsTv(serialData);

            Assert.assertNotEquals(wheel.targetVa(), wheelCopy.targetVa());
            Equivalent.wheelSettings(wheel, wheelCopy);
            TestUtils.testClose(wheelCopy);
        }

        { // copy constructor:
            WheelSettingsTv wheelCopy = new WheelSettingsTv(wheel);

            Assert.assertNotEquals(wheel.targetVa(), wheelCopy.targetVa());
            Equivalent.wheelSettings(wheel, wheelCopy);
            TestUtils.testClose(wheelCopy);
        }

        TestUtils.testClose(wheel);
    }

    /**
     * Test replication of {@code WheelSettingsWv} objects.
     */
    private static void testWheelSettingsWv() {
        WheelSettingsWv wheel = new WheelSettingsWv();
        wheel.setEnableSuspensionForcePoint(true);
        wheel.setMaxSteerAngle(0.1f);
        wheel.setPosition(new Vec3(1f, 0f, -2f));

        { // serialize and then deserialize binary state:
            String serialData = serializeCooked(wheel);
            WheelSettingsWv wheelCopy = dcWheelSettingsWv(serialData);

            Assert.assertNotEquals(wheel.targetVa(), wheelCopy.targetVa());
            Equivalent.wheelSettings(wheel, wheelCopy);
            TestUtils.testClose(wheelCopy);
        }

        { // copy constructor:
            WheelSettingsWv wheelCopy = new WheelSettingsWv(wheel);

            Assert.assertNotEquals(wheel.targetVa(), wheelCopy.targetVa());
            Equivalent.wheelSettings(wheel, wheelCopy);
            TestUtils.testClose(wheelCopy);
        }

        TestUtils.testClose(wheel);
    }
}
