package studiplayer.audio;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class ControllablePlayListIterator implements Iterator<AudioFile>{
	private List<AudioFile> files;
	private int currentIndex;
	private String search;
	
	public ControllablePlayListIterator(List<AudioFile> files) {
		this.files = files;
		this.currentIndex = 0;
	}

	public ControllablePlayListIterator(List<AudioFile> files, String search, SortCriterion sortCriterion) {
		this.currentIndex = 0;
		this.files = filterAndSort(files, search, sortCriterion);
	}
	
	public List<AudioFile> getFiles(){
		return this.files;
	}
	
	public List<AudioFile> filterAndSort(List<AudioFile> playList, String search, SortCriterion sortCriterion) {
        List<AudioFile> filteredList = new ArrayList<>();
        
       
        if (search == null || search.isEmpty()) {
            filteredList.addAll(playList);
        } else {
            for (AudioFile file : playList) {
                if (containsSearchStringForTagged(file, search) || containsSearchStringForWav(file, search)) {
                    filteredList.add(file);
                }
            }
        }
        
        

       
        if (sortCriterion != SortCriterion.DEFAULT) {
            Comparator<AudioFile> comparator = getComparator(sortCriterion);
            filteredList.sort(comparator);
        }

        return filteredList;
    }
	
	public int getCurrentIndex() {
		return currentIndex;
	}
	
	public void setCurrentIndex(int index) {
		this.currentIndex = index;
	}
	
	private boolean containsSearchStringForTagged(AudioFile file, String search) {
        String author = (file instanceof TaggedFile) ? ((TaggedFile) file).getAuthor() : "";
        String title = file.getTitle();
        String album = (file instanceof TaggedFile) ? ((TaggedFile) file).getAlbum() : "";
        return author.contains(search) || title.contains(search) || album.contains(search);
    }
	
	private boolean containsSearchStringForWav(AudioFile file, String search) {
        String author = (file instanceof WavFile) ? ((WavFile) file).getAuthor() : "";
        String title = file.getTitle();
        //String album = (file instanceof WavFile) ? ((WavFile) file).getAlbum() : "";
        return author.contains(search) || title.contains(search);
    }
	
	public AudioFile jumpToAudioFile(AudioFile file) {
		int fileIndex = files.indexOf(file);
		if (fileIndex != -1 && fileIndex < files.size()-1) {
			currentIndex = fileIndex + 1;
			return file;
		
		}
		else if (fileIndex == files.size() - 1) {
			currentIndex = files.size();
			return file;
		}
		return null;
	}
	
	@Override
	public boolean hasNext() {
		return currentIndex < files.size();
	}
	
	private Comparator<AudioFile> getComparator(SortCriterion criterion) {
        switch (criterion) {
            case TITLE:
                return new TitleComparator();
            case ALBUM:
                return new AlbumComparator();
            case DURATION:
                return new DurationComparator();
            case AUTHOR:
                return new AuthorComparator();
            default:
                return (file1, file2) -> 0;
        }
    }

	@Override
	public AudioFile next() {
		if (!hasNext()) {
			throw new NoSuchElementException("No more elements in the iterator");
		}
		return files.get(currentIndex++);
	}
	
}
