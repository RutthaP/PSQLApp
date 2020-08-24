import java.io.File;
import java.util.Scanner;

public class Helper {
	public static String[] readFile(String path) throws Exception {
		String[] result = new String[3];
		File file = new File(path);
		Scanner sc =  new Scanner(file);		
		
		int i = 0;
		while(sc.hasNextLine()) {
			result[i++] = sc.nextLine();
		}
		
		sc.close();
		return result;
	}
}
