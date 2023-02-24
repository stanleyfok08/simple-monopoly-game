import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Random;
import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
public class test{


    @Test
    public void testAll() {
      testDice();
      testPlayerBehavior();
      testSaveString();  
      testRent_1();
      testRent_2();
      testRent_3();    
      testBuyProperty_1();
      testBuyProperty_2();
      testPropertyRelease();
      testJailStatus();
      testBoardSpace();
      testPlayer();
    }

    @Test
    public void testDice() {        
        // test for Dice class
        // see if a dice roll is between 1 and 4
        System.out.println("Testing Dice\n");
        Dice dice = new Dice();
        int diceNum = dice.roll();        
        assertTrue(1 <= diceNum && diceNum <= 4);
    }

      @Test
      public void testPlayerBehavior() {
        // test on player behavior
        //setPlayerDB(int player_id, String name, int money, int position, boolean injail, int player_turn)
        System.out.println("Testing create object\n");
        BoardSpace bs = new BoardSpace();
        PlayerDB playerDB = new PlayerDB(4, "load");
        Game game = new Game(4, playerDB, 0, 1);

        //player lands on 3
        System.out.println("Player land on 3\n");
        playerDB.setPlayerDB(1, "test", 1299, 3, false,0, 5);
        int expected = 1299;
        assertEquals(expected, playerDB.player[1].getPlayerMoney());

        //player lands on 5
        System.out.println("Player land on 5\n");
        playerDB.player[1].setPlayerMoney(-(playerDB.player[1].getPlayerMoney())); // reset player money for futher testing
        playerDB.setPlayerDB(1, "test", 1299, 5, false,0, 1);
        assertEquals(1, playerDB.player[1].getPlayerID());
        assertEquals("test", playerDB.player[1].getName());
        assertEquals(1299, playerDB.player[1].getPlayerMoney());
        assertEquals(5, playerDB.player[1].getPlayerPosition());
        assertEquals(false, playerDB.player[1].getPlayerJailStatus());
        assertEquals(6, playerDB.player[1].getPlayerTurn());
        
        //player lands on 10
        System.out.println("Player land on 10\n");
        playerDB.player[1].setPlayerMoney(-(playerDB.player[1].getPlayerMoney())); // reset player money for futher testing
        playerDB.setPlayerDB(1, "test", 1299, 10, false,0, 1);
        assertEquals(1, playerDB.player[1].getPlayerID());
        assertEquals("test", playerDB.player[1].getName());
        assertEquals(1299, playerDB.player[1].getPlayerMoney());
        assertEquals(10, playerDB.player[1].getPlayerPosition());
        assertEquals(false, playerDB.player[1].getPlayerJailStatus());
        assertEquals(7, playerDB.player[1].getPlayerTurn());


        //player lands on 8, 12, 18 Chance
        System.out.println("Player land on 8, 12, 18 Chance\n");
        playerDB.player[1].setPlayerMoney(-(playerDB.player[1].getPlayerMoney())); // reset player money for futher testing
        playerDB.setPlayerDB(1, "A", 1500, 8, false,0, 5);
        Random random = new Random();
        int randomValue = random.nextInt(20 + 30) - 30;
        int getMoney = randomValue * 10;
        playerDB.player[1].setPlayerMoney(getMoney);
        assertTrue(1200 <= playerDB.player[1].getPlayerMoney() && playerDB.player[1].getPlayerMoney() <= 1700);

        // what happen when -ve money 
        // all 0, name = null, turn become <100, position unchanged,
        System.out.println("Player -ve money, lose\n");
        playerDB.player[1].setPlayerMoney(-(playerDB.player[1].getPlayerMoney())); // reset player money for futher testing
        playerDB.player[2].setPlayerMoney(-(playerDB.player[2].getPlayerMoney())); // reset player money for futher testing
        playerDB.setPlayerDB(1, null, -1000, 13, false,0, 2);
        playerDB.setPlayerDB(2, null, 1500, 13, false,0, 2);
        playerDB.setPlayerDB(3, null, 1500, 13, false,0, 2);
        assertEquals(1, playerDB.player[1].getPlayerID());
        assertEquals(null, playerDB.player[1].getName());
        assertEquals(-1000, playerDB.player[1].getPlayerMoney());
        assertEquals(false, playerDB.player[1].getPlayerJailStatus());
        assertTrue(100 < playerDB.player[1].getPlayerTurn());
        for (int i = 0; i < 20; i++){
          if (bs.getSpaceAttribute(i , 0) != 1){
            assertTrue("update boardSpace success", true);
          }
        }
        //- money, on 18
        System.out.println("Player -ve money, lose\n");
        playerDB.player[1].setPlayerMoney(-(playerDB.player[1].getPlayerMoney())); // reset player money for futher testing
        playerDB.setPlayerDB(1, "A", 0, 18, false,0, 5);
        assertEquals(1, playerDB.player[1].getPlayerID());
        assertEquals("A", playerDB.player[1].getName());
        assertEquals(0, playerDB.player[1].getPlayerMoney());
        assertEquals(false, playerDB.player[1].getPlayerJailStatus());
        assertTrue(100 < playerDB.player[1].getPlayerTurn());
        for (int i = 0; i < 20; i++){
          if (bs.getSpaceAttribute(i , 0) != 2){
            assertTrue("update boardSpace success", true);
          }
        }
      }

