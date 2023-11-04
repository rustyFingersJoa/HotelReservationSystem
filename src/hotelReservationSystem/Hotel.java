package hotelReservationSystem;
import java.util.Scanner;
import java.io.*;

public class Hotel {
	
	private String hotelName;
	private String hotelAddress;
	private int MAX_ROOM_NUMBER;
	private Room[] rooms;      
	private Scanner scan;
	private String path;  
	
	
	Hotel () {           
		this.setMAX_ROOM_NUMBER(20);
		scan = new Scanner(System.in);
                this.path = "/Users/Admin/Desktop/Workspace/HotelReservationSystem/customers.txt";
                rooms = new Room[MAX_ROOM_NUMBER];
	}
	
	Hotel (String name, String address, int maxRoomNumber, String path) {
		this.setHotelName(name);
		this.setHotelAddress(address);
		this.setPath(path);
		this.setMAX_ROOM_NUMBER(maxRoomNumber);
		scan = new Scanner(System.in);
		rooms = new Room[maxRoomNumber];
		
	    for (int i = 0; i < rooms.length; i++)
		    rooms[i] = new Room(i+1);
	}
	
	public void readDataFromFile() throws IOException {
		try {
			File file = new File(path);
			Scanner scan = new Scanner(file);
			int counter = 0;
			
			while (scan.hasNextLine()) {				
				String line = scan.nextLine();
                String[] parts = line.split(", ");
                
                String customerName = parts[0];
                int rn = Integer.parseInt(parts[1]);
                String roomType = parts[2];
                	
                if (rooms[rn - 1].isRoomAvailable()) {
            		     rooms[rn - 1].reserve();
            		     rooms[rn - 1].setCustomerName(customerName);
            		     rooms[rn - 1].setRoomType(roomType);     
                }
                
                if (counter == 0)                 	               	
                	System.out.println("Room Number      Room Type      Customer Name\n");
       		     
       		    System.out.println(rooms[rn - 1].getRoomNumber() + "                  " + rooms[rn - 1].getRoomType() + "             " + rooms[rn - 1].getCustomerName());      		     
                counter++;              			   
			}
			scan.close();
			
		} catch (FileNotFoundException e) {
			 System.out.println("The file 'customers.txt' could not be found. Please make sure it exists in the specified path.");
		} catch (NumberFormatException e) {
			  System.out.println("An error occurred while reading the room number from the file.");
        }
		
	}
	
	public void updateCustomersFile() throws IOException {
        try {
        	File file = new File(path);
        	BufferedWriter writer = new BufferedWriter (new PrintWriter(file));
        	for (int i = 0; i < rooms.length; i++) {
        		if (!rooms[i].isRoomAvailable())
        			writer.write(rooms[i].toString() + "\n");
        	}
        	writer.close();
           
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred while writing to the file.");
        }
    }

	public void reserveRoom () throws IOException {
		System.out.println("Please enter the customer's name: ");
		String customerName = scan.next();
		
		listAvailableRooms();
		System.out.println("\nEnter a room number: ");
		int rn = askForRoomNumber(scan.nextInt());
				
		if (rooms[rn - 1].isRoomAvailable()) {
		rooms[rn - 1].reserve();
		rooms[rn - 1].setCustomerName(customerName);
		System.out.println("Thank you! The room Number " + rn + " has been reserved for you.\n");
		System.out.println("Now, lets select the room type.");
		selectRoomType(rooms[rn - 1].getRoomNumber());
	} else {
		System.out.println("This room is already booked.");
	 }
		updateCustomersFile();
	}
	
	private void selectRoomType(int roomNumber) {
		System.out.println("Please select one of the following room types: \n"
				+ "-Single\r\n"
				+ "-Double\r\n"
				+ "-King\r\n"
				+ "-Deluxe"        );
	    String roomtype = scan.next();
		
		while(!checkRoomType(roomtype)) {
			System.out.println("\nSorry there is no such room type: " + roomtype + "\n" + "Please select a room type as follows:\n"
					
							+ "-Single\r\n"
							+ "-Double\r\n"
							+ "-King\r\n"
							+ "-Deluxe"        );
					        	roomtype = scan.next();
		   }
		
		if (rooms[roomNumber - 1].getRoomType() == null) {
			rooms[roomNumber - 1].setRoomType(roomtype);
			System.out.println("Thank you, the room type: '" + rooms[roomNumber - 1].getRoomType() + "' has been selected for you");
		} else if ((rooms[roomNumber - 1].getRoomType()).equals(roomtype)) {
			System.out.println("Already has the same room type.");
		} else {
			rooms[roomNumber - 1].setRoomType(roomtype);
			System.out.println("Room type has been changed.");
		}
	}
	
