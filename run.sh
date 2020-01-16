#! /bin/sh

. ./vars.sh

echo "install..."
adb install -r $FINAL_APK

echo "turning on..."
./turn-on.sh

echo "start..."
adb shell am start -a android.intent.action.MAIN -n com.example/.HelloWorld
