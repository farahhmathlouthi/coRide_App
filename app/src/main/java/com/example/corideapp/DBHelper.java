package com.example.corideapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "MaBaseDeDonnees";
    private static final String TABLE_NAME = "MaTable";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NOM = "nom";
    // Ajoutez d'autres colonnes au besoin

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_NOM + " TEXT"
                // Ajoutez d'autres colonnes au besoin
                + ")";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // Méthodes CRUD

    // Ajouter une nouvelle entrée
    public void ajouterDonnee(String nom) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NOM, nom);
        // Insérer une ligne dans la table
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    // Lire une entrée
    public Cursor getDonnees() {
        SQLiteDatabase db = this.getReadableDatabase();
        // Sélectionnez toutes les lignes de la table
        return db.query(TABLE_NAME, null, null, null, null, null, null);
    }

    // Mettre à jour une entrée
    public void mettreAJourDonnee(int id, String nouveauNom) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NOM, nouveauNom);
        // Mettre à jour la ligne avec l'ID spécifié
        db.update(TABLE_NAME, values, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    // Supprimer une entrée
    public void supprimerDonnee(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        // Supprimer la ligne avec l'ID spécifié
        db.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }
}

