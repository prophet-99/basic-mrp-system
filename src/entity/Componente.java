package entity;
/**
 * 
 * @author Alexander Avila <alexanderavilab at gmail.com>
 */
import java.util.Map;

public class Componente {
    
    private Integer nivel;
    private String nombre;
    private Integer stock_disponible;
    private Tiempo tiempo_entrega;
    private Map<Tiempo, Integer> ped_pendientes_recibir;

    public Tiempo getTiempo_entrega() {
        return tiempo_entrega;
    }

    public void setTiempo_entrega(Tiempo tiempo_entrega) {
        this.tiempo_entrega = tiempo_entrega;
    }

    public Map<Tiempo, Integer> getPed_pendientes_recibir() {
        return ped_pendientes_recibir;
    }

    public void setPed_pendientes_recibir(Map<Tiempo, Integer> ped_pendientes_recibir) {
        this.ped_pendientes_recibir = ped_pendientes_recibir;
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
    
}
