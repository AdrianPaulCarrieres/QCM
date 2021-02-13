package fr.lpiot.qcm.modele;

public class Utilisateur {
    protected int id_utilisateur;
    protected String nom;
    protected String motDePasse;

    public Utilisateur(int id_utilisateur, String nom, String motDePasse) {
        this.id_utilisateur = id_utilisateur;
        this.nom = nom;
        this.motDePasse = motDePasse;
    }

    public Utilisateur(String nom, String motDePasse) {
        this.id_utilisateur = 0;
        this.nom = nom;
        this.motDePasse = motDePasse;
    }

    public int getId_utilisateur() {
        return id_utilisateur;
    }

    public void setId_utilisateur(int id_utilisateur) {
        this.id_utilisateur = id_utilisateur;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }
}
