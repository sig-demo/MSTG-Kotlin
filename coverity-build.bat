@pushd %~dp0
@ECHO off
IF "%ANDROID_SDK_ROOT%"=="" (
	@ECHO ANDROID_SDK_ROOT not found
	@ECHO attempting to use ANDROID_STUDIO_SDK
	set ANDROID_SDK_ROOT=%ANDROID_STUDIO_SDK%
)
cov-build --dir idir --fs-capture-search . gradlew.bat -DANDROID_SDK_ROOT=%ANDROID_SDK_ROOT% --no-daemon --no-build-cache assemble
@popd
