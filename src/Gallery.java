import java.io.File;

public class Gallery {
	private File Music;
	private String Namelist;
	public  MyDoublyLinkedList Image;



	public Gallery(String namelist,MyDoublyLinkedList image,File music) {
		Namelist = namelist;
		Image = image;
		Music = music;
	}
	public String getName(){
		return Namelist;
	}
	public File getMusic() {
		return Music;
	}
	public void setMusic(File music) {
		this.Music = music;
	}


}
