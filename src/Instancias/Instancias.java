/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Instancias;

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

/**
 *
 * @author Matheus Prachedes Batista
 */
public class Instancias {
    public static final int NORMALIZAR_ENTRE_0E1 = 1;
    public static final int NORMALIZAR_ENTRE_M1E1 = 2;
    
    private ArrayList<Instancia> instancias = new ArrayList<>();
    private HashMap<String,Double[]> mapeamentoSaidas = new HashMap<>(10);
    private ArrayList<String> classes = new ArrayList<>();
    
    private int numClasses=0;
    private int numAtributos = 0;

    public Instancias(){
        
    }
    
    private Instancias(Instancias copia) {
        this.instancias = (ArrayList<Instancia>)copia.instancias.clone();
        this.mapeamentoSaidas = copia.mapeamentoSaidas;
        this.classes = copia.classes;
        this.numAtributos = copia.numAtributos;
        this.numClasses = copia.numClasses;
    }
    
    public void addInstancia(Instancia i){
        instancias.add(i);
    }
    
    public Instancia getInstancia(int i){
        return instancias.get(i);
    }
    
    public int size(){
        return instancias.size();
    }
    
    public double[] getAtributos(int i){
        return instancias.get(i).atributos;
    }
    
    public void embaralhar(){
        ArrayList<Instancia> novaInstancias = new ArrayList<>();
        Random r = new Random();
        while(instancias.size() > 0){
            novaInstancias.add(instancias.remove(r.nextInt(instancias.size())));
        }
        instancias = novaInstancias;
    }
    
    public Double[] getSaida(int i){
        return mapeamentoSaidas.get(instancias.get(i).classe);
    }
    
    public boolean abrirArquivo(File arquivo){
        instancias.clear();
        BufferedReader reader=null;
        try {
            int i,cont=0;
            String classe;
            reader = new BufferedReader(new FileReader(arquivo));
            String linha;
            String[] tokens;
            
            tokens = reader.readLine().split(",");
            numAtributos = tokens.length-1;
            while((linha = reader.readLine())!=null){
                double[] atributos = new double[numAtributos];
                tokens = linha.split(",");
                for(i=0;i<numAtributos;i++){
                    atributos[i] = Double.valueOf(tokens[i]);
                }
                classe = tokens[i];
                instancias.add(new Instancia(atributos,classe));
                mapeamentoSaidas.putIfAbsent(classe,null);
            }
            numClasses = mapeamentoSaidas.size();
            definirSaidasClasses();
            reader.close();
        } catch (IOException ex ) {
            instancias.clear();
            if(reader!=null){
                try {
                    reader.close();
                } catch (IOException ex1) {}
            }
        }
        return instancias.isEmpty();
    }   
    
    public void normalizar(int forma){
        switch(forma){
            case NORMALIZAR_ENTRE_0E1:
                normalizar(0,1);
                break;
            case NORMALIZAR_ENTRE_M1E1:
                normalizar(-1,1);
        }
    }

    private void normalizar(double limiteMin,double limiteMax) {
        int numAtr = instancias.get(0).atributos.length;
        double min[] = new double[numAtr];
        double max[] = new double[numAtr];
        for(int i=0;i<numAtr;i++){
            min[i] = Double.MAX_VALUE;
            max[i] = -Double.MAX_VALUE;
        }
        for(Instancia i : instancias){
            double[] atributos = i.atributos;
            for(int cont=0;cont<numAtr;cont++){
                if(atributos[cont] < min[cont])min[cont] = atributos[cont];
                if(atributos[cont] > max[cont])max[cont] = atributos[cont];
            }
        }
        for(Instancia i : instancias){
            i.normalizar(min,max,limiteMin,limiteMax);
        }
    }

    private void definirSaidasClasses() {
        Set<String> valorClasses = mapeamentoSaidas.keySet();
        int cont=0;
        for(String i : valorClasses){
            Double[] saidaClasses = new Double[numAtributos];
            for(int j=0;j<numAtributos;j++){
                saidaClasses[j] = 0.0d;
            }
            saidaClasses[cont++] = 1.0d;
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

    public Instancias separarTeste(double porcentagem) {
        Instancias teste = new Instancias();
        teste.classes = classes;
        teste.mapeamentoSaidas = mapeamentoSaidas;
        teste.numAtributos = numAtributos;
        teste.numClasses = numClasses;
        int tamTeste = (int)(instancias.size()*porcentagem/100d);
        Random r = new Random();
        for(int i=0;i<tamTeste;i++){
            teste.addInstancia(instancias.remove(r.nextInt(instancias.size())));
        }
        return teste;
    }

    public Instancias[] kfold(int i, int k) {
        Instancias[] retorno = new Instancias[2];
        retorno[0] = new Instancias(this);
        retorno[1] = new Instancias(this);
        retorno[1].instancias = new ArrayList<>(instancias.size()/k);
        for(int j=instancias.size()/k;j>=0;j--){
            if(j+k*i >= instancias.size())continue;
            retorno[1].instancias.add(retorno[0].instancias.remove(j+k*i));
        }
        return retorno;
    }

    
}
