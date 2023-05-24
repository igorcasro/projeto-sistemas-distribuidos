package clientSocketHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import com.google.gson.Gson;

import entities.Usuario;
import gui.client.ClientUnlogged;

public class Cliente {
	
	private static String ip;
	private static int ConnectionPort;
	private static String json;
	
	public static String getIp() {
		return ip;
	}

	public static int getConnectionPort() {
		return ConnectionPort;
	}

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}

	public Cliente(String ip, int ConnectionPort) {
		
		this.ip = ip;
		this.ConnectionPort = ConnectionPort;
	}
	
    public static void main(String[] args) throws IOException {

//    	ip = "127.0.0.1";
//    	ConnectionPort = 24001;
//        if (args.length > 0)
//           serverHostname = args[0];
    	String serverHostname = new String(ip);
        System.out.println ("Attemping to connect to host " +
                serverHostname + " on port " + ConnectionPort + ".");

        Socket echoSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;

        try {
            echoSocket = new Socket(serverHostname, ConnectionPort);
            out = new PrintWriter(echoSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(
                                        echoSocket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: " + serverHostname);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for "
                               + "the connection to: " + serverHostname);
            System.exit(1);
        }

		BufferedReader stdIn = new BufferedReader( new InputStreamReader(System.in));
		String userInput;
		
		while ((userInput = in.readLine()) != null) {
			System.out.println("Client sent: " + userInput);
			out.println(userInput);
			System.out.println("Server sent: " + in.readLine());
			
			
	        // end loop
	        if (userInput.equals(null))
	        	break;
	
		    
		}
	
		out.close();
		in.close();
		stdIn.close();
		echoSocket.close();
    }
    
    public static Usuario SendJson(String json) {
    	System.out.println(json);
    	
    	Gson gson = new Gson();
    	Usuario usuario = gson.fromJson(json, Usuario.class);
    	
    	return usuario;
    }
}
