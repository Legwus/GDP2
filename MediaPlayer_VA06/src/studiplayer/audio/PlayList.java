package studiplayer.audio;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.AccessControlException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.io.BufferedReader;
import java.io.File;

public class PlayList implements Iterable<AudioFile>{
private List<AudioFile> files;
private String search;
private SortCriterion sortCriterion;
private ControllablePlayListIterator iterator;
	
public PlayList() {
	files = new ArrayList<>();
	this.sortCriterion = SortCriterion.DEFAULT;
	this.iterator = new ControllablePlayListIterator(files, search, sortCriterion);
}



public PlayList(String m3uPathname){
	this();
	
		loadFromM3U(m3uPathname);

	//this.sortCriterion = sortCriterion;
}




public void add(AudioFile file) {
	files.add(file);
	resetIterator();
}

public String getSearch() {
	return this.search;
}

public void setSearch(String search) {
	this.search = search;
	sortPlayList();
	resetIterator();
}

public SortCriterion getSortCriterion() {
	return sortCriterion;
}

public void sortPlayList() {
	iterator.filterAndSort(this.files, this.search, this.sortCriterion);
}

public void setSortCriterion(SortCriterion sort) {
	this.sortCriterion = sort;
	sortPlayList();
	resetIterator();
	//currentAudioFile();
}

public void remove(AudioFile file) {
	files.remove(file);
	resetIterator();
}

public int size() {
	return files.size();
}

public AudioFile currentAudioFile() {
	if (iterator != null && iterator.hasNext() && !this.files.isEmpty()) {
		return iterator.getFiles().get(iterator.getCurrentIndex());
    }else if (iterator != null && !iterator.hasNext() && !this.files.isEmpty()) {
    	resetIterator();
    	return iterator.getFiles().get(iterator.getCurrentIndex());
    }
    
	return null;
}


public void nextSong() {
	if (iterator != null && iterator.hasNext()) {
        iterator.next();
    } else {
        
        resetIterator();
        
    }
}

private void resetIterator() {
    iterator = new ControllablePlayListIterator(files, search, sortCriterion);
}

public void loadFromM3U(String pathname){
//	this.current = 0;
	
	if(!files.isEmpty()) {
		return;
	}
	File nf = new File(pathname);
	if(!nf.exists()) {
		throw new RuntimeException("wyjebalo sie: " + pathname);
	}else {
		  try (BufferedReader reader = new BufferedReader(new FileReader(pathname))) {
              String line;
              while ((line = reader.readLine()) != null) {
            	  if (line.trim().isEmpty() || line.trim().startsWith("#")) {
                      continue;
                  }
                  try {
                      AudioFile newFile = AudioFileFactory.createAudioFile(line);
                      add(newFile);
                  } catch (NotPlayableException e) {
                      System.out.println("NotPlayableException beim Laden der M3U-Datei: " + e.getMessage());
                      e.printStackTrace();
	}
              }
              } catch (IOException | AccessControlException e) {
                  
            	  e.printStackTrace();
              }
	}
	 resetIterator();
}

public void jumpToAudioFile(AudioFile audioFile) {
    if (iterator != null) {
    	var fileList = iterator.getFiles();
    	if (fileList.contains(audioFile)) {
    		iterator.setCurrentIndex(fileList.indexOf(audioFile));
    	}
//        while (iterator.hasNext()) {
//            AudioFile next = iterator.next();
//            if (next.equals(audioFile)) {
//               
//                break;
//            }
       // }
    }
}

public void saveAsM3U(String pathname){
	try {
		FileWriter writer = new FileWriter(pathname);
		for(AudioFile element : this.getList()) {
			writer.write(element.getPathname() + System.lineSeparator());
		}
		
		writer.close();
		System.out.println("Variable saved to file successfully.");
	}
	catch (IOException e) {
		System.err.println("Error saving variable to file: " + e.getMessage());
	}
	
	
}



public List<AudioFile> getList(){
	return files;
}

//public int getCurrent() {
//	return current;
//}
//
//public void setCurrent(int current) {
//		this.current = current;
//
//
//		
//	}

public String toString() {
	String fileString = "[";
	for (int i = 0 ; i < this.files.size() ; i++) {
		fileString += this.files.get(i) + ", ";
	}
	fileString = fileString.substring(0, fileString.length() - 2) + "]";
	return fileString;
}



@Override
public Iterator<AudioFile> iterator() {
	return new ControllablePlayListIterator(files, search, sortCriterion);
	//return iterator;
}

}

