package studiplayer.audio;
import java.io.File;

import studiplayer.basic.WavParamReader;

public class WavFile extends SampledFile {

		public WavFile() {

		}

		public WavFile(String path) throws NotPlayableException {
			super(path);
			readAndSetDurationFromFile();
			this.checkIfReadable(path);
		}

		public void readAndSetDurationFromFile() throws NotPlayableException {			
			try { 
				WavParamReader.readParams(getPathname());	
			} catch (Exception e) {
				throw new NotPlayableException(this.getPathname(), "idk something", e);
			}
			var frameRate = WavParamReader.getFrameRate();
			var numberOfFrames = WavParamReader.getNumberOfFrames();
			this.setDuration(WavFile.computeDuration((long)numberOfFrames, frameRate)) ;
			//this.checkIfReadable(getPathname());
		}

	
		public String toString() {
			return super.toString() + " - " + WavFile.timeFormatter(this.getDuration());
		}
		
		public static long computeDuration(long numberOfFrames, float frameRate) {
			var durationResult = (long) Math.floor((numberOfFrames/frameRate)*1000000);
			return durationResult;
		}
		



	}