package tool;

import java.util.List;
import java.util.Date; 
import java.util.Calendar; 

import java.text.SimpleDateFormat; 

public class Time {

	public static String getTime(){ 
		Date now = new Date(); 
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");//修改日期格式


		String time = dateFormat.format( now ); 
		return time;
	}
  
} 