	public void changeRoomType () throws IOException {
		listAvailableRooms();
		System.out.println("\nEnter a room number: ");
		int rn = askForRoomNumber(scan.nextInt());
		
		if (!rooms[rn - 1].isRoomAvailable()) {
			selectRoomType(rooms[rn - 1].getRoomNumber());
		} else 
			System.out.println("The room is not booked yet.");
		updateCustomersFile();
	}
	
	public void deleteReservedRoom() throws IOException {
		listAvailableRooms();
		System.out.println("Enter a room number: ");
		int rn = askForRoomNumber(scan.nextInt());
		
		if (!rooms[rn - 1].isRoomAvailable()) 
			rooms[rn - 1].delete();
		else
			System.out.println("Sorry, room is not booked yet.");
		updateCustomersFile();
	}
	
	public void deleteAllReservedRooms() throws IOException {
		int count = 0;
		for (int i = 0; i < rooms.length; i++) {
			if (!rooms[i].isRoomAvailable()) { 
				
				if (count == 0)
					System.out.println("The deleted rooms: ");
				
				System.out.print(rooms[i].getRoomNumber() + " ");
				
				rooms[i].delete();
				count++;
				if (count == 10)
					System.out.println();
		  }
	 }
		if (count == 0) {
			System.out.println("Sorry, there are no reserved rooms to be deleted.");
		}
		updateCustomersFile();
	}
	
	public void showAllReservedRoomsInfo() {
		int count = 0;
		for (int i = 0; i < rooms.length; i++) {
			if (!rooms [i].isRoomAvailable()) {
				if (count == 0)
			    System.out.println("Room Number      Room Type      Customer Name\n");
			System.out.println("    " + rooms[i].getRoomNumber() + "              " + rooms [i].getRoomType() + "            " + rooms [i].getCustomerName());
				count++;
		  }
		}
		if (count == 0) 
			System.out.println("There are no rooms to show.");
	}
	
	private boolean checkRoomType(String roomType) {
		if (roomType.equals("Single") || roomType.equals("Double") || roomType.equals("King") || roomType.equals("Deluxe")) 
			return true;
		else
			return false;
	}
	
	private int askForRoomNumber(int rn) {
		if (rn > 0 && rn < 21) 
			return rn;
		 else {
			 System.out.println("Invalid number. Try again: ");
			 rn = scan.nextInt();
			return askForRoomNumber(rn);
	     }
	}
	
	private void listAvailableRooms() {
		System.out.println("Available rooms:\n");
		int counter = 0;
		for (int i = 0; i < rooms.length; i++) {
			
			if (rooms[i].isRoomAvailable()) {
				System.out.print(rooms[i].getRoomNumber() + " ");
			}
			counter++;
			if (counter == 11)
				System.out.println();
			
		}
	}
	
    // Setters and getters methods:
	
    public void setHotelName (String hotelName) {
        this.hotelAddress = hotelName;
    }

    public String getHotelName () {
        return hotelName;
    }

    public void setHotelAddress(String hotelAddress) {
    this.hotelAddress = hotelAddress;
    }

	public String getHotelAddress () {
	    return hotelAddress;
	}
	
	public int getMAX_ROOM_NUMBER() {
		return MAX_ROOM_NUMBER;
	}
	
	public void setMAX_ROOM_NUMBER(int MAX_ROOM_NUMBER) {
		if (MAX_ROOM_NUMBER < 20 || MAX_ROOM_NUMBER > 30)
			System.out.println("Invalid number. The valid range is 20-30.");
		else
			this.MAX_ROOM_NUMBER = MAX_ROOM_NUMBER;
	}
	
	public void setFilePath(String path) {
		this.path = path;
	}
	
	public void setPath (String path) {
		if (path instanceof String)
			this.path = path;
		else
			throw new IllegalArgumentException("Invalid data type. String type expected.");
	}

	public String getPath() {
		return path;
	}

	
}







