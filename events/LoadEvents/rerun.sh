#!/bin/bash

USER=`whoami`

export HADOOP_HOME=/opt/mapr/hadoop/hadoop-2.7.0
export LD_LIBRARY_PATH=$HADOOP_HOME/lib/native
export CLASSPATH=.:$(hbase classpath)
export HADOOP_CLASSPATH=$CLASSPATH

rm -rf /user/$USER/LoadEvents/OUT

ARGS=$1

hadoop jar EventLoader.jar loadevents.EventLoaderDriver $ARGS /user/$USER/TF_IDF/OUT /user/$USER/LoadEvents/OUT
