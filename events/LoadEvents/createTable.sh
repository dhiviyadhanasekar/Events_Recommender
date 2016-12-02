export CLASSPATH=$(hbase classpath)
javac -classpath $CLASSPATH CreateEventsTable.java
java -classpath $CLASSPATH:.:CreateEventsTable.class CreateEventsTable
