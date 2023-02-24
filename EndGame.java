import java.util.Arrays;
import java.io.File;

public class EndGame {
    public static void endGame(){
        System.out.println("Leaderboard");
        System.out.println(String.format("Rank%3s", "") + String.format("Name%10s", "money"));
        int size = PlayerDB.player.length-1;
        //sorting
        for (int i = 0; i < size-1; i++)
            for (int j = 1; j < size-i; j++)
                if (PlayerDB.player[j].getPlayerMoney() < PlayerDB.player[j+1].getPlayerMoney()) {
                    // swap arr[j+1] and arr[j]
                    Player temp = PlayerDB.player[j];
                    PlayerDB.player[j] = PlayerDB.player[j+1];
                    PlayerDB.player[j+1] = temp;
                }
        for (int i = 1; i < PlayerDB.player.length; i++){
            System.out.println(i + String.format("%10s", PlayerDB.player[i].getName()) + String.format("%10s", PlayerDB.player[i].getPlayerMoney()));
        }
        if(PlayerDB.player[1].getPlayerMoney() == PlayerDB.player[2].getPlayerMoney()){
            System.out.println("\nThis round is a draw!\n");
        }
        else{
            System.out.println(String.format("\n%s wins this round! Congratulations!\n", PlayerDB.player[1].getName()));
        }
        System.out.println("Thanks for playing!\n");
        File f = new File("save\\save.txt"); 
        f.delete();
        Menu.main(null);
    }
}
