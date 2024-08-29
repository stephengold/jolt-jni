# release log for the jolt-jni project

## Version 0.6.0 released on 28 August 2024

+ Renamed the `RefShape` class to `ShapeRef`.
+ Bugfix:  JVM crashes when `OnBodyDeactivated()` invoked by a native thread.
+ Bugfix:  work around `JobSystemSingleThreaded` deadlocks on Windows.

+ Added classes to the library:
  + `BodyIdVector`
  + `Character`
  + `CharacterBase`
  + `CharacterBaseSettings`
  + `CharacterRef`
  + `CharacterSettings`
  + `CharacterSettingsRef`
  + `CharacterVirtual`
  + `CharacterVirtualRef`
  + `CharacterVirtualSettings`
  + `CharacterVirtualSettingsRef`
  + `ConeConstraint`
  + `ConeConstraintSettings`
  + `CollideShapeResult`
  + `Constraint`
  + `Constraints`
  + `ConstraintRef`
  + `ConstraintSettings`
  + `ConstraintSettingsRef`
  + `Contact`
  + `ContactList`
  + `ContactManifold`
  + `ContactSettings`
  + `DistanceConstraint`
  + `DistanceConstraintSettings`
  + `FixedConstraint`
  + `FixedConstraintSettings`
  + `GearConstraint`
  + `GearConstraintSettings`
  + `HingeConstraint`
  + `HingeConstraintSettings`
  + `MotorSettings`
  + `PhysicsMaterial`
  + `PhysicsMaterialRef`
  + `Plane`
  + `PlaneShape`
  + `PlaneShapeSettings`
  + `PointConstraint`
  + `PointConstraintSettings`
  + `RMat44`
  + `SixDofConstraint`
  + `SixDofConstraintSettings`
  + `SliderConstraint`
  + `SliderConstraintSettings`
  + `SpringSettings`
  + `SubShapeId`
  + `SubShapeIdPair`
  + `TwoBodyConstraint`
  + `TwoBodyConstraintSettings`

+ Added enums to the library:
  + `EAxis` 
  + `EBackFaceMode`
  + `EConstraintSubType`
  + `EConstraintSpace`
  + `EConstraintType`
  + `EGroundState`
  + `EMotorState`
  + `ESpringMode`
  + `ESwingType`
  + `ValidateResult`

+ Added interfaces to the library:
  + `ConstBody`
  + `ConstCharacter`
  + `ConstCharacterBase`
  + `ConstCharacterBaseSettings`
  + `ConstCharacterSettings`
  + `ConstCharacterVirtual`
  + `ConstCharacterVirtualSettings`
  + `ConstConstraint`
  + `ConstConstraintSettings`
  + `ConstContact`
  + `ConstPhysicsMaterial`
  + `ConstPlane`
  + `ConstRMat44`

+ Added many public methods to the library.
+ Updated Jolt source code to 8feb90c (=sg240827).
+ Updated jSnapLoader to v1.0.0-stable .
+ Reorganized the "glue" source files into multiple directories.

## Version 0.5.0 released on 1 August 2024

+ Split off the Jolt Physics enums into a "joltjni.enumerate" package.
+ Use GCC v9 when compiling for the Linux_ARM32hf platform.
+ Added the `RefShape` class to the library.
+ Added a no-argument constructor for `MutableCompoundShape`.
+ Added 2 public methods to the library:
  + `CompoundShape.getNumSubShapes()`
  + `Shape.toRef()`

## Version 0.4.1 released on 30 July 2024

+ Split off the read-only interfaces into a "joltjni.readonly" package.
+ Added support for 2 Linux-on-ARM platforms.
+ Began using jSnapLoader to select native libraries for testing.
+ Purged JMonkeyEngine code from the project and simplified the license.

## Version 0.3.2 released on 24 July 2024

+ Added many methods.
+ Added the `cDefaultConvexRadius` constant.
+ Added classes to the library:
  + `BodyLockInterface`
  + `BodyLockInterfaceLocking`
  + `CompoundShape`
  + `CompoundShapeSettings`
  + `MutableCompoundShape`
  + `MutableCompoundShapeSettings`
  + `RotatedTranslatedShape`
  + `RotatedTranslatedShapeSettings`
  + `SharedMutex`
  + `SharedMutexBase`
  + `StaticCompoundShape`
  + `StaticCompoundShapeSettings`
  + `TriangleShape`
  + `TriangleShapeSettings`

## Version 0.3.1 released on 20 July 2024

+ Bugfix:  memory leak in `MassProperties`
+ Added many methods.
+ Added classes and interfaces to the library:
  + `BroadPhaseLayerInterface`
  + `ConstBodyCreationSettings`
  + `ConstBroadPhaseLayerInterface`
  + `ConstMassProperties`
  + `ConstObjectLayerPairFilter`
  + `ConstObjectVsBroadPhaseLayerFilter`
  + `EAllowedDofs`
  + `EOverrideMassProperties`
  + `Mat44`
  + `Mat44Arg`
  + `ObjectLayerPairFilter`
  + `ObjectVsBroadPhaseLayerFilter`
  + `UVec4`
  + `UVec4Arg`

## Version 0.3.0 released on 16 July 2024

+ Added a `java.lang.ref.Cleaner` to automatically free native
  objects (as an alternative to explicitly closing each physics object).
+ Deleted the `JoltPhysicsObject.unassignNativeObject()` method.
+ Deleted the `ShapeSettings.createShape()` method.
+ Added many methods.
+ Added classes and interfaces to the library:
  + `CapsuleShapeSettings`
  + `ConstAaBox`
  + `ConstBodyId`
  + `ConstJoltPhysicsObject`
  + `ConstShape`
  + `CylinderShapeSettings`
  + `RefTarget`
  + `ScaledShapeSettings`
  + `ShapeRefC`
  + `ShapeResult`
  + `ShapeSettingsRef`

## Version 0.2.0 released on 9 July 2024

For development only. Not for production use.

+ Added many classes and methods.
+ Made the automated testing more systematic.
+ Worked around deadlocks in 2 destructors when running on Windows.
+ Solved a `SIGTRAP` in ConvexHullShape.cpp .
+ Shortened the names of 30 glue files.
+ Re-organized the test sourcecode into 3 new packages.
+ Modified `Shape.newShape()` to accept a zero argument.
+ Reduced the recommended number of threads when there are >9 CPUs.
+ Corrected the return type of `ConvexHullShapeSettings.createShape()` .

## Version 0.1.10 released on 3 July 2024

Initial baseline pre-release, for development only. Not for production use.