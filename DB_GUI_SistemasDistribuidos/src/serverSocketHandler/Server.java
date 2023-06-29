package serverSocketHandler;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.concurrent.CountDownLatch;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import com.google.gson.Gson;

import conectorPane.ServerPane;
import entities.Incidente;
import entities.Retorno;
import entities.Usuario;
import exceptions.GeneralErrorException;
import listener.UsuarioListener;
import service.IncidenteService;
import service.UsuarioService; 

	public class Server extends Thread { 
		private static int connectionPort;
		protected Socket clientSocket;
		
		private UsuarioService usuarioService;
		private IncidenteService incidenteService;
		private UsuarioListener usuarioListener;
	
		public static void main(String[] args) throws IOException {
	        CountDownLatch latch = new CountDownLatch(1); // Criando o latch com uma contagem de 1

	        ServerPane serverPane = new ServerPane();
	        serverPane.addWindowListener(new WindowAdapter() {
	            @Override
	            public void windowClosed(WindowEvent e) {
	                latch.countDown(); // Indica que a janela foi fechada e libera a contagem
	            }
	        });

	        SwingUtilities.invokeLater(() -> {
	            serverPane.setVisible(true);
	        });

	        try {
	        	latch.await();

//	            SwingUtilities.invokeLater(() -> {
//	                exibirListaUsuariosLogados();
//	            });
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }

	        String valor = serverPane.getValor();
	        connectionPort = Integer.parseInt(valor);
	        startServer(connectionPort);
	    }

	    private static void startServer(int connectionPort) {
	        ServerSocket serverSocket = null;
	        try {
	            InetAddress enderecoIp = InetAddress.getByName("0.0.0.0");
	            serverSocket = new ServerSocket(connectionPort, 0, enderecoIp);
	            System.out.println("Connection Socket Created");
	            JOptionPane.showMessageDialog(null, "Conexão aberta.", "Conexão - Servidor", JOptionPane.INFORMATION_MESSAGE);
	            try {
	            	SwingUtilities.invokeLater(() -> {
	                    exibirListaUsuariosLogados();
	                });
	                while (true) {
	                    System.out.println("Waiting for Connection");
	                    new Server(serverSocket.accept());
	                }
	            } catch (IOException e) {
	                JOptionPane.showMessageDialog(null, "Problema ao conectar à porta especificada.", "Conexão - Servidor", JOptionPane.ERROR_MESSAGE);
	                System.err.println("Accept failed.");
	                System.exit(1);
	            }
	        } catch (IOException e) {
	            JOptionPane.showMessageDialog(null, "Problema ao conectar à porta especificada.", "Conexão - Servidor", JOptionPane.ERROR_MESSAGE);
	            System.err.println("Could not listen on port: " + connectionPort + ".");
	            System.exit(1);
	        } finally {
	            try {
	                serverSocket.close();
	            } catch (IOException e) {
	                System.err.println("Could not close port: " + connectionPort + ".");
	                System.exit(1);
	            }
	        }
	    }

	    private static void exibirListaUsuariosLogados() {
	        UsuarioListener usuarioListener = new UsuarioListener();
	        ListaUsuariosLogados listaUsuarios = new ListaUsuariosLogados(usuarioListener);
	        listaUsuarios.setVisible(true);
	    }
	    
	private Server (Socket clientSoc) {
		
		this.usuarioService = new UsuarioService();
		this.incidenteService = new IncidenteService();
		this.usuarioListener = new UsuarioListener();
		
		clientSocket = clientSoc;
		start();
	}

 	public void run(){
 		
 		System.out.println ("New Communication Thread Started");
 		try { 
 			PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), 
	                                      true); 
	        BufferedReader in = new BufferedReader( 
	        new InputStreamReader( clientSocket.getInputStream())); 
	
	        String inputLine; 
	
	        while ((inputLine = in.readLine()) != null || true){ 

		        Gson gson = new Gson();
			    Usuario user = null;
			    user = gson.fromJson(inputLine, Usuario.class);
			    
			    Incidente incident = null;
			    incident = gson.fromJson(inputLine, Incidente.class);
			    String json = null;
			        	
			    System.out.println("\nClient sent: " + inputLine);
		        		       
			    try {
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
			    	} else if(user.getId_operacao() == 2) {
			    		
			    		System.out.println("====== Atualizar ======");
			    		
			    		try {
				        	Retorno retorno = usuarioService.atualizarCadastro(user);
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
				        	
				        	usuarioListener.notifyUsuariosLogadosChanged();
				        	
				        	json = gson.toJson(retorno);
				        	out.println(json);
				        } catch(SQLException | GeneralErrorException gee) {
		        			json = erro(gson, gee.getMessage());
		        			out.println(json);
				        } finally {
				        	System.out.println("Server sent: " + json);
				        }
		
			    	} else if(user.getId_operacao() == 4) {
			    		System.out.println("== Reportar Incidente ==");
			    		
			    		try {
				        	Retorno retorno = incidenteService.cadastrar(incident);
				        	json = gson.toJson(retorno);
				        	out.println(json);
				        } catch(SQLException | GeneralErrorException | ParseException gee) {
		        			json = erro(gson, gee.getMessage());
		        			out.println(json);
				        } finally {
				        	System.out.println("Server sent: " + json);
				        }
		
			    	} else if(user.getId_operacao() == 5) {
			    		System.out.println("== Ver Lista de Incidentes ==");
			    		
			    		try {
				        	Retorno retorno = incidenteService.buscarTodos(incident);
				        	json = gson.toJson(retorno);
				        	out.println(json);
				        } catch(SQLException | GeneralErrorException | ParseException gee) {
		        			json = erro(gson, gee.getMessage());
		        			out.println(json);
				        } finally {
				        	System.out.println("Server sent: " + json);
				        }
		
			    	} else if(user.getId_operacao() == 6) {
			    		System.out.println("== Ver Incidentes Reportados ==");
			    		
			    		try {
				        	Retorno retorno = incidenteService.buscarPorIdUsuario(user);
				        	json = gson.toJson(retorno);
				        	out.println(json);
				        } catch(SQLException | GeneralErrorException gee) {
		        			json = erro(gson, gee.getMessage());
		        			out.println(json);
				        } finally {
				        	System.out.println("Server sent: " + json);
				        }
		
			    	} else if(user.getId_operacao() == 7) {
			    		System.out.println("== Remover Incidente ==");
			    		
			    		try {
			    			Usuario userSent = new Usuario();
			    			userSent.setId_usuario(incident.getId_usuario());
			    			userSent.setToken(incident.getToken());
			    			
				        	Retorno retorno = incidenteService.removerIncidente(userSent, incident);
				        	json = gson.toJson(retorno);
				        	out.println(json);
				        } catch(SQLException | GeneralErrorException gee) {
		        			json = erro(gson, gee.getMessage());
		        			out.println(json);
				        } finally {
				        	System.out.println("Server sent: " + json);
				        }
		
			    	} else if(user.getId_operacao() == 8) {
			    		System.out.println("== Remover Usuário ==");
			    		
			    		try {
			    			Usuario userSent = new Usuario();
			    			userSent.setId_usuario(user.getId_usuario());
			    			userSent.setToken(user.getToken());
			    			userSent.setEmail(user.getEmail());
			    			userSent.setSenha(user.getSenha());
			    			
				        	Retorno retorno = usuarioService.removerUsuario(userSent);
				        	
				        	usuarioListener.notifyUsuariosLogadosChanged();
				        	
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
			    	} else if(user.getId_operacao() == 10) {
			    		System.out.println("==== Editar Incidente ====");
			    		
			    		try {
				        	Retorno retorno = incidenteService.atualizarIncidente(incident);
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
			    
			    } catch (NumberFormatException nfe) {
			    	
			    	json = erro(gson, nfe.getMessage());
			    	System.out.println("Server sent: " + json);
        			out.println(json);
			    	
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
		
		return json;
	}
} 