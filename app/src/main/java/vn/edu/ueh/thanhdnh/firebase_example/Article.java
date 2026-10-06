package vn.edu.ueh.thanhdnh.firebase_example;

public class Article {

  private int article_id;
  private String article_title;
  private String article_image;
  private String article_description;
  private long view;

  public Article() {
  }

  public Article(int article_id, String article_title,
                 String article_image, String article_description) {
    this.article_id = article_id;
    this.article_title = article_title;
    this.article_image = article_image;
    this.article_description = article_description;
    this.view = 0;
  }

  public int getArticle_id() {
    return article_id;
  }

  public void setArticle_id(int article_id) {
    this.article_id = article_id;
  }

  public String getArticle_title() {
    return article_title;
  }

  public void setArticle_title(String article_title) {
    this.article_title = article_title;
  }

  public String getArticle_image() {
    return article_image;
  }

  public void setArticle_image(String article_image) {
    this.article_image = article_image;
  }

  public String getArticle_description() {
    return article_description;
  }

  public void setArticle_description(String article_description) {
    this.article_description = article_description;
  }

  public long getView() {
    return view;
  }

  public void setView(long view) {
    this.view = view;
  }
}
