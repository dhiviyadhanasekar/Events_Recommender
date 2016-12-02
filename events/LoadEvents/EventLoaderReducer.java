package loadevents;

import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.mapreduce.TableReducer;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.util.Bytes;
import java.io.IOException;
import java.text.DecimalFormat;


public class EventLoaderReducer extends TableReducer <LongWritable,Text,ImmutableBytesWritable> {

	public void reduce(LongWritable key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
		double[] wordArr = new double[100];
		for(Text val: values) {
			String valStr = val.toString();
			String[] splitStr = valStr.split(",");
			double tf_idf = Double.parseDouble(splitStr[1]);
			int word_index = Integer.parseInt(splitStr[0]);
			wordArr[word_index] = tf_idf;
		}
		String output = "";
		for(int i=0; i<100; i++) {
			output += wordArr[i];
			if(i != 99) output += ",";
		}
		Put p = new Put(Bytes.toBytes(key.toString()));
		p.add(Bytes.toBytes("description"), Bytes.toBytes("vector"), Bytes.toBytes(output));
		context.write(null, p);
	}

}
