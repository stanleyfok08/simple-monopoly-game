import java.util.Random;

public class Dice{
    public static int maxFace = 5;
    private static int faceValue;

    public static int roll(){
        Random random = new Random();
        faceValue = random.nextInt(maxFace) % maxFace;
        if (faceValue == 0) faceValue += 1;
        return faceValue ; 
    }
}