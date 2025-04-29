# release log for the Jolt-JNI project

## Version 0.9.10 released on 28 April 2025

+ API change:
  + remove the `MapObj2Bp` class (use `BroadPhaseLayerInterfaceTable` instead)

+ Bugfixes:
  + `Shape.copyDebugTriangles()` doesn't account for the center-of-mass
  + infinite recursion colliding triangle with triangle (JoltPhysics issue 1620)

+ Added 4 classes:
  + `BroadPhaseLayerInterfaceTable`
  + `ObjectLayerPairFilterTable`
  + `ObjectVsBroadPhaseLayerFilterTable`
  + `OrientedBox`

+ Added some public methods to the libraries.
+ Declared an open module that exports all packages.
+ Updated the Jolt source code and assets to 2dcab94 (=sg250427).
+ Updated the OSHI library to v6.8.1 .

## Version 0.9.9 released on 13 April 2025

+ API changes:
  + Began representing body IDs, character IDs, and sub-shape IDs
    using `int` primitives instead of objects.
  + Removed the `BodyId`, `CharacterId`, and `SubShapeId` classes.
  + Removed the `ConstBodyId` interface.
  + Moved the `sSetNextCharacterId()` method from `CharacterId` to `Jolt`.
  + Added chaining to 15 public setters in the `SoftBodyCreationSettings` class.
  + Altered the return types and/or semantics of 14 other methods:
    + `Body.getCollisionGroup()`
    + `Body.setCollisionGroup()`
    + `CollisionGroup.getGroupFilter()`
    + `ConstBodyCreationSettings.getMassPropertiesOverride()`
    + `ConstCharacterVirtual.getActiveContacts()`
    + `ConstContact.getCharacterB()`
    + `Skeleton.getJoint()`
    + `Skeleton.getJoints()`
    + `SkeletonRef.getJoint()`
    + `SkeletonRef.getJoints()`
    + `SoftBodyCreationSettings.getSettings()`
    + `SoftBodyMotionProperties.getFace()`
    + `SoftBodyMotionProperties.getFaces()`
    + `SoftBodyMotionProperties.getSettings()`
  + Generalized 2 methods:
    + `SoftBodyCreationSettings.setCollisionGroup()`
    + `BodyCreationSettings.setCollisionGroup()`

+ Bugfixes:
  + unimplemented `CharacterVirtual.getTransformedShape()`
  + 2 logic errors in `CustomCharacterContactListener`
  + assertion failure in `JoltPhysicsObject` while creating a `SoftBodyVertex`

+ Added 2 interfaces:
  + `ConstCollisionGroup`
  + `ConstGroupFilter`

+ Added many public methods to the libraries.

## Version 0.9.8 released on 30 March 2025

+ API changes:
  + Removed the `containsBody()` method from the `PhysicsSystem` class.
  + Deleted the public "user data" accessors from the `Shape` class.
  + Redeclared the `ConvexHullBuilder` constructor.
  + Changed `VehicleConstraint` to manage the step listener
    like a contained object.
  + Added chaining to 41 public methods.
  + Changed the return types of the `getHits()` methods in the
   `AllHitCollideShapeCollector` and `AllHitRayCastBodyCollector` classes.
  + Corrected the return type of `SoftBodyCreationSettings.getSettings()`.

+ Bugfixes:
  + `Shape` throws an `UnsatisfiedLinkError`
  + `getHit()` doesn't return a new object
  + silently starting extra cleaner threads
  + logic error in MeshShapeSettings.cpp
  + logic errors in `countDebugTriangles()` and `copyDebugTriangles()`
  + bugfix: unimplemented `putEdgeIndices()` method

+ Added classes:
  + `AllHitCastRayCollector`
  + `AllHitCastShapeCollector`
  + `AllHitCollidePointCollector`
  + `AllHitCollideShapeBodyCollector`
  + `AllHitTransformedShapeCollector`
  + `AnyHitCastRayCollector`
  + `AnyHitCastShapeCollector`
  + `AnyHitCollideShapeCollector`
  + `CharacterRefC`
  + `CharacterVirtualRefC`
  + `ClosestHitCollideShapeCollector`
  + `CsrFace`
  + `Float2`
  + `GetTrianglesContext`
  + `PhysicsMaterialRefC`
  + `ScaleHelpers`
  + `ShapeSettingsRefC`
  + `SupportingFace`
  + `TransformedShape`
  + `TransformedShapeCollector`
  + `VertexArray`

