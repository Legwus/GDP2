package studiplayer.audio;
import java.io.File;
import java.util.Map;

import studiplayer.basic.TagReader;

public class TaggedFile extends SampledFile{

		private String album = "";
	
		public TaggedFile(){

		}

		public TaggedFile(String path) throws NotPlayableException {
			this.parsePathname(path);
			
			this.parseFilename(getFilename());
			this.readAndStoreTags();
			this.checkIfReadable(path);
		}

		public String getAlbum() {
			
			return album;
		}
	

		public void readAndStoreTags() throws NotPlayableException {
				Map<String, Object> tagMap;
				try { tagMap = TagReader.readTags(this.getPathname());										
				} catch (Exception e) {
					throw new NotPlayableException(this.getPathname(), "Unable to read tags", e);
				}
				var title = tagMap.get("title");
				if (title != null) {
					this.setTitle(title.toString().trim());
				}else {
					this.setTitle(getTitle());
				}
				var author = tagMap.get("author");
				if (author != null) {
					this.setAuthor(author.toString().trim());
				}
				//String durationString;
				var durationString = tagMap.get("duration");
				
				if (durationString != null) {
					var duration = Integer.parseInt(durationString.toString());	
					this.setDuration(duration);
				}
				var album = tagMap.get("album");
				if (album != null) {
					this.album = album.toString().trim();
				}
				
		}

		public String toString() {
			String taggedFileToString = "";
			if(this.getAuthor() != "") {
				taggedFileToString = this.getAuthor() + " - ";
			}
			
			taggedFileToString += this.getTitle();
			
			if(this.getAlbum() != "") {
				taggedFileToString += " - " + getAlbum();
			}
			
			taggedFileToString += " - " + TaggedFile.timeFormatter(this.getDuration());
			
			return taggedFileToString;
		}


		
		
	}
