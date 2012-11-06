package w3se.Model.Database;

import org.hsqldb.server.Server;

public class DBServer
{
	String[] m_properties = null;
	Server m_server = new Server();
	
	/**
	 * constructor
	 * @param properties - array of strings, format(databaseFilePath, databaseName) example ("file:database/UsersDB", "UsersDB")
	 * @param port - server port
	 */
	public DBServer(String[] properties, int port)
	{
		m_properties = properties;
		m_server.setLogWriter(null);
		m_server.setErrWriter(null);
		m_server.setAddress("localhost");
		
		int dbNum = 0;
		for (int i = 0; i < m_properties.length; i += 2 )
		{
			m_server.setDatabasePath(dbNum, m_properties[i]);
			m_server.setDatabaseName(dbNum, m_properties[i+1]);
			dbNum++;
		}
		
		m_server.setPort(9001);
		
	}
	
	public void start()
	{
		m_server.start();
	}
	
	public void stop()
	{
		m_server.stop();
	}
	
	public void shutdown()
	{
		m_server.shutdown();
	}
	
}
