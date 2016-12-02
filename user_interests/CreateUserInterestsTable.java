import java.io.IOException;

import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.TableName;

import org.apache.hadoop.conf.Configuration;

public class CreateUserInterestsTable {

   public static void main(String[] args) throws IOException {
     // Instantiating configuration class
     Configuration con = HBaseConfiguration.create();

     // Instantiating HbaseAdmin class
     HBaseAdmin admin = new HBaseAdmin(con);

     // Instantiating table descriptor class
     HTableDescriptor tableDescriptor = new HTableDescriptor(TableName.valueOf("/user/user01/user_interests"));

     // Adding column families to table descriptor
     tableDescriptor.addFamily(new HColumnDescriptor("interested"));
     tableDescriptor.addFamily(new HColumnDescriptor("not_interested"));

     // Execute the table through admin
     admin.createTable(tableDescriptor);
     System.out.println(" Table created ");
   }

}
