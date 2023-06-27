package classesAuxiliaires;

import java.util.logging.FileHandler;
import java.util.logging.Logger;


public class CoucheLiaison extends Couche
{
    int erreurCRC = 0;
    int paquetTransmit = 0;
    int paquetRecut = 0;
    int paquetPerdu =0;
    @Override
    public String traite(Requete donnees)
    {

        if(prochaine_couche instanceof CouchePhysique)
        {
            donnees.setBytes(calculerCRC(donnees.getBytes()));

            paquetTransmit ++;

            prochaine_couche.traite(donnees);
            return "Couche Liaison --> Couche Physique\n" + prochaine_couche.traite(donnees);
        }
        else if(prochaine_couche instanceof CoucheTransport) {

            if(verifierCRC(donnees.getBytes(), POLYNOMIAL))
            {
                erreurCRC++;
            }

            if(donnees.getString() != null)
            {
                paquetRecut++;
            }

                paquetPerdu = paquetTransmit- paquetRecut;

                prochaine_couche.traite(donnees);
                return "Couche Liaison --> Couche Transport\n" + prochaine_couche.traite(donnees);
        }

        return "Couche Liaison : traitement impossible";
    }


    private static final int POLYNOMIAL = 0xEDB88320;

    public static byte[] calculerCRC(byte[] data) {
        int crc = 0xFFFFFFFF;
        for (byte b : data) {
            crc ^= b;
            for (int i = 0; i < 8; i++) {
                if ((crc & 1) == 1) {
                    crc = (crc >>> 1) ^ POLYNOMIAL;
                } else {
                    crc >>>= 1;
                }
            }
        }
        crc ^= 0xFFFFFFFF;
        byte[] crcBytes = new byte[4];
        crcBytes[0] = (byte) (crc & 0xFF);
        crcBytes[1] = (byte) ((crc >>> 8) & 0xFF);
        crcBytes[2] = (byte) ((crc >>> 16) & 0xFF);
        crcBytes[3] = (byte) ((crc >>> 24) & 0xFF);
        return crcBytes;
    }

    public static boolean verifierCRC(byte[] data, long crc) {
        crc ^= 0xFFFFFFFF;
        for (byte b : data) {
            crc ^= b;
            for (int i = 0; i < 8; i++) {
                if ((crc & 1) == 1) {
                    crc = (crc >>> 1) ^ POLYNOMIAL;
                } else {
                    crc >>>= 1;
                }
            }
        }
        return crc == 0;
    }

}


