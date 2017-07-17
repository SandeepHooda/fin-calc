package amfiindia;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TestNavParser {

	public static void main(String[] args) {
		File  file = new File("C:/Users/shaurya/Desktop/data.txt");
		List<String> data = new ArrayList<String>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String sCurrentLine = null;
			while ((sCurrentLine = reader.readLine()) != null) {
				data.add(sCurrentLine);
			}
			reader.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Map<String, CompanyVO> map = NavService.parseNav(data);
		System.out.println(map.get("Axis Mutual Fund").getNavs().get(0));
		System.out.println(map.get("Baroda Pioneer Mutual Fund").getNavs().get(1));
	}

}
