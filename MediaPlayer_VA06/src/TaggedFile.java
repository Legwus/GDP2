import java.util.Map;

import studiplayer.basic.TagReader;

public class TaggedFile extends SampledFile{

		private String album = "";
	
		public TaggedFile(){

		}

		public TaggedFile(String path) {
			this.parsePathname(path);
			
			this.parseFilename(getFilename());
			this.readAndStoreTags();
		}

		public String getAlbum() {
			
			return album;
		}
		

		public void readAndStoreTags() {
			
				Map<String, Object> tagMap = TagReader.readTags(this.getPathname());										
				var title = tagMap.get("title");
				if (title != null) {
					this.setTitle(title.toString().trim());
				}else {
					this.setTitle(getFilename().substring(0,getFilename().lastIndexOf(".")));
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
