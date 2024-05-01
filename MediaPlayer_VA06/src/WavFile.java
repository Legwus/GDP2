import studiplayer.basic.WavParamReader;

public class WavFile extends SampledFile {

		public WavFile() {

		}

		public WavFile(String path) {
			super(path);
			readAndSetDurationFromFile();
		}

		public void readAndSetDurationFromFile() {			
			WavParamReader.readParams(getPathname());			
			var frameRate = WavParamReader.getFrameRate();
			var numberOfFrames = WavParamReader.getNumberOfFrames();
			this.setDuration(WavFile.computeDuration((long)numberOfFrames, frameRate)) ;
			
		}

	
		public String toString() {
			return super.toString() + " - " + WavFile.timeFormatter(this.getDuration());
		}
		
		public static long computeDuration(long numberOfFrames, float frameRate) {
			var durationResult = (long) Math.floor((numberOfFrames/frameRate)*1000000);
			return durationResult;
		}
		



	}