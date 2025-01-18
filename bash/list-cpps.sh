#!/bin/bash

/usr/bin/find ./src/main/native/glue -name '*.cpp' -printf '$(N)/glue/%P \\\n' \
| /usr/bin/sort

/usr/bin/find ./src/main/native/Jolt -name '*.cpp' -printf '$(N)/Jolt/%P \\\n' \
| /usr/bin/sort

/usr/bin/find ./src/main/native -name '*.inl' -printf '$(N)/%P \\\n' \
| /usr/bin/sort

echo '$(N)/TestFramework/External/Perlin.cpp'
