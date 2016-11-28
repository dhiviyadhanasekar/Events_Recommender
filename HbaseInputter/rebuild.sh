#!/bin/bash

USER=`whoami`

export CLASSPATH=.:$(hadoop classpath)
export HADOOP_CLASSPATH=$CLASSPATH

javac -d classes HousesMapper.java
javac -d classes HousesReducer.java
jar -cvf Houses.jar -C classes/ .
javac -classpath $CLASSPATH:Houses.jar -d classes HousesDriver.java
jar -uvf Houses.jar -C classes/ .
