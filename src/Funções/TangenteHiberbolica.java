/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Funções;

/**
 *
 * @author Matheus Prachedes Batista
 */
public class TangenteHiberbolica extends Função{

    @Override
    public double compute(double x) {
        return (1-Math.exp(-2*x))/(1+Math.exp(-2*x));
    }

    @Override
    public double derivada(double x) {
        return 1-Math.pow(compute(x),2);
    }

    @Override
    public double menorValorImagem() {
        return -1;
    }

    @Override
    public double maiorValorImagem() {
        return 1;
    }
    
}
