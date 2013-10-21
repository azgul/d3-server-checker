import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class ServerChecker {
	private static ServerChecker serverChecker;
	private static JFrame frame;
	private static JLabel label;
	private static HashMap<String, ServerType> servers = null;
	private static String OS = System.getProperty("os.name").toLowerCase();
	
	public ServerChecker() {}

	public static void main(String[] args) {
		serverChecker = new ServerChecker();
		
		createAndShowGUI();
		try {
			updateIPs();
		} catch (ParseException e) {
			return;
		}
		
		while(true) {
			try {
				checkServerRoutine();
			} catch (IOException e) {
				label.setText("Failed to check server status");
				e.printStackTrace();
			}
			System.out.flush();
			sleep(1000);
		}
	}

	private static void createAndShowGUI() {
		//Create and set up the window.
		frame = new JFrame("d3-server-checker");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setAlwaysOnTop(true);
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
				label.setText(String.format("IP: %s - GOOD! :-)", currentIP));
			else if (type == ServerType.BAD)
				label.setText(String.format("IP: %s - BAD! :-(", currentIP));
		} else {
			label.setText(String.format("IP: %s - UNKNOWN :-/", currentIP));
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
				String[] arr = strContents.split("  ");

				// return the D3 IP, excluding lobby IP
				for (int i=0; i<arr.length; i++)
					if (arr[i].contains("80.239.") && arr[i].contains(":1119") && !arr[i].contains(lobbyIP))
						return arr[i].split(":")[0].replace(" ", ""); //strip :1119 because it's useless
			} else if (isMac()) {
				// nasty as hell, I know ....
				String[] arr = strContents.split("  ");
				
				for (int i=0; i<arr.length; i++)
					if (arr[i].contains("80.239.") && !arr[i].contains(lobbyIP))
						return arr[i].replace(" ", "").replace(".1119", ""); //strip :1119 because it's useless
			}
		}

		return null;
	}

	private static void updateIPs() throws ParseException {
		if (servers == null) {
			servers = new HashMap<String, ServerType>();
			
			try {
				downloadIPs(ServerType.GOOD);
				downloadIPs(ServerType.BAD);
			} catch (DownloadException e) {
				e.printStackTrace();
				label.setText(e.getMessage());
				sleep(5000);
			}
			
			try {
				parseFile(ServerType.GOOD);
				parseFile(ServerType.BAD);
			} catch (ParseException e) {
				e.printStackTrace();
				label.setText(e.getMessage());
				throw e;
			}
		}
	}
	
	private static void parseFile(ServerType type) throws ParseException {
		File file = null;
		String filename  = "";
		
		if (type == ServerType.GOOD)
			filename = "good";
		else if (type == ServerType.BAD)
			filename = "bad";
		else
			return;
		

		file = new File(filename);
		label.setText(String.format("Parsing %s IPs", filename));
		
		sleep(1000);
		
		Scanner input;
		try {
			input = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw serverChecker.new ParseException("File did not exist: " + filename);
		}

		while(input != null && input.hasNext()) {
		    String line = input.nextLine();
		    
		    if (!line.startsWith("#"))
		    	servers.put(line, type);
		}

		if (input != null)
			input.close();
	}
	
	private static void downloadIPs(ServerType type) throws DownloadException {
		if (!(type == ServerType.GOOD || type == ServerType.BAD))
			return; // don't do anything if called with ServerType.UNKNOWN
		
		String filename = "";
		String url = "";
		
		if (type == ServerType.GOOD) 
			filename = "good";
		else if (type == ServerType.BAD)
			filename = "bad";
		
		url = "https://raw.github.com/azgul/d3-server-checker/master/" + filename;
		label.setText(String.format("Downloading %s IPs", filename));
		sleep(1000);
		
		BufferedInputStream in = null;
		FileOutputStream fout = null;
		try	{
			in = new BufferedInputStream(new URL(url).openStream());
			fout = new FileOutputStream(filename);
			
			byte data[] = new byte[1024];
			int count;
			while ((count = in.read(data, 0, 1024)) != -1) {
				fout.write(data, 0, count);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw serverChecker.new DownloadException("File not found: "+ filename);
		} catch (MalformedURLException e) {
			e.printStackTrace();
			throw serverChecker.new DownloadException("Could not download: "+ url);
		} catch (IOException e) {
			e.printStackTrace();
			throw serverChecker.new DownloadException("IOException when trying to download: "+ url);
		}
		
		finally	{
			try {
				if (in != null)
					in.close();
				if (fout != null)
					fout.close();
			} catch (Exception e) {
				throw serverChecker.new DownloadException("Failed to finalize download of: " + filename);
			}
		}
	}
	
	private class DownloadException extends Exception {
		DownloadException(String s) {
			super(s);
		}
	}
	
	private class ParseException extends Exception {
		ParseException(String s) {
			super(s);
		}
	}
	
	private static void sleep(long l) {
		try {
			Thread.sleep(l);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private static boolean isWindows() {
		return (OS.indexOf("win") >= 0);
 
	}
 
	private static boolean isMac() {
		return (OS.indexOf("mac") >= 0);
 
	}
 

	private enum ServerType {
		GOOD, BAD, UNKNOWN
	}
}
