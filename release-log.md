# release log for the jolt-jni project

## Version 0.9.1 released on 30 October 2024

+ Finalized the `ObjectStreamIn` class.
+ Bugfix:  various methods lack a native implementation
+ Bugfix:  some native objects could be freed more than once
+ Bugfix:  some `NonCopyable` native objects are never freed
+ Added the `TempAllocatorImplWithMallocFeedback` class (issue #3)
+ Added many public methods to the library.
+ Futher reorganization of the "glue" source files.

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