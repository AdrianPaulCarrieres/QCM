package fr.lpiot.qcm.donnee;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BaseDeDonnees extends SQLiteOpenHelper{

    //Singleton
    private static BaseDeDonnees instance = null;

    public static BaseDeDonnees getInstance(Context contexte) {
        instance = new BaseDeDonnees(contexte);
        return instance;
    }

    public static BaseDeDonnees getInstance() {
        return instance;
    }

    public BaseDeDonnees(Context contexte) {
        super(contexte, "QCM", null, 1);
    }

    public BaseDeDonnees(Context contexte, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(contexte, name, factory , version);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        /*String DELETE = "delete from utilisateur where 1 = 1";
        db.execSQL(DELETE);
         */
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_UTILISATEUR = "create table utilisateur(id_utilisateur INTEGER PRIMARY KEY, nom TEXT, mot_de_passe TEXT)";
        db.execSQL(CREATE_TABLE_UTILISATEUR);

        String CREATE_TABLE_TOKEN = "create table token(id_token INTEGER PRIMARY KEY, nom_utilisateur TEXT)";
        db.execSQL(CREATE_TABLE_TOKEN);

        String CREATE_TABLE_SCORE = "create table score(id_score INTEGER PRIMARY KEY, nom_utilisateur TEXT, score INTEGER)";
        db.execSQL(CREATE_TABLE_SCORE);

        seed(db);
    }

    private void seed(SQLiteDatabase db) {
        String INSERT_TEST_USER = "insert into utilisateur(id_utilisateur, nom, mot_de_passe) VALUES('1', 'test', 'test')";
        db.execSQL(INSERT_TEST_USER);

        //Pour sauter l'écran de connexion
        String CONNECT_TEST_USER = "insert into token(id_token, nom_utilisateur) VALUES('1', 'test')";
        db.execSQL(CONNECT_TEST_USER);

        //Scores
        String SCORE1 = "insert into score(id_score, nom_utilisateur, score) VALUES('1', 'IUT Paris Descartes', '30')";
        String SCORE2 = "insert into score(id_score, nom_utilisateur, score) VALUES('2', 'LPIOT', '40')";
        String SCORE3 = "insert into score(id_score, nom_utilisateur, score) VALUES('3', 'Paris', '25')";

        db.execSQL(SCORE1);
        db.execSQL(SCORE2);
        db.execSQL(SCORE3);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        //String DETRUIRE_TABLE = "drop table jeu";
        //db.execSQL(DETRUIRE_TABLE);
        //String CREER_TABLE = "create table jeu(id_jeu INTEGER PRIMARY KEY, titre TEXT, plateforme TEXT, dateLive TEXT, alarmeActivee INTEGER)";
        //db.execSQL(CREER_TABLE);
    }
}
