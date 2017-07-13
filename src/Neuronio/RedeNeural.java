/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Neuronio;

import Funções.Função;
import Funções.Logistica;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Matheus Prachedes Batista
 */
public class RedeNeural {
    /**Usado para gerar os pesos aleatorios dos neuronios*/
    public static final Random rand = new Random();
    
    /**Função de propagação usada pelos neuronios da rede*/
    protected Função funçãoPropagação;
    
    /**Camada de neuronios de entrada*/
    protected CamadaEntrada entrada;
    
    /**Camadas de neuronios ocultos usados para o processamento*/
    protected CamadaProcessamento[] oculta;
    
    /**Camada de neuronios na saida*/
    protected CamadaProcessamento saida;
    
    /**Taxa de aprendizado para esta rede*/
    protected double taxaAprendizado = 0.1;
    
    /**
     * Numero de iterações para parar o treinamento caso a rede não convergir.
     * Caso o erro da rede seja menor que <code>limiar</code> o treinamento para
     * antes
     */
    protected int numIteraçõesLimite = 20000;
    
    /**Limiar usado para parar o treinamento antes do limite de iterações.
     * O treinamento para caso o valor do erro seja menor que o limiar
     */
    protected double limiar = 0.000001f;
    
    /**
     * Cria a rede neural e instancia as camadas e os neuronios da rede.
     * Os pesos dos neuronios são decididos aleatoriamente seguindo uma distribuição
     * gaussiana com media 0 e desvio padrão 1.
     * 
     * As camadas ocultas possuem a mesma quantidade de neuronios.
     * 
     * @param numNeuroniosEntrada Numero de neuronios na camada de entrada
     * @param numNeuroniosOcultos Numero de neuronios na camada oculta
     * @param numCamadasOcultas Numero de camadas ocultas
     * @param numNeuroniosSaida Numero de neuronios na camada de saida
     * @param propagação Função de propagação que será usado na rede
     */
    public RedeNeural(int numNeuroniosEntrada, int numNeuroniosOcultos, int numCamadasOcultas, int numNeuroniosSaida, Função propagação){
        entrada = new CamadaEntrada(numNeuroniosEntrada);
        oculta = new CamadaProcessamento[numCamadasOcultas];
        oculta[0] = new CamadaProcessamento(numNeuroniosOcultos, propagação,numNeuroniosEntrada);
        for(int i=1;i<oculta.length;i++){
            oculta[i] = new CamadaProcessamento(numNeuroniosOcultos,propagação,numNeuroniosOcultos);
        }
        saida = new CamadaProcessamento(numNeuroniosSaida,propagação,numNeuroniosOcultos);
        funçãoPropagação = propagação;
    }
    
    /**
     * Realiza a alimentação na rede com a entrada especificada no parametro
     * @param inputs entrada da rede
     * @return retorna a saida da rede
     */
    public double[] feedFoward(double[] inputs){
        double[] sinais = inputs;
        for(int i=0;i<oculta.length;i++){
            sinais = oculta[i].feedFoward(sinais);
        }
        sinais = saida.feedFoward(sinais);
        return sinais;
    }
    
    /** 
     * Realiza uma iteração na rede.
     * A iteração é composta de um feedfoward e um backpropagation para uma entrada.
     * @param inputs entrada para a iteração
     * @param outputEsperado resultado esperado
     * @return retorna o erro da rede nesta iteração.
     */
    public double iteration(double[] inputs, Double[] outputEsperado){
        double[] output = feedFoward(inputs);
        backPropagation(output,outputEsperado);
        return erroRede();
    }
    
    /**
     * Realiza o processo de treinamento da rede com as instancias do parametro
     * @param instancias instancias utilizadas para o treinamento
     */
    public void treinamento(Instancias instancias){
        boolean houveErro;
        double maiorErro;
        for(int i=0;i<numIteraçõesLimite;i++){
            houveErro = false;
            maiorErro = 0;
            double erroAtual;
            for(int j=0;j<instancias.size();j++){
                erroAtual=iteration(instancias.getAtributos(j),instancias.getSaida(j));
                if(erroAtual > maiorErro)maiorErro = erroAtual;
                if(erroAtual > limiar)houveErro = true;
            }
            //System.out.println(i + " - " + maiorErro);
            if(!houveErro)return;
        }
        
    }
    //arrumar
    public int[][] testarRede(Instancias instancias){
        int numClasses = instancias.getNumClasses();
        int[][] matrizConfusão = new int[numClasses][numClasses];
        for(int i=0;i<numClasses;i++){
            for(int j=0;j<numClasses;j++){
                matrizConfusão[i][j] = 0;
            }
        }
        for(int i=0;i<instancias.size();i++){
            double[] saida = feedFoward(instancias.getAtributos(i));
            int classeCalculada = indexMaiorSinalSaida(saida);
            int classeDesejada = instancias.getIndexClasse(i);
            matrizConfusão[classeCalculada][classeDesejada]++;
        }
        return matrizConfusão;
    }
    
    /**
     * Processo de backpropagation da rede
     * @param outputs Saida calculada no final do processo feedFoward
     * @param outputEsperado Saida esperada pela rede
     */
    public void backPropagation(double[] outputs, Double[] outputEsperado){
        saida.calculaErros(outputEsperado);
        oculta[oculta.length-1].calculaErros(saida);
        for(int i=oculta.length-2;i>=0;i--){
            oculta[i].calculaErros(oculta[i+1]);
        }
        saida.ajustarPesos(taxaAprendizado);
        for(int i=0;i<oculta.length;i++){
            oculta[i].ajustarPesos(taxaAprendizado);
        }
    }
    
    /**
     * @return Retorna o erro da rede para a iteração atual 
     */
    private double erroRede() {
        return saida.erroRede();
    }
    
    
    
    public static void main(String[] args) throws FileNotFoundException, IOException{
        Instancias instancias = new Instancias();
        instancias.abrirArquivo(new File("C:\\Users\\mortz\\Documents\\Faculdade ( too late my friend)\\4ºAno-1ºSemestre\\IA\\Treinamento e Teste\\treinamento.csv"));
        instancias.embaralhar();
        instancias.normalizar(Instancias.NORMALIZAR_ENTRE_M1E1);
        RedeNeural r = new RedeNeural(6,6,1,5,new Logistica());
        r.treinamento(instancias);
        
        Instancias teste = new Instancias();
        teste.abrirArquivo(new File("C:\\Users\\mortz\\Documents\\Faculdade ( too late my friend)\\4ºAno-1ºSemestre\\IA\\Treinamento e Teste\\teste.csv"));
        teste.normalizar(Instancias.NORMALIZAR_ENTRE_M1E1);
        int[][] matriz = r.testarRede(teste);
        for(int i=0;i<matriz.length;i++){
            for(int j=0;j<matriz[i].length;j++){
                System.out.print(matriz[i][j] + " ");
            }
            System.out.println("");
        }
    }

    private int indexMaiorSinalSaida(double[] saida) {
        int pos=0;
        double maior = Integer.MIN_VALUE;
        for(int i=0;i<saida.length;i++){
            if(maior < saida[i]){
                maior = saida[i];
                pos = i; 
            }
        }
        return pos;
    }


}
