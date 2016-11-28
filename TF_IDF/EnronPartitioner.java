package Enron;

import org.apache.hadoop.mapreduce.Partitioner;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class EnronPartitioner<K, V> extends Partitioner<K, V> {
	private int m=22;
	private int firstLetterValue=0;

	public int getPartition(K key, V value, int numReduceTasks) {
		// TODO override getPartition(K, V, int) method
		//System.out.println("numReduceTasks ======>>> " + numReduceTasks);
		//if(numReduceTasks == 0) return 0;
		//if(numReduceTasks == 1) return 0;
		//if(numReduceTasks == 2){
		//	String recipient = (String) key.toString();
		//	char firstChar = recipient.charAt(0);
		//	if( firstChar <= 'm') return 0;
		//	return 1;
		//}
		return 0;
	}
}

