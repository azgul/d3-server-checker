import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
	public static void main(String[] args) throws IOException, InterruptedException {		
		while(true) {
			checkServerRoutine();
			System.out.flush();
			Thread.sleep(1000);
		}
	}
	
	private static void checkServerRoutine() throws IOException {
		String currentIP = getCurrentIP();
		if (currentIP == null) {
			System.out.print("\rNot in game.                            ");
			return;	
		}

		if (getBadIPs().contains(currentIP))
			System.out.print(String.format("\rCurrent IP: %s -- BAD!!!        ", currentIP));
		else if (getGoodIPs().contains(currentIP))
			System.out.print(String.format("\rCurrent IP: %s -- GOOD!!!        ", currentIP));
		else
			System.out.print(String.format("\rCurrent IP: %s -- UNKNOWN!!!        ", currentIP));
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
		ArrayList<String> matches = new ArrayList<String>();

		while( (bytesRead = in.read(contents)) != -1){ 
			strContents = new String(contents, 0, bytesRead);

			// .. and "simulate" netstat -an |findstr :1119 
			if (strContents.contains(":1119")) {
				String[] arr = strContents.split("    ");

				// returns the D3 IP, excluding lobby IP
				for (int i=0; i<arr.length; i++)
					if (arr[i].contains("80.239.") && arr[i].contains(":1119") && !arr[i].contains(lobbyIP)) 
						return arr[i].split(":")[0]; //strip :1119 because it's useless
			}
		}

		return null;
	}
	
	private static ArrayList<String> bad = null;
	private static ArrayList<String> good = null;

	private static ArrayList<String> getBadIPs() {
		if (bad == null) {
			bad = new ArrayList<String>();
			
			// 80.239.209.x
			bad.add("80.239.209.16");
			bad.add("80.239.209.19");
			bad.add("80.239.209.21");
			bad.add("80.239.209.22");
			bad.add("80.239.209.23");
			bad.add("80.239.209.25");
			bad.add("80.239.209.26");
			bad.add("80.239.209.28");
			bad.add("80.239.209.32");
			bad.add("80.239.209.33");
			bad.add("80.239.209.34");
			bad.add("80.239.209.35");
			bad.add("80.239.209.36");
			bad.add("80.239.209.37");
			bad.add("80.239.209.38");
			bad.add("80.239.209.39");
			bad.add("80.239.209.41");
			bad.add("80.239.209.43");
			bad.add("80.239.209.44");
			bad.add("80.239.209.48");
			bad.add("80.239.209.49");
			bad.add("80.239.209.50");
			bad.add("80.239.209.51");
			bad.add("80.239.209.54");
			bad.add("80.239.209.55");
			bad.add("80.239.209.57");
			bad.add("80.239.209.59");
			bad.add("80.239.209.60");
			bad.add("80.239.209.61");
			bad.add("80.239.209.62");
			bad.add("80.239.209.63");
			bad.add("80.239.209.64");
			bad.add("80.239.209.65");
			bad.add("80.239.209.66");
			bad.add("80.239.209.67");
			bad.add("80.239.209.71");
			bad.add("80.239.209.72");
			bad.add("80.239.209.74");
			
			// 80.239.210.x
			bad.add("80.239.210.91");
			bad.add("80.239.210.93");
			bad.add("80.239.210.95");
			bad.add("80.239.210.97");
			bad.add("80.239.210.99");
			bad.add("80.239.210.100");
			bad.add("80.239.210.101");
			bad.add("80.239.210.102");
			bad.add("80.239.210.105");
			bad.add("80.239.210.109");
			bad.add("80.239.210.110");
			bad.add("80.239.210.111");
			bad.add("80.239.210.114");
			bad.add("80.239.210.115");
			bad.add("80.239.210.116");
			bad.add("80.239.210.117");
			bad.add("80.239.210.118");
			bad.add("80.239.210.119");
			bad.add("80.239.210.122");
			bad.add("80.239.210.123");
			bad.add("80.239.210.124");
			bad.add("80.239.210.126");
			bad.add("80.239.210.127");
			bad.add("80.239.210.128");
			bad.add("80.239.210.129");
			bad.add("80.239.210.130");
			bad.add("80.239.210.131");
			bad.add("80.239.210.135");
			bad.add("80.239.210.138");
			bad.add("80.239.210.139");
			bad.add("80.239.210.140");
			bad.add("80.239.210.142");
			bad.add("80.239.210.144");
			bad.add("80.239.210.145");
			bad.add("80.239.210.147");
			bad.add("80.239.210.148");
			bad.add("80.239.210.151");
			bad.add("80.239.210.152");
			bad.add("80.239.210.154");
			bad.add("80.239.210.155");
			bad.add("80.239.210.156");
			bad.add("80.239.210.158");
			bad.add("80.239.210.159");
			bad.add("80.239.210.161");
			bad.add("80.239.210.163");
			bad.add("80.239.210.164");
			bad.add("80.239.210.165");
			bad.add("80.239.210.167");
			bad.add("80.239.210.168");
			bad.add("80.239.210.169");
			bad.add("80.239.210.170");
			bad.add("80.239.210.171");
			bad.add("80.239.210.174");
			bad.add("80.239.210.175");
			bad.add("80.239.210.178");
			bad.add("80.239.210.179");
			bad.add("80.239.210.185");
			bad.add("80.239.210.186");
			bad.add("80.239.210.187");
			bad.add("80.239.210.189");
			bad.add("80.239.210.191");
			bad.add("80.239.210.192");
			bad.add("80.239.210.193");
			bad.add("80.239.210.194");
			bad.add("80.239.210.196");
			bad.add("80.239.210.197");
			bad.add("80.239.210.198");
			bad.add("80.239.210.199");
			bad.add("80.239.210.200");
			bad.add("80.239.210.202");
			bad.add("80.239.210.207");
			bad.add("80.239.210.208");
			bad.add("80.239.210.218");
			bad.add("80.239.210.219");
			bad.add("80.239.210.220");
			bad.add("80.239.210.221");
			bad.add("80.239.210.222");
			bad.add("80.239.210.223");
			bad.add("80.239.210.224");
			bad.add("80.239.210.225");
			bad.add("80.239.210.226");
			bad.add("80.239.210.227");
			bad.add("80.239.210.229");
			bad.add("80.239.210.230");
			bad.add("80.239.210.231");
			bad.add("80.239.210.232");
			bad.add("80.239.210.233");
			bad.add("80.239.210.243");
			bad.add("80.239.210.247");
			bad.add("80.239.210.251");
			bad.add("80.239.210.252");
			bad.add("80.239.210.254");
			
			// 80.239.211.x
			bad.add("80.239.211.8");
			bad.add("80.239.211.9");
			bad.add("80.239.211.11");
			bad.add("80.239.211.13");
			bad.add("80.239.211.14");
			bad.add("80.239.211.16");
			bad.add("80.239.211.17");
			bad.add("80.239.211.19");
			bad.add("80.239.211.22");
			bad.add("80.239.211.24");
			bad.add("80.239.211.25");
			bad.add("80.239.211.27");
			bad.add("80.239.211.28");
			bad.add("80.239.211.33");
			bad.add("80.239.211.36");
			bad.add("80.239.211.37");
			bad.add("80.239.211.39");
			bad.add("80.239.211.40");
			bad.add("80.239.211.43");
			bad.add("80.239.211.44");
			bad.add("80.239.211.45");
			bad.add("80.239.211.48");
			bad.add("80.239.211.52");
			bad.add("80.239.211.54");
			bad.add("80.239.211.56");
			bad.add("80.239.211.64");
			bad.add("80.239.211.65");
			bad.add("80.239.211.66");
			bad.add("80.239.211.69");
			bad.add("80.239.211.70");
			bad.add("80.239.211.71");
			bad.add("80.239.211.74");
			bad.add("80.239.211.79");
			bad.add("80.239.211.84");
			bad.add("80.239.211.102");
			bad.add("80.239.211.109");
			bad.add("80.239.211.124");
			bad.add("80.239.211.125");
			bad.add("80.239.211.128");
			bad.add("80.239.211.129");
		}
		
		return bad;
	}
	
	private static ArrayList<String> getGoodIPs() {

		if (good == null) {
			good = new ArrayList<String>();

			// 80.239.209.x
			good.add("80.239.209.12");
			good.add("80.239.209.13");
			good.add("80.239.209.18");
			good.add("80.239.209.31");
			good.add("80.239.209.52");
			good.add("80.239.209.69");
			
			// 80.239.210.x
			good.add("80.239.210.75");
			good.add("80.239.210.76");
			good.add("80.239.210.78");
			good.add("80.239.210.80");
			good.add("80.239.210.81");
			good.add("80.239.210.82");
			good.add("80.239.210.83");
			good.add("80.239.210.84");
			good.add("80.239.210.85");
			good.add("80.239.210.86");
			good.add("80.239.210.87");
			good.add("80.239.210.88");
			good.add("80.239.210.89");
			good.add("80.239.210.146");
			good.add("80.239.210.166");
			good.add("80.239.210.184");
			good.add("80.239.210.203");
			good.add("80.239.210.206");
			good.add("80.239.210.207");
			good.add("80.239.210.209");
			good.add("80.239.210.210");
			good.add("80.239.210.211");
			good.add("80.239.210.212");
			good.add("80.239.210.213");
			good.add("80.239.210.214");
			good.add("80.239.210.215");
			good.add("80.239.210.217");
			good.add("80.239.210.218");
			good.add("80.239.210.235");
			good.add("80.239.210.236");
			good.add("80.239.210.237");
			good.add("80.239.210.239");
			good.add("80.239.210.240");
			good.add("80.239.210.241");
			good.add("80.239.210.242");
			good.add("80.239.210.245");
			good.add("80.239.210.247");
			good.add("80.239.210.250");
			
			// 80.239.211.x
			good.add("80.239.211.1");
			good.add("80.239.211.4");
			good.add("80.239.211.6");
			good.add("80.239.211.7");
			good.add("80.239.211.18");
			good.add("80.239.211.35");
			good.add("80.239.211.51");
			good.add("80.239.211.55");
			good.add("80.239.211.56");
			good.add("80.239.211.57");
			good.add("80.239.211.58");
			good.add("80.239.211.59");
			good.add("80.239.211.61");
			good.add("80.239.211.77");
			good.add("80.239.211.78");
			good.add("80.239.211.80");
			good.add("80.239.211.82");
			good.add("80.239.211.83");
			good.add("80.239.211.86");
			good.add("80.239.211.88");
			good.add("80.239.211.100");
			good.add("80.239.211.101");
			good.add("80.239.211.102");
			good.add("80.239.211.103");
			good.add("80.239.211.104");
			good.add("80.239.211.106");
			good.add("80.239.211.107");
			good.add("80.239.211.108");
			good.add("80.239.211.110");
			good.add("80.239.211.113");
			good.add("80.239.211.116");
			good.add("80.239.211.117");
			good.add("80.239.211.118");
			good.add("80.239.211.119");
			good.add("80.239.211.120");
			good.add("80.239.211.121");
			good.add("80.239.211.122");
			good.add("80.239.211.127");
			good.add("80.239.211.132");
			good.add("80.239.211.133");
			good.add("80.239.211.134");
			good.add("80.239.211.135");
			good.add("80.239.211.136");
			good.add("80.239.211.137");
			good.add("80.239.211.138");
			good.add("80.239.211.139");
			good.add("80.239.211.141");
			good.add("80.239.211.142");
			good.add("80.239.211.146");
			good.add("80.239.211.211");
		}
		
		return good;
	}
}
