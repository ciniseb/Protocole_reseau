package classesAuxiliaires;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

public class Requete
{
    private String donnees;

    public Requete(String donnees)
    {
        setString(donnees);
    }

    public void setString(String donnees)
    {
        this.donnees = donnees;
    }

    public void setBytes(byte[] octets)
    {
        this.donnees = new String(octets);;
    }

    public void setPaquet(DatagramPacket paquet)
    {
        this.donnees = new String(paquet.getData(), 0, paquet.getLength());
    }

    public String getString()
    {
        return donnees;
    }

    public byte[] getBytes()
    {
        return donnees.getBytes();
    }

    public DatagramPacket getPaquet()
    {
        byte[] sendData = getBytes();

        //TODO: analyse des données pour les adresses, etc

        try
        {
            int port = 25001; //Temporaire
            InetAddress adresseIP_receveur = InetAddress.getByName("localhost"); //Temporaire

            return new DatagramPacket(sendData, sendData.length, adresseIP_receveur, port);
        } catch (UnknownHostException e)
        {
            throw new RuntimeException(e);
        }
    }
}
