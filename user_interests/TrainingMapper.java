package training;

import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.util.List;
import java.util.Arrays;
import java.lang.*;
import java.io.IOException;

public class TrainingMapper  extends Mapper <LongWritable,Text,LongWritable,Text> {
	public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
		// Skipping first line
		if (key != new LongWritable(0)) {
			try {
			List<String> valueTokens = Arrays.asList(value.toString().split(","));
			Long userId              = Long.parseLong(valueTokens.get(0));
			Long eventId             = Long.parseLong(valueTokens.get(1));
			Integer interested       = Integer.parseInt(valueTokens.get(4));
			Integer notInterested    = Integer.parseInt(valueTokens.get(5));
			String out = "" + eventId;

			if (interested == 1) {
					out = "1," + out;
			} else if (notInterested == 1){
					out = "-1," + out;
			} else {
				// No response given
				out = "0," + out;
			}
			context.write(new LongWritable(userId), new Text(out));
			} catch(NumberFormatException e) {
			}
		}
	}
}
