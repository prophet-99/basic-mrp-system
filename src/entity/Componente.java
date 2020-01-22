package entity;
/**
 * 
 * @author Alexander Avila <alexanderavilab at gmail.com>
 */
import java.util.*;

public class Componente {
    
    private Integer nivel;
    private String nombre;
    private String cPadre;
    private Integer stock_disponible;
    private Integer tiempo_entrega;
    private List<Map<String,Integer>> lstPed_pendientes_recibir;

    public Integer getTiempo_entrega() {
        return tiempo_entrega;
    }

    public void setTiempo_entrega(Integer tiempo_entrega) {
        this.tiempo_entrega = tiempo_entrega;
    }

    public List<Map<String, Integer>> getLstPed_pendientes_recibir() {
        return lstPed_pendientes_recibir;
    }

    public void setLstPed_pendientes_recibir(List<Map<String, Integer>> lstPed_pendientes_recibir) {
        this.lstPed_pendientes_recibir = lstPed_pendientes_recibir;
    }
        
    public Integer getNivel() {
        return nivel;
    }

    public void setNivel(Integer nivel) {
        this.nivel = nivel;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getStock_disponible() {
        return stock_disponible;
    }

    public void setStock_disponible(Integer stock_disponible) {
        this.stock_disponible = stock_disponible;
    }
    
    public String getcPadre() {
        return cPadre;
    }

    public void setcPadre(String cPadre) {
        this.cPadre = cPadre;
    }
}
