import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
public class Game {

    private static PlayerDB playerDB;
    private static Dice dice1, dice2;
    private static Player currentPlayer;
	private static int turn = 1;
	private static boolean paidRent = false;

	//Class constructor
    public Game(int playerNumber,PlayerDB playerDB, int playerID, int turn) {
		currentPlayer = playerDB.player[playerID];
		this.turn = turn;
		System.out.println("Welcome to Monopoly\n");
		//normal gameplay
		if (playerID > 0) { Game.gameProcess(); }

		// for testing purpose
		else {
			currentPlayer = playerDB.player[1];
			paidRent = false;
		}	
	}

	//All the game process is through this method
	public static void gameProcess(){
		updateInformation();
		System.out.println(currentPlayer.getName() + "'s turn.");
		if (currentPlayer.getPlayerJailStatus() == false){
			System.out.println("Plese press \"Enter\" to throw dice");
			Scanner scanner = new Scanner(System.in);
			scanner.nextLine();
			dice(-1);															//-1 means not rolled yet
			buy();
			endTurn();
		}
		else if(currentPlayer.getPlayerJailStatus() == true){
			jail();
		}	
	}

	public static void jail() {
		System.out.println("Do you want to pay a fine($150) to escape?");
		System.out.println("1.Yes(Please press 1)");
		System.out.println("2.No(Please press 2)");
		Scanner scanner = new Scanner(System.in);
		int input = scanner.nextInt();
		do{
			if(input == 1){
				//Go to roll
				currentPlayer.setPlayerMoney(-150); 							//player pays 150
				currentPlayer.setInJailTurn(-currentPlayer.getInJailTurn());	//reset player injail turn for next time
				currentPlayer.setPlayerJailStatus(false);						//reset player jail status
				//Start the normal game process
				dice(-1);														//-1 means not rolled yet
				buy();
				endTurn();
			}
			else if(input == 2){
				// roll 2 dice
				System.out.println("Plese press \"Enter\" to throw dice");
				scanner.nextLine();
				int dice1Value = dice1.roll();
				int dice2Value = dice2.roll();
				System.out.println("The value of first dice is " + dice1Value);
				System.out.println("The value of second dice is " + dice2Value);
				// check the dice are the same or not
				if(dice1Value == dice2Value){
				currentPlayer.setInJailTurn(-currentPlayer.getInJailTurn());				//reset player injail turn for next time
				currentPlayer.setPlayerJailStatus(false);									//reset player jail status
				dice(dice1Value+dice2Value);												//move according to 2 dice values
				}
				//two dice values are not the same
				else {
					System.out.println("The value of the two dice are not the same, please try again in next turn." );
					currentPlayer.setInJailTurn(1);											// add one more turn
					endTurn();
				}

			}
			else if(input != 1 && input != 2){
				System.out.println("Incorrect input, please type again");
				System.out.println("Do you want to pay a fine to escape?");
				System.out.println("1.Yes(Please press 1)");
				System.out.println("2.No(Please press 2)");
				input = scanner.nextInt();
			}
		}while(input != 1 && input != 2);
	}


	// method to send turn variable
	public static int getTurn(){
		return turn;
	}

	// method to send current player variable
	public static int getCurrentPlayerID(){
		return currentPlayer.getPlayerID();
	}
    
    public static void dice(int isDiceValue){
		int value;	//the value a player moves
		//If the player hasn't rolled
		//isDiceValue = -1 means not rolled yet
			if (isDiceValue == -1){
				value = dice1.roll() + dice2.roll();
			}
			//if isDiceValue > -1 means dice has been rolled in other place and move according to those number
			else{
				value = isDiceValue;
			}
            
			System.out.println(currentPlayer.getName()+ " rolled a " + value);
			int temp = value + currentPlayer.getPlayerPosition();
			//If the dice total + player space exceeds 19, which
			//is boardwalk, then the space should be set to a 0, and
			//1500 should be added to the player total.
			if(temp > 19) {	
				temp= temp % 20;
				currentPlayer.setPlayerMoney(1500);
				System.out.println(currentPlayer.getName()+ " visited Go and got $1500.");
			}
			//Set the player space to the new space.
			currentPlayer.setPlayerPosition(temp);
			System.out.println(currentPlayer.getName() + " landed on " + BoardSpace.getName(temp) + "\n");
		evaluateBoard();
		updateInformation();
	}

