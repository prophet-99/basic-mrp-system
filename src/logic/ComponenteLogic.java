package logic;

import dao.ComponenteDAO;
import entity.Componente;

/**
 * 
 * @author Alexander Avila <alexanderavilab at gmail.com>
 */
public class ComponenteLogic {

    public static boolean insertarComponente(Componente oComponente){
    
        return ComponenteDAO.insertarComponente(oComponente);
    }
}
