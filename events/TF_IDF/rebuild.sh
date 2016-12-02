#!/bin/bash

USER=`whoami`

export CLASSPATH=.:$(hadoop classpath)
export HADOOP_CLASSPATH=$CLASSPATH

javac -d classes TfIdfMapper.java
javac -d classes TfIdfReducer.java
javac -d classes TfIdfPartitioner.java
jar -cvf TfIdf.jar -C classes/ .
javac -classpath $CLASSPATH:TfIdf.jar -d classes TfIdfDriver.java
jar -uvf TfIdf.jar -C classes/ .
