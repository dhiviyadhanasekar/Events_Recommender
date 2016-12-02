import java.io.IOException;

import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.TableName;

import org.apache.hadoop.conf.Configuration;

public class CreateEventsTable {
   public static void main(String[] args) throws IOException {
     Configuration con = HBaseConfiguration.create();
     HBaseAdmin admin = new HBaseAdmin(con);
     HTableDescriptor tableDescriptor = new HTableDescriptor(TableName.valueOf("/user/user01/events"));
     tableDescriptor.addFamily(new HColumnDescriptor("description"));
     admin.createTable(tableDescriptor);
     System.out.println(" Table created ");
   }
 }
