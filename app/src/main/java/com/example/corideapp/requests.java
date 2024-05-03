package com.example.corideapp;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class requests extends AppCompatActivity {

    private EditText editTextText;
    private Button button9;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_requests);

        editTextText = findViewById(R.id.editTextText);
        button9 = findViewById(R.id.button9);

        // Reference to the Firebase database
        databaseReference = FirebaseDatabase.getInstance().getReference();

        button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessageToDatabase();
            }
        });
    }

    private void sendMessageToDatabase() {
        // Récupérer le texte saisi dans l'EditText
        String message = editTextText.getText().toString().trim();

        // Vérifier si le message n'est pas vide
        if (!message.isEmpty()) {
            // Créer un nouvel ID pour le message
            String messageId = databaseReference.child("messages").push().getKey();

            // Vérifier si l'ID a été généré avec succès
            if (messageId != null) {
                // Créer un nœud "messages" avec l'ID généré
                DatabaseReference messageReference = databaseReference.child("messages").child(messageId);

                // Enregistrer le message dans la base de données
                messageReference.setValue(message)
                        .addOnSuccessListener(aVoid -> {
                            // Message enregistré avec succès
                            Toast.makeText(requests.this, "Message sent successfully!", Toast.LENGTH_SHORT).show();
                            // Effacer le texte de l'EditText après l'envoi
                            editTextText.setText("");
                        })
                        .addOnFailureListener(e -> {
                            // Erreur lors de l'enregistrement du message
                            Toast.makeText(requests.this, "Failed to send message: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        });
            } else {
                // Gestion de l'erreur si l'ID n'a pas été généré
                Toast.makeText(requests.this, "Failed to generate message ID!", Toast.LENGTH_SHORT).show();
            }
        } else {
            // Message vide, afficher un message d'erreur
            Toast.makeText(requests.this, "Please enter a message!", Toast.LENGTH_SHORT).show();
        }
    }
}