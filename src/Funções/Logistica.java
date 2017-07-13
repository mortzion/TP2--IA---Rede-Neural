/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Funções;

/**
 * Função logistica para a função de propagação
 * @author Matheus Prachedes Batista
 */
public class Logistica extends Função{
    /**Calcula f(x)*/
    @Override
    public double compute(double x) {
        return ((double)1)/(1+Math.exp(-x));
    }
    /**Calcula f'(x)*/
    @Override
    public double derivada(double x) {
        return compute(x)*(1-compute(x));
    }
}
