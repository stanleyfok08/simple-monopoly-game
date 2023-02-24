import java.util.Scanner;

public class PlayerDB {
    static Player player[];

    public PlayerDB(int playerNumber, String purpose) {
        if(purpose == "load"){
            //for loading a game
            player = new Player[playerNumber];
            for (int i= 1; i < playerNumber; i++){
                player[i] = new Player(i,"null",0,0,false,0,0);
            }
        }
        else if(purpose == "create"){
            // for creating a new game
            player = new Player[playerNumber];
            for (int i= 1; i < playerNumber; i++){
                Scanner scanner = new Scanner(System.in);
                System.out.println("Please input the name of player" + i);
                String name = scanner.next();
                player[i] = new Player(name,i);
            }
        }
	}
    
    public void setPlayerDB(int playerID, String name, int money, int position, boolean injail, int injailTurn, int turn) {
        player[playerID].setPlayerID(playerID);
        player[playerID].setName(name);
        player[playerID].setPlayerMoney(money);
        player[playerID].setPlayerPosition(position);
        player[playerID].setPlayerJailStatus(injail);
        player[playerID].setInJailTurn(injailTurn);
        player[playerID].setPlayerTurn(turn);
        
	}
    
}