	//check a land has owner or not
	public static void evaluateBoard() {
		if(!paidRent) {
			int rent;
			int owner = BoardSpace.getSpaceAttribute(currentPlayer.getPlayerPosition(), 0); //First of all, handle the rent condition
			if(owner>0) {
				//If the current player is not the owner of the property and
				//he hasn't paid rent yet.
				if(currentPlayer.getPlayerID()!=owner && !paidRent) {
					rent = BoardSpace.getSpaceAttribute(currentPlayer.getPlayerPosition(), 2);
					PlayerDB.player[owner].setPlayerMoney(-rent); // -rent means owner get money
					currentPlayer.setPlayerMoney(rent);
					System.out.println(currentPlayer.getName() + " just payed " + -rent + " to " + PlayerDB.player[owner].getName() + "\n");
					paidRent = true;
				}
			}
			//Then handle all of the other spaces, such as GO and freeparking.
			else if(owner == -1) {
				int tempSpace = currentPlayer.getPlayerPosition();
				int money = BoardSpace.getSpaceAttribute(currentPlayer.getPlayerPosition(), 2);
				//If the space is either go or free parking, add the money.
				
				if(tempSpace == 0) {
				}
				//position is in income tax
				else if(tempSpace == 3) {
					System.out.println("You need to pay an income tax that is 10% of your property");
					currentPlayer.setPlayerMoney((int)(currentPlayer.getPlayerMoney() * 0.1 * -1));
					System.out.println("Your current money is :" + currentPlayer.getPlayerMoney());
				}
				//position is in jail
				else if(tempSpace == 5) {
					System.out.println("You are just visiting. Say hi to your friends!");
				}
				//position is in chance
				else if(tempSpace == 8 || tempSpace == 12 || tempSpace == 18) {
					System.out.println("Time to test your luck, press \"Enter\" to get the result");
					Scanner scanner = new Scanner(System.in);
					scanner.nextLine();
					Random random = new Random();
					//random.nextInt(max - min) + min, result between 20 to -30
					int randomValue = random.nextInt(20 + 30) - 30;
					int getMoney = randomValue * 10;
					// if the randomValue < 0
					if (randomValue  < 0){
						System.out.println("Oh! You loss $" + getMoney * -1);
						currentPlayer.setPlayerMoney(getMoney);
						System.out.println("Your current money is :" + currentPlayer.getPlayerMoney());
					}
					else{
						System.out.println("Yes! You get $" + getMoney);
						currentPlayer.setPlayerMoney(getMoney);
						System.out.println("Your current money is :" + currentPlayer.getPlayerMoney());
					}
				}
				//position in free parking, no function actions
				else if(tempSpace == 10) {
					System.out.println("You are in free parking, just take a rest ^_^");
				}
				//position is go to jail, player move to jail
				else if(tempSpace == 15) {
					currentPlayer.setPlayerPosition(5);
					System.out.println("You are in jail, stop action in this turn");
					currentPlayer.setPlayerJailStatus(true);
					endTurn();
				}
				
			}
		}
	}

