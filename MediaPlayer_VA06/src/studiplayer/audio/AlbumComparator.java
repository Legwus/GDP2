package studiplayer.audio;

import java.util.Comparator;

public class AlbumComparator implements Comparator<AudioFile>{

	@Override
	public int compare(AudioFile o1, AudioFile o2) {
		try {if (o1 instanceof TaggedFile && o2 instanceof TaggedFile) {
            return ((TaggedFile) o1).getAlbum().compareTo(((TaggedFile) o2).getAlbum());
        } else if (o1 instanceof TaggedFile) {
            return 1; 
        } else if (o2 instanceof TaggedFile) {
            return -1; 
        } else {
            return 0; 
        }
		} catch (Exception e) {
			throw new RuntimeException("The parameters contain null values");
		}
    }
	}