+ Added interfaces:
  + `ConstFace`
  + `ConstMotionProperties`
  + `ConstSoftBodyMotionProperties`
  + `ConstSoftBodyVertex`

+ Implemented revision counting for collision shapes.
+ Added many public constructors and methods to the libraries.
+ Defined another desktop platform:  Linux_LoongArch64
+ Updated the Jolt source code and assets to 0373ec0 (=v5.3.0).
+ Updated the jSnapLoader library to v1.1.1-stable .
+ Updated the OSHI library to v6.8.0 .

## Version 0.9.7 released on 6 March 2025

+ API changes:
  + Renamed the `RVec3.addLocal()` method to `addInPlace()`.
  + Added a return value to the `Jolt.newFactory()` method.

+ Bugfixes:
  + Debug builds suppress diagnostic output
  + `product()` methods always return an identity matrix

+ Added 2 desktop platforms:  "Linux64_fma" and "Windows64_avx2".
+ Added the `ConstShapeSettings` interface.
+ Added many public constructors and methods to the libraries.

## Version 0.9.6 released on 20 February 2025

+ API changes:
  + Renamed `CharacterVsCharacterCollisionSimple.getCharactersAsArray()`.
  + Delete 5 trig functions from `Std` in favor of their `Jolt` replacements.

+ Bugfixes:
  + `ClassCastException` in "BoatTest.java" with flavor=Dp
  + native assertion failures at DMat44.h:115
  + out-of-range error in `CharacterVsCharacterCollisionSimple`

+ Switched to LLVM when compiling (but not cross-compiling) on Linux.
+ Added V-HACD v4.1.0 source code to the project.

+ Added classes, enums, and interfaces to the libraries:
  + `CharacterId`
  + `ConstSoftBodyContactSettings`
  + `ConvexHull`
  + `CustomSoftBodyContactListener`
  + `Decomposer`
  + `FillMode`
  + `InvBind`
  + `Parameters`
  + `ProgressListener`
  + `Skinned`
  + `SkinWeight`
  + `SoftBodyContactListener`
  + `SoftBodyContactSettings`
  + `SoftBodyManifold`
  + `SoftBodyMotionProperties`
  + `SoftBodyShape`
  + `SoftBodyValidateResult`
  + `SoftBodyVertex`

+ Added many public methods to the libraries.
+ Updated the Jolt source code and assets to c1bdc5a (=sg250217).

## Version 0.9.5 released on 20 January 2025

+ Bugfix:  heap corruption in `ConvexHullShapeSettings.createSettings()`
+ Bugfix:  `UnsatisfiedLinkError` in `VehicleConstraintSettings` on Windows

+ Added support for 4 Android platforms.
+ Added classes and interfaces to the libraries:
  + `RandomNumberEngine`
  + `SkeletonMapper`
  + `SkeletonMapperRef`
  + `VehicleAntiRollBar`
  + `VehicleCollisonTesterCastSphere`
  + `VehicleCollisonTesterCastSphereRef`
  + `VehicleEngine`
  + `VehicleTransmission`
+ Added many public methods to the libraries.

## Version 0.9.4 released on 6 January 2025