	public static void buy() {
			int currentSpace = currentPlayer.getPlayerPosition();
			//This checks if the property is actually ownable.
			if(BoardSpace.getSpaceAttribute(currentSpace, 0)!=-1 ) {
				//If the property isn't owned
				if(BoardSpace.getSpaceAttribute(currentSpace, 0) == 0 ){
					System.out.println("Do you want to buy this land?");
					System.out.println("1.Yes(Please press 1)");
					System.out.println("2.No(Please press 2)");
					Scanner scanner = new Scanner(System.in);
					int input = scanner.nextInt();
					do{
						if(input == 1){
							if(BoardSpace.getSpaceAttribute(currentSpace, 0) == 0) {
							int cost = BoardSpace.getSpaceAttribute(currentSpace, 1);
							//If they have enough money
							if(currentPlayer.getPlayerMoney() - cost>=0) {
								currentPlayer.setPlayerMoney(-cost);
								BoardSpace.setSpaceAttribute(currentSpace, 0, currentPlayer.getPlayerID());
								System.out.println(currentPlayer.getName() + " just purchased " 
										+ BoardSpace.getName(currentSpace) + " for $" + cost + "\n");
								updateInformation();
							}
							//Otherwise tell them they don't.
							else
							System.out.println("You cannot afford this property.\n");
							}
							//Tell them that the property is owned
							else {
								if(BoardSpace.getSpaceAttribute(currentSpace, 0) == currentPlayer.getPlayerID())
									System.out.println("This is your property\n");
								else
									System.out.println("The property is already owned by " + PlayerDB.player[BoardSpace.getSpaceAttribute(currentSpace,0)] + "\n");
							}
						}
						else if(input != 1 && input != 2){
							System.out.println("Incorrect input, please type again");
							System.out.println("Do you want to buy this land? Yes or No");
							input = scanner.nextInt();
						}
					}while(input != 1 && input != 2);
				}
			}
				
			//Otherwise tell the player that the property is not ownable.
			else {
				System.out.println("This space cannot be purchased.\n");
			}
			System.out.println("Buy period end");
	}
	
	public static void endTurn() {
			System.out.println(currentPlayer.getName() + "'s turn ended.\n");
			currentPlayer.setPlayerTurn(1);
			Boolean changeUser = false; // check if a player's turn has been changed
			int i = 1;
			// do...while for find the next player turn
			do{
				if (i > playerDB.player.length - 1){
					//if all player finished their turn switch to next game turn
					turn += 1;
					if(turn > 100){
						//if game turn is 100
						System.out.println("The last turn finishes. The Game has ended!");
						EndGame.endGame();
					}
					currentPlayer = playerDB.player[1]; //new game turn switch player turn to player1
					changeUser = true;
				}
				else if (playerDB.player[i].getPlayerTurn() < currentPlayer.getPlayerTurn()){
					//check which player still not to finish their player turn. If yes, give the player turn the highest order player.
					currentPlayer = playerDB.player[i];
					changeUser = true;
				}
				else{
					i++;
				}
			}while(changeUser != true);

			paidRent = false;
		Save.saveFile();
		System.out.println(String.format("%s\t%s", "Player's name", "Player's position"));
		for (int x = 1; x < PlayerDB.player.length; x++){
			System.out.println(String.format("%7s%17s", PlayerDB.player[x].getName(), PlayerDB.player[x].getPlayerPosition()+1));
		}
		gameProcess();
	}

	public static void updateInformation() {
		String s = "";
		//put player information list in String s
		for (int i=1; i<PlayerDB.player.length; i++){
			s = s + PlayerDB.player[i].getName() + ":" + PlayerDB.player[i].getPlayerMoney() + " ";
		}
		System.out.println("Current Game Rounds:" + Game.getTurn() + " "+ s + " "); //print Game information 
		ArrayList<String> properties = new ArrayList<String>(); //create an array find current player properties list
		for(int a = 0; a<20; a++) {
			//findding the current player properties list
			if(BoardSpace.getSpaceAttribute(a, 0) == currentPlayer.getPlayerID()) {
				properties.add(BoardSpace.getName(a));
			}
		}
		if(!properties.isEmpty()) {
			System.out.println( currentPlayer.getName() + "'s properties are: \n");
			for(String c:properties) {
				System.out.println("   " + c + "\n");
			} 
		}
		else
		System.out.println( currentPlayer.getName() + " don't own any properties\n");
	}
}

