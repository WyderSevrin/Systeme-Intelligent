package tp2;
import jdk.jshell.spi.ExecutionControl;

import java.util.Arrays;
import java.util.Random;

public class Population {

    private Individual[] individuals;
    private int genesPerPop;
    private Crosstype crosstype;
    private float mutationChance;

    /**
     * Representation of a population of pseudo-randomly generated individuals
     * @param popSize set the size of this population
     * @param genesPerPop sets the gene size of each individual in the pool
     * @param crosstype the crosstype to be used by this population
     * @param mutationChance chance for an individual to mutate at birth
     */
    public Population(int popSize, int genesPerPop, Crosstype crosstype, float mutationChance)
    {
        this.individuals = new Individual[popSize];
        this.genesPerPop = genesPerPop;
        this.crosstype = crosstype;
        this.mutationChance = mutationChance;
        for(int i=0; i<popSize; i++)
            this.individuals[i] = new Individual(genesPerPop);
    }

    /**
     * Representation of a population of pre-computed individuals
     * @param individuals an array of individuals
     * @param crosstype the crosstype to be used by this population
     * @param mutationChance chance for an individual to mutate at birth
     */
    public Population(Individual[] individuals, Crosstype crosstype, float mutationChance)
    {
        assert individuals.length > 0;
        this.individuals = individuals;
        this.genesPerPop = individuals[0].getGenes().length;
        this.crosstype = crosstype;
        this.mutationChance = mutationChance;
    }

    /**
     * Creates a new population using this generation's individuals
     * @return the newly generated population
     */
    public Population generateNewPopulation() 
    {
        Population popu = this;
        Individual[] newIndvidials = new Individual[this.individuals.length];
        
        //Utilisez les CROSSTYPE ici pour différencier le type de sélection

        if(this.crosstype == Crosstype.ROULETTE){

            int counter = 0;
            while(counter < this.individuals.length){
                //System.out.println("générate population "+counter); 
                Individual ind1 = roulette();
                Individual ind2 = roulette();
                
                //Croissement pour avoir les enfants
                Random rand = new Random();
                int aléatoire = rand.nextInt(3);
                Individual[] kids= new Individual[2];
                
                kids = reproduceIndividuals(ind1,ind2,aléatoire);
                if(counter < this.individuals.length){
                   newIndvidials[counter] = ind1;
                    //System.out.println("");
                   counter++; 
                }
                if(counter < this.individuals.length-1){
                   newIndvidials[counter] = kids[0];
                counter++; 
                }
                if(counter < this.individuals.length-2){
                    newIndvidials[counter] = ind2;
                counter++;
                }
                if(counter < this.individuals.length-3){
                    newIndvidials[counter] = kids[1];
                counter++;
                }
            }
            popu = new Population(newIndvidials,Crosstype.ROULETTE,this.mutationChance);
        }
        else{           // Tournoi

            int counter = 0;
            while(counter < this.individuals.length){
                //System.out.println("générate population "+counter); 
                Individual ind1 = tournoi(counter);
                Individual ind2 = tournoi(counter);
                
                //Croissement pour avoir les enfants
                Random rand = new Random();
                int aléatoire = rand.nextInt(3);
                Individual[] kids= new Individual[2];
                
                kids = reproduceIndividuals(ind1,ind2,aléatoire);
                
                if(counter < this.individuals.length){
                   newIndvidials[counter] = ind1;
                    //System.out.println("");
                   counter++; 
                }
                
                if(counter < this.individuals.length-1){
                   newIndvidials[counter] = kids[0];
                counter++; 
                }
                
                if(counter < this.individuals.length-2){
                    newIndvidials[counter] = ind2;
                counter++;
                }
                
                if(counter < this.individuals.length-3){
                    newIndvidials[counter] = kids[1];
                counter++;
                }

                
            }
            popu = new Population(newIndvidials,Crosstype.TOURNOI,this.mutationChance);
        }
        
        
        //compute fitness
        for (int i = 0; i < this.individuals.length-1; i++) {
            this.individuals[i].computeFitnessScore();
        }
        
        System.out.println("outpute : "+popu.toString());
        
        
        return popu;
    }

    
    private Individual roulette(){
        Random rand = new Random();
        int aléatoire = rand.nextInt(this.individuals.length);       
        int cumul =0;
        int index =0;
        int value = cumul + this.individuals[index].getFitnessScore();
       // System.out.println("aléatoire "+aléatoire);
        while(cumul + this.individuals[index].getFitnessScore() < aléatoire){
            cumul += this.individuals[index].getFitnessScore();
            cumul++; //ou on augmente la fitness de tous les individus de 1 sinon on rentre dans une condition infinie jusqu'a dépasser l'index max
            index++;
        }
        
        return this.individuals[index];
    }
    
