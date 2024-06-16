package studiplayer.audio;

public enum SortCriterion {
	DEFAULT,
	AUTHOR,
	TITLE,
	ALBUM,
	DURATION;
	
	@Override
	public String toString() {
	    // Convert enum name to lowercase and then capitalize the first letter
	    String name = name().toLowerCase();
	    return name.substring(0, 1).toUpperCase() + name.substring(1);
	}
}



