import java.util.Scanner;

public class Start {
    public static void start() {
        // new game function
        int playerNumber = 0;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please input the number of players.");
        playerNumber = scanner.nextInt();
        while(playerNumber < 2 || playerNumber > 6){
            System.out.println("The number of players must be between 2 - 6.");
            System.out.println("Please input again.");
            playerNumber = scanner.nextInt();
        }
        PlayerDB playerDB = new PlayerDB(playerNumber + 1, "create");
        new Game(playerNumber,playerDB,1,1);
    }
}
