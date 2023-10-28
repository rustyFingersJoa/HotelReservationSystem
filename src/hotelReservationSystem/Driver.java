package hotelReservationSystem;

public class Driver {

	public static void main(String[] args) throws IOException {
		
		java.util.Scanner scan = new java.util.Scanner(System.in);
		Hotel hotel = new Hotel("Olive Garden", "Jerusalem", 20, "/Users/Admin/Desktop/Workspace/HotelReservationSystem/Test.txt");
		int methodOfChoice = 0;
	                         
		while (methodOfChoice != 6) {	
			
		System.out.println("\n----------------------------------\n"
				+ "Please Select an Option (1-6):\r\n\n"
				+ "1- Reserve an available room.\r\n"
				+ "2- Change room type.\r\n"
				+ "3- Delete a reserved room.\r\n"
				+ "4- Delete all reserved rooms.\r\n"
				+ "5- Show all reserved rooms' information.\r\n"
				+ "6- Exit System.");
		
        methodOfChoice = scan.nextInt();
            	
		switch (methodOfChoice) {
		
		case 1: hotel.reserveRoom(); break;
		case 2: hotel.changeRoomType(); break;
		case 3: hotel.deleteReservedRoom(); break;
		case 4: hotel.deleteAllReservedRooms(); break;
		case 5: hotel.readDataFromFile(); break;
		case 6: System.out.println("You have quit the system."); break;
		default: System.out.println("Invalid method choice. Please choose a valid method."); break;
		}     
		
	  }		
		scan.close();
	}

}
