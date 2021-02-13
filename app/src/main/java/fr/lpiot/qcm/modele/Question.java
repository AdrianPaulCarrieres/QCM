package fr.lpiot.qcm.modele;

import java.util.ArrayList;

public class Question {
    protected String categorie;
    protected String difficulte;
    protected ArrayList<Reponse> listeReponses;

    public Question(String categorie, String difficulte) {
        this.categorie = categorie;
        this.difficulte = difficulte;
        listeReponses = new ArrayList<>();
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getDifficulte() {
        return difficulte;
    }

    public void setDifficulte(String difficulte) {
        this.difficulte = difficulte;
    }

    public ArrayList<Reponse> getListeReponses() {
        return listeReponses;
    }

    public void setListeReponses(ArrayList<Reponse> listeReponses) {
        this.listeReponses = listeReponses;
    }
}
