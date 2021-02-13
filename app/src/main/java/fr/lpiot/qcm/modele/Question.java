package fr.lpiot.qcm.modele;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Arrays;

public class Question {
    @SerializedName("category")
    @Expose
    protected String categorie;
    @SerializedName("type")
    @Expose
    protected String type;
    @SerializedName("difficulty")
    @Expose
    protected String difficulte;
    @SerializedName("question")
    @Expose
    protected String question;
    @SerializedName("correct_answer")
    @Expose
    protected String bonneReponse;
    @SerializedName("incorrect_answers")
    @Expose
    protected String[] listeFaussesReponses;

    public Question(String categorie, String type, String difficulte, String question, String bonneReponse, String[] listeFaussesReponses) {
        this.categorie = categorie;
        this.type = type;
        this.difficulte = difficulte;
        this.question = question;
        this.bonneReponse = bonneReponse;
        this.listeFaussesReponses = listeFaussesReponses;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDifficulte() {
        return difficulte;
    }

    public void setDifficulte(String difficulte) {
        this.difficulte = difficulte;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getBonneReponse() {
        return bonneReponse;
    }

    public void setBonneReponse(String bonneReponse) {
        this.bonneReponse = bonneReponse;
    }

    public String[] getListeFaussesReponses() {
        return listeFaussesReponses;
    }

    public void setListeFaussesReponses(String[] listeFaussesReponses) {
        this.listeFaussesReponses = listeFaussesReponses;
    }

    @Override
    public String toString() {
        return "Question{" +
                "categorie='" + categorie + '\'' +
                ", type='" + type + '\'' +
                ", difficulte='" + difficulte + '\'' +
                ", question='" + question + '\'' +
                ", bonneReponse=" + bonneReponse +
                ", listeFaussesReponses=" + Arrays.toString(listeFaussesReponses) +
                '}';
    }
}
