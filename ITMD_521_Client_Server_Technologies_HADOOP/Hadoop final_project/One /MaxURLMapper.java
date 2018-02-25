// Mapper class to find the per month URL
// Made by Anirudh Dutt
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class MaxURLMapper
  extends Mapper<LongWritable, Text, Text, Text> {

	
  public void map(LongWritable key,Text value, Context context)
      throws IOException, InterruptedException {
String[] myline= null;
String line = value.toString();
myline=line.split("\\s+");

String month, quality, URL;
try{
	      month=myline[0];
	     quality=myline[10];
		//month = line.substring(5, 7);
      //quality = line.substring(123, 126);
    if (quality.matches("200")) {
    	 //URL = line.substring(39, 50);
		 URL= myline[4];
   context.write(new Text(month), new Text(URL));  
}
}
catch(StringIndexOutOfBoundsException ex){
	 month = " ";
	 URL =" ";
	 quality = " ";
}
catch(ArrayIndexOutOfBoundsException Ex){
	month = " ";
	URL =" ";
	quality = " ";
}

}
}
