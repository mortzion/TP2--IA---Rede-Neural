/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Funções;

/**
 * Classe abstrata usada para repreesntar uma função de propagação;
 * @author mortz
 */
public abstract class Função {
    /**Calcula f(x)*/
    public abstract double compute(double x);
    /**Calcula f'(x)*/
    public abstract double derivada(double x);
}
