package udpSocket;
import java.io.*;

import java.net.*;
import java.util.Scanner;



public class ServeurUDP {
    public static void main(String[] argv) throws IOException, ClassNotFoundException {
        int port = 0;
        Scanner keyb = new Scanner(System.in);

// on récupère le paramètre : port d'écoute
            System.out.println("port d'écoute : ");
            port = keyb.nextInt();
            DatagramPacket packet;
// création d'une socket liée au port précisé en paramètre
            DatagramSocket socket = new DatagramSocket(port);


//receive data
            byte[] data = new byte[15];
            packet = new DatagramPacket(data, data.length);
            socket.receive(packet);
            ObjectInputStream iStream = new ObjectInputStream(new ByteArrayInputStream(packet.getData()));
            voiture vv = (voiture) iStream.readObject();
            iStream.close();
            vv.setCarburant(20);

// récupération et affichage des données (une chaîne de caractères)

            System.out.println(" carburant : " + vv.getCarburant());
            System.out.println(" ca vient de : " + packet.getAddress() + ":" + packet.getPort());

// on met une nouvelle donnée dans le paquet
// (qui contient donc le couple @IP/port de la socket coté client)
            ByteArrayOutputStream bStream = new ByteArrayOutputStream();
            ObjectOutput oo = new ObjectOutputStream(bStream);
            oo.writeObject(vv);
            oo.close();
            byte[] reponse = bStream.toByteArray();
            packet.setData(reponse);
            packet.setLength(reponse.length);
// on envoie le paquet au client
            socket.send(packet);

    }
}

