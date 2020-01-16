/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package logic;
/**
 * 
 * @author Alexander Avila <alexanderavilab at gmail.com>
 */
import dao.PMaestroDAO;
import entity.PMaestro;
import java.util.*;
public class PMaestroLogic {

    public static boolean setProd_requer(PMaestro prodRq){
    
        PMaestroDAO.insertarPMaestro(prodRq);
        return true;
    }
    
    public static List<PMaestro> getLstProd_requer(){

        return PMaestroDAO.getLstPMaestro();
    }   
    
    public static void resetData(){
    
        PMaestroDAO.resetData();
    }
}
