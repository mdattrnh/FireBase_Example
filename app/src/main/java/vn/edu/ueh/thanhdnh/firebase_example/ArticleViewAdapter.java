package vn.edu.ueh.thanhdnh.firebase_example;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ArticleViewAdapter
        extends RecyclerView.Adapter<ArticleViewHolder> {

  private final LayoutInflater mInflater;
  private final Context context;
  private List<Article> articles;
  private final FirebaseFirestore db;

  public ArticleViewAdapter(Context context, List<Article> articles) {
    this.context = context;
    this.mInflater = LayoutInflater.from(context);
    this.articles = articles;
    this.db = FirebaseFirestore.getInstance();
  }

  public void update(List<Article> articles) {
    this.articles = articles;
  }

  @NonNull
  @Override
  public ArticleViewHolder onCreateViewHolder(
          @NonNull ViewGroup parent, int viewType) {

    View view = mInflater.inflate(
            R.layout.article_item, parent, false);

    return new ArticleViewHolder(view);
  }

  @Override
  public void onBindViewHolder(
          @NonNull ArticleViewHolder holder, int position) {

    Article article = articles.get(position);

    holder.tvTitle.setText(article.getArticle_title());
    holder.tvContent.setText(article.getArticle_description());
    holder.tvViews.setText("Views: " + article.getView());

    String imageUrl = article.getArticle_image();

    Picasso.get().cancelRequest(holder.imgCover);
    holder.imgCover.setImageDrawable(null);

    if (imageUrl != null && !imageUrl.trim().isEmpty()) {
      Picasso.get()
              .load(imageUrl)
              .resize(160, 160)
              .centerCrop()
              .into(holder.imgCover);
    }

    // Nhấn vào Article để tăng Views và xem chi tiết
    holder.itemView.setOnClickListener(v -> {

      int currentPosition = holder.getAdapterPosition();

      if (currentPosition == RecyclerView.NO_POSITION) {
        return;
      }

      Article selectedArticle = articles.get(currentPosition);

      // Tìm Article tương ứng trên Firestore
      db.collection("articles")
              .whereEqualTo(
                      "article_id",
                      selectedArticle.getArticle_id())
              .limit(2)
              .get()
              .addOnSuccessListener(snapshots -> {

                if (snapshots.size() != 1) {
                  Toast.makeText(context,
                          "Article ID missing or duplicated",
                          Toast.LENGTH_SHORT).show();
                  return;
                }

                // Lấy document
                com.google.firebase.firestore.DocumentReference docRef =
                        snapshots.getDocuments().get(0)
                                .getReference();

                // Tăng Views trực tiếp trên Firebase
                docRef.update(
                                "view",
                                FieldValue.increment(1))
                        .addOnSuccessListener(unused -> {

                          Intent intent = new Intent(
                                  context,
                                  ArticleDetailActivity.class);

                          intent.putExtra(
                                  "title",
                                  selectedArticle.getArticle_title());

                          intent.putExtra(
                                  "content",
                                  selectedArticle.getArticle_description());

                          intent.putExtra(
                                  "image",
                                  selectedArticle.getArticle_image());

                          intent.putExtra(
                                  "view",
                                  selectedArticle.getView() + 1);
                            Toast.makeText(context,
                                    "Opening Detail",
                                    Toast.LENGTH_SHORT).show();
                          context.startActivity(intent);
                        })
                        .addOnFailureListener(e -> {
                          Toast.makeText(context,
                                  "Error: " + e.getMessage(),
                                  Toast.LENGTH_LONG).show();
                        });

              })
              .addOnFailureListener(e -> {
                Toast.makeText(context,
                        "Error: " + e.getMessage(),
                        Toast.LENGTH_LONG).show();
              });
    });
  }

  @Override
  public int getItemCount() {
    return articles.size();
  }
}
