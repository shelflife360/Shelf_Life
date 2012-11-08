package w3se.Model;

import java.io.FileOutputStream;
import java.util.ArrayList;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class ExcelExporter implements Exporter
{
	private String m_filename;
	
	public ExcelExporter()
	{}
	
	public ExcelExporter(String filename)
	{
		m_filename = filename;
	}
	
	@Override
	public void setFilename(String filename)
	{
		m_filename = filename;
	}

	@Override
	public void export(ArrayList list) throws Exception
	{
		if (list.size() > 0)
		{
			
			String title = ((Exportable)list.get(0)).getExportableTitle();
			String[] head = ((Exportable)list.get(0)).getExportableHeadRow();
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet(title);
			HSSFRow rowhead = sheet.createRow(0);
			for (int i = 0; i < head.length; i++)
			{
				rowhead.createCell(i).setCellValue(head[i]);
			}
			
			for (int i = 0; i < list.size(); i++)
			{
				Exportable expo = (Exportable)list.get(i);
				String[] rowFormat = expo.getExportableRow();
				HSSFRow row = sheet.createRow(i);
				
				for (int j = 0; j < rowFormat.length; j++)
				{
					row.createCell(j).setCellValue(rowFormat[j]);
				}
			}
			FileOutputStream output = new FileOutputStream(m_filename+".xls");
		
			workbook.write(output);
			output.close();
		}
		
		
	}
}
