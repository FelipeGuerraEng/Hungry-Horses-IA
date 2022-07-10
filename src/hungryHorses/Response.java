package hungryHorses;

public class Response {
    private int move;
    private int puntaje;
    private Game state;
    
    public Response() {

    }
    
    public Response(int move, int puntaje, Game state) {
        this.move = move;
        this.puntaje = puntaje;
        this.state = state;
    }
    public int getMove() {
        return move;
    }
    public void setMove(int move) {
        this.move = move;
    }
    public int getPuntaje() {
        return puntaje;
    }
    public void setPuntaje(int puntaje) {
        this.puntaje = puntaje;
    }
    public Game getState() {
        return state;
    }
    public void setState(Game state) {
        this.state = state;
    }
}
