package classesAuxiliaires;

public class CoucheLiaison extends Couche
{

    @Override
    public void traite(Requete donnees)
    {
        if(prochaine_couche instanceof CouchePhysique)
        {
            //TODO: Traitement de la couche actuelle

            System.out.println("Couche Liaison --> Couche Physique");
            prochaine_couche.traite(donnees);
        }
        else if(prochaine_couche instanceof CoucheTransport)
        {
            //TODO: Traitement de la couche actuelle

            System.out.println("Couche Liaison --> Couche Transport");
            prochaine_couche.traite(donnees);
        }
        else
        {
            System.err.println("Couche Liaison : traitement impossible");
        }
    }
}
