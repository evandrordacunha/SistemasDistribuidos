/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import sun.applet.Main;

/**
 *
 * @author ecunha
 */
/**
 *
 * @author ecunha
 */
public class Server {

    //HASHMAP COM OS ARQUIVOS DE CADA CLIENTE CONECTADO E SEUS ENDEREÇAMENTOS
    private static HashMap<Inet4Address, ArrayList<String>> arquivosCompartilhados = new HashMap<>();
    private  ArrayList<String> meusArquivos = new ArrayList<>();
    //CRIANDO BUFFER PARA DADOS DE RECEBIMENTO E ENVIO DE RESPOSTAS
    byte[] buffer = new byte[1024];
    private int porta = 45000;

        //USADO PARA LISTAR TODOS OS DADOS ENCONTRADOS NA HASH DO SERVER
        private String listarArquivos(HashMap<Inet4Address, ArrayList<String>> arquivos){
            String s = "";
            Set<Inet4Address> chaves = arquivos.keySet();
            for (Inet4Address chave : chaves)
		{
			if(chave != null)
                            System.out.println("### DADOS ATUALIZADOS ###");
				System.out.println(chave.getAddress().toString() + arquivos.get(chave));
		}      
            return s;
            
        }
 
        
    /**
     * TRATA CONEXÃO COM PROTOCOLOS
     */
    private void inicializar() {

        try {
            //CRIANDO DATAGRAMA
            DatagramSocket ServerSocket = new DatagramSocket(porta);
            System.out.println("Servidor respondendo na porta " +porta);

            //ESPERANDO POR CONEXÕES
            while (true) {

                //MONTANDO PROTOCOLO UDP
                DatagramPacket receberPacote = new DatagramPacket(buffer, buffer.length);
                //CASO SEJA RECEBIDA ALGUMA CONEXÃO, PREPARA O PROTOCOLO PARA RECEBIMENTO
                ServerSocket.receive(receberPacote);

                //TRADUZINDO DADOS
                String dadosRecebidos = new String(receberPacote.getData());
                meusArquivos.add(dadosRecebidos);
                //GRAVANDO DADOS DOS ARQUIVOS COMPARTILHADOS DIRETAMENTE NO SERVIDOR
                arquivosCompartilhados.put((Inet4Address) receberPacote.getAddress(), meusArquivos);
            }
        } catch (SocketException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);

        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    


    public static void main(String[] args) {
        Server server = new Server();
        server.inicializar();
        server.listarArquivos(arquivosCompartilhados);
        
}
}

