package studiplayer.audio;
import java.io.File;

import studiplayer.basic.BasicPlayer;
import studiplayer.basic.WavParamReader;

public abstract class SampledFile extends AudioFile {
		private long duration = 0;


		private static long durationResult = 1;
		public SampledFile() {

		}

		public SampledFile(String path) throws NotPlayableException{			
			super(path);
			this.parsePathname(path);
			this.checkIfReadable(path);
		}
 
		
		public void play() throws NotPlayableException {			
			try {
				BasicPlayer.play(getPathname());
			} catch (Exception e) {
				throw new NotPlayableException(this.getPathname(), "file could not be played", e);
			}
			
		}

		public void togglePause() {
			BasicPlayer.togglePause();
		}

		
		public void stop() {
			BasicPlayer.stop();
		}

		
		public String formatDuration() {
			
			return WavFile.timeFormatter(getDuration());
		}
		
		public String formatPosition() {
			return WavFile.timeFormatter(BasicPlayer.getPosition());
		}

		public static String timeFormatter(long timeInMicroSeconds) {		
			if (timeInMicroSeconds < 0 || timeInMicroSeconds >= 6000000000L) {
				throw new RuntimeException("wyjebalo sie");
			}
			long durationInSeconds = timeInMicroSeconds/1000000;
			long minutesPart = durationInSeconds/60;
			long secondsPart = durationInSeconds%60;
			return String.format("%02d:%02d", minutesPart,secondsPart);
		}

		public long getDuration() {
			return duration;
		}
		
		public void setDuration(long duration) {
			this.duration = duration;
		}
		
	}
