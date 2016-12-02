package tfidf;

import org.apache.hadoop.mapreduce.Partitioner;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TfIdfPartitioner<K, V> extends Partitioner<K, V> {

	public int getPartition(K key, V value, int numReduceTasks) {
		if(numReduceTasks == 0) return 0;
		if(numReduceTasks == 1) return 0;
		if(numReduceTasks == 2){
			Long eventId = Long.parseLong(key.toString());
			if( eventId % 2 == 0) return 0;
			return 1;
		}
		return 0;
	}
}
