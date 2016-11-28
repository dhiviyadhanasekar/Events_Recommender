#!/bin/bash

USER=`whoami`

export HADOOP_HOME=/opt/mapr/hadoop/hadoop-2.7.0
export LD_LIBRARY_PATH=$HADOOP_HOME/lib/native
export CLASSPATH=.:$(hadoop classpath)
export HADOOP_CLASSPATH=$CLASSPATH

rm -rf /user/$USER/HbaseInputter/OUT

ARGS=$1

hadoop jar Houses.jar Houses.HousesDriver $ARGS /user/$USER/HbaseInputter/DATA/part-r-00000 /user/$USER/HbaseInputter/OUT
