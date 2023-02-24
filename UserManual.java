import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class UserManual {
    public static void showManual(){
        try {
            File manObj = new File("save\\userManual.txt"); 
            Scanner fileReader = new Scanner(manObj);
            while (fileReader.hasNextLine()) {
                String txt = fileReader.nextLine();
                System.out.println(txt);
            }
            fileReader.close();
            Menu.main(null);
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred. Please go to save/userManual.txt to read the details.");
        }
    }
}
