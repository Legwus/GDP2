package studiplayer.audio;

import java.util.Comparator;
import java.util.List;

public class AuthorComparator implements Comparator<AudioFile>{
	
	public AuthorComparator() {
		
	}
	
	public int compare(AudioFile o1, AudioFile o2) {
		try {
		return o1.getAuthor().compareTo(o2.getAuthor());
	} catch (Exception e) {
		throw new RuntimeException("The parameters contain null values");
	}
	}
	
}
