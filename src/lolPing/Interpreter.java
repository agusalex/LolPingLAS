package lolPing;

import java.io.*;

public class Interpreter extends Thread{
	String ip="";
	String lastIpMatch="default";

	boolean interrupted=false;
	int ping=0;
	boolean packetloss=false;

	public void run(){
		while(!interrupted) {
				ping = findLine();
				Lol.updateStatus(ping, packetloss);
		}
		try {
			this.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}


	public Interpreter(String ip){
			this.ip=ip;

		}

	    public int findLine(){

			int hops=10;
			String[] result= new String[hops+5];
			String command = "tracert -h "+hops+" " + ip;
			int x=0;
	        try {
	            Runtime r = Runtime.getRuntime();
	            Process p = r.exec(command);
	            BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
	            String inputLine;
	            
	            while ((inputLine = in.readLine()) != null) {
					if(interrupted){
						return 3;
					}
	                if (x>=3){
	                	result[x-3]=inputLine;

	                	if (x < hops + 3){
	                		if(ipEarlyMatch(inputLine)){
	                			return pingFinder(inputLine);}}}
	                	x++; } 
	            	in.close();}
	        
	        catch (IOException e) {
	            System.out.println(e);
	            return 3;}
	        
	        
	    	return interpret(result);}

	   public boolean ipEarlyMatch(String linea1){
		   String laip="216.241.0.23";//216.241.0.23
		   String laotraip="host51.181.119.0.ifxnw.com.ar"; //host59.181.119.0.ifxnw.com.ar
	
		   linea1=linea1.substring(32);
		   int corte=linea1.indexOf(' ');
		   linea1=linea1.substring(0,corte);
		   
		   if (linea1.equals(lastIpMatch)||linea1.equals("10.133.246.34")){
			   return true;}
		   return false;}
	    
	   public int pingFinder(String linea){
		   lastIpMatch=linea;
		   System.out.print(linea);
		   String Sping1,Sping2,Sping3;
		   int Ping1,Ping2,Ping3,Pingf;
		   int promedio=3;
		   int offset=10;
		   
		   Sping1=linea.substring(6,9);
		   Sping2=linea.substring(15,18);
		   Sping3=linea.substring(24,27);

		   if (Sping1.indexOf(' ')>=0){Sping1=Sping1.substring(1,3);}
		   if (Sping2.indexOf(' ')>=0){Sping2=Sping2.substring(1,3);}
		   if (Sping3.indexOf(' ')>=0){Sping3=Sping3.substring(1,3);}
		   if (Sping1.indexOf('*')>=0){Sping1="0";promedio-=1;packetloss=true;}
		   if (Sping2.indexOf('*')>=0){Sping2="0";promedio-=1;packetloss=true;}
		   if (Sping3.indexOf('*')>=0){Sping3="0";if (promedio==1){promedio=1;}else{promedio-=1;}packetloss=true;}
		   
		   Ping1=Integer.parseInt(Sping1);
		   Ping2=Integer.parseInt(Sping2);
		   Ping3=Integer.parseInt(Sping3);
		   Pingf=(Ping1+Ping2+Ping3)/promedio;
		   return Pingf+offset;}

		public static boolean tracertIsFinished(String linea){
			return linea.equals("Trace complete.")||linea.equals("Traza completa.");
		}
	   
	   public int interpret(String[] commandlineResult){
		   packetloss=false;
		   int ping=0;
		   int cont=0;

		   for (int y=0; y<commandlineResult.length;y++){
			   String linea=commandlineResult[y];
			   if (linea!=null){
				   if (tracertIsFinished(linea)){
					  ping=pingFinder(commandlineResult[y-cont-2]);
					  
					  return ping;}}

			   if (linea != null && linea.length() > 31) {
				   if (ipEarlyMatch(linea)) {
					   ping = pingFinder(linea);

					   return ping;
				   }


				   if ((linea.charAt(32) == 'R') || (linea.charAt(32) == 'T')) {
					   cont = cont + 1;

					   if (cont >= 3) {

						   ping = pingFinder(commandlineResult[y - cont]);
						   return ping;
					   }
				   } else {
					   cont = 0;
				   }
			   }
		   }
		    
		             return 999;}





	public int getPing() {
		return ping;
	}

	@Override
	public boolean isInterrupted() {

		return interrupted;
	}

	public void setInterrupted(boolean interrupted) {
		this.interrupted = interrupted;
	}

	public boolean isPacketloss() {
		return packetloss;
	}
	public  boolean getpacketloss() {
		return packetloss;
	}
	public String getIp() {
		return ip;
	}


}




	    



