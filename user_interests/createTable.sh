export CLASSPATH=$(hbase classpath)
javac -classpath $CLASSPATH CreateUserInterestsTable.java
java -classpath $CLASSPATH:.:CreateUserInterestsTable.class CreateUserInterestsTable
