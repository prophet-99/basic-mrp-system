package logic;
/**
 * 
 * @author Alexander Avila <alexanderavilab at gmail.com>
 */

import entity.PMaestro;
import java.util.*;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
public class ModelRegistro {
   
    private static List<String> lstNombComp;
    
    public static DefaultTableModel modelRegistroInv(PMaestro planMaster){
        
        DefaultTableModel modelo = new DefaultTableModel();
       
        List<String> headersDinam = new ArrayList<>();
        headersDinam.add("Código");
        headersDinam.add("Nivel");
        headersDinam.add("Unidades por Componente");
        headersDinam.add("Componente-Padre");
        headersDinam.add("Tiempo-Entrega");
        headersDinam.add("Stock Actual");
              
        for (int i = 0; i < planMaster.getnPeriodos(); i++) {
            
            headersDinam.add("P" + String.valueOf(i+1));
        }
        String[] labels = new String[headersDinam.size()];
        labels = headersDinam.toArray(labels);
        
        modelo.setColumnIdentifiers(labels);
        
        lstNombComp = new ArrayList<>();
        for (int i = 0; i < planMaster.getnComponentes(); i++) {
            
            String[] headers = {"C" + String.valueOf(i+1), "", "", "Seleccionar Padre"};
            lstNombComp.add(headers[0]);
            
            modelo.addRow(headers);
        }
          
        return modelo;
    }
    
    public static void setJComboTable(JTable table, TableColumn columna){
    
        //combo de padres
        JComboBox<String> cPadre = new JComboBox<>();
        for (String componente : lstNombComp) {
  
            cPadre.addItem(componente);          
        }
      
        columna.setCellEditor(new DefaultCellEditor(cPadre));
        DefaultTableCellRenderer render = new DefaultTableCellRenderer();
        columna.setCellRenderer(render);
    }
    
}