+ API changes:
 + Renamed 25 public static methods in the `Op` class.
 + Split off the `Std` class from the `Jolt` class.
 + Split off the "com.stephengold.joltjni.std" package.
 + Renamed the `UniformRealDistribution` class.
 + Renamed the `Vec3.add()` method.
 + Deleted 3 classes from the libraries:
   + `TrackedVehicleControllerRef`
   + `VehicleControllerRef`
   + `WheeledVehicleControllerRef`
 + Renamed the `Ragdoll.getBodyIDs()` method.
 + Moved the `cDefaultConvexRadius` constant to the `Jolt` class.
 + Altered the value returned by the `AaBox.biggest()` method.
 + Altered the semantics of the `BodyIdVector.get()` method.
 + Altered the signature of the `BodyIdArray.set()` method.
 + Altered the signature of the `SkeletonPose.setSkeleton()` method.
 + Added an argument to the `Part.getToParent()` method.
 + Altered the return type of the `BodyIdArray.get()` method.
 + Altered the default values returned by the `getMaxHeightValue()` and
   `getMinHeightValue()` methods in the `HeightfiedShapeSettings` class.
 + Deleted a redundant `addShape()` method from
   the `CompoundShapeSettings` class.
 + Redesigned the `CharacterVsCharacterCollisionSimple` class.
 + Depublicized the `Wheel.newWheel()` method.

+ Bug fixes:
 + crash due to use-after-free when a `PhysicsSystem` gets cleaned
   before a character or `Ragdoll`
 + premature cleaning of various physics objects
 + crash due to uninitialized data in `ConvexHullBuilder.initialize()`
 + assertion error while instantiating a `PhysicsScene`
 + lossy casts in 4 `PlaneShape` getters
 + `SkeletonPose.getJointMatrices()` returns an invalid pointer
 + methods that should return `null` but are unabled to:
   + `RagdollSettings.createRagdoll()`
   + `RagdollSettingsRef.createRagdoll()`
   + `Contact.getCharacterB()`
 + memory leaks in `ObjectStreamIn`

+ Added classes and enums to the libraries:
  + `AddConvexRadiusSupport`
  + `AdditionalConstraint`
  + `ChbEdge`
  + `ChbFace`
  + `ConvexHullBuilder`
  + `EConstraintOverride`
  + `EResult`
  + `EStreamType`
  + `ESupportMode`
  + `HeightFieldShapeConstants`
  + `Joint`
  + `Mat44Array`
  + `ObjectStreamOut`
  + `PhysicsMaterialSimple`
  + `PhysicsSceneResult`
  + `RagdollResult`
  + `ShapeList`
  + `Sphere`
  + `Stats`
  + `StreamInWrapper`
  + `StringStream`
  + `Support`
  + `SupportBuffer`

+ Added many public constants and public methods to the libraries.
+ Added chaining capability to 7 methods.
+ Updated the Jolt source code and assets to ba8beb8 (=sg250106).

## Version 0.9.3 released on 13 November 2024

+ API changes:
  + Redesigned the `BroadPhase` class.
  + Changed the semantics of `BroadPhaseCastResult.getBodyId()` and
    `ConstBodyCreationSettings.getMassPropertiesOverride()`.
  + Replaced the `va()` method in the `ConstJoltPhysicsObject` interface
    with non-final `targetVa()` .
  + Deleted the `getUserData()` and `setUserData()` methods from
    the `Constraint` class.
  + Finalized the `GroupFilterRef` and `WheelSettingsTvRef` classes.

+ Bug fixes:
  + dynamic linkage failures and unimplemented methods
  + `Vec3.add()` always returns `(0,0,0)`
  + many premature garbage-collection bugs
  + some ref targets instantiated as owners
  + native assert while creating an empty `ConvexHullShapeSettings`
  + crash when a collision group has a `null` filter
  + assertion failure while instantiating `MotorcycleControllerSettings`
  + assertion failures in the `JoltPhysicsObject` constructor
    and `setVirtualAddress()`
  + index out of range in `ConstraintVsCOMChangeTest.Initialize()`
  + `Constraint.newConstraint()` returns the wrong subclass
  + unable to cast `VehicleController`
  + `ConstraintSettings.getControllerType()` can return wrong values
  + `IllegalStateException` in `MotorcycleTest`

+ Added many public methods to the library.
+ Added chaining capability to many methods.
+ Implemented Perlin noise.
+ Added the `ConstTwoBodyConstraint` interface to the library.

+ Added classes to the library:
  + `AllHitCollideShapeCollector`
  + `BodyIdArray`
  + `ClosestPoint`
  + `CollisionDispatch`
  + `PathConstraintPath`
  + `PathConstraintPathRef`
  + `PathConstraintPathHermite`
  + `SubShapeIdCreator`
  + `TwoBodyConstraintRef`
  + `TwoBodyConstraintSettingsRef`
  + `VehicleConstraintRef`

