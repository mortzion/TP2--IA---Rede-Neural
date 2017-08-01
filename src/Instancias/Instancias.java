package Instancias;

import Funções.Função;
import Instancias.Instancia;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/*Classe que representa um conjunto de instancias*/
public class Instancias {
    public static final int NORMALIZAR_ENTRE_0E1 = 1;
    public static final int NORMALIZAR_ENTRE_M1E1 = 2;

    /*Instancias  do conjunto*/
    private ArrayList<Instancia> instancias = new ArrayList<>();
    
    /**Saida esperada da rede para cada classe
     *Armazenada para evitar calcular a saida toda hora*/
    private HashMap<String, Double[]> mapeamentoSaidas = new HashMap<>(10);
    
    /*Classes do conjunto de instancias*/
    private ArrayList<String> classes = new ArrayList<>();

    private int numClasses = 0;
    private int numAtributos = 0;

    public Instancias(){}
  
    public Instancia getInstancia(int i) {
        return instancias.get(i);
    }

    public int size() {
        return instancias.size();
    }
    
    /*Retorna os atributos da instancia i*/
    public double[] getAtributos(int i) {
        return instancias.get(i).atributos;
    }

    /*Embaralha o conjunto de instancias*/
    public void embaralhar() {
        ArrayList<Instancia> novaInstancias = new ArrayList<>();
        Random r = new Random();
        while (instancias.size() > 0) {
            novaInstancias.add(instancias.remove(r.nextInt(instancias.size())));
        }
        instancias = novaInstancias;
    }

    /**Retorna a saida esperada da rede para a instancia i**/
    public Double[] getSaida(int i) {
        return mapeamentoSaidas.get(instancias.get(i).classe);
    }
    
    /**Abre o arquivo especificado pelo parametro e carrega as instancias**/
    public boolean abrirArquivo(File arquivo) {
        instancias.clear();
        BufferedReader reader = null;
        try {
            int i, cont = 0;
            String classe;
            reader = new BufferedReader(new FileReader(arquivo));
            String linha;
            String[] tokens;

            tokens = reader.readLine().split(",");
            numAtributos = tokens.length - 1;
            while ((linha = reader.readLine()) != null) {
                double[] atributos = new double[numAtributos];
                tokens = linha.split(",");
                for (i = 0; i < numAtributos; i++) {
                    atributos[i] = Double.valueOf(tokens[i]);
                }
                classe = tokens[i];
                instancias.add(new Instancia(atributos, classe));
                mapeamentoSaidas.putIfAbsent(classe, null);
            }
            numClasses = mapeamentoSaidas.size();
            reader.close();
        } catch (IOException ex) {
            instancias.clear();
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException ex1) {
                }
            }
        }
        return instancias.isEmpty();
    }

    /**
     * Recupera o minimo e maximo de cada atributo do conjunto 'c' e retorna nos vetores
     * de entrada
     */
    private static void recuperaMinMax(double[] min, double[]max, Instancias c){
        if(c==null)return;
        for(Instancia i : c.instancias){
            double[] atributos = i.atributos;
            for (int cont = 0; cont < atributos.length; cont++) {
                if (atributos[cont] < min[cont]) {
                    min[cont] = atributos[cont];
                }
                if (atributos[cont] > max[cont]) {
                    max[cont] = atributos[cont];
                }
            }
        }
    }
    
    /**
     * Normaliza cada instancia do conjunto 'c'
     * @param min menor valor de cada atributo do conjunto
     * @param max maior valor de cada stributo do conjunto
     * @param limiteMin menor valor do intervalo após normalização
     * @param limiteMax maior valor do intervalo após normalização
     * @param c conjunto que será normalizado
     */
    private static void normaliza(double[] min, double[] max, double limiteMin, double limiteMax, Instancias c){
        if(c == null)return;
        for(Instancia i : c.instancias){
            i.normalizar(min, max, limiteMin, limiteMax);
        }
    }
    
    /**
     * Normaliza este conjunto para que os atributos estejam entre [limiteMin,limiteMax]
     * @param c Conjunto que será normalizado junto com este.
     */
    public void normalizar(double limiteMin, double limiteMax, Instancias c) {
        int numAtr = instancias.get(0).atributos.length;
        double min[] = new double[numAtr];
        double max[] = new double[numAtr];
        for (int i = 0; i < numAtr; i++) {
            min[i] = Double.MAX_VALUE;
            max[i] = -Double.MAX_VALUE;
        }
        recuperaMinMax(min, max, this);
        recuperaMinMax(min,max,c);
        normaliza(min,max,limiteMin,limiteMax,c);
        normaliza(min,max,limiteMin,limiteMax,this);
    }

    /**
     * Define as saidas esperadas pela rede para cada classe do conjunto.
     * @param funçãoPropagação A função é usada para definir o minimo e o maximo da saida.
     */
    public void definirSaidasClasses(Função funçãoPropagação) {
        Set<String> valorClasses = mapeamentoSaidas.keySet();
        int cont = 0;
        for (String i : valorClasses) {
            Double[] saidaClasses = new Double[numAtributos];
            for (int j = 0; j < numAtributos; j++) {
                saidaClasses[j] = funçãoPropagação.menorValorImagem();
            }
            saidaClasses[cont++] = funçãoPropagação.maiorValorImagem();
            mapeamentoSaidas.put(i, saidaClasses);
            classes.add(i);
        }
    }

    public int getNumClasses() {
        return numClasses;
    }

    public int getIndexClasse(int i) {
        return classes.indexOf(instancias.get(i).classe);
    }
}
