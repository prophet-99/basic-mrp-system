package logic;
/**
 * 
 * @author Alexander Avila <alexanderavilab at gmail.com>
 */
import entity.Componente;
import entity.PMaestro;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Map.Entry;
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

    
    public static boolean generarReporte(PMaestro masterP, List<Componente> lstComponentes){
     
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
       
        sheet1.addMergedRegion(new CellRangeAddress(0, 0, 5, 4 + masterP.getnPeriodos()));
        String[] colH2 = {"PERIODOS"};
        cell = rowH1.createCell(5);
        cell.setCellValue(colH2[0]);
        
        XSSFRow rowH3 = sheet1.createRow(1);
        String[] colH3 = new String[masterP.getnPeriodos()];
        for (int i = 0; i < masterP.getnPeriodos(); i++) {
            
            colH3[i] = String.valueOf("P" + (i+1));
            
            cell = rowH3.createCell(i+5);
            cell.setCellValue(colH3[i]);
        }
        
        String [] rowHeaders = {"NECESIDADES BRUTAS", "RECEPCIONES PROGRAMADAS", 
                                "DISPONIBLE ESTIMADO", "NECESIDADES NETAS",
                                "RECEPCIÓN DE ORDEN", "LANZAMIENTO DE ORDEN"};
        
        int patron = 2;
        for (int col = 0; col < (masterP.getnComponentes()); col++) {
           
           //COMBINACION DE CELDAS PLAZO-DISPONIBLE...
           sheet1.addMergedRegion(new CellRangeAddress(col + patron, col + patron+5, 0, 0));
           sheet1.addMergedRegion(new CellRangeAddress(col + patron, col + patron+5, 1, 1));
           sheet1.addMergedRegion(new CellRangeAddress(col + patron, col + patron+5, 2, 2));
           sheet1.addMergedRegion(new CellRangeAddress(col + patron, col + patron+5, 3, 3));
           
           //SECCION de Headers secundarios(necBrutas-recProgramdas....)
           XSSFRow nb = sheet1.createRow(col + patron);
           //crea Plazo
           cell = nb.createCell(0);
           cell.setCellValue(lstComponentes.get(col).getTiempo_entrega());
           //crea stock
           cell = nb.createCell(1);
           cell.setCellValue(lstComponentes.get(col).getStock_disponible());
           //crea codigo nivel
           cell = nb.createCell(2);
           cell.setCellValue(lstComponentes.get(col).getNivel());
           //crea codigo articulo
           cell = nb.createCell(3);
           cell.setCellValue(lstComponentes.get(col).getNombre());
           //crea HEADER SECUNDARIO necesidades brutas
           cell = nb.createCell(4);
           cell.setCellValue(rowHeaders[0]);
           //crea los datos del plan maestro(periodo)
           List<PMaestro> lstProd_req = PMaestroLogic.getLstProd_requer();
            for (int i = 0; i < masterP.getnPeriodos(); i++) {
                
                boolean band = (lstProd_req.get(i)!= null)?true:false;
                    
                if(band){
                   
                    for (Entry<String, Integer> e : lstProd_req.get(i).getCant_prod().entrySet()) {
                       
                      if(e != null){
                        XSSFCell celda = sheet1.getRow(1).getCell(i+5);
                        String idt = celda.getStringCellValue().substring(1);
                          
                        if(e.getKey().equals(idt)){
                            cell = nb.createCell(i+5);
                            cell.setCellValue(e.getValue());
                        }
                      }
                    }
                }
            }
           
           XSSFRow ex = sheet1.createRow(col + (++patron));
           cell = ex.createCell(4);
           cell.setCellValue(rowHeaders[1]);
           
           XSSFRow de = sheet1.createRow(col + (++patron));
           cell = de.createCell(4);
           cell.setCellValue(rowHeaders[2]);
           
           XSSFRow nn = sheet1.createRow(col + (++patron));
           cell = nn.createCell(4);
           cell.setCellValue(rowHeaders[3]);
           
           XSSFRow ro = sheet1.createRow(col + (++patron));
           cell = ro.createCell(4);
           cell.setCellValue(rowHeaders[4]);
           
           XSSFRow lo = sheet1.createRow(col + (++patron));
           cell = lo.createCell(4);
           cell.setCellValue(rowHeaders[5]);
        }
        
        //Crear Excel
        try(FileOutputStream out = new FileOutputStream("NB.xlsx")){
            
            workbook.write(out);
        }catch(Exception ex){
        
            JOptionPane.showMessageDialog(null, "Ocurrió un errror " + ex);
        }
        
        return true;
    }
   
}
