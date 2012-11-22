package w3se.Model;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class Configurations
{
	public static final String SL_ICON = "resources/icon.jpg";
	public static final String SL_LOGO = "resources/logo.jpg";
	public static final String W3SE_LOGO = "resources/w3se.jpg";
	public static final String RESOURCES_D = "resources.zip";
	public static final String RESOURCES_S = "resources";
	private LinkedHashMap<String, String> m_docMap = new LinkedHashMap<String, String>();
	private static String m_filename;
	
	private Configurations()
	{}
	
	public static Configurations getConfigFromFile(String filename)
	{
		m_filename = filename;
		String readFile;
		try
		{	
			BufferedInputStream inStream = new BufferedInputStream(new FileInputStream(m_filename));
			ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
			
			int result = 0;
			
			while ((result = inStream.read()) > -1)
			{
				byteStream.write(result);
			}
			
			inStream.close();
			
			readFile = byteStream.toString();
			Configurations config = new Configurations();
			config.parseConfigFile(readFile);
			
			return config;	
		} 
		catch (Exception e)
		{
			System.out.println("File not found!");
		} 
		
		return null;
	}
	
	public void setNewConfiguration(String key, String value)
	{
		m_docMap.put(key, value);
	}
	
	public void parseConfigFile(String readFile)
	{
		try
		{
			InputSource inSource = new InputSource(new StringReader(readFile));
			Document doc = null;
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.parse(inSource);
			doc.getDocumentElement().normalize();
			
			NodeList nList = doc.getElementsByTagName("Config");
			
			for (int i = 0; i < nList.getLength(); i++)
			{	
				Element e1 = (Element)nList.item(i);
				
				if (e1 != null)
				{
					String name = getAttribute("Name", e1);
					String value = getAttribute("Value", e1);
					m_docMap.put(name, value);
				}
			}
			
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
	
	public void writeToFile()
	{
		try
		{
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			
			Document doc = docBuilder.newDocument();
			
			Element root = doc.createElement("IMS");
			doc.appendChild(root);
			
			Iterator<Entry<String, String>> iter = m_docMap.entrySet().iterator();
			
			while (iter.hasNext())
			{
				Map.Entry<String,String> pair = iter.next();
				Element e = doc.createElement("Config");
				e.setAttribute("Name", pair.getKey());
				e.setAttribute("Value", pair.getValue());
				root.appendChild(e);
			}
			
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File("config"));
			
			transformer.transform(source, result);
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public String getValue(String name)
	{
		return m_docMap.get(name);
	}
	
	/**
	 * 
	 * @param attribute
	 * @param element
	 * @return
	 */
	private String getAttribute(String attribute, Element element)
	{
		return element.getAttribute(attribute);
	}
	
	/**
	 * 
	 * @param tag
	 * @param element
	 * @return
	 */
	private String getTagValue(String tag, Element element)
	{
		NodeList nList = element.getElementsByTagName(tag).item(0).getChildNodes();
		Node node = (Node) nList.item(0);
		
		if (node != null)
			return node.getNodeValue();
		else
			return "";
	}
}
