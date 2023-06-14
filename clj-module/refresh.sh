#!/usr/bin/env bash

rm -rf lib
mkdir lib
cd .. && gradle clean build &&
cd clj-module && cp ../build/libs/Dumortierite-0.1.0.jar lib/
