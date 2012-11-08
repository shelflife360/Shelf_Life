package w3se.Model.Database;

import java.sql.SQLException;

public interface Database<T, R>
{
	public void retrieve(Object obj) throws Exception;
	
	public void add(T obj) throws Exception;
	
	public void remove(T obj) throws Exception;
	
	public R getResult() throws Exception;

	public void shutdown() throws Exception;
	
	public void close() throws Exception;

}
