package classesAuxiliaires;

import java.util.Arrays;

import static classesAuxiliaires.Constantes.NB_MAX_OCTETS;
import static classesAuxiliaires.Constantes.NOM_FICHIER;

public class CoucheTransport extends Couche
{
    private Requete donnees_temp;

    public CoucheTransport()
    {
        donnees_temp = new Requete(null);
    }

    @Override
    public void traite(Requete donnees)
    {
        if(prochaine_couche instanceof CoucheLiaison)
        {
            System.out.println("Couche Transport --> Couche Liaison");

            //Envoie des segments
            //traite_segments_out(donnees.getBytes());
            prochaine_couche.traite(donnees);
        }
        else if(prochaine_couche instanceof CoucheApplication)
        {
            System.out.println("Couche Transport --> Couche Application");

            //Réception des segments
            //traite_segments_in(donnees.getBytes());
            prochaine_couche.traite(donnees);
        }
        else
        {
            System.err.println("Couche Transport : traitement impossible");
        }
    }

    private void traite_segments_out(byte[] donnees)
    {
        int nb_segments = (int) Math.ceil((double) donnees.length / NB_MAX_OCTETS);

        System.out.println(donnees.length);
        System.out.println(nb_segments);

        byte[][] segments = new byte[nb_segments][];

        //Envoie du nom de fichier
        Segment titre = new Segment((byte) 0, (byte) 0, (byte) (nb_segments-1), NOM_FICHIER.getBytes());
        prochaine_couche.traite(new Requete(titre.getSegment()));

        for (int i = 0; i < nb_segments; i++)
        {
            int taille_segment = Math.min((NB_MAX_OCTETS - 3), donnees.length - (i * NB_MAX_OCTETS));
            segments[i] = new byte[taille_segment + 3];
            segments[i][0] = (byte) (0); // Byte de séquence
            segments[i][1] = (byte) (i+1); // Byte de séquence
            segments[i][2] = (byte) (nb_segments-1); // Byte de séquence
            System.arraycopy(donnees, i * NB_MAX_OCTETS, segments[i], 3, taille_segment);

            prochaine_couche.traite(new Requete(segments[i]));
        }
    }

    private void traite_segments_in(byte[] donnees)
    {
        //System.out.println(new String(donnees));


        if(donnees[0] == (byte) 0 && donnees[2] >= (byte) 0)
        {
            if(donnees[1] == (byte) 0)
            {
                //TODO : récupère le nom de fichier
                System.out.println("PASSE ICI 0");
            }
            else if(donnees[1] < donnees[2])
            {
                byte[] newArray = new byte[donnees_temp.getBytes().length + donnees.length];
                System.arraycopy(donnees_temp.getBytes(), 0, newArray, 0, donnees_temp.getBytes().length);
                System.arraycopy(donnees, 0, newArray, donnees_temp.getBytes().length, donnees.length);
                donnees_temp.setBytes(newArray);

                System.out.println(donnees_temp.getString());
                System.out.println(Arrays.toString(newArray));
                System.out.println("PASSE ICI 1,2,3,...");
            }
            else if(donnees[1] == donnees[2])
            {
                System.out.println("PASSE ICI fin");
                prochaine_couche.traite(donnees_temp);

                System.out.println(donnees_temp.getString());

                donnees_temp = new Requete(null);
            }
        }
    }
}
