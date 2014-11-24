package appControl;

public class CSC301ListViewItem {

	private String itemString;
	private long itemID;
	
	public CSC301ListViewItem(long setID, String setString) {
		itemID = setID;
		itemString = setString;
	}
	
	public String toString() {
		return itemString;
	}
	
	public long getID() {
		return itemID;
	}
}
