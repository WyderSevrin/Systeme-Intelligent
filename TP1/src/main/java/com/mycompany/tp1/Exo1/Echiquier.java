/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.tp1.Exo1;

import java.util.ArrayList;
import java.util.Random;



/**
 *
 * @author XiaoMi
 */
public class Echiquier {
    private Cellule[][] echiquier;
    private int taille;
    
    public Echiquier(int taille){
        this.taille = taille;
        echiquier = new Cellule[taille][taille];
        initialiserEchequier();
        
    }
    
    public void initialiserEchequier(){
        for (int x = 0; x < taille; x++) {
            for (int y = 0; y < taille; y++) {
                echiquier[x][y] = new Cellule(x, y);
            }
        }
    }
    
    public void modifierCellule(int x,int y, int valeur){
        echiquier[x][y].setTypeOccupation(valeur);
        
    }
    
    /**
     * Placer la reine
     * @param x
     * @param y  
     * @return  le nombre de case occuper
     */
    public int placerReine(int x, int y, Boolean def){
        int counter =taille*taille+10;
        if(echiquier[x][y].getTypeOccupation() == Cellule.LIBRE){
            counter =0;
            if(def){
                modifierCellule(x,y,Cellule.REINE); 
            }
            counter++;
            for (int i = 0; i < taille; i++) {
                for (int j = 0; j < taille; j++) {
                    //Menace en ligne
                    if((i==x || j==y )&& echiquier[i][j].getTypeOccupation() == Cellule.LIBRE){
                        if(def){
                           modifierCellule(i,j,Cellule.MENACEE); 
                        }
                        counter++;
                    //Menace en diagonale    
                    }else if(Math.abs(i-x) == Math.abs(j-y) && echiquier[i][j].getTypeOccupation() == Cellule.LIBRE){
                        if(def){
                           modifierCellule(i,j,Cellule.MENACEE); 
                        } 
                        counter++;
                    }
                }
            } 
        }
        return counter;
    }
    
    /**
     * Exercice 2 Algorithme 1
     * Permet de place rla reine dans uen cellule qui génère le moins de menace
     */
    public Reine algo1_a(){
        int x =0;
        int y =0;
        int menace = taille * taille +1;
        int menaceTemp =0;
        
        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++) {
                menaceTemp = placerReine(i, j,false); //On test
                if(menaceTemp<menace){
                   x = i;
                   y = j;
                   menace = menaceTemp;
                }
            }
        }
        //System.out.println("Menace : "+placerReine(x, y,true));//on place la reine pour de bon
        placerReine(x, y,true);
        //System.out.println("Algo Reien _ a : x "+x+" y: "+y);
        return new Reine(x,y);
    }
    
    /**
     * Exercice 2 Algorithme 2
     */
    public ArrayList<Reine> algo1_c(){
        ArrayList<Reine> Reines = new ArrayList<>(); //Pour afficher le nombre de reine et leurs position
        
        int celluleLibre = celluleVide();
        
        while(0< celluleLibre){
            Reine e = algo1_a();//On ajout une reien à son meilleur emplacement
            Reines.add(e);
            System.out.println("ajoute Reine cord x: "+e.getX()+" y: "+e.getY());
            celluleLibre = celluleVide();
        }
        System.out.println("Le nombre totale de reine placer son : "+Reines.size());
        return Reines;
    }
    
    /**
     * Méthode aléatoire pour trouver le plus de reines
     * @param tryard 
     */
    public void algo_meilleur_Alea(int tryard){
        ArrayList<Reine> Reines = new ArrayList<>();
        for (int i = 0; i < tryard; i++) {
            
            ArrayList<Reine> Reines2 = new ArrayList<>();
            int celluleLibre = celluleVide();
            
            while(0< celluleLibre){
                Random r = new Random();
                int x = r.nextInt((taille));
                int y = r.nextInt((taille));
                
                if(taille*taille+10 != placerReine(x,y,true)){
                    Reines2.add(new Reine(x,y));
                }
                
                celluleLibre = celluleVide();
            } 
            if(Reines2.size()>Reines.size()){
               Reines = Reines2; 
            }
            clear();
        }
        System.out.println("nb Riene : "+Reines.size());
        for(Reine r : Reines){
            placerReine(r.x,r.y,true);
        }
        
    }
    
    
    
    
    
    
    
    
    
    
    private int celluleVide(){
        int counter = 0;
        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++) {
                if(echiquier[i][j].getTypeOccupation() == Cellule.LIBRE){
                    counter++;
                }
            }
        }
        
        return counter;
    }
    
    
    private void clear(){
        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++) {
                modifierCellule(i,j,Cellule.LIBRE);
                
            }
        }
 
    }
    
    /**
     * Fonction d'affichage de l'echiquier
     * @return le tableau en string
     */
    public String toString(){
        String outpute ="";
        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++) {
                if(this.echiquier[j][i].getTypeOccupation() == Cellule.LIBRE){
                    outpute +="~";
                }else if(this.echiquier[j][i].getTypeOccupation() == Cellule.REINE){
                    outpute +="x";
                }else if(this.echiquier[j][i].getTypeOccupation() == Cellule.MENACEE){
                    outpute +="o";
                }
            }
            outpute+="\n";
        }
        return outpute;
    }
    
}


