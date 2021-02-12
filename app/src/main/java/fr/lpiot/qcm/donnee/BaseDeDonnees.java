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

    private BaseDeDonnees(Context contexte) {
        super(contexte, "QCM", null, 1);
    }

    public BaseDeDonnees(Context contexte, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(contexte, name, factory , version);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        /*String DELETE = "delete from jeu where 1 = 1";
        db.execSQL(DELETE);
        String INSERT_1 = "insert into jeu(id_jeu, titre, plateforme, alarmeActivee) VALUES('1','Dofus', 'PC', 0)";
        String INSERT_2 = "insert into jeu(id_jeu, titre, plateforme, alarmeActivee) VALUES('2','Halo', 'XBox', 0)";
        String INSERT_3 = "insert into jeu(id_jeu, titre, plateforme, alarmeActivee) VALUES('3','Detroit Become Human', 'PS4', 0)";
        db.execSQL(INSERT_1);
        db.execSQL(INSERT_2);
        db.execSQL(INSERT_3);*/
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "create table jeu(id_jeu INTEGER PRIMARY KEY, titre TEXT, plateforme TEXT)";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        //String DETRUIRE_TABLE = "drop table jeu";
        //db.execSQL(DETRUIRE_TABLE);
        String CREER_TABLE = "create table jeu(id_jeu INTEGER PRIMARY KEY, titre TEXT, plateforme TEXT, dateLive TEXT, alarmeActivee INTEGER)";
        db.execSQL(CREER_TABLE);
    }
}
