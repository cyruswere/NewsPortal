package models;

import java.util.Objects;

public class News {
    private String title;
    private String content;
    private int  authorId;
    private int id;


    public News(String title, String content,int authorId){
        this.title = title;
        this.content = content;
        this.authorId = authorId;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        News news = (News) o;
        return authorId == news.authorId && id == news.id &&  title.equals(news.title) && content.equals(news.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, content, authorId, id);
    }
}
