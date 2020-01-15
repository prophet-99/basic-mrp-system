package entity;
/**
 * 
 * @author Alexander Avila <alexanderavilab at gmail.com>
 */
import java.util.Map;

public class PMaestro {

    private Integer nPeriodos;
    private Integer nComponentes;
    private Map<String, Integer> cant_prod;

    public Map<String, Integer> getCant_prod() {
        return cant_prod;
    }

    public void setCant_prod(Map<String, Integer> cant_prod) {
        this.cant_prod = cant_prod;
    }

    public Integer getnPeriodos() {
        return nPeriodos;
    }

    public void setnPeriodos(Integer nPeriodos) {
        this.nPeriodos = nPeriodos;
    }

    public Integer getnComponentes() {
        return nComponentes;
    }

    public void setnComponentes(Integer nComponentes) {
        this.nComponentes = nComponentes;
    }
    
    
}
