/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.io.File;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Scanner;
import javax.swing.JFileChooser;

/**
 *
 * @author ecunha
 */
public class Client {

 File meusArquivos[];
 private int porta = 45000;
	private String diretorio;
	byte[] buffer = new byte[10240];

	/**
	 * SELECIONANDO DIRETORIO DOS ARQUIVOS
	 */
	private void setarDiretorio() {
		JFileChooser chooserDiretorio = new JFileChooser();
		chooserDiretorio.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int res = chooserDiretorio.showOpenDialog(null);
		diretorio = chooserDiretorio.getSelectedFile().getAbsolutePath();
		System.out.println("Diret�rio compartilhado =  " + diretorio);
	}

	/**
	 * CHAMA OS PRINCIPAIS METODOS DE EXECU��O DA APLICA��O
	 */
	private void executar() {
		compartilharArquivos();
		abrirConexao();
	}

	/**
	 * VISUALIZA TODOS ARQUIVOS CONTIDOS NA PASTA E POPULA UMA LISTA DE ARQUIVOS
	 * COMPARTILHADOS
	 */
	private void compartilharArquivos() {
		File arquivo = new File(diretorio);
		meusArquivos = arquivo.listFiles();
		System.out.println("#############################################");
		System.out.println("ARQUIVOS COMPARTILHADOS:");
		int i = 0;
		int total = 0;
		for (int j = meusArquivos.length; i < j; i++) {
			total++;
			File arquivoIdentificado = meusArquivos[i];
			String nomeArquivo = arquivoIdentificado.getName();
			// CONVERTE NOME DO ARQUIVO EM BYTE
			byte[] dadosNome = nomeArquivo.getBytes();
			// COPIA NOMO DO ARQUIVO PARA O BUFFER
			buffer[i] = dadosNome[i];

			System.out.println(arquivoIdentificado.getName());
		}
		System.out.println("#############################################");
		System.out.println("Total:  " + total + " arquivos compartilhados");
		System.out.println("#############################################");
	}

	/**
	 * ABRINDO CONEX�O COM O SERVIDOR
	 */
	private void abrirConexao() {
		try {
			// CRIANDO PACOTE A SER TRANSFERIDO
			DatagramSocket clientSocket = new DatagramSocket();
                        //VARIAVEL PARA LEITURA DAS MENSAGENS
                        
			// MODIFICAR PARA O IP DE DESTINO DO SERVIDOR
			InetAddress ip = InetAddress.getByName("localhost");
			// CRIANDO PACOTE UDP COM AS INFORMA��ES DO
			DatagramPacket pacoteEnvio = new DatagramPacket(buffer, buffer.length, ip, porta);
			// ENVIANDO PACOTE
			clientSocket.send(pacoteEnvio);
			// FECHANDO A CONEXAO
			clientSocket.close();

		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {

		Client cliente = new Client();
		cliente.setarDiretorio();

		while (true) {
			try {
				Thread.sleep(5000);
				cliente.executar();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	} 
}
