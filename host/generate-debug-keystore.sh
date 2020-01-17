#! /bin/sh

keytool -sigalg MD5withRSA -keyalg RSA -keysize 1024 -genkey -v -keystore debug.keystore -storepass android -alias androiddebugkey -keypass android -dname "CN=Android Debug,O=Android,C=US"
