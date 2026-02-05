#!/bin/bash

## A bash script to compile Jolt's compute shaders (.hlsl files)
## to Apple's intermediate representation (.air files)

set -euo pipefail

[[ $VULKAN_SDK ]]
echo VULKAN_SDK = $VULKAN_SDK

./gradlew unpackJoltSource

SRC=./src/main/native/Jolt/Shaders
DEST=./src/main/resources/metal/com/github/stephengold

mkdir -p ${DEST}

LIST=$(cd ${SRC} ; find -- *.hlsl -maxdepth 1 | awk '{sub(/.hlsl/,"");print}')
for NAME in ${LIST}
do
    echo "  compiling ${NAME}.hlsl to ${NAME}.spv"
    "${VULKAN_SDK}/bin/dxc" -E main -T cs_6_0 -I ${SRC} -WX -O3 -all_resources_bound \
            "${SRC}/$NAME.hlsl" -spirv -fvk-use-dx-layout -fspv-entrypoint-name=${NAME} -Fo "${NAME}.spv"

    echo "   compiling ${NAME}.spv to ${NAME}.mtl"
    "${VULKAN_SDK}/bin/spirv-cross" "${NAME}.spv" --msl --output "${NAME}.mtl"
    rm ${NAME}.spv

    echo "    compiling ${NAME}.mtl to ${NAME}.air"
    xcrun -sdk macos metal -c "${NAME}.mtl" -o "${DEST}/${NAME}.air"
    rm ${NAME}.mtl
done
