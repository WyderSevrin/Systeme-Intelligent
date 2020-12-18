import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Echiquier echec = new Echiquier(10);
		echec.placerReine(3, 1);
		echec.placerReine(3, 5);
		echec.placerReine(5, 5);
		System.out.println(echec);
		
		echec.minReine();
		
		ArrayList<Integer> S = echec.getSolution();
		ArrayList<Integer> minPos = echec.getMinMenaceePos();
		
		for(int i = 0; i<S.size(); i++) {
			System.out.println(S.get(i));
			System.out.println(minPos.get(i));
		}
		
	}

}
