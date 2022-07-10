package hungryHorses;

public class Minimax {
    public Response minimaxRunning(Game g, int level) {
        return dfs(g, level, Integer.MIN_VALUE, Integer.MAX_VALUE, 0);
    }
    private Response dfs(Game g, int level, int alfa, int beta, int jugador) {
        if (level == 0 || g.isFinished()) 
            return new Response(-1, g.getPoints(), g);
        Response pointsForGame;
        if(jugador == 0){
            Response ALFA = new Response(-1, alfa, g);
            Game[]possiblePlays = g.possiblePlays(jugador);
            for(int i = 0 ; i < 8 ; ++i){
                Game game = possiblePlays[i];
                if(game == null)
                    continue;
                pointsForGame = dfs(game, level - 1, alfa, beta, 1);
                if(pointsForGame.getPuntaje() > ALFA.getPuntaje()){
                    ALFA = new Response(i, pointsForGame.getPuntaje(), game);
                    alfa = ALFA.getPuntaje();
                }
                if(beta <= alfa){
                    break;
                }

            }
            return ALFA;
        }else{
            Response BETA = new Response(-1, beta, g);
            Game[]possiblePlays = g.possiblePlays(jugador);
            for(int i = 0 ; i < 8 ; ++i){
                Game game = possiblePlays[i];
                if(game == null)
                    continue;
                pointsForGame = dfs(game, level - 1, alfa, beta, 0);
                if(pointsForGame.getPuntaje() < BETA.getPuntaje()){
                    BETA= new Response(i, pointsForGame.getPuntaje(), game);
                    beta = BETA.getPuntaje();
                }
                if(beta <= alfa){
                    break;
                }
            }
            return BETA;
        }

    }
}