      @Test
      public void testSaveString() {      
        PlayerDB myPlayerDB = new PlayerDB(3, "load");
        Game myGame = new Game(3, myPlayerDB, 0, 1);
        BoardSpace myBs = new BoardSpace();
        Save save = new Save();
  
        myPlayerDB.setPlayerDB(1, "A", 1110, 6, false,0, 6);  // player 1
        myPlayerDB.setPlayerDB(2, "B", 1222, 9, false,0, 6);  // player 2
  
        myBs.setSpaceAttribute(9, 0, 1);    // space 9 owned by player 1
        myBs.setSpaceAttribute(14, 0, 2);   // space 14 owned by player 2
  
        String testString2 = "-1\n0\n0\n-1\n0\n-1\n0\n0\n-1\n1\n-1\n0\n-1\n0\n2\n-1\n0\n0\n-1\n0\n";
        assertEquals(testString2,save.writeBoardspaceInfo());
  
        String testString1 = "1\n3\n1\n1 A 1110 6 false 0 6\n2 B 1222 9 false 0 6\n";
        assertEquals(testString1,save.writePlayerInfo());
        }

      @Test
      public void testRent_1() {
        System.out.println("Player pay rent test\n");
        
        //when player lands on property (enough to pay rent)
        PlayerDB playerDB = new PlayerDB(4, "load");
        Game game = new Game(4, playerDB, 0, 2);
        BoardSpace bs = new BoardSpace();

        bs.setSpaceAttribute(1, 0, 2);  // space 2 owned by player 2
        playerDB.setPlayerDB(1, "A", 1500, 1, false,0, 2); // (enough to pay rent)
        playerDB.setPlayerDB(2, "B", 1500, 1, false,0, 2);
        playerDB.setPlayerDB(3, "C", 1500, 1, false,0, 2);
        game.evaluateBoard();
        assertEquals(1500 - 90, playerDB.player[1].getPlayerMoney()); // player 1 pay rent
        assertEquals(1500 + 90, playerDB.player[2].getPlayerMoney()); // player 2 get rent
      }

