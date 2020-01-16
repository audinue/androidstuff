#! /bin/sh

. ./vars.sh

echo "aapt (package)..."
aapt p -I $ANDROID_JAR -M AndroidManifest.xml -f -F $UNSIGNED_APK

echo "javac..."
javac -bootclasspath $ANDROID_JAR -source 7 -target 7 $(find -type f -name '*.java')

echo "dx..."
dx --dex --output=classes.dex $(find -type f -name '*.class')

echo "aapt (add)..."
aapt a $UNSIGNED_APK classes.dex

echo "jarsigner..."
jarsigner -keystore debug.keystore -storepass android -keypass android -signedjar $UNALIGNED_APK $UNSIGNED_APK androiddebugkey

echo "zipalign..."
zipalign -f 4 $UNALIGNED_APK $FINAL_APK
