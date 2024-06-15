package studiplayer.audio;

import java.util.Comparator;

public class DurationComparator implements Comparator<AudioFile>{

	

	@Override
	public int compare(AudioFile o1, AudioFile o2) {
		boolean doesFile1HaveADuration = o1 instanceof SampledFile;
        boolean doesFile2HaveADuration = o2 instanceof SampledFile;

        try{if (doesFile1HaveADuration && doesFile2HaveADuration) {
            long duration1 = ((SampledFile) o1).getDuration();
            
            long duration2 = ((SampledFile) o2).getDuration();

            
            
            if (duration1 != -1 && duration2 != -1) {
                return Long.compare(duration1, duration2);
            }

            
//            if (duration1 != -1) return 1;
//            if (duration2 != -1) return -1;
            
 
            
            return 0;
        } else if (doesFile1HaveADuration) {
            return 1; 
        } else if (doesFile2HaveADuration) {
            return -1; 
        } else {
            return 0; 
        }
        } catch (Exception e) {
        	throw new RuntimeException("The parameters contain null values");
        }
}
}
//	try {if (o1 instanceof SampledFile && o2 instanceof SampledFile) {
//        return Float.compare(((SampledFile) o1).getDuration(), ((SampledFile) o2).getDuration());
//    } else {
//        return 0;
//    }
//	} catch (Exception e) {
//		throw new RuntimeException("The parameters contain null values");
//	}
//}