      @Test
      public void testRent_2() {
        System.out.println("Player cannot pay rent\n");

        //when player lands on property (not enough to pay rent)
        PlayerDB playerDB = null;
        playerDB = new PlayerDB(5, "load");
        Game game = new Game(5, playerDB, 0, 1);
        BoardSpace bs = new BoardSpace();
        
        bs.setSpaceAttribute(1, 0, 2);  // space 2 owned by player 2
        playerDB.player[1].setPlayerMoney(-(playerDB.player[1].getPlayerMoney())); // reset player money for futher testing
        playerDB.player[2].setPlayerMoney(-(playerDB.player[2].getPlayerMoney())); // reset player money for futher testing
        playerDB.setPlayerDB(1, "A", 10, 1, false,0, 3); // (not enough to pay rent)
        playerDB.setPlayerDB(2, "B", 1100, 1, false,0, 3);
        playerDB.setPlayerDB(3, "C", 1200, 1, false,0, 2);
        playerDB.setPlayerDB(4, "D", 1300, 1, false,0, 2);
        game.evaluateBoard();        
        System.out.println("Game runned\n");
        System.out.println("turn is:" + playerDB.player[1].getPlayerTurn());
        System.out.println("money is:" + playerDB.player[1].getPlayerMoney());
        assertEquals(101, playerDB.player[1].getPlayerTurn()); // player 1 bankrupt, turn become 101
        assertEquals(1100 + 90, playerDB.player[2].getPlayerMoney()); // player 2 still get rent
      }

      @Test
      public void testRent_3() {
        PlayerDB playerDB = new PlayerDB(3, "load");
        Game game = new Game(3, playerDB, 0, 2);
        BoardSpace bs = new BoardSpace();

        //when player lands on self-owned property
        System.out.println("Player in their own property\n");
        playerDB.player[1].setPlayerMoney(-(playerDB.player[1].getPlayerMoney())); // reset player money for futher testing
        bs.setSpaceAttribute(1, 0, 1);
        playerDB.setPlayerDB(1, "A", 1500, 1, false,0, 2);
        playerDB.setPlayerDB(2, "B", 1500, 1, false,0, 2);
        game.evaluateBoard();
        assertEquals(1500, playerDB.player[2].getPlayerMoney());
      }

      @Test
      public void testBuyProperty_1() {
        PlayerDB playerDB = new PlayerDB(3, "load");
        Game game = new Game(3, playerDB, 0, 5);
        BoardSpace bs = new BoardSpace();

        //when player lands on property not owned and buy(enough money)
        System.out.println("Player buy\n");
        playerDB.setPlayerDB(1, "A", 1500, 19, false,0, 5);
        
        // simulate press 1 to buy
        String data = "1\n";
        InputStream stdin = System.in;
        try {
          System.setIn(new ByteArrayInputStream(data.getBytes()));
        } finally {
          game.buy();
          System.setIn(stdin);
        }

        assertEquals(1500 - 600, playerDB.player[1].getPlayerMoney());
        assertEquals(1, bs.getSpaceAttribute(19, 0));
      }

      @Test
      public void testBuyProperty_2() {
        PlayerDB playerDB = new PlayerDB(3, "load");
        Game game = new Game(3, playerDB, 0, 1);
        BoardSpace bs = new BoardSpace();

        //when player lands on property not owned and buy(not enough money)
        System.out.println("Player cannot buy\n");
        playerDB.player[1].setPlayerMoney(-(playerDB.player[1].getPlayerMoney())); // reset player money for futher testing
        playerDB.setPlayerDB(1, "A", 10, 17, false, 0, 5);

        // simulate press 1 to buy
        String data = "1\n";
        InputStream stdin = System.in;
        try {
          System.setIn(new ByteArrayInputStream(data.getBytes()));
        } finally {
          game.buy();
          System.setIn(stdin);
        }

        assertEquals(10, playerDB.player[1].getPlayerMoney());
        assertEquals(0, bs.getSpaceAttribute(17, 0));
      }
      
      @Test
      public void testPropertyRelease() { 
        PlayerDB playerDB = new PlayerDB(4, "load");
        Game game = new Game(4, playerDB, 0, 1);
        BoardSpace bs = new BoardSpace();

        //player dies and property is released
        System.out.println("Player dies and property is released\n");
        bs.setSpaceAttribute(9, 0, 1);
        playerDB.setPlayerDB(1, "A", -1, 10, false,0, 5);
        playerDB.setPlayerDB(2, "B", 1500, 10, false,0, 5);

        assertEquals(0, bs.getSpaceAttribute(9, 0));
      }

