package training;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.mapreduce.TableReducer;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.util.Bytes;
import java.io.IOException;

public class TrainingReducer extends TableReducer <LongWritable,Text,ImmutableBytesWritable> {
	public void reduce(LongWritable key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
		StringBuilder interestedList = new StringBuilder();
		StringBuilder notInterestedList = new StringBuilder();
		for (Text value : values) {
			String[] tokens = value.toString().split(",");
			if (tokens[0].equals("1")) {
				interestedList.append(tokens[1] + ",");
			} else if (tokens[0].equals("-1")) {
				notInterestedList.append(tokens[1] + ",");
			}
		}
		int strLength = interestedList.length();
		String interestedCSV = strLength > 0 ? interestedList.substring(0, strLength - 1) : "";
		strLength = notInterestedList.length();
		String notInterestedCSV = strLength > 0 ? notInterestedList.substring(0, strLength - 1) : "";

		Put p = new Put(Bytes.toBytes(key.toString()));
		p.add(Bytes.toBytes("interested"), Bytes.toBytes("list"), Bytes.toBytes(interestedCSV));
		p.add(Bytes.toBytes("not_interested"), Bytes.toBytes("list"), Bytes.toBytes(notInterestedCSV));

		context.write(null, p);
	}
}
