import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Logging
{
	private Logger log;
	
	public Logging()
	{
		getLoggingProperties();
		log = Logger.getLogger("Logging");
		
		// log.info ("Logging constructor set up the log correctly");
	}
	
	/**
	 * Log an information level message
	 * @param message
	 */
	public void logInfoMessage(String message)
	{
		log.info(message);
	}
	
	/**
	 * Log a warning level message
	 * @param message
	 */
	public void logWarningMessage(String message)
	{
		log.warning(message);
	}
	
	/**
	 * Log a message at the provided level
	 * @param level
	 * @param message
	 */
	public void logMessage(Level level, String message)
	{
		log.log (level, message);
	}
	
	/*
	 * This reads the logging.properties file and configures the logger accordingly
	 */
    private void getLoggingProperties()
	{
		// Read logging properties from the file
		try (InputStream is = this.getClass().getClassLoader().getResourceAsStream("logging.properties"))
		{
			LogManager.getLogManager().readConfiguration(is);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
