package servidorCliente;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import com.google.gson.Gson;

import entities.Usuario;

public class EchoClient2 {
	
	//Alterar o funcionamento das váriaveis abaixo
	@SuppressWarnings("unused")
	private static boolean isLogged = false;
	private static String tokenReceived = null;
	private static Integer idReceived = null;
	
    public static void main(String[] args) throws IOException {

    	String ip = ("127.0.0.1"); //Meu
//    	String ip = "10.20.8.179"; //Ruivo
//    	String ip = "10.20.8.76"; //Sanches
//    	String ip = "10.20.8.79"; //Danilo
//    	String ip = "10.20.8.77"; //Mairon
//    	String ip = "10.40.11.114"; // Du
//    	String ip = "10.20.8.81"; //Kenji
    	
        String serverHostname = new String (ip);
        int ConnectionPort = 24001;

        if (args.length > 0)
           serverHostname = args[0];
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

		BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
		String userInput;
	
	    System.out.println ("Type Message (\"Bye.\" to quit)");
	    
	    System.out.println("===================================================");
	    System.out.println("1 - Cadastrar");
	    System.out.println("2 - Atualizar cadastro");
	    System.out.println("3 - Logar no sistema");
	    System.out.println("4 - Reportar Incidente");
	    System.out.println("5 - Solicitar lista de incidentes na rodovia");
	    System.out.println("6 - Solicitar os incidentes reportados pelo usuário");
	    System.out.println("7 - Remover incidente reportado pelo usuário");
	    System.out.println("8 - Remover cadastro do usuário");
	    System.out.println("9 - Pedir para sair do Sistema");
	    System.out.println("10 - Editar um incidente");
	    System.out.println("===================================================");
	    
		while ((userInput = stdIn.readLine()) != null) {
			int id_operacao = -1;
			Usuario usuario = null;
			
			Gson gson = new Gson();	
			String json = null;
			Retorno retornoJson = null;
			
			switch(userInput) {
				case "1": // Cadastrar
					System.out.println("==== Cadastrar ====");
				
					usuario = new Usuario();
					usuario.cadastrarCliente(stdIn);

					json = gson.toJson(usuario);	
					
					System.out.println("Client sent: " + json);
					out.println(json);
					
	        		break;
				case "2": // Atualizar cadastro
					id_operacao = 2;
					
					System.out.println("==== Atualizar Cadastro ====");
					
					
					break;
				case "3": // Logar no sistema
	        		System.out.println("==== Logar no sistema ====");
	        		
	        		usuario = new Usuario();
	        		usuario.logarCliente(stdIn);

					json = gson.toJson(usuario);	
					
					System.out.println("Client sent: " + json);
					out.println(json);
		        	
					break;
				case "4": // Reportar incidente
					id_operacao = 4;
					
	        		System.out.println("==== Reportar Incidente ====");
					
	        		out.println(id_operacao);
					break;
				case "5": // Solicitar lista de incidentes
					id_operacao = 5;

	        		System.out.println("==== Solicitar lista de incidentes na rodovia ====");
					
	        		out.println(id_operacao);
					break;
				case "6": // Solicitar os incidentes reportados pelo usuário
					id_operacao = 6;

	        		System.out.println("==== Solicitar os incidentes reportados pelo usuário ====");
					
	        		out.println(id_operacao);
					break;
				case "7": // Remover incidente reportado pelo usuário
					id_operacao = 7;

	        		System.out.println("==== Remover incidente reportado pelo usuário ====");
					
					out.println(id_operacao);
					break;
				case "8": // Remover cadastro do usuário
					id_operacao = 8;

	        		System.out.println("==== Remover cadastro do usuário ====");
					
					out.println(id_operacao);
					break;
				case "9": // Pedir para sair do sistema
					
					if(tokenReceived == null)
						tokenReceived = "";
					if(idReceived == null)
						idReceived = -1;
					
	        		System.out.println("==== Pedir para sair do Sistema ====");
					
	        		usuario = new Usuario();
	        		usuario.deslogarCliente(tokenReceived, idReceived);
	        		
					json = gson.toJson(usuario);
					
					System.out.println("Client sent: " + json);
					out.println(json);
					
					break;
				case "10": // Editar um incidente
					id_operacao = 10;

	        		System.out.println("==== Editar um incidente ====");
	        		
					out.println(id_operacao);
					break;
	        	default: 
	        		if(userInput.equals("Bye.")) {
	        			break;
	        		}
	        		System.out.println("saindo");
	        		out.println(userInput);
			}
			
			String status = in.readLine();			
			System.out.println("Server sent: " + status);			
			
			if(userInput.equals("3")) {
				
				retornoJson = new Retorno();
				retornoJson = gson.fromJson(status, Retorno.class);		
	        	if(retornoJson.getCodigo().equals(200)) { 		       		
	        		isLogged = true;
	        		tokenReceived = retornoJson.getToken();
	        		idReceived = retornoJson.getId_usuario(); 
	        		
	    		} else if(retornoJson.getCodigo().equals(500)){
	    			retornoJson = null;
	    		}
			} else if(userInput.equals("9")) {
				retornoJson = new Retorno();
				retornoJson = gson.fromJson(status, Retorno.class);
	        	if(retornoJson.getCodigo().equals(200)) {			         		
	        		isLogged = false;
	        		tokenReceived = null;
	        		idReceived = null;       
	        		
	        	} else if(retornoJson.getCodigo().equals(500)){
	    			retornoJson = null;
	    		}
			}
			
			// end loop
			if (userInput.equals("Bye.")) 
				break;

		}
	
		out.close();
		in.close();
		stdIn.close();
		echoSocket.close();
    }
    
}