public class BoardSpace {
	
	/*
	 * The 0 column is for ownership. -1 which means the property cannot be owned, 0 of unowned
	 * 		and the player number if owned by a player.
	 * The 1 column is the cost of the property.
	 * The 2 column is the rent of the property.
	 */

	public static int properties[][] = {
  //column 0    1     2    									 space
        { -1,   0,    0 },    //Go                            0(1)			
        {  0, 800,  -90 },    //Central       	              1(2)
        {  0, 700,  -65 },    //Wan Chai                      2(3)
        { -1,   0, 	  0 },    //Income Tax                    3(4)
        {  0, 600,  -60 },    //Stanley                       4(5)			
        { -1,   0,    0 },    //In jail                       5(6)
        {  0, 400,  -10 },    //Shek O                        6(7)
        {  0, 500,  -40 },    //Mong Kok                      7(8)
        { -1,   0,    0 },    //CHANCE                        8(9)
        {  0, 400,  -15 },    //Tsing Yi                      9(10)
        { -1,   0,    0 },    //FREE PARKING                  10(11)
        {  0, 700,  -75 },    //Shatin                        11(12)
        { -1,   0,    0 },    //CHANCE                        12(13)
        {  0, 400,  -20 },    //Tuen Mun                      13(14)
        {  0, 500,  -25 },    //Tai Po                        14(15)
        { -1,   0,    0 },    //GO TO JAIL                    15(16)
        {  0, 400,  -10 },    //Sai Kung                      16(17)
        {  0, 400,  -25 },    //Yuen Long                     17(18)
        { -1,   0,   0  },    //CHANCE                        18(19)
        {  0, 600,  -25 },    //Tai O                         19(20)
        };
	
	private static String names[] =  {
			"Go",
			"Central",
			"Wan Chai",
			"Income Tax",
			"Stanley",
            "In jail",
			"Shek O",
			"Mong Kok",
			"CHANCE",
			"Tsing Yi",
			"FREE PARKING", 
			"Shatin",
			"CHANCE",
			"Tuen Mun ",
			"Tai Po",
			"GO TO JAIL",
			"Sai Kung",
			"Yuen Long",
			"CHANCE",
			"Tai O"
		};

	public static int getSpaceAttribute(int space, int column) {
		return properties[space][column];
	}
	
	public static void setSpaceAttribute(int space, int column, int value) {
		properties[space][column] = value;
	}
	public static String getName(int space) {
		return names[space];
	}
    
}