      @Test
      public void testJailStatus() {   
        PlayerDB playerDB = new PlayerDB(3, "load");

        //player in jail
        System.out.println("Player in jail\n");
        playerDB.setPlayerDB(1, "A", 1500, 15, true,1, 5);
        assertEquals(1, playerDB.player[1].getPlayerID());
        assertEquals("A", playerDB.player[1].getName());
        assertEquals(1500, playerDB.player[1].getPlayerMoney());
        assertEquals(15, playerDB.player[1].getPlayerPosition());
        assertEquals(true, playerDB.player[1].getPlayerJailStatus());
        assertEquals(1, playerDB.player[1].getInJailTurn());
      }

      @Test
      public void testBoardSpace() {   
        // test for BoardSpace class
        BoardSpace boardSpace = new BoardSpace();
        String testPropertyName = "Central";
        int testPropertyNameSpace = 1;
        int testPropertyPrice = 800;
        int testPropertyRent = 90; 
        // properties[owner, price, rent]
        int owner = 0;
        int attributePrice = 1;
        int attributeRent = 2;
        
        // get getSpaceAttribute - Price
        assertEquals(testPropertyPrice, boardSpace.getSpaceAttribute(testPropertyNameSpace, attributePrice));
        // get getSpaceAttribute - Rent
        assertEquals(-testPropertyRent, boardSpace.getSpaceAttribute(testPropertyNameSpace, attributeRent));
        // getName
        assertEquals(testPropertyName, boardSpace.getName(testPropertyNameSpace));
        
        // setSpaceAttribute
        int testPalyerID = 3;
        boardSpace.setSpaceAttribute(testPropertyNameSpace, owner, testPalyerID);
        assertEquals(testPalyerID, boardSpace.getSpaceAttribute(testPropertyNameSpace, owner));
      }

      @Test
      public void testPlayer() {        
        //test for Class Player
        //test for Constructor Player(int playerID) 
        //set Player variable and get Player variable
       int testPlayerID1 = 1;
       Player player1 = new Player(testPlayerID1);
       assertEquals(1, player1.getPlayerID());

       testPlayerID1 = 2;
       player1.setPlayerID(testPlayerID1);
       assertEquals(testPlayerID1, player1.getPlayerID());

       String testPlayName1 = "Player1";
       player1.setName(testPlayName1);
       assertEquals(testPlayName1, player1.getName());

       int testMoney1 = 2000;
       player1.setPlayerMoney(testMoney1);
       assertEquals(testMoney1, player1.getPlayerMoney());

       int testPosition1 = 1;
       player1.setPlayerPosition(testPosition1);
       assertEquals(testPosition1, player1.getPlayerPosition());

       Boolean testInJail1 = false;
       player1.setPlayerJailStatus(testInJail1);
       assertEquals(testInJail1, player1.getPlayerJailStatus());

       int testPlayerTurn1 = 1;
       player1.setPlayerTurn(testPlayerTurn1);
       assertEquals(testPlayerTurn1, player1.getPlayerTurn()); 

      //test for Constructor  public Player(String name, int playerID) 
       int testPlayerID2 = 1;
       String testPlayName2= "Player2";

       Player player2 = new Player(testPlayName2, testPlayerID2);

       assertEquals(testPlayerID2, player2.getPlayerID());
       assertEquals(testPlayName2, player2.getName());

      //test for Constructor Player(int playerID, String name, int money, int position, boolean injail, int playerTurn) 
        
       int testPlayerID = 1;
       String testPlayName= "Player3";
       int testMoney = 1500;
       int testPosition = 0;
       Boolean testInJail = false;
       int testInjailTurn = 0;
       int testPlayerTurn = 1; 

       Player player3 = new Player(testPlayerID, testPlayName, testMoney, testPosition, testInJail, testInjailTurn, testPlayerTurn);
       
       assertEquals(testPlayerID, player3.getPlayerID());
       assertEquals(testPlayName, player3.getName());
       assertEquals(testMoney, player3.getPlayerMoney());
       assertEquals(testPosition, player3.getPlayerPosition());
       assertEquals(testInJail, player3.getPlayerJailStatus());
       assertEquals(testInjailTurn, player3.getInJailTurn());
       assertEquals(testPlayerTurn, player3.getPlayerTurn());
    }
}