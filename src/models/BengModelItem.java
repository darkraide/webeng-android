package models;


import models.BengModelListItem.BengType;

public class BengModelItem {
	private String id;
	private BengType type;
	private String name;
	private String deadline;
	private String status;
	private String timestamp;
	private String[] thumps;
	private String[] photo;
	private String[] location;
	private String address;
	private String description;
	private String winner;

	public BengModelItem(String Id,BengType Type,String Name,String Deadline,String status,String Timestamp,String[] thumps,String[] Photo){
		id= Id;
		type = Type;
		name = Name;
		deadline = Deadline;
		this.status = status;
		timestamp = Timestamp;
		this.thumps = thumps;
		this.photo = Photo;
	}
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

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
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String[] getThumps() {
		return thumps;
	}

	public void setThumps(String[] thumps) {
		this.thumps = thumps;
	}

	public String[] getPhoto() {
		return photo;
	}

	public void setPhoto(String[] photo) {
		this.photo = photo;
	}

	public BengType getType() {
		return type;
	}

	public void setType(BengType type) {
		this.type = type;
	}
	public String[] getLocation() {
		return location;
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
	}

}
