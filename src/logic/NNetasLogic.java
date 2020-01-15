package logic;
/**
 * 
 * @author Alexander Avila <alexanderavilab at gmail.com>
 */
import entity.Componente;
import java.io.FileOutputStream;
import javax.swing.JOptionPane;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
public class NNetasLogic {

    
    public static boolean generarReporte(Integer periodos, Componente component){
     
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet1 = workbook.createSheet("NNetas");
        XSSFSheet sheet2 = workbook.createSheet("NBrutas");
        
        XSSFCell cell = null;
        
        String[] colH1 = {"PLAZO", "DISPONIBLE", "CÓDIGO-NIVEL", "CÓDIGO-ARTICULO",
                          " "};
        
        XSSFRow rowH1 = sheet1.createRow(0);
        for (int i = 0; i < colH1.length; i++) {
            
            sheet1.addMergedRegion(new CellRangeAddress(0, 1, i, i));
            
            cell = rowH1.createCell(i);
            cell.setCellValue(colH1[i]);
        }
       
        sheet1.addMergedRegion(new CellRangeAddress(0, 0, 5, 4 + periodos));
        String[] colH2 = {"PERIODOS"};
        cell = rowH1.createCell(5);
        cell.setCellValue(colH2[0]);
        
        XSSFRow rowH3 = sheet1.createRow(1);
        String[] colH3 = new String[periodos];
        for (int i = 0; i < periodos; i++) {
            
            colH3[i] = String.valueOf("P" + (i+1));
            
            cell = rowH3.createCell(i+5);
            cell.setCellValue(colH3[i]);
        }
        
        String [] rowHeaders = {"NECESIDADES BRUTAS", "RECEPCIONES PROGRAMADAS", 
                                "DISPONIBLE ESTIMADO", "NECESIDADES NETAS",
                                "RECEPCIÓN DE ORDEN", "LANZAMIENTO DE ORDEN"};
        
        for (int row = 0; row < (colH1.length + colH2.length + colH3.length); row++) {
           
            
        }
        
        
        //Crear Excel
        try(FileOutputStream out = new FileOutputStream("NB.xlsx")){
            
            workbook.write(out);
        }catch(Exception ex){
        
            JOptionPane.showMessageDialog(null, "Ocurrió un errror");
        }
        
        return true;
    }
   
}
