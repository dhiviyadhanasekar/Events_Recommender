package loadevents;

import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.LongWritable;
import java.io.IOException;
import java.util.StringTokenizer;

public class EventLoaderMapper extends Mapper <LongWritable,Text,LongWritable,Text> {

   	public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
		    String input = value.toString();
        String[] inputArr = input.split("\\t");
        Long eventId = Long.parseLong(inputArr[0]);
        context.write(new LongWritable(eventId), new Text(inputArr[1]));
   	}

}
