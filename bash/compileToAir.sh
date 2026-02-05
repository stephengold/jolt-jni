#!/bin/bash

## A bash script to compile Jolt's compute shaders (.hlsl files)
## to Apple's intermediate representation (.air files)

set -euo pipefail

[[ ${VULKAN_SDK} ]]
echo VULKAN_SDK = ${VULKAN_SDK}

./gradlew unpackJoltSource

SRC=./src/main/native/Jolt/Shaders
TMP=./build/metal
DEST=./src/main/resources/mtl/com/github/stephengold

mkdir -p ${TMP}
mkdir -p ${DEST}

LIST=$(cd ${SRC} ; find -- *.hlsl -maxdepth 1 | awk '{sub(/.hlsl/,"");print}')
for NAME in ${LIST}
do
    echo "  compiling ${NAME}.hlsl -> ${NAME}.spv"
    "${VULKAN_SDK}/bin/dxc" -E main -T cs_6_0 -I ${SRC} -WX -O3 -all_resources_bound \
            "${SRC}/${NAME}.hlsl" -spirv -fvk-use-dx-layout \
            -fspv-entrypoint-name=${NAME} -Fo "${TMP}/${NAME}.spv"

    echo "   compiling ${NAME}.spv -> ${NAME}.metal"
    "${VULKAN_SDK}/bin/spirv-cross" "${TMP}/${NAME}.spv" --msl --output "${TMP}/${NAME}.metal"

    echo "    compiling ${NAME}.metal -> ${NAME}.air"
    xcrun -sdk macosx26.2 metal -c "${TMP}/${NAME}.metal" -o "${DEST}/${NAME}.air"
done
