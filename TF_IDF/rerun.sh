#!/bin/bash

USER=`whoami`

export HADOOP_HOME=/opt/mapr/hadoop/hadoop-2.7.0
export LD_LIBRARY_PATH=$HADOOP_HOME/lib/native
export CLASSPATH=.:$(hadoop classpath)
export HADOOP_CLASSPATH=$CLASSPATH

rm -rf /user/$USER/TF_IDF/OUT

ARGS=$1

hadoop jar Enron.jar Enron.EnronDriver $ARGS /user/$USER/TF_IDF/DATA/EVENTS /user/$USER/TF_IDF/OUT
