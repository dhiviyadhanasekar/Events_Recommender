package Enron;

import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.IntWritable;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;

public class EnronReducer  extends Reducer <Text,Text,Text,Text> {

	private static final int docCount = 3137973;
	public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
     		int sum = 0;
     		ArrayList<String> valuesArr = new ArrayList<String>();
		for (Text value: values) {
			String input = value.toString();
			String[] inArr = input.split(",");
			double tf = Double.parseDouble(inArr[1]);
			if(Double.compare(tf, 0) > 0) sum++; 
			System.out.println("Sum =>" + sum);
			valuesArr.add(input);
     		}
		
		double idf = (sum==0) ? 0 : Math.log(docCount/sum);
		for (String input: valuesArr) {
                        //String input = value.toString();
                        String[] inArr = input.split(",");
                        double tf = Double.parseDouble(inArr[1]);
			double tf_idf = tf*idf;
			System.out.println("Event " + inArr[0] + " => word " + key.toString() + " " + tf_idf);
			context.write(new Text(inArr[0]), new Text(key.toString() + "," + tf_idf));
                }
     		//context.write(key, new IntWritable(sum)); 
   	}
}
