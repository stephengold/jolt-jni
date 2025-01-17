#!/bin/bash

/usr/bin/find ./src/main/native -name '*.cpp' -printf '$(N)/%P \\\n' \
| /usr/bin/sort

/usr/bin/find ./src/main/native -name '*.inl' -printf '$(N)/%P \\\n' \
| /usr/bin/sort
