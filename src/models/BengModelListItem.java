package models;


import android.graphics.Bitmap;


public class BengModelListItem {
	
	private String Name;
	private String Deadline;
	private Bitmap Image;
	private  BengType Beng;
	
	
	public enum BengType{
		BENGING,
		WINNER;
	}
	
public BengModelListItem(String name,String deadline,Bitmap image, BengType bengstate){
	Name = name;
	Deadline  = deadline;
	Image  = image;
	Beng = bengstate;
	
}
	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getDaedline() {
		return Deadline;
	}

	public void setDeadline(String deadline) {
		Deadline = deadline;
	}

	public Bitmap getImage() {
		return Image;
	}

	public void setImage(Bitmap image) {
		this.Image = image;
	}

	public BengType getBeng() {
		return Beng;
	}

	public void setBeng(BengType beng) {
		this.Beng = beng;
	}

	


}
