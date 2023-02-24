import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Save {
  public static void saveFile(){
    //save game function
    try {
        FileWriter myWriter = new FileWriter("save\\save.txt");
        myWriter.write(writePlayerInfo());
        myWriter.write(writeBoardspaceInfo());
        myWriter.close();
        System.out.println("The game progress was auto-saved successfully.\n");
      } catch (IOException e) {
        System.out.println("An error occurred.");
        e.printStackTrace();
      }
    }

    public static String writePlayerInfo(){
        String s = "";
        s += Game.getTurn() + "\n";
        s += PlayerDB.player.length +"\n";
        s += Game.getCurrentPlayerID() +"\n";
        for(int i = 1; i < PlayerDB.player.length; i++){
            s += String.valueOf(PlayerDB.player[i].getPlayerID() + " ");
            s += String.valueOf(PlayerDB.player[i].getName() + " ");
            s += String.valueOf(PlayerDB.player[i].getPlayerMoney() + " ");
            s += String.valueOf(PlayerDB.player[i].getPlayerPosition() + " ");
            s += String.valueOf(PlayerDB.player[i].getPlayerJailStatus() + " ");
            s += String.valueOf(PlayerDB.player[i].getInJailTurn() + " ");
            s += PlayerDB.player[i].getPlayerTurn() + "\n";
        }
        return s;
    }

    public static String writeBoardspaceInfo(){
        String s = "";
        for(int i = 0; i < BoardSpace.properties.length; i++){
            s += String.valueOf(BoardSpace.getSpaceAttribute(i,0) + "\n");
        }
        return s;
    }
}
