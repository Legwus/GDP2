
public class AudioFileFactory {
	public static AudioFile createAudioFile(String pathname) {
		String extension = getExtension(pathname);
			
		
		
		switch(extension.toLowerCase()){
			
				
			case "wav":
				return new WavFile(pathname);
			case "mp3":
			case "ogg":
				return new TaggedFile(pathname);
			
			default:
				throw new IllegalArgumentException("Unknown suffix for AudioFile \"" + pathname + "\"");
		}

	}
	
		private static String getExtension(String pathname) {
			int indexOfExtensionDot = pathname.lastIndexOf('.');
			if (indexOfExtensionDot == -1) {
				return "";
			}
			return pathname.substring(indexOfExtensionDot + 1);
		}


}