import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class ServerChecker {
	private static JFrame frame;
	private static JLabel label;
	private static HashMap<String, ServerType> servers = null;

	private enum ServerType {
		GOOD, BAD, UNKNOWN
	}

	public static void main(String[] args) throws IOException, InterruptedException {
		createAndShowGUI();
		
		try {
			updateIPs();
		} catch (FileNotFoundException e) {
			label.setText("Couldn't parse the IP files!");
		}
		while(true) {
			checkServerRoutine();
			System.out.flush();
			Thread.sleep(1000);
		}
	}

	private static void createAndShowGUI() {
		//Create and set up the window.
		frame = new JFrame("d3-server-checker");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		label = new JLabel("Loaded");
		frame.getContentPane().add(label);

		//Display the window.
		frame.setSize(260, 60);
		frame.setVisible(true);
	}
	
	private static void checkServerRoutine() throws IOException {
		String currentIP = getCurrentIP();
		if (currentIP == null) {
			label.setText("Not in game.");
			return;	
		}

		if (servers.containsKey(currentIP)) {
			ServerType type = servers.get(currentIP);
			if (type == ServerType.GOOD)
				label.setText(String.format("IP: %s -- GOOD! :-)", currentIP));
			else if (type == ServerType.BAD)
				label.setText(String.format("IP: %s -- BAD! :-(", currentIP));
		} else {
			label.setText(String.format("IP: %s -- UNKNOWN :-/", currentIP));
		}
	}
	
	private static String getCurrentIP() throws IOException {
		String lobbyIP = "80.239.208.193";

		// Run netstat -an 
		Runtime rt = Runtime.getRuntime();
		Process pr = rt.exec("netstat -an");
		BufferedInputStream in = new BufferedInputStream(pr.getInputStream());
		byte[] contents = new byte[1024];

		int bytesRead=0;
		String strContents = null;

		while( (bytesRead = in.read(contents)) != -1){ 
			strContents = new String(contents, 0, bytesRead);

			// .. and "simulate" netstat -an |findstr :1119 
			if (strContents.contains(":1119")) {
				String[] arr = strContents.split("    ");

				// returns the D3 IP, excluding lobby IP
				for (int i=0; i<arr.length; i++)
					if (arr[i].contains("80.239.") && arr[i].contains(":1119") && !arr[i].contains(lobbyIP))
						return arr[i].split(":")[0].replace(" ", ""); //strip :1119 because it's useless
			}
		}

		return null;
	}

	private static void updateIPs() throws FileNotFoundException {
		if (servers == null) {
			servers = new HashMap<String, ServerType>();
			
			parseFile(ServerType.GOOD);
			parseFile(ServerType.BAD);
		}
	}
	
	private static void parseFile(ServerType type) throws FileNotFoundException {
		File file = null;
		
		if (type == ServerType.GOOD)
			file = new File("good");
		else if (type == ServerType.BAD)
			file = new File("bad");
		else
			return;
		
		Scanner input = new Scanner(file);

		while(input != null && input.hasNext()) {
		    String line = input.nextLine();
		    
		    if (!line.startsWith("#"))
		    	servers.put(line, type);
		}

		input.close();
	}
}
