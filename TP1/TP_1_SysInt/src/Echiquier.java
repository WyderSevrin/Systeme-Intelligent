import java.util.ArrayList;

public class Echiquier {

	private Cellule[][] echiquier;
	private int taille;
	
	private ArrayList<Integer> Solution = new ArrayList<Integer>();
	private ArrayList<Integer> minMenaceePos = new ArrayList<Integer>();
	
	public Echiquier(int taille) {
		this.taille = taille;
		echiquier = new Cellule[taille][taille];
		initialiserEchiquier();
	}
	
	public void initialiserEchiquier() {
		for(int x = 0; x<taille; x++) {
			for(int y = 0; y<taille; y++) {
				echiquier[x][y] = new Cellule(x, y);
			}
		}
	}
	
	public void modifierCellule(int x, int y, int valeur) {
		echiquier[x][y].setTypeOccupation(valeur);
	}
	
	public void placerReine(int x, int y) {
		modifierCellule(x, y, Cellule.REINE);
		for(int X = 0; X<taille; X++) {
			for(int Y = 0; Y<taille; Y++) {
				if(y==Y && echiquier[X][Y].getTypeOccupation() == Cellule.LIBRE) {//Vertical
					modifierCellule(X, Y, Cellule.MENACEE);
				}else if(x==X && echiquier[X][Y].getTypeOccupation() == Cellule.LIBRE) {//Horizontal
					modifierCellule(X, Y, Cellule.MENACEE);
				}else if(Math.abs(X-x) == Math.abs(Y-y) && echiquier[X][Y].getTypeOccupation() == Cellule.LIBRE) {//Diagonal Haut Gauche
					modifierCellule(X, Y, Cellule.MENACEE);
				}
			}
		}
		
	}
	
	public void minReine() {
		ArrayList<Integer> nbMenace = new ArrayList<Integer>();
		ArrayList<Integer> Xpos = new ArrayList<Integer>();
		ArrayList<Integer> Ypos = new ArrayList<Integer>();
		
		
		int menacee = 0;
		int minMenacee = taille*taille;
		
		for(int i =0; i<taille; i++) {
			for(int j = 0; j<taille; i++) {
				for(int X = 0; X<taille; X++) {
					for(int Y = 0; Y<taille; Y++) {
						if(j==Y && echiquier[X][Y].getTypeOccupation() == Cellule.LIBRE) {//Vertical
							modifierCellule(X, Y, Cellule.MENACEE);
							menacee++;
						}else if(i==X && echiquier[X][Y].getTypeOccupation() == Cellule.LIBRE) {//Horizontal
							modifierCellule(X, Y, Cellule.MENACEE);
							menacee++;
						}else if(Math.abs(X-i) == Math.abs(Y-j) && echiquier[X][Y].getTypeOccupation() == Cellule.LIBRE) {//Diagonal Haut Gauche
							modifierCellule(X, Y, Cellule.MENACEE);
							menacee++;
						}
					}
				}
				nbMenace.add(menacee);
				Xpos.add(menacee);
				Ypos.add(menacee);
			}
		}
		
		for(int k = 0; k<nbMenace.size(); k++) {
			if(nbMenace.get(k)<minMenacee) {
				minMenacee=nbMenace.get(k);
			}
		}
		
		for(int k = 0; k<nbMenace.size(); k++) {
			if(nbMenace.get(k) == minMenacee) {
				this.Solution.add(nbMenace.get(k));
				this.minMenaceePos.add(k);
			}
		}
	}

	public ArrayList<Integer> getSolution() {
		return Solution;
	}

	public ArrayList<Integer> getMinMenaceePos() {
		return minMenaceePos;
	}

	@Override
	public String toString() {
		String S = "";
		for(int Y = 0; Y<taille; Y++) {
			for(int X = 0; X<taille; X++) {
				if(echiquier[X][Y].getTypeOccupation() == Cellule.REINE) {
					S+="0";
				}else if(echiquier[X][Y].getTypeOccupation() == Cellule.MENACEE) {
					S+="X";
				}else if(echiquier[X][Y].getTypeOccupation() == Cellule.LIBRE){
					S+=" ";
				}
				if(X == taille-1) {
					S+="\n";
				}
			}
		}
		return S;
	}
	
	
	
}
