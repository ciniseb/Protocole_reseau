package classesAuxiliaires;

import static classesAuxiliaires.Constantes.NB_MAX_OCTETS;
import static classesAuxiliaires.Constantes.NOM_FICHIER;

public class CoucheTransport extends Couche
{

    @Override
    public String traite(Requete donnees)
    {
        if(prochaine_couche instanceof CoucheLiaison)
        {
            //Envoie des segments
            traite_segments_out(donnees.getBytes());

            return "Couche Transport --> Couche Liaison\n" + prochaine_couche.traite(donnees);
        }
        else if(prochaine_couche instanceof CoucheApplication)
        {

            //Réception des segments
            traite_segments_in(donnees.getBytes());

            return "Couche Transport --> Couche Application\n" + prochaine_couche.traite(donnees);
        }

        return "Couche Transport : traitement impossible";
    }

    private void traite_segments_out(byte[] donnees)
    {
        int nb_segments = (int) Math.ceil((double) donnees.length / NB_MAX_OCTETS);
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
        if(donnees[0] == 0 && donnees[2] >= 0)
        {
            if(donnees[1] == 0)
            {
                //TODO : récupère le nom de fichier
            }
            else if(donnees[1] < donnees[2])
            {
                prochaine_couche.traite(new Requete(new Segment(donnees).getDonnees()));
            }
        }
    }
}
