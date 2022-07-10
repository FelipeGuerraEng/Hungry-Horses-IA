package hungryHorses;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Game {
    private ElementsOfGame[][]grid;
    private int[] players;
    private static int[] playsI = { -2, -2, -1, 1, 2, 2, 1, -1 };
    private static int[] playsJ = { -1, 1, 2, 2, 1, -1, -2, -2 };

    public Game(){
         
    }
    public void initGame() {
        this.players = new int[]{0, 0};
        this.grid = new ElementsOfGame[8][8];
        
        int[][] numberGrid = new int[8][8];
        
        List<Integer> listaPos = new ArrayList<>();

		for(int i = 0 ; i < 14 ; ++i){
				
				listaPos.add(1);
		}
		for(int i = 14 ; i < 19 ; ++i){
			
			listaPos.add(2);
		}
		for(int i = 19 ; i < 21 ; ++i){
			
			listaPos.add(3);
		}
        
		listaPos.add(4);
		listaPos.add(5);
		
		Collections.shuffle(listaPos);
		
		for(int t = 0 ; t < listaPos.size() ; ++t) {
			int i = new Random().nextInt(8);
			int j = new Random().nextInt(8);
			while(numberGrid[i][j] != 0) {
				i = new Random().nextInt(8);
				j = new Random().nextInt(8);
			}
			numberGrid[i][j] = listaPos.get(t);
		}
        
        for(int i = 0 ; i < 8 ; ++i){
            for(int j = 0 ; j < 8 ; ++j){
                if (numberGrid[i][j] == 1){
                    grid[i][j] = ElementsOfGame.CESPED;
                } else if (numberGrid[i][j] == 2) {
                    grid[i][j] = ElementsOfGame.FLORES;
                }else if (numberGrid[i][j] == 3){
                    grid[i][j] = ElementsOfGame.MANZANAS;
                } else if (numberGrid[i][j] == 4) {
                    grid[i][j] = ElementsOfGame.CABALLO_NEGRO;
                } else if (numberGrid[i][j] == 5) {
                    grid[i][j] = ElementsOfGame.CABALLO_BLANCO;
                }else{
                    grid[i][j] = ElementsOfGame.EMPTY;
                }
            }
        }
    }

    public Game[] possiblePlays(int player){
        ElementsOfGame horse = player == 0 ? ElementsOfGame.CABALLO_BLANCO : ElementsOfGame.CABALLO_NEGRO;
        int[]pos = findPlayer(horse);
        Game[]options = new Game[8];

        for(int i = 0 ; i < 8 ; ++i){
            if(isValid(pos[0] + playsI[i],pos[1] + playsJ[i])){
                Game newGame = (Game) this.clone();
                newGame.players[player]+= getPoints(pos[0] + playsI[i],pos[1] + playsJ[i]);
                newGame.grid[pos[0]][pos[1]] = ElementsOfGame.EMPTY;
                newGame.grid[pos[0] + playsI[i]][pos[1] + playsJ[i]] = horse;
                options[i] = newGame;
            }else{
                options[i] = null;
            }
        }
        return options;
    }

    public int getPoints(){
        if(isFinished()){
            return players[0] > players[1] ? Integer.MAX_VALUE : Integer.MIN_VALUE;
        }
        return players[0] - players[1];
    }
    private int getPoints(int i, int j){
        switch (grid[i][j]){
            case CESPED:
                return 1;
            case FLORES:
                return 3;
            case MANZANAS:
                return 5;
            default:
                return 0;
        }
    }
    public boolean isFinished(){
        for(int i = 0 ; i < 8 ; ++i){
            for(int j = 0 ; j < 8 ; ++j){
                if(grid[i][j] == ElementsOfGame.CESPED || grid[i][j] == ElementsOfGame.FLORES ||
                grid[i][j] == ElementsOfGame.MANZANAS){
                    return false;
                }
            }
        }
        return true;
    }
    private boolean isValid(int i, int j){
        return !(i < 0 || i> 7 || j < 0 || j > 7)
                && !(grid[i][j] == ElementsOfGame.CABALLO_BLANCO || grid[i][j] == ElementsOfGame.CABALLO_NEGRO);
    }
    private int[] findPlayer(ElementsOfGame player){
        int[] pos = {-1,-1};
        for(int i = 0 ; i < 8 ; ++i){
            for(int j = 0 ; j < 8 ; ++j){
                if(player == grid[i][j]){
                    pos[0] = i;
                    pos[1] = j;
                    return pos;
                }
            }
        }
        return pos;
    }

    @Override
    protected Object clone() {
        Game newGame = new Game();
        newGame.grid = new ElementsOfGame[8][8];
        for(int i = 0 ; i < 8 ; ++i){
            for(int j = 0 ; j < 8 ; ++j){
                newGame.grid[i][j] = this.grid[i][j];
            }
        }
        newGame.players = this.players.clone();
        return newGame;
    }
    private String stateOfGame(){
        String res = "";
        for(int i = 0 ; i < 8 ; ++i){
            for(int j = 0 ; j < 8 ; ++j){
                res+=grid[i][j].ordinal() + "\t";
            }
            res+="\n";
        }
        return res;
    }
    @Override
    public String toString() {
        return "Game{" +
                "grid=\n" + stateOfGame() +
                "\n, players=" + Arrays.toString(players) +
                '}';
    }

    public ElementsOfGame[][] getGrid() {
        return grid;
    }

    public void setGrid(ElementsOfGame[][] grid) {
        this.grid = grid;
    }

    public int[] getPlayers() {
        return players;
    }

    public void setPlayers(int[] players) {
        this.players = players;
    }

    public static int[] getPlaysI() {
        return playsI;
    }

    public static int[] getPlaysJ() {
        return playsJ;
    }


    
}


