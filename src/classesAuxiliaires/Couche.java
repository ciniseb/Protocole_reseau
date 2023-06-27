package classesAuxiliaires;

public abstract class Couche implements ICouche
{
    protected ICouche prochaine_couche;

    public void setProchaineCouche(ICouche prochaine)
    {
        prochaine_couche = prochaine;
    }

    public abstract void traite(Requete donnees);
}
