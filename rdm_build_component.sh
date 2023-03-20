#!/bin/sh
rm -rf ./bin
mkdir ./bin

# Build
chmod 777 gradlew

echo "$1=$1"


case $1 in
	true)
		./gradlew :DEMO:assembleDebug --stacktrace
		;;
	false)
		./gradlew :DEMO:assembleRelease --stacktrace
		;;
esac

#echo "test=1"
#./gradlew :DEMO:assembleDebug --stacktrace


# case $1 in
# 	true)
# 		echo "test=12"
# 		case $2 in
# 			true)
# 				echo "test=1"
# 				./gradlew assembleTmfhotpatchRelease --stacktrace
# 				;;
# 			false)
# 				echo "test=2"
# 				./gradlew assembleTmfhotpatchDebug --stacktrace
# 				;;
# 		esac
# 		;;
# 	false)
# 		echo "test=34"
# 		case $2 in
# 			true)
# 				echo "test=3"
# 				./gradlew assembleTmfMainRelease --stacktrace
# 				;;
# 			false)
# 				echo "test=4"
# 				./gradlew assembleTmfMainDebug --stacktrace
# 				;;
# 		esac
# 		;;
# esac

cd ..


