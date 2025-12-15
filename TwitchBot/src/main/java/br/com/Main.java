package br.com;

import java.io.*;
import java.net.Socket;

public class Main {

    public static void main(String[] args) {
        String token = "oauth:TOKEN_DO_BOT";
        String botName = "NOME_DO_BOT";
        String channel = "NOME_DO_CANAL";

        try {
            Socket socket = new Socket("irc.chat.twitch.tv", 6667);

            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(socket.getOutputStream())
            );
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(socket.getInputStream())
            );

            // Login
            send(writer, "PASS " + token);
            send(writer, "NICK " + botName);
            send(writer, "JOIN #" + channel);

            System.out.println("Bot conectado ao chat!");

            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);

                // Responde ao ping (obrigatório)
                if (line.startsWith("PING")) {
                    send(writer, "PONG :tmi.twitch.tv");
                }

                // Detecta mensagem do chat
                if (line.contains("PRIVMSG")) {
                    String msg = line.substring(line.indexOf(":", 1) + 1);
                    System.out.println("Mensagem: " + msg);

                    if (msg.equalsIgnoreCase("!teste")) {
                        Thread.sleep(500);
                        send(writer, "PRIVMSG #" + channel + " :Bot online 🚀");
                    } else if(msg.equalsIgnoreCase("!pix")){
                        Thread.sleep(500);
                        send(writer, "PRIVMSG #" + channel + " :chave pix: gilvanpedro2006@gmail.com");
                    } else if(msg.equalsIgnoreCase("!ping")){
                        Thread.sleep(500);
                        send(writer, "PRIVMSG #" + channel + " :PONG");
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static void send(BufferedWriter writer, String msg) throws IOException {
        writer.write(msg + "\r\n");
        writer.flush();
    }
}
