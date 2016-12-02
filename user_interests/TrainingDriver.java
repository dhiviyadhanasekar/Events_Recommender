package training;

import org.apache.hadoop.mapreduce.Counters;
import org.apache.hadoop.mapreduce.Counter;
import org.apache.hadoop.mapreduce.CounterGroup;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;

public class TrainingDriver extends Configured implements Tool {
    public int run(String[] args) throws Exception {
      if (args.length != 2) {
        System.err.println("wrong number of arguments. Expected: 2");
        System.exit(1);
      }
      Job job = new Job(getConf());

      job.setJarByClass(TrainingDriver.class);
      job.setMapperClass(TrainingMapper.class);
      TableMapReduceUtil.initTableReducerJob(
    		"/user/user01/user_interests",
    		TrainingReducer.class,
    		job
      );
      job.setInputFormatClass(TextInputFormat.class);
      job.setMapOutputKeyClass(LongWritable.class);
      job.setMapOutputValueClass(Text.class);

      FileInputFormat.addInputPath(job, new Path(args[0]));
      FileOutputFormat.setOutputPath(job, new Path(args[1]));

      return job.waitForCompletion(true) ? 0 : 1;
    }

    public static void main(String[] args) throws Exception {
      Configuration conf = HBaseConfiguration.create();
      System.exit(ToolRunner.run(conf, new TrainingDriver(), args));
    }

}
