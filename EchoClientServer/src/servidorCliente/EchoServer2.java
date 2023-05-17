package servidorCliente;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
//import com.google.gson.Gson;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import entities.Usuario;
import exceptions.GeneralErrorException;

public class EchoServer2 extends Thread {
	
	protected Socket clientSocket;
	private static Integer error;
	private List<Usuario> listaUsuarioCadastrado;
	private List<Usuario> listaUsuarioLogado;
	private String erroGenerico = "Erro genérico.";
	
	
	public static void main(String[] args) throws IOException { 
    
		ServerSocket serverSocket = null; 
		int ConnectionPort = 24001;
		
		try { 
			
			InetAddress enderecoIP = InetAddress.getByName("0.0.0.0");
			serverSocket = new ServerSocket(ConnectionPort, 0, enderecoIP); 
			System.out.println ("Connection Socket Created");
				
			try { 
				while (true) {
					
					System.out.println ("Waiting for Connection");
					new EchoServer2 (serverSocket.accept());
					
				}
			} catch (IOException e) { 
					
				System.err.println("Accept failed."); 
				System.exit(1); 
			} 
		} catch (IOException e) {
			
			System.err.println("Could not listen on port: " + ConnectionPort + "."); 
			System.exit(1); 
		} finally {
			
			try {
				serverSocket.close(); 
            } catch (IOException e) { 
              
            	System.err.println("Could not close port: " + ConnectionPort + "."); 
            	System.exit(1); 
            } 
       }
	}

	private EchoServer2 (Socket clientSoc) {

		error = 500;
		listaUsuarioCadastrado = new ArrayList<Usuario>();
		listaUsuarioLogado = new ArrayList<Usuario>();
		
		clientSocket = clientSoc;
	    start();
	}

	public void run() {
		
		System.out.println ("New Communication Thread Started");
	
	    try {
	    	
	        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), 
	                                      true); 
	        BufferedReader in = new BufferedReader( 
	                 new InputStreamReader( clientSocket.getInputStream())); 
	
	        String inputLine; 
	
	        while ((inputLine = in.readLine()) != null) {

	        	Gson gson = new Gson();
	        	Usuario user = gson.fromJson(inputLine, Usuario.class);
	        	
	        	System.out.println("\nClient sent: " + inputLine);
	        		        	
	        	if(user.getId_operacao() == 1) {
	        		System.out.println("==== Cadastrar ====");
	        		
	        		try {
	        			
	        			Usuario usuario = new Usuario();
	        			listaUsuarioCadastrado = usuario.cadastrarServidor(out, user);
	        					        		
	        		} catch (GeneralErrorException gee) {
	        			
	        			System.out.println(gee.getMessage());
	        			String json = erro(gson, gee.getMessage());
	        			out.println(json);
	        		}
	        		
	        	} else if (user.getId_operacao() == 2){
	        		System.out.println("==== Atualizar Cadastro ====");
	        		System.out.println("Método ainda não implementado.");
	        		
	        		String json = erro(gson, erroGenerico);
        			out.println(json);
	        		
	        	} else if (user.getId_operacao() == 3){
	        		System.out.println("==== Logar no sistema ====");
	        		
	        		try {
	        			
	        			Usuario usuario = new Usuario();
	        			listaUsuarioLogado = usuario.logarServidor(out, user);
		        		
	        		} catch (GeneralErrorException gee) {
	        			
	        			System.out.println(gee.getMessage());
	        			String json = erro(gson, gee.getMessage());
	        			out.println(json);
	        		}
	        		
	        		
	        	} else if (user.getId_operacao() == 4){
	        		System.out.println("==== Reportar Incidente ====");
	        		System.out.println("Método ainda não implementado.");
	        		
	        		String json = erro(gson, erroGenerico);
        			out.println(json);
	        		
	        	} else if (user.getId_operacao() == 5){
	        		System.out.println("==== Solicitar lista de incidentes na rodovia ====");
	        		System.out.println("Método ainda não implementado.");
	        		
	        		String json = erro(gson, erroGenerico);
        			out.println(json);
	        		
	        	} else if (user.getId_operacao() == 6){
	        		System.out.println("==== Solicitar os incidentes reportados pelo usuário ====");
	        		System.out.println("Método ainda não implementado.");
	        		
	        		String json = erro(gson, erroGenerico);
        			out.println(json);
	        			        		
	        	} else if (user.getId_operacao() == 7){
	        		System.out.println("==== Remover incidente reportado pelo usuário ====");
	        		System.out.println("Método ainda não implementado.");
	        		
	        		String json = erro(gson, erroGenerico);
        			out.println(json);
	        		
	        	} else if (user.getId_operacao() == 8){
	        		System.out.println("==== Remover cadastro do usuário ====");
	        		System.out.println("Método ainda não implementado.");
	        		
	        		String json = erro(gson, erroGenerico);
        			out.println(json);
	        		
	        	} else if (user.getId_operacao() == 9){
	        		System.out.println("==== Pedir para sair do Sistema ====");
	        		
	        		try {
	        			Usuario usuario = new Usuario();
	        			listaUsuarioLogado = usuario.deslogarServidor(out, user);
	        		} catch(GeneralErrorException gee) {
	        			System.out.println(gee.getMessage());
	        			String json = erro(gson, gee.getMessage());
	        			out.println(json);
	        		}
	        		
	        	} else if (user.getId_operacao() == 10){
	        		System.out.println("==== Editar um incidente ====");
	        		System.out.println("Método ainda não implementado.");
	        		
	           		String json = erro(gson, erroGenerico);
        			out.println(json);
	        		
	        	} else {        		
	        		String json = erro(gson, erroGenerico);
        			out.println(json);
	        	}
	        	
	        	imprimeListaUsuariosCadastrados(listaUsuarioCadastrado);
	        	imprimeListaUsuariosLogados(listaUsuarioLogado);        	

	            if (inputLine.equals("Bye.")) 
	            	break; 
	        } 
	
	        out.close(); 
	        in.close(); 
	        clientSocket.close(); 
	    } catch (IOException e) { 
	        
	    	System.err.println("Problem with Communication Server");
	        System.exit(1); 
	        
	    } 
	}
	
	public static void imprimeListaUsuariosCadastrados(List<Usuario> listaUsuarioCadastrado) {
		if(!listaUsuarioCadastrado.isEmpty()) {
    		System.out.println("\nLista de Usuários Cadastrados");
        	int index = 1;
        	for (Usuario usuarioCadastrado : listaUsuarioCadastrado) {        		
        		
        		System.out.println("======== " + index + " =========");
        		System.out.println("Nome: " + usuarioCadastrado.getNome());
        		System.out.println("Email: " + usuarioCadastrado.getEmail());
        		index++;
        	}
    	}
	}
	
	public static void imprimeListaUsuariosLogados(List<Usuario> listaUsuarioLogado) {
		if(!listaUsuarioLogado.isEmpty()) {
			System.out.println("\nLista de Usuários Logados");
        	int index = 1;
        	for (Usuario usuarioLogado : listaUsuarioLogado) {        		
        		System.out.println("======== " + index + " =========");	        		
        		System.out.println("Email: " + usuarioLogado.getEmail());
        		System.out.println("Nome: " + usuarioLogado.getNome());
        		index++;
        	}
    	}
	}
	
	public static String erro(Gson gson, String mensagem) {
			
		Retorno errorObject = new Retorno();
		errorObject.setCodigo(error);
		errorObject.setMensagem(mensagem);
		
		String json = gson.toJson(errorObject);
		System.out.println("Server sent: " + json);
		
		return json;
	}
} 