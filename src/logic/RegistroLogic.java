package logic;
/**
 * 
 * @author Alexander Avila <alexanderavilab at gmail.com>
 */

import entity.PMaestro;
import java.util.*;
import javax.swing.table.DefaultTableModel;
public class RegistroLogic {
    
    public static DefaultTableModel modelRegistroInv(PMaestro planMaster){
        
        DefaultTableModel modelo = new DefaultTableModel();
       
        List<String> headersDinam = new ArrayList<>();
        headersDinam.add("Código");
        headersDinam.add("Nivel");
        headersDinam.add("Tiempo-Entrega");
        headersDinam.add("Stock Actual");
        
        for (int i = 0; i < planMaster.getnPeriodos(); i++) {
            
            headersDinam.add("P" + String.valueOf(i+1));
        }
        String[] labels = new String[headersDinam.size()];
        labels = headersDinam.toArray(labels);
        
        modelo.setColumnIdentifiers(labels);
        
        List<String> bodyDinam = new ArrayList<>();
       
        for (int i = 0; i < planMaster.getnComponentes(); i++) {
            
            String[] headers = {"C" + String.valueOf(i+1)};
           
            modelo.addRow(headers);
        }
       
        return modelo;
    }
    
}
