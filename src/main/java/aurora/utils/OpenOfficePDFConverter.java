package aurora.utils;
import java.io.File;

import org.artofsolving.jodconverter.OfficeDocumentConverter;
import org.artofsolving.jodconverter.office.DefaultOfficeManagerConfiguration;
import org.artofsolving.jodconverter.office.OfficeManager;



/**
 * 使用openOffice将文档转换为pdf，依赖于openOffice的安装目录
 * @author 纯白
 *
 */
public class OpenOfficePDFConverter{
	
	//default directory : "D:" + File.separator + "Program Files" + File.separator + "LibreOffice 5"
	public static void toPDF(String sofficePath, String inputFilePath, String outputFilePath) {
        OfficeManager officeManager = new DefaultOfficeManagerConfiguration()  
                         .setOfficeHome(sofficePath)  
                         .buildOfficeManager();  
        officeManager.start();  
        OfficeDocumentConverter converter = new OfficeDocumentConverter(officeManager);
        converter.convert(new File(inputFilePath), new File(outputFilePath));  
        officeManager.stop();  
    }
		//synchronized
		public static  void toPDF(String sofficePath, File inputFile, File outputFile) {
	        OfficeManager officeManager = new DefaultOfficeManagerConfiguration()  
	                         .setOfficeHome(sofficePath)  
	                         .buildOfficeManager();  
	        officeManager.start();  
	        OfficeDocumentConverter converter = new OfficeDocumentConverter(officeManager);  
	        converter.convert(inputFile, outputFile);
	        officeManager.stop();  
	    }  
	
}
