package udpSocket;


import java.io.*;
import java.net.*;
import java.util.Scanner;



    public class ClientUDP {
        public static void main(String[] argv) throws IOException, ClassNotFoundException {
            int port = 0;
            String host = "";
            Scanner keyb = new Scanner(System.in);

// on récupère les paramètres : nom de la machine serveur et
// numéro de port
                System.out.println("Adress du serveur : ");
                host = keyb.next();
                System.out.println("port d'écoute du serveur : ");
                port = keyb.nextInt();
                InetAddress adr;
                DatagramPacket packet;
                DatagramSocket socket;
// adr contient l'@IP de la partie serveur
                adr = InetAddress.getByName(host);

// création d'une socket, sans la lier à un port particulier
                socket = new DatagramSocket();
                voiture v=new voiture("rat","rat");
                // serialize object
                ByteArrayOutputStream bStream = new ByteArrayOutputStream();
                ObjectOutput oo = new ObjectOutputStream(bStream);
                oo.writeObject(v);
                oo.close();
                byte[] data=bStream.toByteArray();
                // création du paquet avec les données et en précisant l'adresse
// du serveur (@IP et port sur lequel il écoute)
                packet = new DatagramPacket(data, data.length, adr, port);

// envoi du paquet via la socket
                socket.send(packet);
// création d'un tableau vide pour la réception
                byte[] reponse = new byte[15];
                packet.setData(reponse);
                packet.setLength(reponse.length);
// attente paquet envoyé sur la socket du client
                socket.receive(packet);
                ObjectInputStream iStream = new ObjectInputStream(new ByteArrayInputStream(packet.getData()));
                voiture vv = (voiture) iStream.readObject();
                iStream.close();
// récupération et affichage de la donnée contenue dans le paquet


                System.out.println(" reçu du serveur : " + vv.getCarburant() );
        }
    }

