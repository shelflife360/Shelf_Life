package w3se.Base;

import java.util.ArrayList;

public interface Exporter
{
	public void setFilename(String filename);
	public void export(ArrayList list) throws Exception;
}
