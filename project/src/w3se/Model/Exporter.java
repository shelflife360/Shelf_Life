package w3se.Model;

import java.util.ArrayList;

public interface Exporter
{
	public void setFilename(String filename);
	public void export(ArrayList list) throws Exception;
}
