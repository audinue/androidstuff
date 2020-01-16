#! /bin/sh
#! /bin/sh

if adb shell dumpsys power | grep -q 'Display Power: state=OFF' ; then
	# press menu button
	adb shell input keyevent 82
	# input password
	adb shell input text 1234
	# press ok
	adb shell input keyevent 66
else
	adb shell input tap 0 0
fi
