LOCAL_PATH := $(call my-dir)
N := $(LOCAL_PATH)/src/main/native

include $(CLEAR_VARS)

LOCAL_C_INCLUDES := \
$(N)/auto \
$(N) \
$(N)/TestFramework

LOCAL_CFLAGS := \
-DANDROID_STL=c++_static \
-DANDROID_TOOLCHAIN=clang \
-DJPH_OBJECT_STREAM \
-Werror=return-type \
-std=c++17

LOCAL_CPP_EXTENSION := .cpp
LOCAL_MODULE := joltjni

LOCAL_SRC_FILES := \
$(N)/glue/a/AaBox.cpp \
$(N)/glue/a/AddConvexRadiusSupport.cpp \
$(N)/glue/a/AdditionalConstraint.cpp \
$(N)/glue/a/AllHitCastRayCollector.cpp \
$(N)/glue/a/AllHitCastShapeCollector.cpp \
$(N)/glue/a/AllHitCollidePointCollector.cpp \
$(N)/glue/a/AllHitCollideShapeBodyCollector.cpp \
$(N)/glue/a/AllHitCollideShapeCollector.cpp \
$(N)/glue/a/AllHitRayCastBodyCollector.cpp \
$(N)/glue/a/AllHitTransformedShapeCollector.cpp \
$(N)/glue/a/AnyHitCastRayCollector.cpp \
$(N)/glue/a/AnyHitCastShapeCollector.cpp \
$(N)/glue/a/AnyHitCollideShapeCollector.cpp \
$(N)/glue/b/BroadPhaseBruteForce.cpp \
$(N)/glue/b/BroadPhaseCastResult.cpp \
$(N)/glue/b/BroadPhase.cpp \
$(N)/glue/b/BroadPhaseLayerFilter.cpp \
$(N)/glue/b/BroadPhaseLayerInterface.cpp \
$(N)/glue/b/BroadPhaseQuadTree.cpp \
$(N)/glue/b/BroadPhaseQuery.cpp \
$(N)/glue/bo/BodyActivationListener.cpp \
$(N)/glue/bo/Body.cpp \
$(N)/glue/bo/BodyCreationSettings.cpp \
$(N)/glue/bo/BodyFilter.cpp \
$(N)/glue/bo/BodyIdArray.cpp \
$(N)/glue/bo/BodyId.cpp \
$(N)/glue/bo/BodyIdVector.cpp \
$(N)/glue/bo/BodyInterface.cpp \
$(N)/glue/bo/BodyLockInterfaceLocking.cpp \
$(N)/glue/bo/BodyLockInterfaceNoLock.cpp \
$(N)/glue/bo/BodyLockRead.cpp \
$(N)/glue/bo/BodyLockWrite.cpp \
$(N)/glue/bo/BodyManager.cpp \
$(N)/glue/bo/BodyManagerDrawSettings.cpp \
$(N)/glue/bo/BodyVector.cpp \
$(N)/glue/bo/BoxShape.cpp \
$(N)/glue/bo/BoxShapeSettings.cpp \
$(N)/glue/c/CapsuleShape.cpp \
$(N)/glue/c/CapsuleShapeSettings.cpp \
$(N)/glue/c/CastRayCollector.cpp \
$(N)/glue/c/CastShapeCollector.cpp \
$(N)/glue/c/ClosestHitCastRayCollector.cpp \
$(N)/glue/c/ClosestHitCastShapeCollector.cpp \
$(N)/glue/c/ClosestHitCollideShapeCollector.cpp \
$(N)/glue/c/ClosestPoint.cpp \
$(N)/glue/c/CylinderShape.cpp \
$(N)/glue/c/CylinderShapeSettings.cpp \
$(N)/glue/ch/CharacterBase.cpp \
$(N)/glue/ch/CharacterBaseSettings.cpp \
$(N)/glue/ch/CharacterContactSettings.cpp \
$(N)/glue/ch/Character.cpp \
$(N)/glue/ch/CharacterId.cpp \
$(N)/glue/ch/CharacterRefC.cpp \
$(N)/glue/ch/CharacterSettings.cpp \
$(N)/glue/ch/CharacterVirtual.cpp \
$(N)/glue/ch/CharacterVirtualRefC.cpp \
$(N)/glue/ch/CharacterVirtualSettings.cpp \
$(N)/glue/ch/CharacterVsCharacterCollision.cpp \
$(N)/glue/ch/CharacterVsCharacterCollisionSimple.cpp \
$(N)/glue/ch/ChbEdge.cpp \
$(N)/glue/ch/ChbFace.cpp \
$(N)/glue/co/CombineFunction.cpp \
$(N)/glue/co/CompoundShape.cpp \
$(N)/glue/co/CompoundShapeSettings.cpp \
$(N)/glue/co/ConeConstraint.cpp \
$(N)/glue/co/ConeConstraintSettings.cpp \
$(N)/glue/co/Constraint.cpp \
$(N)/glue/co/Constraints.cpp \
$(N)/glue/co/ConstraintSettings.cpp \
$(N)/glue/co/Contact.cpp \
$(N)/glue/co/ContactList.cpp \
$(N)/glue/co/ContactListener.cpp \
$(N)/glue/co/ContactManifold.cpp \
$(N)/glue/co/ContactSettings.cpp \
$(N)/glue/co/ConvexHullBuilder.cpp \
$(N)/glue/co/ConvexHull.cpp \
$(N)/glue/co/ConvexHullShape.cpp \
$(N)/glue/co/ConvexHullShapeSettings.cpp \
$(N)/glue/co/ConvexShape.cpp \
$(N)/glue/co/ConvexShapeSettings.cpp \
$(N)/glue/col/CollidePointCollector.cpp \
$(N)/glue/col/CollidePointResult.cpp \
$(N)/glue/col/CollideSettingsBase.cpp \
$(N)/glue/col/CollideShapeBodyCollector.cpp \
$(N)/glue/col/CollideShapeCollector.cpp \
$(N)/glue/col/CollideShapeResult.cpp \
$(N)/glue/col/CollideShapeSettings.cpp \
$(N)/glue/col/CollisionDispatch.cpp \
$(N)/glue/col/CollisionGroup.cpp \
$(N)/glue/cu/CustomBodyActivationListener.cpp \
$(N)/glue/cu/CustomCastRayCollector.cpp \
$(N)/glue/cu/CustomCastShapeCollector.cpp \
$(N)/glue/cu/CustomCharacterContactListener.cpp \
$(N)/glue/cu/CustomCollidePointCollector.cpp \
$(N)/glue/cu/CustomCollideShapeBodyCollector.cpp \
$(N)/glue/cu/CustomCollideShapeCollector.cpp \
$(N)/glue/cu/CustomContactListener.cpp \
$(N)/glue/cu/CustomPhysicsStepListener.cpp \
$(N)/glue/cu/CustomRayCastBodyCollector.cpp \
$(N)/glue/cu/CustomSoftBodyContactListener.cpp \
$(N)/glue/d/DebugRenderer.cpp \
$(N)/glue/d/DebugRendererRecorder.cpp \
$(N)/glue/d/Decomposer.cpp \
$(N)/glue/d/DecoratedShape.cpp \
$(N)/glue/d/DefaultBroadPhaseLayerFilter.cpp \
$(N)/glue/d/DefaultObjectLayerFilter.cpp \
$(N)/glue/d/DefaultRandomEngine.cpp \
$(N)/glue/d/DistanceConstraint.cpp \
$(N)/glue/d/DistanceConstraintSettings.cpp \
$(N)/glue/e/Edge.cpp \
$(N)/glue/e/EmptyShape.cpp \
$(N)/glue/e/EmptyShapeSettings.cpp \
$(N)/glue/e/ExtendedUpdateSettings.cpp \
$(N)/glue/f/Face.cpp \
$(N)/glue/f/FixedConstraintSettings.cpp \
$(N)/glue/g/GearConstraint.cpp \
$(N)/glue/g/GearConstraintSettings.cpp \
$(N)/glue/g/GetTrianglesContext.cpp \
$(N)/glue/g/GroupFilter.cpp \
$(N)/glue/g/GroupFilterTable.cpp \
$(N)/glue/h/HeightFieldShape.cpp \
$(N)/glue/h/HeightFieldShapeSettings.cpp \
$(N)/glue/h/HingeConstraint.cpp \
$(N)/glue/h/HingeConstraintSettings.cpp \
$(N)/glue/i/IgnoreMultipleBodiesFilter.cpp \
$(N)/glue/i/IndexedTriangle.cpp \
$(N)/glue/i/IndexedTriangleList.cpp \
$(N)/glue/i/IndexedTriangleNoMaterial.cpp \
$(N)/glue/i/InvBind.cpp \
$(N)/glue/j/JobSystem.cpp \
$(N)/glue/j/JobSystemSingleThreaded.cpp \
$(N)/glue/j/JobSystemThreadPool.cpp \
$(N)/glue/j/Joint.cpp \
$(N)/glue/j/JointState.cpp \
$(N)/glue/j/Jolt.cpp \
$(N)/glue/m/MapObj2Bp.cpp \
$(N)/glue/m/MassProperties.cpp \
$(N)/glue/m/Mat44Array.cpp \
$(N)/glue/m/Mat44.cpp \
$(N)/glue/m/MeshShape.cpp \
$(N)/glue/m/MeshShapeSettings.cpp \
$(N)/glue/m/MotionProperties.cpp \
$(N)/glue/m/MotorcycleController.cpp \
$(N)/glue/m/MotorcycleControllerSettings.cpp \
$(N)/glue/m/MotorSettings.cpp \
$(N)/glue/m/Mt19937.cpp \
$(N)/glue/m/MutableCompoundShape.cpp \
$(N)/glue/m/MutableCompoundShapeSettings.cpp \
$(N)/glue/m/Mutex.cpp \
$(N)/glue/n/NarrowPhaseQuery.cpp \
$(N)/glue/n/NarrowPhaseStat.cpp \
$(N)/glue/o/ObjectLayerFilter.cpp \
$(N)/glue/o/ObjectLayerPairFilter.cpp \
$(N)/glue/o/ObjectStreamIn.cpp \
$(N)/glue/o/ObjectStreamOut.cpp \
$(N)/glue/o/ObjectVsBroadPhaseLayerFilter.cpp \
$(N)/glue/o/ObjVsBpFilter.cpp \
$(N)/glue/o/ObjVsObjFilter.cpp \
$(N)/glue/o/OffsetCenterOfMassShape.cpp \
$(N)/glue/o/OffsetCenterOfMassShapeSettings.cpp \
$(N)/glue/ph/PhysicsMaterial.cpp \
$(N)/glue/ph/PhysicsMaterialList.cpp \
$(N)/glue/ph/PhysicsMaterialRefC.cpp \
$(N)/glue/ph/PhysicsMaterialSimple.cpp \
$(N)/glue/ph/PhysicsScene.cpp \
$(N)/glue/ph/PhysicsSceneResult.cpp \
$(N)/glue/ph/PhysicsSettings.cpp \
$(N)/glue/ph/PhysicsStepListenerContext.cpp \
$(N)/glue/ph/PhysicsSystem.cpp \
$(N)/glue/p/Parameters.cpp \
$(N)/glue/p/Part.cpp \
$(N)/glue/p/PathConstraint.cpp \
$(N)/glue/p/PathConstraintPath.cpp \
$(N)/glue/p/PathConstraintPathHermite.cpp \
$(N)/glue/p/PathConstraintSettings.cpp \
$(N)/glue/p/PlaneShape.cpp \
$(N)/glue/p/PlaneShapeSettings.cpp \
$(N)/glue/p/PointConstraintSettings.cpp \
$(N)/glue/p/PulleyConstraintSettings.cpp \
$(N)/glue/r/RackAndPinionConstraint.cpp \
$(N)/glue/r/RackAndPinionConstraintSettings.cpp \
$(N)/glue/r/Ragdoll.cpp \
$(N)/glue/r/RagdollResult.cpp \
$(N)/glue/r/RagdollSettings.cpp \
$(N)/glue/r/RayCastBodyCollector.cpp \
$(N)/glue/r/RayCast.cpp \
$(N)/glue/r/RayCastResult.cpp \
$(N)/glue/r/RayCastSettings.cpp \
$(N)/glue/r/RMat44.cpp \
$(N)/glue/r/RotatedTranslatedShape.cpp \
$(N)/glue/r/RotatedTranslatedShapeSettings.cpp \
$(N)/glue/r/RRayCast.cpp \
$(N)/glue/r/RShapeCast.cpp \
$(N)/glue/sh/ShapeCastResult.cpp \
$(N)/glue/sh/ShapeCastSettings.cpp \
$(N)/glue/sh/Shape.cpp \
$(N)/glue/sh/ShapeFilter.cpp \
$(N)/glue/sh/ShapeList.cpp \
$(N)/glue/sh/ShapeRefC.cpp \
$(N)/glue/sh/ShapeResult.cpp \
$(N)/glue/sh/ShapeSettings.cpp \
$(N)/glue/sh/ShapeSettingsRefC.cpp \
$(N)/glue/sh/SharedMutexBase.cpp \
$(N)/glue/s/ScaledShape.cpp \
$(N)/glue/s/ScaledShapeSettings.cpp \
$(N)/glue/s/SixDofConstraint.cpp \
$(N)/glue/s/SixDofConstraintSettings.cpp \
$(N)/glue/s/SkeletalAnimation.cpp \
$(N)/glue/s/Skeleton.cpp \
$(N)/glue/s/SkeletonMapper.cpp \
$(N)/glue/s/SkeletonPose.cpp \
$(N)/glue/s/SkeletonPoseDrawSettings.cpp \
$(N)/glue/s/Skinned.cpp \
$(N)/glue/s/SkinWeight.cpp \
$(N)/glue/s/SliderConstraint.cpp \
$(N)/glue/s/SliderConstraintSettings.cpp \
$(N)/glue/s/SoftBodyContactListener.cpp \
$(N)/glue/s/SoftBodyContactSettings.cpp \
$(N)/glue/s/SoftBodyCreationSettings.cpp \
$(N)/glue/s/SoftBodyManifold.cpp \
$(N)/glue/s/SoftBodyMotionProperties.cpp \
$(N)/glue/s/SoftBodySharedSettings.cpp \
$(N)/glue/s/SoftBodyVertex.cpp \
$(N)/glue/s/SpecifiedBroadPhaseLayerFilter.cpp \
$(N)/glue/s/SpecifiedObjectLayerFilter.cpp \
$(N)/glue/s/Sphere.cpp \
$(N)/glue/s/SphereShape.cpp \
$(N)/glue/s/SphereShapeSettings.cpp \
$(N)/glue/s/SpringSettings.cpp \
$(N)/glue/s/SubShape.cpp \
$(N)/glue/s/SubShapeId.cpp \
$(N)/glue/s/SubShapeIdCreator.cpp \
$(N)/glue/s/SubShapeIdPair.cpp \
$(N)/glue/s/SupportBuffer.cpp \
$(N)/glue/s/SupportingFace.cpp \
$(N)/glue/s/SwingTwistConstraint.cpp \
$(N)/glue/s/SwingTwistConstraintSettings.cpp \
$(N)/glue/st/StateRecorder.cpp \
$(N)/glue/st/StateRecorderFilter.cpp \
$(N)/glue/st/StateRecorderImpl.cpp \
$(N)/glue/st/StaticCompoundShapeSettings.cpp \
$(N)/glue/st/Stats.cpp \
$(N)/glue/st/Std.cpp \
$(N)/glue/st/StreamIn.cpp \
$(N)/glue/st/StreamInWrapper.cpp \
$(N)/glue/st/StreamOut.cpp \
$(N)/glue/st/StreamOutWrapper.cpp \
$(N)/glue/st/StringStream.cpp \
$(N)/glue/t/TaperedCapsuleShape.cpp \
$(N)/glue/t/TaperedCapsuleShapeSettings.cpp \
$(N)/glue/t/TaperedCylinderShape.cpp \
$(N)/glue/t/TaperedCylinderShapeSettings.cpp \
$(N)/glue/t/TempAllocator.cpp \
$(N)/glue/t/TempAllocatorImpl.cpp \
$(N)/glue/t/TempAllocatorImplWithMallocFallback.cpp \
$(N)/glue/t/TempAllocatorMalloc.cpp \
$(N)/glue/t/TrackedVehicleController.cpp \
$(N)/glue/t/TrackedVehicleControllerSettings.cpp \
$(N)/glue/t/TransformedShapeCollector.cpp \
$(N)/glue/t/TransformedShape.cpp \
$(N)/glue/t/Triangle.cpp \
$(N)/glue/t/TriangleShape.cpp \
$(N)/glue/t/TriangleShapeSettings.cpp \
$(N)/glue/t/TwoBodyConstraint.cpp \
$(N)/glue/t/TwoBodyConstraintSettings.cpp \
$(N)/glue/u/UniformFloatDistribution.cpp \
$(N)/glue/u/UniformIntDistribution.cpp \
$(N)/glue/v/VehicleAntiRollBar.cpp \
$(N)/glue/v/VehicleCollisionTesterCastCylinder.cpp \
$(N)/glue/v/VehicleCollisionTesterCastSphere.cpp \
$(N)/glue/v/VehicleCollisionTester.cpp \
$(N)/glue/v/VehicleCollisionTesterRay.cpp \
$(N)/glue/v/VehicleConstraint.cpp \
$(N)/glue/v/VehicleConstraintSettings.cpp \
$(N)/glue/v/VehicleController.cpp \
$(N)/glue/v/VehicleControllerSettings.cpp \
$(N)/glue/v/VehicleDifferentialSettings.cpp \
$(N)/glue/v/VehicleEngine.cpp \
$(N)/glue/v/VehicleEngineSettings.cpp \
$(N)/glue/v/VehicleStepListener.cpp \
$(N)/glue/v/VehicleTrackSettings.cpp \
$(N)/glue/v/VehicleTransmission.cpp \
$(N)/glue/v/VehicleTransmissionSettings.cpp \
$(N)/glue/v/VertexAttributes.cpp \
$(N)/glue/v/Vertex.cpp \
$(N)/glue/v/Volume.cpp \
$(N)/glue/w/Wheel.cpp \
$(N)/glue/w/WheeledVehicleController.cpp \
$(N)/glue/w/WheeledVehicleControllerSettings.cpp \
$(N)/glue/w/WheelSettings.cpp \
$(N)/glue/w/WheelSettingsTv.cpp \
$(N)/glue/w/WheelSettingsWv.cpp \
$(N)/Jolt/AABBTree/AABBTreeBuilder.cpp \
$(N)/Jolt/Core/Color.cpp \
$(N)/Jolt/Core/Factory.cpp \
$(N)/Jolt/Core/IssueReporting.cpp \
$(N)/Jolt/Core/JobSystemSingleThreaded.cpp \
$(N)/Jolt/Core/JobSystemThreadPool.cpp \
$(N)/Jolt/Core/JobSystemWithBarrier.cpp \
$(N)/Jolt/Core/LinearCurve.cpp \
$(N)/Jolt/Core/Memory.cpp \
$(N)/Jolt/Core/Profiler.cpp \
$(N)/Jolt/Core/RTTI.cpp \
$(N)/Jolt/Core/Semaphore.cpp \
$(N)/Jolt/Core/StringTools.cpp \
$(N)/Jolt/Core/TickCounter.cpp \
$(N)/Jolt/Geometry/ConvexHullBuilder2D.cpp \
$(N)/Jolt/Geometry/ConvexHullBuilder.cpp \
$(N)/Jolt/Geometry/Indexify.cpp \
$(N)/Jolt/Geometry/OrientedBox.cpp \
$(N)/Jolt/Math/Vec3.cpp \
$(N)/Jolt/ObjectStream/ObjectStreamBinaryIn.cpp \
$(N)/Jolt/ObjectStream/ObjectStreamBinaryOut.cpp \
$(N)/Jolt/ObjectStream/ObjectStream.cpp \
$(N)/Jolt/ObjectStream/ObjectStreamIn.cpp \
$(N)/Jolt/ObjectStream/ObjectStreamOut.cpp \
$(N)/Jolt/ObjectStream/ObjectStreamTextIn.cpp \
$(N)/Jolt/ObjectStream/ObjectStreamTextOut.cpp \
$(N)/Jolt/ObjectStream/SerializableObject.cpp \
$(N)/Jolt/ObjectStream/TypeDeclarations.cpp \
$(N)/Jolt/Physics/Body/Body.cpp \
$(N)/Jolt/Physics/Body/BodyCreationSettings.cpp \
$(N)/Jolt/Physics/Body/BodyInterface.cpp \
$(N)/Jolt/Physics/Body/BodyManager.cpp \
$(N)/Jolt/Physics/Body/MassProperties.cpp \
$(N)/Jolt/Physics/Body/MotionProperties.cpp \
$(N)/Jolt/Physics/Character/CharacterBase.cpp \
$(N)/Jolt/Physics/Character/Character.cpp \
$(N)/Jolt/Physics/Character/CharacterVirtual.cpp \
$(N)/Jolt/Physics/Collision/BroadPhase/BroadPhaseBruteForce.cpp \
$(N)/Jolt/Physics/Collision/BroadPhase/BroadPhase.cpp \
$(N)/Jolt/Physics/Collision/BroadPhase/BroadPhaseQuadTree.cpp \
$(N)/Jolt/Physics/Collision/BroadPhase/QuadTree.cpp \
$(N)/Jolt/Physics/Collision/CastConvexVsTriangles.cpp \
$(N)/Jolt/Physics/Collision/CastSphereVsTriangles.cpp \
$(N)/Jolt/Physics/Collision/CollideConvexVsTriangles.cpp \
$(N)/Jolt/Physics/Collision/CollideSphereVsTriangles.cpp \
$(N)/Jolt/Physics/Collision/CollisionDispatch.cpp \
$(N)/Jolt/Physics/Collision/CollisionGroup.cpp \
$(N)/Jolt/Physics/Collision/EstimateCollisionResponse.cpp \
$(N)/Jolt/Physics/Collision/GroupFilter.cpp \
$(N)/Jolt/Physics/Collision/GroupFilterTable.cpp \
$(N)/Jolt/Physics/Collision/ManifoldBetweenTwoFaces.cpp \
$(N)/Jolt/Physics/Collision/NarrowPhaseQuery.cpp \
$(N)/Jolt/Physics/Collision/NarrowPhaseStats.cpp \
$(N)/Jolt/Physics/Collision/PhysicsMaterial.cpp \
$(N)/Jolt/Physics/Collision/PhysicsMaterialSimple.cpp \
$(N)/Jolt/Physics/Collision/Shape/BoxShape.cpp \
$(N)/Jolt/Physics/Collision/Shape/CapsuleShape.cpp \
$(N)/Jolt/Physics/Collision/Shape/CompoundShape.cpp \
$(N)/Jolt/Physics/Collision/Shape/ConvexHullShape.cpp \
$(N)/Jolt/Physics/Collision/Shape/ConvexShape.cpp \
$(N)/Jolt/Physics/Collision/Shape/CylinderShape.cpp \
$(N)/Jolt/Physics/Collision/Shape/DecoratedShape.cpp \
$(N)/Jolt/Physics/Collision/Shape/EmptyShape.cpp \
$(N)/Jolt/Physics/Collision/Shape/HeightFieldShape.cpp \
$(N)/Jolt/Physics/Collision/Shape/MeshShape.cpp \
$(N)/Jolt/Physics/Collision/Shape/MutableCompoundShape.cpp \
$(N)/Jolt/Physics/Collision/Shape/OffsetCenterOfMassShape.cpp \
$(N)/Jolt/Physics/Collision/Shape/PlaneShape.cpp \
$(N)/Jolt/Physics/Collision/Shape/RotatedTranslatedShape.cpp \
$(N)/Jolt/Physics/Collision/Shape/ScaledShape.cpp \
$(N)/Jolt/Physics/Collision/Shape/Shape.cpp \
$(N)/Jolt/Physics/Collision/Shape/SphereShape.cpp \
$(N)/Jolt/Physics/Collision/Shape/StaticCompoundShape.cpp \
$(N)/Jolt/Physics/Collision/Shape/TaperedCapsuleShape.cpp \
$(N)/Jolt/Physics/Collision/Shape/TaperedCylinderShape.cpp \
$(N)/Jolt/Physics/Collision/Shape/TriangleShape.cpp \
$(N)/Jolt/Physics/Collision/TransformedShape.cpp \
$(N)/Jolt/Physics/Constraints/ConeConstraint.cpp \
$(N)/Jolt/Physics/Constraints/Constraint.cpp \
$(N)/Jolt/Physics/Constraints/ConstraintManager.cpp \
$(N)/Jolt/Physics/Constraints/ContactConstraintManager.cpp \
$(N)/Jolt/Physics/Constraints/DistanceConstraint.cpp \
$(N)/Jolt/Physics/Constraints/FixedConstraint.cpp \
$(N)/Jolt/Physics/Constraints/GearConstraint.cpp \
$(N)/Jolt/Physics/Constraints/HingeConstraint.cpp \
$(N)/Jolt/Physics/Constraints/MotorSettings.cpp \
$(N)/Jolt/Physics/Constraints/PathConstraint.cpp \
$(N)/Jolt/Physics/Constraints/PathConstraintPath.cpp \
$(N)/Jolt/Physics/Constraints/PathConstraintPathHermite.cpp \
$(N)/Jolt/Physics/Constraints/PointConstraint.cpp \
$(N)/Jolt/Physics/Constraints/PulleyConstraint.cpp \
$(N)/Jolt/Physics/Constraints/RackAndPinionConstraint.cpp \
$(N)/Jolt/Physics/Constraints/SixDOFConstraint.cpp \
$(N)/Jolt/Physics/Constraints/SliderConstraint.cpp \
$(N)/Jolt/Physics/Constraints/SpringSettings.cpp \
$(N)/Jolt/Physics/Constraints/SwingTwistConstraint.cpp \
$(N)/Jolt/Physics/Constraints/TwoBodyConstraint.cpp \
$(N)/Jolt/Physics/DeterminismLog.cpp \
$(N)/Jolt/Physics/IslandBuilder.cpp \
$(N)/Jolt/Physics/LargeIslandSplitter.cpp \
$(N)/Jolt/Physics/PhysicsScene.cpp \
$(N)/Jolt/Physics/PhysicsSystem.cpp \
$(N)/Jolt/Physics/PhysicsUpdateContext.cpp \
$(N)/Jolt/Physics/Ragdoll/Ragdoll.cpp \
$(N)/Jolt/Physics/SoftBody/SoftBodyCreationSettings.cpp \
$(N)/Jolt/Physics/SoftBody/SoftBodyMotionProperties.cpp \
$(N)/Jolt/Physics/SoftBody/SoftBodyShape.cpp \
$(N)/Jolt/Physics/SoftBody/SoftBodySharedSettings.cpp \
$(N)/Jolt/Physics/StateRecorderImpl.cpp \
$(N)/Jolt/Physics/Vehicle/MotorcycleController.cpp \
$(N)/Jolt/Physics/Vehicle/TrackedVehicleController.cpp \
$(N)/Jolt/Physics/Vehicle/VehicleAntiRollBar.cpp \
$(N)/Jolt/Physics/Vehicle/VehicleCollisionTester.cpp \
$(N)/Jolt/Physics/Vehicle/VehicleConstraint.cpp \
$(N)/Jolt/Physics/Vehicle/VehicleController.cpp \
$(N)/Jolt/Physics/Vehicle/VehicleDifferential.cpp \
$(N)/Jolt/Physics/Vehicle/VehicleEngine.cpp \
$(N)/Jolt/Physics/Vehicle/VehicleTrack.cpp \
$(N)/Jolt/Physics/Vehicle/VehicleTransmission.cpp \
$(N)/Jolt/Physics/Vehicle/Wheel.cpp \
$(N)/Jolt/Physics/Vehicle/WheeledVehicleController.cpp \
$(N)/Jolt/RegisterTypes.cpp \
$(N)/Jolt/Renderer/DebugRenderer.cpp \
$(N)/Jolt/Renderer/DebugRendererPlayback.cpp \
$(N)/Jolt/Renderer/DebugRendererRecorder.cpp \
$(N)/Jolt/Renderer/DebugRendererSimple.cpp \
$(N)/Jolt/Skeleton/SkeletalAnimation.cpp \
$(N)/Jolt/Skeleton/Skeleton.cpp \
$(N)/Jolt/Skeleton/SkeletonMapper.cpp \
$(N)/Jolt/Skeleton/SkeletonPose.cpp \
$(N)/Jolt/TriangleSplitter/TriangleSplitterBinning.cpp \
$(N)/Jolt/TriangleSplitter/TriangleSplitter.cpp \
$(N)/Jolt/TriangleSplitter/TriangleSplitterMean.cpp \
$(N)/TestFramework/External/Perlin.cpp

include $(BUILD_SHARED_LIBRARY)

###
