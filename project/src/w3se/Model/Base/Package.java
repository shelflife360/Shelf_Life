package w3se.Model.Base;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.swing.JOptionPane;

public class Package
{
	public static final int BUFFER_SIZE = 1024;
	private String m_dest;
	private String m_src;
	private String m_key;
	
	public Package(String src, String dest, String key)
	{
		m_dest = dest;
		m_src = src;
		m_key = key;
		File temp = new File(m_dest);
		temp.mkdir();
	}
	
	public void setKey(String key)
	{
		m_key = key;
	}
	
	public void unpack()
	{
		try
		{
			FileInputStream in = new FileInputStream(m_src);
			FileOutputStream out = new FileOutputStream("tmp.zip");
			encryptOrDecrypt(m_key, Cipher.DECRYPT_MODE, in, out);
			FileInputStream fis = new FileInputStream("tmp.zip");
			ZipInputStream zis = new ZipInputStream(new BufferedInputStream(fis));
			ZipEntry entry;
			
			while ((entry = zis.getNextEntry()) != null)
			{
				if (entry.getName().contains(".DS_Store") == false)
				{
					int size;
					byte[] buff = new byte[BUFFER_SIZE];
					FileOutputStream fos = new FileOutputStream(entry.getName());
					BufferedOutputStream bos = new BufferedOutputStream(fos, buff.length);
					
					while ((size = zis.read(buff, 0, buff.length)) != -1)
					{
						bos.write(buff, 0, size);
					}
					
					bos.flush();
					bos.close();
				}
			}
			
			zis.close();
			fis.close();
			
		}
		catch (Exception e)
		{
			JOptionPane.showMessageDialog(null, "System Resources not found!", "Fatal Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}

	public void pack()
	{
		try
		{
			FileOutputStream out = new FileOutputStream(m_src);
			ZipOutputStream zip = null;
			FileOutputStream fos = null;
				
			fos = new FileOutputStream("tmp.zip");
			zip = new ZipOutputStream(fos);
			
			
			zipFolder("", m_dest, zip);
			zip.flush();
			zip.close();
			
			FileInputStream in = new FileInputStream("tmp.zip");
			encryptOrDecrypt(m_key, Cipher.ENCRYPT_MODE, in, out);
		}
		catch (Exception e)
		{
			JOptionPane.showMessageDialog(null, "System Resources could not be packed!", "Fatal Error", JOptionPane.ERROR_MESSAGE);
			System.out.println(e.getMessage());
		}
	}
	
	public void close()
	{
		deleteDir(new File("tmp"));
		deleteDir(new File("tmp.zip"));
	}

	public void encryptOrDecrypt(String key, int mode, InputStream is, OutputStream os)
	{

		try
		{
			DESKeySpec dks = new DESKeySpec(key.getBytes());
			SecretKeyFactory skf = SecretKeyFactory.getInstance("DES");
			SecretKey desKey = skf.generateSecret(dks);
			Cipher cipher = Cipher.getInstance("DES"); // DES/ECB/PKCS5Padding for SunJCE
	
			if (mode == Cipher.ENCRYPT_MODE) {
				cipher.init(Cipher.ENCRYPT_MODE, desKey);
				CipherInputStream cis = new CipherInputStream(is, cipher);
				byte[] bytes = new byte[64];
				int numBytes;
				while ((numBytes = is.read(bytes)) != -1) {
					os.write(bytes, 0, numBytes);
				}
				os.flush();
				os.close();
				is.close();
				
			} 
			else if (mode == Cipher.DECRYPT_MODE)
			{
				cipher.init(Cipher.DECRYPT_MODE, desKey);
				CipherOutputStream cos = new CipherOutputStream(os, cipher);
				byte[] bytes = new byte[64];
				int numBytes;
				while ((numBytes = is.read(bytes)) != -1) {
					os.write(bytes, 0, numBytes);
				}
				os.flush();
				os.close();
				is.close();
			}
		}
		catch (Exception e)
		{
			
		}
	}
	
	private void deleteDir(File directory)
	{
		if (!directory.exists())
		{
	           System.out.println("Directory does not exist.");
	           System.exit(0);
	 
	    }
		else
	    {
	 
	           try
	           {
	 
	               delete(directory);
	 
	           }
	           catch(IOException e){
	               System.exit(0);
	           }
	        }
	}
	
	private void delete(File file) throws IOException
	{

		if(file.isDirectory()){

			//directory is empty, then delete it
			if(file.list().length==0)
			{

				file.delete();

			}
			else
			{

				//list all the directory contents
				String files[] = file.list();

				for (String temp : files) 
				{
					//construct the file structure
					File fileDelete = new File(file, temp);

					//recursive delete
					delete(fileDelete);
				}

				//check the directory again, if empty then delete it
				if (file.list().length==0)
				{
					file.delete();
				}
			}

		}
		else
		{
			//if file, then delete it
			file.delete();
		}
	}
	
	private void zipFolder(String path, String srcFolder, ZipOutputStream zstream) throws Exception
	{
		File folder = new File(srcFolder);
		for (int i = 0; i < folder.list().length; i++)
		{
			String filename = folder.list()[i];
			if (path.equals(""))
			{
				addFileToZip(folder.getName(), srcFolder + File.separator + filename, zstream);
			}
			else
			{
				addFileToZip(path+"/"+folder.getName(), srcFolder+File.separator+filename, zstream);
			}
		}
	}

	private void addFileToZip(String path, String src, ZipOutputStream zstream) throws Exception
	{ 
		File folder = new File(src);
		if (folder.isDirectory())
		{
			zipFolder(path, src, zstream);
		}
		else
		{
			byte[] buff = new byte[BUFFER_SIZE];
			int len;
			FileInputStream in = new FileInputStream(src);
			zstream.putNextEntry(new ZipEntry(path+File.separator+folder.getName()));
			while ((len = in.read(buff)) > 0)
			{
				zstream.write(buff, 0, len);
			}
			
		}
	}
	
}
