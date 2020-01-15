package dao;
/**
 * 
 * @author Alexander Avila <alexanderavilab at gmail.com>
 */
import entity.PMaestro;
import java.util.*;

public class PMaestroDAO {

    private static List<PMaestro> lstPMaestro = new ArrayList<>();
    
    public static boolean insertarPMaestro(PMaestro pMaestro){
    
       lstPMaestro.add(pMaestro);
       
       return true;
    }
    
    public static List<PMaestro> getLstPMaestro(){
    
        return lstPMaestro;
    }
}
