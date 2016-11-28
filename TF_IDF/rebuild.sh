#!/bin/bash

USER=`whoami`

export CLASSPATH=.:$(hadoop classpath)
export HADOOP_CLASSPATH=$CLASSPATH

javac -d classes EnronMapper.java
javac -d classes EnronReducer.java
javac -d classes EnronPartitioner.java
jar -cvf Enron.jar -C classes/ .
javac -classpath $CLASSPATH:Enron.jar -d classes EnronDriver.java
jar -uvf Enron.jar -C classes/ .
