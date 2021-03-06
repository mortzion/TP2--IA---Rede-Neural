package Instancias;

/*Representa uma instancia de dados com os atributos e o rotulo da classe*/
public class Instancia {
    public double[] atributos;
    public String classe;
    
    public Instancia(double[] atributos, String classe){
        this.atributos = atributos;
        this.classe = classe;
    }
    
    /**
     * Normaliza os atributos desta instancia de modo que os atributos do conjunto
     * estejam no intervalo [limiteMin, limiteMax]
     * @param min menor valor de cada atributo no conjunto
     * @param max maior valor de cada atributos no conjunto
     * @param limiteMin limiteMin da normalização
     * @param limiteMax limiteMax da normalização
     */
    protected void normalizar(double[] min, double[] max,double limiteMin, double limiteMax) {
        for(int i=0;i<atributos.length;i++){
            atributos[i] = ((atributos[i]-min[i])/(max[i]-min[i]))*(limiteMax-limiteMin)+limiteMin;
        }
    }
}
