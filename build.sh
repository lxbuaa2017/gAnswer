#!/bin/bash
mkdir temp5678
javac -encoding utf8 -cp ./src -d ./temp5678 ./src/main/java/application/GanswerHttp.java
jar cfm gAnswer.jar ./src/META-INF/MANIFEST.MF -C ./temp5678 .
chmod -x gAnswer.jar
rm -rf ./temp5678