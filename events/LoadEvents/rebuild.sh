#!/bin/bash

USER=`whoami`

export CLASSPATH=.:$(hbase classpath)
export HADOOP_CLASSPATH=$CLASSPATH

javac -d classes EventLoaderMapper.java
javac -d classes EventLoaderReducer.java
jar -cvf EventLoader.jar -C classes/ .
javac -classpath $CLASSPATH:EventLoader.jar -d classes EventLoaderDriver.java
jar -uvf EventLoader.jar -C classes/ .
