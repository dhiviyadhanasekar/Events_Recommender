package tfidf;

import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.IntWritable;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class TfIdfMapper  extends Mapper <LongWritable,Text,Text,Text> {

	private IntWritable one = new IntWritable(1);
	private int colCount = 101;

   	public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

	if(key.compareTo(new LongWritable((long) 0)) == 0) {

		return;
	}
	String[] inputArray = (value.toString()).split(",");

	int[] wordCount = new int[colCount];
	int sum = 0;
	for(int i=0; i<colCount; i++){
	    String numStr = inputArray[i+9];
	    int num = 0;
	    try { num = Integer.parseInt(numStr.trim()); }
	    catch(Exception e){ }
	    wordCount[i] = num;
	    sum += num;
	}

	String output = "";
	for(int i=0; i<colCount-1; i++){
		output = inputArray[0] + "," + ((double)wordCount[i]/sum);
		context.write(new Text(i+""), new Text(output) );
	}
   }

}
