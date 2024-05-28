
public class ParseToTime {
	java.util.Scanner scanner = new java.util.Scanner(System.in);
	//int x = 0;
	public int scanNumber() {
		int x;
		x = scanner.nextInt();		
		if(x < 0 || x > 10000000 ) {
			throw new RuntimeException("Please Input an actual number bigger than 1 and smaller than 10 million without commas!");
		}
		
		return x;
	}
	public String parseSecondsToDaysHoursMinutesSecondsFormat(int seconds) {
		int daysPart = seconds/86400;
		int daysRest = seconds%86400;
		int hoursPart = daysRest/3600;
		int hoursRest = daysRest%3600;
		int minutesPart = hoursRest/60;
		int minutesRest = hoursRest%60;
		int secondsPart = minutesRest;
		String formattedTimeRemaining = String.format("%d Days, %d Hours, %d Minutes and %d Seconds.", daysPart, hoursPart, minutesPart, secondsPart);
		return formattedTimeRemaining;
		
		
	}
	
	public static void main(String[] args) {
		ParseToTime ptt = new ParseToTime();
		System.out.println("Please input a valid number from 1 to 10 Million:");
		//ptt.scanNumber();
		System.out.println(ptt.parseSecondsToDaysHoursMinutesSecondsFormat(ptt.scanNumber()));
	}
}
