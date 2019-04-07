package ServerAdmin;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class LogFormatter extends Formatter{

  @Override
  public String format(LogRecord record) {
    // TODO Auto-generated method stub
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
    String date = sdf.format(new Date());
    return "<" + date + ">" + "(" + record.getSourceClassName() + ")" + "{"
    + record.getSourceMethodName() + "}" + "[" + record.getMessage() + "]" + "\n";
  }
  
}
