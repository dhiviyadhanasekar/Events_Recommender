USER=`whoami`
export HADOOP_HOME=/opt/mapr/hadoop/hadoop-2.7.0
export LD_LIBRARY_PATH=$HADOOP_HOME/lib/native
export CLASSPATH=.:$(hbase classpath)
export HADOOP_CLASSPATH=$CLASSPATH
rm -rf ./OUT/
ARGS=$1
javac -d classes TrainingMapper.java
javac -d classes TrainingReducer.java
jar -cvf Training.jar -C classes/ .
javac -classpath $CLASSPATH:Training.jar -d classes TrainingDriver.java
jar -uvf Training.jar -C classes/ .
hadoop jar Training.jar training.TrainingDriver $ARGS /user/$USER/dataset/train.csv ./OUT/
