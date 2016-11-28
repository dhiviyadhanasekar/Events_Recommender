package Houses;

import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.IntWritable;
import java.io.IOException;
import java.text.DecimalFormat;


public class HousesReducer  extends Reducer <Text,Text,Text,Text> {

	public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
		double[] wordArr = new double[100];
		for(Text val: values){
		   String valStr = val.toString();
		   String[] splitStr = valStr.split(",");
		   double tf_idf = Double.parseDouble(splitStr[1]);
		   int word_index = Integer.parseInt(splitStr[0]);
		   wordArr[word_index] = tf_idf;
		}
		
		String output = "";
		for(int i=0; i<100; i++){
		   output += wordArr[i];
		   if(i != 99) output += ",";
		}
		
		System.out.println("output => " + output);
		context.write(key, new Text(output));
   	}
}
