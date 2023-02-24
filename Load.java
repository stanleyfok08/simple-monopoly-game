import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Load {

    public static int playerNumber;
    public static PlayerDB playerDB;
    public static int currentPlayerID;

    public static void load(){
        int gameTurn = 0;
        try {
            File myObj = new File("save\\save.txt"); //save game file to this location.If same file name exist, replace the file. There should only be one file.
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                    gameTurn = myReader.nextInt();
                    playerNumber = myReader.nextInt();
                    playerDB = new PlayerDB(playerNumber,"load");
                    currentPlayerID = myReader.nextInt();
                    for (int i = 1; i <playerNumber; i++){
                        // save players' information
                        int playerID = myReader.nextInt();
                        String name = myReader.next();
                        int money = myReader.nextInt();
                        int position = myReader.nextInt();
                        boolean injail = Boolean.valueOf(myReader.next());
                        int playerTurn = myReader.nextInt();
                        int injailTurn = myReader.nextInt();
                        playerDB.setPlayerDB(playerID, name, money, position, injail, injailTurn, playerTurn);
                    }
                    for (int i = 0; i <BoardSpace.properties.length; i++){ 
                        //save owner information
                        int ownership = myReader.nextInt();
                        BoardSpace.setSpaceAttribute(i, 0, ownership);
                    }
                    String lastLine = myReader.nextLine();
            }
            
            myReader.close();
          } catch (Exception e) {
            System.out.println("An error occurred. Try to start a new game.\n");
            Menu.main(null);
          }
        Game myGame = new Game(playerNumber,playerDB,currentPlayerID, gameTurn);
        myGame.gameProcess();
    }
}
