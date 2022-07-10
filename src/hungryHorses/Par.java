package hungryHorses;

import java.util.ArrayList;
import java.util.List;

public class Par {

	private int i;
	private int j;


	public Par(int i, int j) {
		
		this.i = i;
		this.j = j;
	}

	public Par() {
		

	}
	


	public int getI() {
		return i;
	}

	public void setI(int i) {
		this.i = i;
	}

	public int getJ() {
		return j;
	}

	public void setJ(int j) {
		this.j = j;
	}
	
	
	public List<Par> listaDePos(){
		
		List<Par> listaPos = new ArrayList<>();

		for(int i = 0 ; i < 8 ; ++i){

			for(int j = 0 ; j < 8 ; ++j){
				
				listaPos.add(new Par(i,j));
				
			}

		}
		
		for(int i = 0; i < listaPos.size(); i++) {   
		    System.out.print("\nLista de po:"+listaPos.get(i).getI()+", "+listaPos.get(i).getJ()+" ");
		}  
		
		return listaPos;
	}
}
