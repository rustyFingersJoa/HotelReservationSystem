package hotelReservationSystem;

public class Room {
	
	private int roomNumber;
	private String roomType;
	private boolean isRoomAvailable;
	private String customerName;

	public Room () {                     // No-arg const.
		this.isRoomAvailable = true;
	}
	
	public Room (int roomNumber) {       // Const. with args.
		this.setRoomNumber(roomNumber);
		this.isRoomAvailable = true;
	}
	
	public boolean isRoomAvailable () {      
		return isRoomAvailable;
	}
	
	public void reserve () {           
		isRoomAvailable = false;
	}
	
	public void delete () {            
		isRoomAvailable = true;
		customerName = null;
		roomType = null;
	}
	
	public String toString() {
		return this.customerName + ", " + this.roomNumber + ", " + this.roomType;
	}
	

	// Setters and Getters
	
	
	public int getRoomNumber () {
		return roomNumber;
	}
	
	public void setRoomNumber (int roomNumber) {
		this.roomNumber = roomNumber;
	}
	
	public String getRoomType() {
		return roomType;
    }
	
	public void setRoomType (String roomType) {
			this.roomType = roomType;
	}
	
	public String getCustomerName() {
		return customerName;
    }
	
	public void setCustomerName (String customerName) {
			this.customerName = customerName;
	}
}
