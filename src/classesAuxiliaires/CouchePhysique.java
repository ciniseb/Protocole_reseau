package classesAuxiliaires;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class CouchePhysique extends Couche
{

    @Override
    public String traite(String donnees)
    {
        int port = 25001;

        if(prochaine_couche == null)
        {
            try
            {
                DatagramSocket socket = new DatagramSocket();

                // Préparation du message à envoyer
                byte[] donnees_bytes_envoie = donnees.getBytes();
                InetAddress adresseIP_receveur = InetAddress.getByName("localhost");
                DatagramPacket paquet_envoie = new DatagramPacket(donnees_bytes_envoie, donnees_bytes_envoie.length, adresseIP_receveur, port);

                // Envoi du paquet
                socket.send(paquet_envoie);

                /*// Attente de réception de la réponse
                byte[] donnees_bytes_reception = new byte[1600];
                DatagramPacket paquet_reception = new DatagramPacket(donnees_bytes_reception, donnees_bytes_reception.length);
                socket.receive(paquet_reception);

                // Traitement de la réponse
                String reponse = new String(paquet_reception.getData(), 0, paquet_reception.getLength());
                System.out.println("Réponse : " + reponse);*/

                // Fermeture du socket
                socket.close();
            } catch (IOException e)
            {
                e.printStackTrace();
            }

            return "Couche Physique --> Couche Physique\n";
        }
        else if(prochaine_couche instanceof CoucheLiaison)
        {
            try
            {
                // Création du socket
                DatagramSocket socket = new DatagramSocket(port);

                // Attente de réception d'un paquet
                byte[] donnees_bytes_reception = new byte[1600];
                DatagramPacket paquet_reception = new DatagramPacket(donnees_bytes_reception, donnees_bytes_reception.length);
                socket.receive(paquet_reception);

                // Traitement du paquet reçu
                //TODO : ------------------------------------------------------
                donnees = new String(paquet_reception.getData(), 0, paquet_reception.getLength());
                System.out.println("Message reçu : " + donnees);

                /*// Réponse
                InetAddress adresseIP_receveur = paquet_reception.getAddress();
                int port_receveur = paquet_reception.getPort();
                String reponse = "Message reçu avec succès !";
                byte[] donnees_bytes_envoie = reponse.getBytes();
                DatagramPacket paquet_envoie = new DatagramPacket(donnees_bytes_envoie, donnees_bytes_envoie.length, adresseIP_receveur, port_receveur);
                socket.send(paquet_envoie);*/

                // Fermeture du socket
                socket.close();
            } catch (IOException e)
            {
                e.printStackTrace();
            }

            return "Couche Physique --> Couche Liaison\n" + prochaine_couche.traite(donnees);
        }

        return "Couche Physique : traitement impossible";
    }
}
