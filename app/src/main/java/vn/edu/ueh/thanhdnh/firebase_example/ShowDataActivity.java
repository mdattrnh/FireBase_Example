
package vn.edu.ueh.thanhdnh.firebase_example;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class ShowDataActivity extends AppCompatActivity {

    FirebaseFirestore db;
    RecyclerView recyclerView;
    List<Article> articles = new ArrayList<>();
    ArticleViewAdapter adapter;
    ListenerRegistration listenerRegistration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_data);
        findViewById(R.id.btBack)
                .setOnClickListener(v -> finish());
        FirebaseApp.initializeApp(this);
        db = FirebaseFirestore.getInstance();

        recyclerView = findViewById(R.id.reclyclerview);

        adapter = new ArticleViewAdapter(this, articles);

        recyclerView.setLayoutManager(
                new LinearLayoutManager(this)
        );

        recyclerView.setAdapter(adapter);

        listenerRegistration = db.collection("articles")
                .addSnapshotListener((snapshots, error) -> {

                    if (error != null) {
                        Toast.makeText(this,
                                "Error: " + error.getMessage(),
                                Toast.LENGTH_LONG).show();
                        return;
                    }

                    if (snapshots != null) {

                        articles.clear();

                        for (QueryDocumentSnapshot document : snapshots) {
                            android.util.Log.d("FIREBASE_TEST",
                                    "ID: " + document.getId()
                                            + " | Pending writes: "
                                            + document.getMetadata().hasPendingWrites()
                                            + " | From cache: "
                                            + document.getMetadata().isFromCache());
                            Article article =
                                    document.toObject(Article.class);

                            articles.add(article);
                        }

                        adapter.update(articles);
                        adapter.notifyDataSetChanged();
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (listenerRegistration != null) {
            listenerRegistration.remove();
        }
    }
}
