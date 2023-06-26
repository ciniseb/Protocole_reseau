package classesAuxiliaires;

public class CoucheLiaison extends Couche
{

    @Override
    public String traite(String donnees)
    {
        if(prochaine_couche instanceof CouchePhysique)
        {
            //TODO: Traitement de la couche actuelle

            prochaine_couche.traite(donnees);
            return "Couche Liaison --> Couche Physique\n" + prochaine_couche.traite(donnees);
        }
        else if(prochaine_couche instanceof CoucheTransport)
        {
            //TODO: Traitement de la couche actuelle

            prochaine_couche.traite(donnees);
            return "Couche Liaison --> Couche Transport\n" + prochaine_couche.traite(donnees);
        }

        return "Couche Liaison : traitement impossible";
    }
}
