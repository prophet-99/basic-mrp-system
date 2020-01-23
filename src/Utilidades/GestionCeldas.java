package Utilidades;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
 
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class GestionCeldas extends DefaultTableCellRenderer {
    
    private String tipo="text";
    private Font normal = new Font( "Verdana",Font.PLAIN ,12 );
    private Font bold = new Font( "Verdana",Font.BOLD ,12 );
    
    public GestionCeldas(){
   
 }
    public GestionCeldas(String tipo){
  this.tipo=tipo;
 }
    
 public Component getTableCellRendererComponent(JTable table, Object value, boolean selected, boolean focused, int row, int column) {
        
     Color colorFondo = null;
     Color colorFondoPorDefecto=new Color( 192, 192, 192);
     Color colorFondoSeleccion=new Color( 140, 140 , 140);
     if (selected) {                
            this.setBackground(colorFondoPorDefecto );   
        }
        else
        {
            this.setBackground(Color.white);
        }
     if( tipo.equals("texto"))
        {
         //si es tipo texto define el color de fondo del texto y de la celda así como la alineación
            if (focused) {
       colorFondo=colorFondoSeleccion;
      }else{
       colorFondo= colorFondoPorDefecto;
      }
            this.setHorizontalAlignment( JLabel.LEFT );
            this.setText( (String) value );
            //this.setForeground( (selected)? new Color(255,255,255) :new Color(0,0,0) );   
            //this.setForeground( (selected)? new Color(255,255,255) :new Color(32,117,32) );
            this.setBackground( (selected)? colorFondo :Color.WHITE); 
            this.setFont(normal);   
            //this.setFont(bold);
            return this;
        }
     
     if( tipo.equals("numerico"))
        {           
         if (focused) {
        colorFondo=colorFondoSeleccion;
       }else{
        colorFondo=colorFondoPorDefecto;
       }
         // System.out.println(value);
            this.setHorizontalAlignment( JLabel.CENTER );
            this.setText( (String) value );            
            this.setForeground( (selected)? new Color(255,255,255) :new Color(32,117,32) );    
            this.setBackground( (selected)? colorFondo :Color.WHITE);
           // this.setBackground( (selected)? colorFondo :Color.MAGENTA);
            this.setFont(bold);            
            return this;   
        }
     return this;
 }
 }