+ Split the "testjoltjni.app.samples" package into many packages.
+ Updated the Jolt source code and assets to 2d7176a (=sg241107).

## Version 0.9.1 released on 30 October 2024

+ Finalized the `ObjectStreamIn` class.
+ Bugfix:  various methods lack a native implementation
+ Bugfix:  some native objects could be freed more than once
+ Bugfix:  some `NonCopyable` native objects are never freed
+ Added the `TempAllocatorImplWithMallocFeedback` class (issue #3)
+ Added many public methods to the library.
+ Further reorganization of the "glue" source files.

## Version 0.9.0 released on 17 October 2024

+ Bugfix:  `Body.setCollisionGroup()` lacks a native implementation
+ Bugfix:  wrong argument type in implementation of `RMat44.setElement()`
+ Bugfix:  2 missing `TRACE_NEW()` macros
+ Renamed the `CollisionGroup.setSubGroupID()` method for style conformance.
+ Removed the `PhysicsSettings.getSystem()` method

+ Added classes to the library:
  + `ClosestHitCastRayCollector`
  + `ClosestHitCastShapeCollector`
  + `Edge`
  + `Face`
  + `IgnoreMultipleBodiesFilter`
  + `MotorcycleController`
  + `MotorcycleControllerSettings`
  + `Mutex`
  + `PathConstraint`
  + `PathConstraintSettings`
  + `PulleyConstraint`
  + `PulleyConstraintSettings`
  + `RackAndPinionConstraint`
  + `RackAndPinionConstraintSettings`
  + `SoftBodyCreationSettings`
  + `SoftBodySharedSettings`
  + `SoftBodySharedSettingsRef`
  + `SwingTwistConstraint`
  + `SwingTwistConstraintSettings`
  + `TrackedVehicleController`
  + `TrackedVehicleControllerRef`
  + `TrackedVehicleControllerSettings`
  + `WheelSettingsTv`
  + `WheelSettingsTvRef`
  + `WheelTv`
  + `Vec4`
  + `VehicleCollisionTesterCastCylinder`
  + `VehicleCollisionTesterCastCylinderRef`
  + `VehicleDifferentialSettings`
  + `VehicleEngineSettings`
  + `VehicleTrackSettings`
  + `VehicleTransmissionSettings`
  + `Vertex`
  + `VertexAttributes`
  + `Volume`

+ Added 3 enums to the library:
  + `EBendType`
  + `ELraType`
  + `ETransmissionMode`

+ Added 4 interfaces to the library:
  + `ConstVertexAttributes`
  + `ConstSoftBodySharedSettings`
  + `ConstSoftBodyCreationSettings`
  + `TriFunction`
  + `Vec4Arg`

+ Added many public methods to the library.

## Version 0.8.0 released on 6 October 2024

+ Moved all methods equivalent to overloaded operators to a new package.
+ Renamed `DrawSettings` to distinguish it from `SkeletonPose::DrawSettings`.
+ Bugfix: `CharacterVirtual.toRef()` returns the wrong kind of reference
+ Bugfix: `StateRecorder` read methods lack the old values

+ Added classes to the library:
  + `AllHitRayCastBodyCollector`
  + `BodyManager`
  + `BodyVector`
  + `BroadPhase`
  + `BroadPhaseBruteForce`
  + `BroadPhaseQuadTree`
  + `CharacterContactListener`
  + `CharacterContactSettings`
  + `CharacterVsCharacterCollision`
  + `CharacterVsCharacterCollisionSimple`
  + `CollisionGroup`
  + `Color`
  + `CustomCharacterContactListener`
  + `CustomPhysicsStepListener`
  + `DefaultBroadPhaseLayerFilter`
  + `DefaultObjectLayerFilter`
  + `DefaultRandomEngine`
  + `EmptyShape`
  + `EmptyShapeSettings`
  + `ExtendedUpdateSettings`
  + `GroupFilter`
  + `GroupFilterTable`
  + `GroupFilterTableRef`
  + `PhysicsMaterialList`
  + `PhysicsStepListener`
  + `PhysicsStepListenerContext`
  + `SkeletonPoseDrawSettings`
  + `StreamIn`
  + `SubShape`
  + `Triangle`
  + `UniformIntDistribution`
  + `VehicleCollisionTester`
  + `VehicleCollisionTesterRay`
  + `VehicleCollisionTesterRayRef`
  + `VehicleConstraint`
  + `VehicleConstraintSettings`
  + `VehicleController`
  + `VehicleControllerSettings`
  + `VehicleControllerSettingsRef`
  + `Wheel`
  + `WheeledVehicleController`
  + `WheeledVehicleControllerRef`
  + `WheeledVehicleControllerSettings`
  + `WheelSettings`
  + `WheelSettingsWv`
  + `WheelSettingsWvRef`
  + `WheelWv`

+ Added 3 enums to the library:
  + `ECastShadow`
  + `ECullMode`
  + `EDrawMode`
+ Added many public methods to the library.
+ Updated the Jolt source code and assets to fed2b6c (=sg240929).

## Version 0.7.0 released on 12 September 2024

+ Moved the `RefTarget` interface to a new package.
+ Package-protected many methods and no-arg constructors.
+ Bugfix:  `_DEBUG` is #defined in Release builds.
+ Bugfix:  initial user data of a shape isn't always zero.

+ Added classes to the library:
  + `Array`
  + `BodyFilter`
  + `BodyLockInterfaceNoLock`
  + `BodyLockRead`
  + `BodyLockWrite`
  + `BroadPhaseCastResult`
  + `BroadPhaseLayerFilter`
  + `BroadPhaseQuery`
  + `CastRayCollector`
  + `CastShapeCollector`
  + `CollidePointCollector`
  + `CollidePointResult`
  + `CollideSettingsBase`
  + `CollideShapeBodyCollector`
  + `CollideShapeCollector`
  + `CollideShapeSettings`
  + `CustomCastShapeCollector`
  + `CustomCollidePointCollector`
  + `CustomCollideShapeBodyCollector`
  + `CustomCollideShapeCollector`
  + `CustomRayCastBodyCollector`
  + `CustomRayCastCollector`
  + `DebugRenderer`
  + `DebugRendererRecorder`
  + `DrawSettings`
  + `EStateRecorderState`
  + `JointState`
  + `Mt19937`
  + `NarrowPhaseQuery`
  + `NarrowPhaseStat`
  + `ObjectLayerFilter`
  + `ObjectStreamIn`
  + `OffsetCenterOfMassShapeSettings`
  + `OffsetCenterOfMassShape`
  + `Part`
  + `PhysicsScene`
  + `PhysicsSceneRef`
  + `Ragdoll`
  + `RagdollRef`
  + `RagdollSettings`
  + `RagdollSettingsRef`
  + `RayCast`
  + `RayCastBodyCollector`
  + `RayCastResult`
  + `RayCastSettings`
  + `Ref`
  + `RRayCast`
  + `RShapeCast`
  + `ShapeCastResult`
  + `ShapeCastSettings`
  + `ShapeFilter`
  + `Skeleton`
  + `SkeletonAnimation`
  + `SkeletonAnimationRef`
  + `SkeletonPose
  + `SkeletonRef`
  + `SpecifiedBroadPhaseLayerFilter`
  + `SpecifiedObjectLayerFilter`
  + `StreamOut`
  + `StreamOutWrapper`
  + `StateRecorder`
  + `StateRecorderFilter`
  + `StateRecorderImpl`
  + `TaperedCapsuleShape`
  + `TaperedCapsuleShapeSettings`
  + `TaperedCylinderShape`
  + `TaperedCylinderShapeSettings`
  + `UniformRealDistribution`

+ Added 2 enums to the library:
  + `EActiveEdgeMode`
  + `ECollectFacesMode`

+ Added many public methods to the library.
+ Added package-level javadoc.
+ Merged `Ref<>` implementations into corresponding `RefTarget` source files.
+ Updated Jolt source code and assets to 5da6ac8 (=sg240907).

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