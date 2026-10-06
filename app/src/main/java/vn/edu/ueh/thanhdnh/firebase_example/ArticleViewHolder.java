
package vn.edu.ueh.thanhdnh.firebase_example;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ArticleViewHolder extends RecyclerView.ViewHolder {

  public TextView tvTitle;
  public TextView tvContent;
  public TextView tvViews;
  public ImageView imgCover;

  public ArticleViewHolder(@NonNull View itemView) {
    super(itemView);

    tvTitle = itemView.findViewById(R.id.tvTitle);
    tvContent = itemView.findViewById(R.id.tvContent);
    tvViews = itemView.findViewById(R.id.tvViews);
    imgCover = itemView.findViewById(R.id.imgCover);
  }
}
