#!/bin/sh

if [ -z ${ANDROID_SDK_ROOT} ]; then
    echo ANDROID_SDK_ROOT not found
    echo attempting to use ANDROID_STUDIO_SDK
    export ANDROID_SDK_ROOT=$ANDROID_STUDIO_SDK
fi

yes | $ANDROID_SDK_ROOT/tools/bin/sdkmanager --licenses

./gradlew -DANDROID_SDK_ROOT=$ANDROID_SDK_ROOT --no-daemon --no-build-cache assemble
