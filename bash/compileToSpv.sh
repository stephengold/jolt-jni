#!/bin/bash

## A bash script to compile Jolt's compute shaders (.hlsl files)
## to Khronos's SPIR-V intermediate representation (.spv files)

set -euo pipefail

[[ ${VULKAN_SDK} ]]
echo VULKAN_SDK = ${VULKAN_SDK}

./gradlew unpackJoltSource

SRC=./src/main/native/Jolt/Shaders
DEST=./src/main/resources/vk/com/github/stephengold

mkdir -p ${DEST}

LIST=$(cd ${SRC} ; find -- *.hlsl -maxdepth 1 | awk '{sub(/.hlsl/,"");print}')
for NAME in ${LIST}
do
    echo "  compiling ${NAME}.hlsl -> ${NAME}.spv"
    "${VULKAN_SDK}/bin/dxc" -E main -T cs_6_0 -I ${SRC} -WX -O3 -all_resources_bound \
            "${SRC}/${NAME}.hlsl" -spirv -fvk-use-dx-layout \
            -Fo "${DEST}/${NAME}.spv"
done
