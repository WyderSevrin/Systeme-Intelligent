/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.tp1;

import com.mycompany.tp1.Exo1.Echiquier;

/**
 *
 * @author XiaoMi
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("Debut");
        
        Echiquier ech = new Echiquier(8);
        //ech.placerReine(3, 6,true);
        
        ech.algo1_a();
        //ech.algo1_c();
        //ech.algo_meilleur_Alea(100);
        System.out.println("\n\nPlateau Final : \n\n"+ech.toString());
    }
}
