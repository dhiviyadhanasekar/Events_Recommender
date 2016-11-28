package Houses;

import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;



import java.io.IOException;
import java.util.StringTokenizer;

public class HousesMapper  extends Mapper <LongWritable,Text,Text,Text> {
	private static Log log = LogFactory.getLog(HousesMapper.class);

   	public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
		System.out.println("key " + key + " ===> val = " + value);
		String input = value.toString();
		String[] inputArr = input.split("\\t");
		System.out.println("key " + inputArr[0] + " ===> val = " + inputArr[1]);
		context.write(new Text(inputArr[0]), new Text(inputArr[1]));
   	}

}
