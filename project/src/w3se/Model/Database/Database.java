package w3se.Model.Database;

import java.sql.SQLException;



public interface Database<T, R>
{
	public void retrieve(String str) throws Exception;
	
	public void add(T obj) throws SQLException;
	
	public R getResult() throws Exception;

	public void shutdown() throws SQLException;
	
	public void close() throws SQLException;

}
