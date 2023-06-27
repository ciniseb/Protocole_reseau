package classesAuxiliaires;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

public class Requete
{
    private byte[] donnees;

    public Requete(byte[] donnees)
    {
        setBytes(donnees);
    }

    public void setString(String texte)
    {
        this.donnees = texte.getBytes();
    }

    public void setBytes(byte[] octets)
    {
        this.donnees = octets;;
    }

    public void setPaquet(DatagramPacket paquet)
    {
        this.donnees = paquet.getData();
    }

    public String getString()
    {
        return new String(donnees);
    }

    public byte[] getBytes()
    {
        return donnees;
    }

    public DatagramPacket getPaquet()
    {
        //TODO: analyse des données pour les adresses, etc

        try
        {
            int port = 25001; //Temporaire
            InetAddress adresseIP_receveur = InetAddress.getByName("localhost"); //Temporaire

            return new DatagramPacket(donnees, donnees.length, adresseIP_receveur, port);
        } catch (UnknownHostException e)
        {
            throw new RuntimeException(e);
        }
    }
}