    /**
     * 
     * @param position le ième individu à selectionner
     * @return Individual
     */
    private Individual tournoi(int position){
        Individual result;
        if(this.individuals.length - position <2){
            melangePopulation(50);
            position = 0;
        }
        
        Individual ind1 = this.individuals[position];
        Individual ind2 = this.individuals[position+1];
        
        if(ind1.getFitnessScore() > ind2.getFitnessScore()){
            result = ind1;
        }else{
            result = ind2;
        }
        
        return result;
    }
    
    /**
     * Permet de mélanger la population
     * @param nb_Mélange le nombre de mélange à effectuer
     */
    private void melangePopulation(int nb_Mélange){
        Random rand =new Random();
        for (int i = 0; i < nb_Mélange; i++) {
            int index1 = rand.nextInt(this.individuals.length-1);
            int index2 = rand.nextInt(this.individuals.length-1);
            Individual temp = this.individuals[index1];
            this.individuals[index1] = this.individuals[index2];
            this.individuals[index2] = temp;
        }
        
    }
    
    
    /**
     * Takes 2 individuals and create 2 children using their genes
     * @param firstParent the first selected individual
     * @param secondParent the second selected individual
     * @param crosspoint index of the crosspoint
     * @return an array of 2 individuals
     */
    public Individual[] reproduceIndividuals(Individual firstParent, Individual secondParent, int crosspoint)
    {
        Individual[] offsprings = new Individual[2];
        
        Random rand = new Random();
 
       // System.out.println("xP : "+xP);
        
        int[] kid1 = new int[firstParent.getGenes().length];
        int[] kid2 = new int[secondParent.getGenes().length];
        //System.out.println("xP : "+xP);
        for (int i = 0; i < crosspoint; i++) {
            kid1[i] = firstParent.getGenes()[i];
            kid2[i] = secondParent.getGenes()[i];
        }
        for (int i = crosspoint; i < firstParent.getGenes().length; i++) {
            kid1[i] = secondParent.getGenes()[i];
            kid2[i] = firstParent.getGenes()[i];
        }
        offsprings[0] = new Individual(kid1);
        offsprings[1] = new Individual(kid2);
        
        
        //La mutation génétique éventuelle : ici 0.05 donc 5%
        for (int i = 0; i < 2; i++) { 
            for (int j = 0; j < offsprings[i].getGenes().length; j++) {
                float mutation = rand.nextInt(99)+1;
                if(mutation<this.mutationChance*100){   
                   offsprings[i].geneFlip(j);
                }
            } 
        }
        
        return offsprings;

    }
    
    public int testPopulation(){
        int result = 0;
        for (int i = 0; i < this.individuals.length; i++) {
            
            if(result < this.individuals[i].getFitnessScore()){
                result = this.individuals[i].getFitnessScore();
                //System.out.println("score : "+result);
            }
        }
        
        return result;
    }
    
    

    @Override
    public String toString()
    {
        return "Population{" +
                "individuals=" + Arrays.toString(individuals) +
                ", genesPerPop=" + genesPerPop +
                '}';
    }
}
