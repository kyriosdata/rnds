#!/bin/sh
CURRENT_DIR=$(pwd)
JAVA_LIBS_DIR="${CURRENT_DIR}/libs"
CLASSPATH=""

for i in $(ls $JAVA_LIBS_DIR/*.jar)
do
    CLASSPATH=$(printf "${jar_op}${i}:${CLASSPATH}")
done

java -cp $CLASSPATH ui.FhirValidacaoWindow
