package serverSocketHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;

import com.google.gson.Gson;

import entities.Retorno;
import entities.Usuario;
import exceptions.GeneralErrorException;
import service.UsuarioService; 

	public class Server extends Thread { 
		private static int connectionPort;
		protected Socket clientSocket;
		private UsuarioService usuarioService;
	
		public static void main(String[] args) throws IOException { 
	    ServerSocket serverSocket = null; 
	    connectionPort = 24001;
	    try { 
	    	serverSocket = new ServerSocket(connectionPort); 
	        System.out.println ("Connection Socket Created");
	        try { 
	        	while (true){
	            	System.out.println ("Waiting for Connection");
	            	new Server(serverSocket.accept()); 
	            }
	        } catch (IOException e) { 
	        	System.err.println("Accept failed."); 
	            System.exit(1); 
	        } 
	    } catch (IOException e) { 
	    	System.err.println("Could not listen on port: "
	    			+ connectionPort + "."); 
	    	System.exit(1); 
	    } finally {
	    	try {
	    		serverSocket.close(); 
	        } catch (IOException e) { 
	        	System.err.println("Could not close port: "
	        			+ connectionPort + "."); 
	            System.exit(1); 
	        } 
	    }
	}

	private Server (Socket clientSoc) {
		this.usuarioService = new UsuarioService();
		clientSocket = clientSoc;
		start();
	}
	
	public Server(int connectionPort) {
		this.connectionPort = connectionPort;
	}

 	public void run(){
 		System.out.println ("New Communication Thread Started");
 		try { 
 			PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), 
	                                      true); 
	        BufferedReader in = new BufferedReader( 
	        new InputStreamReader( clientSocket.getInputStream())); 
	
	        String inputLine; 
	
	        while ((inputLine = in.readLine()) != null){ 

		        Gson gson = new Gson();
			    Usuario user = null;
			    user = gson.fromJson(inputLine, Usuario.class);
			    String json = null;
			        	
			    System.out.println("\nClient sent: " + inputLine);
		        		        	
		    	if(user.getId_operacao() == 1) {
			    	System.out.println("==== Cadastrar ====");
			        		
			        try {
			        	Retorno retorno = usuarioService.cadastrar(user);
			        	json = gson.toJson(retorno);
			        	out.println(json);
			        } catch(SQLException | GeneralErrorException gee) {
	        			json = erro(gson, gee.getMessage());
	        			out.println(json);
			        } finally {
			        	System.out.println("Server sent: " + json);
			        }
		    	} else if(user.getId_operacao() == 3) {
		    		System.out.println("====== Logar ======");
		    		
		    		try {
			        	Retorno retorno = usuarioService.logar(user);
			        	json = gson.toJson(retorno);
			        	out.println(json);
			        } catch(SQLException | GeneralErrorException gee) {
	        			json = erro(gson, gee.getMessage());
	        			out.println(json);
			        } finally {
			        	System.out.println("Server sent: " + json);
			        }
	
		    	} else if(user.getId_operacao() == 9) {
		    		System.out.println("==== Deslogar ====");
		    		
		    		try {
			        	Retorno retorno = usuarioService.deslogar(user);
			        	json = gson.toJson(retorno);
			        	out.println(json);
			        } catch(SQLException | GeneralErrorException gee) {
	        			json = erro(gson, gee.getMessage());
	        			out.println(json);
			        } finally {
			        	System.out.println("Server sent: " + json);
			        }
		    	} else {
		    		json = erro(gson, "Erro ao solicitar operação.");
        			out.println(json);
		    		break;
		    	}
	        }
	        
	        
	        out.close(); 
	        in.close(); 
	        clientSocket.close(); 
	    } catch (IOException e) { 
	         System.err.println("Problem with Communication Server");
	         System.exit(1); 
	    } 
 	}
 	
 	public static String erro(Gson gson, String mensagem) {
		
		Retorno errorObject = new Retorno();
		errorObject.setCodigo(500);
		errorObject.setMensagem(mensagem);
		
		String json = gson.toJson(errorObject);
		System.out.println("Server sent: " + json);
		
		return json;
	}
} 