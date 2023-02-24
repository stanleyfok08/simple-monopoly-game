public class Player {
    private int playerID;
    private String name;
    private int money;
    private int position = 0;
    private boolean injail = false;
    private int injailTurn = 0;
    private int playerTurn = 0;


    public Player(int playerID){
        this.playerID = playerID;
    }

    // Constructor for creating Player
    public Player(String name, int playerID){
        this.name = name;
        this.playerID = playerID;
        money = 1500;
        playerTurn = 1;
    }

    // Constructor for loading Player
    public Player(int playerID, String name, int money, int position, boolean injail, int injailTurn ,int playerTurn) {
        this.playerID = playerID;
        this.name = name;
        this.money = money;
        this.position = position;
        this.injail = injail;
        this.injailTurn = injailTurn;
        this.playerTurn = playerTurn;
	}

    public void setPlayerID(int playerID){
        this.playerID = playerID;
    }

    public int getPlayerID(){
        return playerID;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void setPlayerMoney(int money){
        this.money+=money;
        if(this.money < 0){
            killPlayer();
        }
    }

    public int getPlayerMoney(){
        return money;
    }
    
    public void setPlayerPosition(int position){
        this.position = position;
    }

    public int getPlayerPosition(){
        return position;
    }

    public void setPlayerJailStatus(boolean injail){
        this.injail = injail;
    }

    public boolean getPlayerJailStatus(){
        return injail;
    }

    public void setInJailTurn(int injailTurn){
        this.injailTurn += injailTurn;
    }

    public int getInJailTurn(){
        return injailTurn;
    }

    public void setPlayerTurn(int turn){
        playerTurn += turn;
    }

    public int getPlayerTurn(){
        return playerTurn; 
    }
    
    public void killPlayer(){
        playerTurn = 101;
        injail = false;
        int numberOfDeath = 0;
        System.out.println(PlayerDB.player[Game.getCurrentPlayerID()].getName() + " bankrupted.");
        for(int i = 1; i < PlayerDB.player.length; i++){
            if(PlayerDB.player[i].getPlayerTurn() == 101){
                numberOfDeath =+ 1;
            }
        }
        System.out.println("Number of player remaining: " + (PlayerDB.player.length - 1 - numberOfDeath));
        for(int i = 0; i < BoardSpace.properties.length; i++){
            if(Game.getCurrentPlayerID() == BoardSpace.getSpaceAttribute(i, 0)){
                BoardSpace.setSpaceAttribute(i, 0, 0);
            }
        }
        if(PlayerDB.player.length - 1 - numberOfDeath <= 1){
            System.out.println("Only one player survived, the game ends. \n");
            EndGame.endGame();
        }
    }
}
