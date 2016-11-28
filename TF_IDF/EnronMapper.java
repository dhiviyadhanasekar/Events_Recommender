package Enron;

import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;



import java.io.IOException;
import java.util.StringTokenizer;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class EnronMapper  extends Mapper <LongWritable,Text,Text,Text> {
	
	private static Log log = LogFactory.getLog(EnronMapper.class);
	private IntWritable one = new IntWritable(1);
	private int colCount = 101;

   	public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

	if(key.compareTo(new LongWritable((long) 0)) == 0) {
		
		return;
	}
	String[] inputArray = (value.toString()).split(",");
	
	//System.out.println("key " + key + " ===> val = " + value);
	int[] wordCount = new int[colCount];
	int sum = 0;
	for(int i=0; i<colCount; i++){
	    String numStr = inputArray[i+9];
	    int num = 0;
	    try { num = Integer.parseInt(numStr.trim()); } 
	    catch(Exception e){ log.error(e.toString()); }
	    wordCount[i] = num;
	    sum += num;	
	}  
	
	System.out.println("key " + key + " ===> val = " +sum);
	String output = "";
	for(int i=0; i<colCount-1; i++){
		output = inputArray[0] + "," + ((double)wordCount[i]/sum);
		System.out.println("word " + i + " => " + output);
		context.write(new Text(i+""), new Text(output) );
		//output += (double)wordCount[i]/sum;
		//if(i != colCount-1) output += ",";
	}
	//context.write(new Text(inputArray[0]), new Text(output));  
   }

}
