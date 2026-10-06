
package vn.edu.ueh.thanhdnh.firebase_example;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class ArticleDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);

        ImageView imgCover = findViewById(R.id.imgDetailCover);
        TextView tvTitle = findViewById(R.id.tvDetailTitle);
        TextView tvContent = findViewById(R.id.tvDetailContent);
        TextView tvViews = findViewById(R.id.tvDetailViews);

        String title = getIntent().getStringExtra("title");
        String content = getIntent().getStringExtra("content");
        String imageUrl = getIntent().getStringExtra("image");
        long views = getIntent().getLongExtra("view", 0);

        tvTitle.setText(title);
        tvContent.setText(content);
        tvViews.setText("Views: " + views);

        if (imageUrl != null && !imageUrl.trim().isEmpty()) {
            Picasso.get()
                    .load(imageUrl)
                    .resize(800, 500)
                    .centerCrop()
                    .into(imgCover);
        }

        findViewById(R.id.btBack)
                .setOnClickListener(v -> finish());
    }
}
