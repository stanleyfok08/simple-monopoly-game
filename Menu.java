import java.util.Scanner;

public class Menu {

    public static void main(String[] args) {
        System.out.println("Welcome to Monopoly\n");
        System.out.println("Please choose the following:");
        System.out.println("0.Quit game(Please press 0)");
        System.out.println("1.Create new game(Please press 1)");
		System.out.println("2.Load game(Please press 2)");
        System.out.println("3.User Manual(Please press 3)");
        Scanner scanner = new Scanner(System.in);
				String input = scanner.next();
				do{
                    if(input.equals("0")){
						System.exit(0);
					}
					else if(input.equals("1")){
						Start.start();
					}
                    else if(input.equals("2")){
                        Load.load();
                    }
                    else if(input.equals("3")){
                        UserManual.showManual();
                    }
					else if(!input.equals("0") && !input.equals("1") && !input.equals("2") && !input.equals("3")){
						System.out.println("Incorrect input, please type again");
                        System.out.println("Please choose create new game or load game");
                        System.out.println("0.Quit game(Please press 0)");
                        System.out.println("1.Create new game(Please press 1)");
                        System.out.println("2.Load game(Please press 2)");
                        System.out.println("3.User Manual(Please press 3)");
						input = scanner.next();
					}
			}while(!input.equals("0") && !input.equals("1") && !input.equals("2") && !input.equals("3"));
    }
}