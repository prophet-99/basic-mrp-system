package dao;

import entity.Componente;
import entity.Tiempo;
import java.util.*;

/**
 * 
 * @author Alexander Avila <alexanderavilab at gmail.com>
 */
public class ComponenteDAO {

    private static List<Componente> lstComponentes;
    private static Tiempo unidadTiempo;
    
    public static boolean insertarComponente(Componente componente){
    
       lstComponentes.add(componente);
       
       return true;
    }
    
}
