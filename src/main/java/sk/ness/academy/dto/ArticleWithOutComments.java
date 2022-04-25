package sk.ness.academy.dto;

import java.util.Date;

public class ArticleWithOutComments {
    private Integer id;
    private String title;
    private String text;
    private String author;
    private Date createTimestamp;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getCreateTimestamp() {
        return createTimestamp;
    }

    public void setCreateTimestamp(Date createTimestamp) {
        this.createTimestamp = createTimestamp;
    }

    public ArticleWithOutComments(Integer id, String title, String text, String author, Date createTimestamp) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.author = author;
        this.createTimestamp = createTimestamp;
    }
}
