import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.File;

public class PlayList {
private List<AudioFile> files;
private int current;
	
public PlayList() {
	files = new ArrayList<>();
	current = 0;
}



public PlayList(String m3uPathname) {
	this();
	loadFromM3U(m3uPathname);
}

public void add(AudioFile file) {
	files.add(file);
}

public void remove(AudioFile file) {
	files.remove(file);
}

public int size() {
	return files.size();
}

public AudioFile currentAudioFile() {
	if (current >=0 && current < files.size()) {
		return files.get(current);
	}
	return null;
}

public void nextSong() {
	current++;
	
	if (current >= files.size()) {
		this.current = 0;
	}
}

public void loadFromM3U(String pathname) {
	this.current = 0;
	if(!files.isEmpty()) {
		return;
	}
	File nf = new File(pathname);
	if(nf.canRead() == false) {
		throw new RuntimeException("wyjebalo sie");
	}
	try {		
		BufferedReader filereader = new BufferedReader(new FileReader(pathname));
		String line = null;
		try {
			line = filereader.readLine();
		} catch (IOException e) {
			//do nothing
		}
			
		
		while (line != null) {
			
			try {
			if (line.equals("")) {
				try {
					line = filereader.readLine();
				} catch (IOException e) {
					//do nothing
				}
				continue;
			}
			if (line.startsWith("#")) {
				try {
					line = filereader.readLine();
				} catch (IOException e) {
					//do nothing
				}
				continue;
			}
			var af = AudioFileFactory.createAudioFile(line);
			files.add(af);
			} catch (IllegalArgumentException e) {
				System.out.println("skipped file " + line + ", because it does not exist or has an unsupported format.");
			}
			try {
				line = filereader.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
			
		}
		
	
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	

}

public void saveAsM3U(String pathname) {
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

public int getCurrent() {
	return current;
}

public void setCurrent(int current) {
		this.current = current;


		
	}

}
