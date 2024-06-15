package studiplayer.audio;

public class AudioFileFactory {
	public static AudioFile createAudioFile(String pathname) throws NotPlayableException {
		String extension = getExtension(pathname);
			
		
		
		switch(extension.toLowerCase()){
			
				
			case "wav":
				return new WavFile(pathname);
			case "mp3":
			case "ogg":
				return new TaggedFile(pathname);
			
			default:
				throw new NotPlayableException("Unknown suffix for AudioFile \"" + pathname + "\"", extension);
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