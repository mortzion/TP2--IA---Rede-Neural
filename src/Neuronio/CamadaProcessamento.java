/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Neuronio;

import Funções.Função;

/**
 *  Representa uma cada de processamento, podendo ser tanto uma camada oculta 
 *  quanto uma camada de saida.
 * @author Matheus Prachedes Batista
 */
public class CamadaProcessamento {
    /**
     * Conjunto de neuronios desta camada
     */
    protected Neuronio[] neuronios;
    
    /**
     * Armazena os erros calculados pelos neuronios desta camada para uso futuro
     */
    protected double[] erros;
    
    /**
     * Constroi a camada de neuronios e instancia os neuronios, setando os pesos
     * aleatoriamente seguinda uma distribuição gaussiana com média 0 e desvio
     * padão 1
     * @param numNeuroniosOcultos Numero de neuronios desta camada
     * @param propagação Função de propagação usadas nos neuronios
     * @param numPesos Numero de pesos que cada neuronios deve ter. Este numero
     * Deve corresponder com o numero de neuronios da camada anterior a esta.
     */
    public CamadaProcessamento(int numNeuroniosOcultos, Função propagação, int numPesos) {
        neuronios = new Neuronio[numNeuroniosOcultos];
        erros = new double[numNeuroniosOcultos];
        for(int i=0;i<neuronios.length;i++){
            neuronios[i] = new Neuronio(propagação,numPesos);
        }
    }

    /**
     * Processo de feedFoward desta camada.
     * @param inputs entrada recebida por esta camada
     * @return retonar os sinais propagados por esta camada
     */
    public double[] feedFoward(double[] inputs) {
        double[] sinais = new double[neuronios.length];
        for(int i=0;i<neuronios.length;i++){
            sinais[i] = neuronios[i].calcularPropagação(inputs);
        }
        return sinais;
    }

    /**
     * Calcula os erros destes neuronios considerandos as saidas desejadas.
     * Esse método deve ser chamado apenas para a camada de saida.
     * @param outputEsperado saidas esperadas por essa camada
     */
    public void calculaErros(Double[] outputEsperado) {
        for(int i=0;i<neuronios.length;i++){
            erros[i] = neuronios[i].calculaErroSaida(outputEsperado[i]);
        }
    }

    public double[] getErros(){
        return erros;
    }

    /**
     * Calcula os erros destes neuronios considerandos os erros propagados pela
     * camada posterior a esta.
     * Esse método deve ser chamado apenas para camadas ocultas.
     * @param saida Camada posterior a esta
     */
    public void calculaErros(CamadaProcessamento saida) {
        for(int i=0;i<neuronios.length;i++){
            double soma = saida.somaErros(i);
            neuronios[i].calculaErro(soma);
        }
    }

    /**
     * Retorna a soma dos erros destes neuronios "i" multiplicados pelo peso Wij
     * Formula: Σ (de i=0 até m)(erro(i)*Wij) 
     * @param j indice do neuronio da camada oculta anterior para o qual está sendo 
     * calculando o erro
     * @return retorna a soma 
     */
    private double somaErros(int j) {
        double soma =0;
        for(int i=0;i<neuronios.length;i++){
            soma+=neuronios[i].erro*neuronios[i].pesos[j];
        }
        return soma;
    }
    
    /**
     * Ajusta os pesos desse neuronio utilizando a taxa de aprendizado vindo
     * pelo parametro.
     * Este metodo deve ser chamado após está camada ter calculado seus erros e
     * de preferencia após toda a rede ter calculada seus erros
     * @param taxaAprendizado Taxa de aprendizado utilizado para ajustar os pesos
     */
    public void ajustarPesos(double taxaAprendizado) {
        for(int i=0;i<neuronios.length;i++){
            neuronios[i].ajustarPesos(taxaAprendizado);
        }
    }

    /**
     * Retorna metade da soma dos quadrados dos erros dos neuronios desta camada
     * Formula: 1/2 * Σ (de i=0 até o)(erro(i)^2)
     * Deve ser chamado apenas para a camada de saida.
     * @return Retorna metade da soma.
     */
    public double erroRede() {
        double soma = 0;
        for(int i=0;i<neuronios.length;i++){
            soma+=Math.pow(neuronios[i].erro, 2);
        }
        return soma/2;
    }

    public double[] getPesos(int neuronio) {
        return neuronios[neuronio].pesos;
    }

    
    
}
