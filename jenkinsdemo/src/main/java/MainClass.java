
public class MainClass {
	
	public static boolean isPanagram(String string) {
		if(string == null || string.length() < 26) return false;
		
		int lettersTracker = 0;
		for(int i = 0; i < string.length(); i++) {
			char curr = string.charAt(i);
			if(curr >= 'a' && curr <= 'z') {
				lettersTracker |= (1 << (curr - 'a'));
			} else if(curr >= 'A' && curr <= 'Z') {
				lettersTracker |= (1 << (curr - 'A'));
			} else {
				return false;
			}
		}

		return(lettersTracker == 67108863) ? true : false;
	}
}