package fr.lpiot.qcm.modele;

public class Score {

    private int id_score;
    private String nomUtilisateur;
    private int score;

    public Score(int id_score, String nomUtilisateur, int score) {
        this.id_score = id_score;
        this.nomUtilisateur = nomUtilisateur;
        this.score = score;
    }

    public Score(String nomUtilisateur, int score) {
        this.nomUtilisateur = nomUtilisateur;
        this.score = score;
    }

    public int getId_score() {
        return id_score;
    }

    public void setId_score(int id_score) {
        this.id_score = id_score;
    }

    public String getNomUtilisateur() {
        return nomUtilisateur;
    }

    public void setNomUtilisateur(String nomUtilisateur) {
        this.nomUtilisateur = nomUtilisateur;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
