package logic;

import dao.ComponenteDAO;
import entity.Componente;
import java.util.List;

/**
 * 
 * @author Alexander Avila <alexanderavilab at gmail.com>
 */
public class ComponenteLogic {

    public static boolean insertarComponente(Componente oComponente){
    
        return ComponenteDAO.insertarComponente(oComponente);
    }
    
    public static List<Componente> getLstComponentes(){
    
        return ComponenteDAO.getLstComponentes();
    }
}
