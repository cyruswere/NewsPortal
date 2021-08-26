package models;

import java.util.ArrayList;
import java.util.Objects;

public class News {
    private int id;
    private String header;
    private String content;
    private String written_by;


    public News(String header, String content, String written_by) {
        this.header = header;
        this.content = content;
        this.written_by = written_by;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getWritten_by() {
        return written_by;
    }

    public void setWritten_by(String written_by) {
        this.written_by = written_by;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        News news = (News) o;
        return id == news.id && header.equals(news.header) && content.equals(news.content) && written_by.equals(news.written_by);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, header, content, written_by);
    }

}
