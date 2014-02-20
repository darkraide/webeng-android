package models;




public class BengModelItem {
	public BengModelItem(String updated, String status, String description,
			String contact, String address, int type, String deadline,
			String user, String id, String winner, String[] location,
			String[] photo, String __v) {
		super();
		this.updated = updated;
		this.status = status;
		this.description = description;
		this.contact = contact;
		this.address = address;
		this.setBengtype(type);
		this.deadline = deadline;
		this.user = user;
		this.setId(id);
		this.winner = winner;
		this.location = location;
		this.setPhotos(photo);
		this.__v = __v;
	}
	private String updated;
	private String status;
	private String description;
	private String contact;
	private String address;
	private int bengtype;
	private String deadline;
	private String user;
	private String _id;
	private String winner;
	private String[] location;
//	private String name;
	
	
	
	
	
	//private String[] thumps;
	private String[] photos;
	private String __v;
	
	


	public BengModelItem() {
	
	}
	
	

	/*public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}*/

	public String getDeadline() {
		return deadline;
	}

	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTimestamp() {
		return __v;
	}

	public void setTimestamp(String timestamp) {
		this.__v = timestamp;
	}

	


/*		return location;
	}
	public void setLocation(String[] location) {
		this.location = location;
	}
	public String getWinner() {
		return winner;
	}
	public void setWinner(String winner) {
		this.winner = winner;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}*/
	public String getUpdated() {
		return updated;
	}
	public void setUpdated(String updated) {
		this.updated = updated;
	}
	public String get__v() {
		return __v;
	}
	public void set__v(String __v) {
		this.__v = __v;
	}
	public String[] getLocation() {
		return location;
	}
	public void setLocation(String[] location) {
		this.location = location;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getWinner() {
		return winner;
	}
	public void setWinner(String winner) {
		this.winner = winner;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}



	public int getBengtype() {
		return bengtype;
	}



	public void setBengtype(int bengtype) {
		this.bengtype = bengtype;
	}



	public String getId() {
		return _id;
	}



	public void setId(String _id) {
		this._id = _id;
	}



	public String[] getPhotos() {
		return photos;
	}



	public void setPhotos(String[] photos) {
		this.photos = photos;
	}

}
