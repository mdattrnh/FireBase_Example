
package vn.edu.ueh.thanhdnh.firebase_example;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener {

  FirebaseFirestore db;
  Button btAdd, btShow;
  EditText etId, etTitle, etImage, etDescription;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    FirebaseApp.initializeApp(this);
    db = FirebaseFirestore.getInstance();

    btAdd = findViewById(R.id.btAdd);
    btShow = findViewById(R.id.btShow);

    etId = findViewById(R.id.etId);
    etTitle = findViewById(R.id.etTitle);
    etImage = findViewById(R.id.etImage);
    etDescription = findViewById(R.id.etDescription);

    btAdd.setOnClickListener(this);
    btShow.setOnClickListener(this);
  }

  @Override
  public void onClick(View view) {

    if (view.getId() == R.id.btAdd) {

      String idText = etId.getText().toString().trim();
      String title = etTitle.getText().toString().trim();
      String image = etImage.getText().toString().trim();
      String description = etDescription.getText().toString().trim();

      if (idText.isEmpty() || title.isEmpty()
              || image.isEmpty() || description.isEmpty()) {
        Toast.makeText(this,
                "Please fill in all fields",
                Toast.LENGTH_SHORT).show();
        return;
      }

      int id;
      try {
        id = Integer.parseInt(idText);
      } catch (NumberFormatException e) {
        Toast.makeText(this,
                "Invalid Article ID",
                Toast.LENGTH_SHORT).show();
        return;
      }

      Article article = new Article(
              id, title, image, description
      );

      db.collection("articles")
              .add(article)
              .addOnSuccessListener(documentReference -> {
                Toast.makeText(this,
                        "Article added successfully",
                        Toast.LENGTH_SHORT).show();

                etId.setText("");
                etTitle.setText("");
                etImage.setText("");
                etDescription.setText("");
              })
              .addOnFailureListener(e -> {
                Toast.makeText(this,
                        "Error: " + e.getMessage(),
                        Toast.LENGTH_LONG).show();
              });

    } else if (view.getId() == R.id.btShow) {

      Intent intent = new Intent(
              this, ShowDataActivity.class
      );
      startActivity(intent);
    }
  }
}
