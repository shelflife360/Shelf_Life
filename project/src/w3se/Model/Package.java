package w3se.Model;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import javax.swing.JOptionPane;

public class Package
{
	public static final int BUFFER_SIZE = 2048;
	private String m_filename;
	
	public Package(String filename)
	{
		m_filename = filename;
	}
	
	public void unpack()
	{
		try
		{
			ZipOutputStream zipO = null;
			FileOutputStream fos = null;
			
			fos = new FileOutputStream(Configurations.RESOURCES_D);
			zipO = new ZipOutputStream(fos);
			
			zipFolder("", Configurations.RESOURCES_S, zipO);
			zipO.flush();
			zipO.close();
			
		}
		catch (Exception e)
		{
			JOptionPane.showMessageDialog(null, "System Resources not found!", "Fatal Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}

	private void zipFolder(String path, String srcFolder, ZipOutputStream zstream) throws IOException
	{
		File folder = new File(Configurations.RESOURCES_S);
		
		for (String filename : folder.list())
		{
			if (path.equals(""))
			{
				addFileToZip(folder.getName(), Configurations.RESOURCES_S + "/" + filename, zstream);
			}
			else
			{
				addFileToZip(path+"/"+folder.getName(), Configurations.RESOURCES_S+"/"+filename, zstream);
			}
		}
	}

	private void addFileToZip(String name, String string, ZipOutputStream zstream) throws IOException
	{ 
		File folder = new File(Configurations.RESOURCES_S);
		if (folder.isDirectory())
		{
			zipFolder(name, Configurations.RESOURCES_S, zstream);
		}
		else
		{
			byte[] buff = new byte[BUFFER_SIZE];
			int len;
			FileInputStream in = new FileInputStream(Configurations.RESOURCES_S);
			zstream.putNextEntry(new ZipEntry(name+"/"+folder.getName()));
			while ((len = in.read(buff)) > 0)
			{
				zstream.write(buff, 0, len);
			}
			
		}
	}
}
