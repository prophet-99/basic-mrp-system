package dao;

import entity.Componente;
import java.util.*;

/**
 * 
 * @author Alexander Avila <alexanderavilab at gmail.com>
 */
public class ComponenteDAO {

    private static List<Componente> lstComponentes = new ArrayList<>();
    
    public static boolean insertarComponente(Componente componente){
    
       lstComponentes.add(componente);
       
       return true;
    }
    
    public static List<Componente> getLstComponentes(){
    
        return lstComponentes;
    }
    
}
