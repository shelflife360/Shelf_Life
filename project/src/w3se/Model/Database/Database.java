package w3se.Model.Database;

import java.sql.SQLException;


public interface Database<T>
{
	public void retrieve(String searchTerm) throws Exception;
	
	public boolean add(T obj) throws SQLException;
	
	public T getResult() throws Exception;

	public void shutdown() throws SQLException;
	
	public void close() throws SQLException;
}
