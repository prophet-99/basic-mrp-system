package logic;
/**
 * 
 * @author Alexander Avila <alexanderavilab at gmail.com>
 */
import entity.Componente;
import entity.PMaestro;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.swing.JOptionPane;
import org.apache.poi.ss.usermodel.BorderStyle;
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

    
    public static boolean generarReporte(PMaestro masterP, List<Componente> lstComponentes, String rutaGuardado){
     
        XSSFWorkbook workbook = new XSSFWorkbook();
        
        XSSFSheet sheet = workbook.createSheet("NBrutas");
        XSSFSheet sheet1 = workbook.createSheet("NNetas");
        
        sheet1.setColumnWidth(4, 7000);
        sheet.setColumnWidth(2, 8000);
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
           
           //SECCION NECESIDADES BRUTAS
           cell = nb.createCell(4);
           cell.setCellValue(rowHeaders[0]);
           //crea los datos del plan maestro(periodo)
            
            List<PMaestro> lstProd_req = PMaestroLogic.getLstProd_requer();
            for (int i = 0; i < masterP.getnPeriodos(); i++) {
                
                boolean band = (lstProd_req.get(i)!= null)?true:false;
                //Plan maestro, solo primer articulo    
                if(band){
                   
                    for (Entry<String, Integer> e : lstProd_req.get(i).getCant_prod().entrySet()) {
                       
                     if(col != 0) break;
                     
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
            //algoritmo generador de procesos a partir del segundo componente
            if(col != 0){
                
             int aum = 2;
                for (int j = 0; j < masterP.getnComponentes()-1; j++){
                    XSSFRow rowCompPadre = sheet1.getRow(j+aum);
                        
                        if(rowCompPadre != null){
                        String codArticulo = rowCompPadre.getCell(3).getStringCellValue();
                        //Compara el componente padre con el componente del articulo
                            if(lstComponentes.get(col).getcPadre().equals(codArticulo)){
                                XSSFRow lOrdenPadre = sheet1.getRow(j+aum+5);
                            
                                for (int k = 0; k < masterP.getnPeriodos(); k++) {
                                    if(lOrdenPadre.getCell(k+5) != null){
                                        Double vComPadre = lOrdenPadre.getCell(k+5).getNumericCellValue();
   
                                        XSSFCell cellComp = nb.createCell(k+5);
                                        cellComp.setCellValue(vComPadre*lstComponentes.get(col).getuXComponent());
                                        
                                        //ESTILO DE LA CELDA
                                        cellComp.setCellStyle(lOrdenPadre.getCell(k+5).getCellStyle());
                                    }
                                }
                            }
                        }
                        aum+=5;
                }
            }
            
           //SECCION RECEPCIONES PROG
           XSSFRow rp = sheet1.createRow(col + (++patron));
           cell = rp.createCell(4);
           cell.setCellValue(rowHeaders[1]);
           
           //SECCION DISPONIBLE ESTIM
           XSSFRow de = sheet1.createRow(col + (++patron));
           cell = de.createCell(4);
           cell.setCellValue(rowHeaders[2]);
           //SETEANDO LA DATA PARA OPERAR
           Double StockDisponible = nb.getCell(1).getNumericCellValue();
            for (int i = 0; i < masterP.getnPeriodos(); i++) {
                
              if(ComponenteLogic.getLstComponentes().get(col).getLstPed_pendientes_recibir() != null){
                for (Map<String, Integer> pedPendientes : ComponenteLogic.getLstComponentes().get(col).getLstPed_pendientes_recibir()) {
                    for (Entry<String, Integer> e : pedPendientes.entrySet()) {
                    
                    Integer periodo = Integer.parseInt(e.getKey());
                    if(periodo == (i+1)){
                    
                        cell = rp.createCell(i + 5);
                        cell.setCellValue(e.getValue());
                    }
                  }
                }
               }
                
                XSSFCell cellNB = nb.getCell(i + 5);
                XSSFCell cellRP = rp.getCell(i + 5);
                cell = de.createCell(i + 5);
                cell.setCellValue(StockDisponible);
                
                if(cellNB == null){
                    
                    cell = de.createCell(i + 5);
                    cell.setCellValue(StockDisponible);
                    
                    if(cellRP != null){
                        StockDisponible += cellRP.getNumericCellValue();
                    }
                                        
                }else{
                    Double nBrut = cellNB.getNumericCellValue();
                    Double dEstim = cell.getNumericCellValue();
                    
                    cell = de.createCell(5 + i);
                    
                    if(cellRP != null){
                        Double recep_progrm = cellRP.getNumericCellValue();
                        if((dEstim + recep_progrm)-nBrut > 0){
                            cell.setCellValue(StockDisponible);
                            StockDisponible = (dEstim + recep_progrm)-nBrut;
                        }else{
                            cell.setCellValue(StockDisponible);
                            StockDisponible = 0.0;
                        }
                        
                    }else{
                        if((dEstim - nBrut) > 0){
                            cell.setCellValue(StockDisponible);
                            StockDisponible = dEstim-nBrut;
                        }else{
                            cell.setCellValue(StockDisponible);
                            StockDisponible = 0.0;
                        }
                    }  
                }
            }
           
           //SECCION NEC NETAS
           XSSFRow nn = sheet1.createRow(col + (++patron));
           cell = nn.createCell(4);
           cell.setCellValue(rowHeaders[3]);
           //Seteando la data
           for (int i = 0; i < masterP.getnPeriodos(); i++) {
             
                   XSSFCell cellBruta = nb.getCell(i+5);
                   XSSFCell cellRecep = rp.getCell(i+5);
                   
                   if(cellBruta != null & cellRecep == null){
                       
                       Double neceBrut = nb.getCell(i+5).getNumericCellValue();
                       Double stockDisp = de.getCell(i+5).getNumericCellValue();
                       
                       if(neceBrut-stockDisp > 0){
                           cell = nn.createCell(i+5);
                           cell.setCellValue(neceBrut - stockDisp);
                       }
                   }
                   
                   if(cellBruta != null & cellRecep != null){
                       
                       Double neceBrut = nb.getCell(i+5).getNumericCellValue();
                       Double stockDisp = de.getCell(i+5).getNumericCellValue();
                       Double recepProg = rp.getCell(i+5).getNumericCellValue();
                       
                       if((neceBrut - (stockDisp+recepProg)) > 0){
                           cell = nn.createCell(i+5);
                           cell.setCellValue(neceBrut - (stockDisp+recepProg));
                       }
                   }
           }
           
           //SECCION RECEPC ORDEN
           XSSFRow ro = sheet1.createRow(col + (++patron));
           cell = ro.createCell(4);
           cell.setCellValue(rowHeaders[4]);
           //seteando la data
            for (int i = 0; i < masterP.getnPeriodos(); i++) {
                XSSFCell cellNn = nn.getCell(i+5);
                
                if(cellNn != null){
                    cell = ro.createCell(i+5);
                    cell.setCellValue(cellNn.getNumericCellValue());
                }
            }
           
           //SECCION LANZ ORDEN
           XSSFRow lo = sheet1.createRow(col + (++patron));
           cell = lo.createCell(4);
           cell.setCellValue(rowHeaders[5]);
           XSSFCellStyle estiloBord = workbook.createCellStyle();
           estiloBord.setBorderBottom(BorderStyle.THICK);
           estiloBord.setBottomBorderColor(IndexedColors.GREY_80_PERCENT.getIndex());
           cell.setCellStyle(estiloBord);
           //seteando la data
            XSSFRow prd1 = sheet1.getRow(1);
            Integer VPrd1 = Integer.parseInt(prd1.getCell(5).getStringCellValue().substring(1));
            
            XSSFCellStyle styleLO = workbook.createCellStyle();
            for (int i = 0; i < masterP.getnPeriodos(); i++) {
                XSSFCell cellNn = nn.getCell(i+5);
                
                if(cellNn != null){
                    Double vCellNn = cellNn.getNumericCellValue();
                    Integer plazo = (int)nb.getCell(0).getNumericCellValue();
                    
                    if(prd1.getCell(i+5-plazo) != null)
                        if(VPrd1 <= Integer.parseInt(prd1.getCell(i+5-plazo).getStringCellValue().substring(1))){
                            cell = lo.createCell((i+5)-plazo);
                            cell.setCellValue(vCellNn);
                            
                            //ESTILO CELDA
                            int r  = (int) (Math.random() * 155) + 100;
                            int g  = (int) (Math.random() * 185) + 70;
                            int b  = (int) (Math.random() * 185) + 70;
                            styleLO.setFillForegroundColor(new XSSFColor(new java.awt.Color(r, g, b)));
                            styleLO.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                            styleLO.setBorderBottom(BorderStyle.THICK);
                            styleLO.setBottomBorderColor(IndexedColors.GREY_80_PERCENT.getIndex());
                            cell.setCellStyle(styleLO);
                        }        
                }
            }
            //SETEO ESTILOS DE BORDES INFERIORES
            XSSFCellStyle estiloBorde = workbook.createCellStyle();
            estiloBorde.setBorderBottom(BorderStyle.THICK);
            estiloBorde.setBottomBorderColor(IndexedColors.GREY_80_PERCENT.getIndex());
            
            lo.setRowStyle(estiloBorde);         
        }
        
        
        
        //SECCION NECESIDADES BRUTAS
        XSSFCell cellNB = null;
        
        String[] colHeaders = {"N°", "CÓDIGO-ARTICULO", " "};
        
        XSSFRow rowBrutHeader = sheet.createRow(0);
        
        for (int i = 0; i < colHeaders.length; i++) {
            sheet.addMergedRegion(new CellRangeAddress(0, 1, i, i));
            cellNB = rowBrutHeader.createCell(i);
            cellNB.setCellValue(colHeaders[i]);
        }
       
       sheet.addMergedRegion(new CellRangeAddress(0, 0, 3, (2 + masterP.getnPeriodos()) ));
       cellNB =  rowBrutHeader.createCell(3);
       cellNB.setCellValue("PERIODOS");
       
       XSSFRow rowBrutHeader2 = sheet.createRow(1);
      
       String[] colHeaders2 = new String[masterP.getnPeriodos()];
       for (int i = 0; i < masterP.getnPeriodos(); i++) {
            
            colHeaders2[i] = String.valueOf("P" + (i+1));
            
            cellNB = rowBrutHeader2.createCell(i+3);
            cellNB.setCellValue(colHeaders2[i]);
        }
        
       int patronNB = 2;
        for (int comp = 0; comp < masterP.getnComponentes(); comp++) {
            
            //Combinan las celdas
            sheet.addMergedRegion(new CellRangeAddress(comp + patronNB, comp + patronNB+1, 0, 0));
            sheet.addMergedRegion(new CellRangeAddress(comp + patronNB, comp + patronNB+1, 1, 1));
            
            //Setear data de Nª-codigo-labels verticales
                XSSFRow fechRequer = sheet.createRow(comp + patronNB);
                cellNB = fechRequer.createCell(0);
                cellNB.setCellValue(comp+1);
                
                cellNB = fechRequer.createCell(1);
                cellNB.setCellValue(lstComponentes.get(comp).getNombre());
            
                cellNB = fechRequer.createCell(2);
                cellNB.setCellValue("FECHA REQUERIDA DE LA ORDEN");
                 
                List<PMaestro> lstProd_req = PMaestroLogic.getLstProd_requer();
            for (int i = 0; i < masterP.getnPeriodos(); i++) {
                
                boolean band = (lstProd_req.get(i)!= null)?true:false;
                //Plan maestro, solo primer articulo    
                if(band){
                   
                    for (Entry<String, Integer> e : lstProd_req.get(i).getCant_prod().entrySet()) {
                       
                     if(comp != 0) break;
                     
                      if(e != null){
                        XSSFCell celda = sheet.getRow(1).getCell(i+3);
                        String idt = celda.getStringCellValue().substring(1);
                          
                        if(e.getKey().equals(idt)){
                            
                            cellNB = fechRequer.createCell(i+3);
                            cellNB.setCellValue(e.getValue());
                            
                            //ESTILO CELDA
                            int r  = (int) (Math.random() * 155) + 100;
                            int g  = (int) (Math.random() * 185) + 70;
                            int b  = (int) (Math.random() * 185) + 70;
                            //ESTILO DE LA CELDA
                            XSSFCellStyle stylePadre = workbook.createCellStyle();
                            
                            stylePadre.setFillForegroundColor(new XSSFColor(new java.awt.Color(r, g, b)));
                            stylePadre.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                            cellNB.setCellStyle(stylePadre);
                        }
                      }
                    }
                }
                    //algoritmo generador de procesos a partir del segundo componente
                if(comp != 0){
                
                int aum = 2;
                for (int j = 0; j < masterP.getnComponentes()-1; j++){
                    XSSFRow rowCompPadre = sheet.getRow(j+aum);
                        
                        if(rowCompPadre != null){
                        String codArticulo = rowCompPadre.getCell(1).getStringCellValue();
                        //Compara el componente padre con el componente del articulo
                            if(lstComponentes.get(comp).getcPadre().equals(codArticulo)){
                                XSSFRow fechLanzPadre = sheet.getRow(j+aum+1);
                            
                                for (int k = 0; k < masterP.getnPeriodos(); k++) {
                                    if(fechLanzPadre.getCell(k+3) != null){
                                        Double vComPadre = fechLanzPadre.getCell(k+3).getNumericCellValue();
                                        
                                        XSSFCell cellComp = fechRequer.createCell(k+3);
                                        cellComp.setCellValue(vComPadre*lstComponentes.get(comp).getuXComponent());
                                        
                                       //ESTILO DE LA CELDA
                                       cellComp.setCellStyle(fechLanzPadre.getCell(k+3).getCellStyle());
                                    }
                                }
                            }
                        }
                        aum+=1;
                    }
                }
            }
             //Setear data de fecha de lanzamiento
                XSSFRow fechLanz = sheet.createRow(comp + (++patronNB));
                cellNB = fechLanz.createCell(2);
                cellNB.setCellValue("FECHA DE LANZAMIENTO DE LA ORDEN");
                XSSFCellStyle estiloBord = workbook.createCellStyle();
                estiloBord.setBorderBottom(BorderStyle.THICK);
                estiloBord.setBottomBorderColor(IndexedColors.GREY_80_PERCENT.getIndex());
                cellNB.setCellStyle(estiloBord);
                //seteando la data
                XSSFRow prd1 = sheet.getRow(1);
                Integer VPrd1 = Integer.parseInt(prd1.getCell(3).getStringCellValue().substring(1));
                 
                //ESTILO DE LA CELDA
                XSSFCellStyle styleHijo = workbook.createCellStyle();
                
                for (int i = 0; i < masterP.getnPeriodos(); i++) {
                    XSSFCell cellfechReq = fechRequer.getCell(i+3);
                
                    if(cellfechReq != null){
                        Double vCellfechReq = cellfechReq.getNumericCellValue();
                        Integer plazo = lstComponentes.get(comp).getTiempo_entrega();
                    
                    if(prd1.getCell(i+3-plazo) != null)
                        if(VPrd1 <= Integer.parseInt(prd1.getCell(i+3-plazo).getStringCellValue().substring(1))){
                            
                            cellNB = fechLanz.createCell((i+3)-plazo);
                            cellNB.setCellValue(vCellfechReq);
                            
                            //ESTILO DE LA CELDA
                            XSSFCellStyle estiloTotal = workbook.createCellStyle();
                            estiloTotal.cloneStyleFrom(cellfechReq.getCellStyle());
                            estiloTotal.setBorderBottom(BorderStyle.THICK);
                            estiloTotal.setBottomBorderColor(IndexedColors.GREY_80_PERCENT.getIndex());
                            
                            cellNB.setCellStyle(estiloTotal);
                        }        
                    }
            }
             
            //SETEO ESTILOS DE BORDES INFERIORES
            XSSFCellStyle estiloBorde = workbook.createCellStyle();
            estiloBorde.setBorderBottom(BorderStyle.THICK);
            estiloBorde.setBottomBorderColor(IndexedColors.GREY_80_PERCENT.getIndex());
            
            fechLanz.setRowStyle(estiloBorde); 
        }
      
   
        
        //Crear Excel 
        try(FileOutputStream out = new FileOutputStream(rutaGuardado + "/MRP.xlsx")){
            
            workbook.write(out);
        }catch(Exception ex){
        
            JOptionPane.showMessageDialog(null, "Ocurrió un errror " + ex);
        }
        
        return true;
    }

}
