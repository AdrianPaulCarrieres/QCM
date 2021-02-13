package fr.lpiot.qcm.donnee;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import fr.lpiot.qcm.modele.Score;
import fr.lpiot.qcm.modele.Utilisateur;

public class ScoreDAO {

    private static ScoreDAO instance = null;
    protected BaseDeDonnees accesseurBaseDeDonnees;
    protected ArrayList<Score> listeScores;

    public static ScoreDAO getInstance() {
        if (instance == null) {
            instance = new ScoreDAO();
        }
        return instance;
    }

    public ScoreDAO() {
        accesseurBaseDeDonnees = BaseDeDonnees.getInstance();
        listeScores = new ArrayList<>();
        listerScores();
    }

    public ArrayList<Score> listerScores() {
        String LISTER_SCORE = "SELECT * FROM score";
        Cursor curseur = accesseurBaseDeDonnees.getReadableDatabase().rawQuery(LISTER_SCORE, null);
        this.listeScores.clear();

        int indexIdScore = curseur.getColumnIndex("id_score");
        int indexNomUtililisateur = curseur.getColumnIndex("nom_utilisateur");
        int indexScore = curseur.getColumnIndex("score");

        for (curseur.moveToFirst(); !curseur.isAfterLast(); curseur.moveToNext()) {
            int idScore = curseur.getInt(indexIdScore);
            String nomUtilisateur = curseur.getString(indexNomUtililisateur);
            int score = curseur.getInt(indexScore);
            listeScores.add(new Score(idScore, nomUtilisateur, score));
        }
        curseur.close();
        return listeScores;
    }

    public void ajouterScore(Score score) {
        SQLiteDatabase db = accesseurBaseDeDonnees.getWritableDatabase();
        SQLiteStatement query = db.compileStatement("INSERT INTO score(id_score, nom_utilisateur ,score) VALUES(null, ?, ?)");
        query.bindString(1, score.getNomUtilisateur());
        query.bindString(2, String.valueOf(score.getScore()));
        query.execute();
    }

    public Score getDernierScore(){
        listerScores();
        return listeScores.get(listeScores.size() - 1);
    }
}
