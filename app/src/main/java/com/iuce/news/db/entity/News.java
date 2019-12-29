package com.iuce.news.db.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

/**
 * @apiNote Tekrarlı URL'lere izin vermez
 * @see <a href="https://android.yemreak.com/veriler/room-database#entity-yapisi">Room Database ~ YEmreAk</a>
 * @see <a href="https://stackoverflow.com/a/48962768/9770490">Unique Contstraits</a>
 */
@Entity(tableName = News.TABLE_NAME, indices = {@Index(value = News.COLUMN_URL, unique = true)})
public class News {

    public static final String TABLE_NAME = "news_table";
    public static final String COLUMN_ID = "nid";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_URL_TO_IMAGE = "url_to_image";
    public static final String COLUMN_CONTENT = "content";
    public static final String COLUMN_SOURCE = "source";
    public static final String COLUMN_PUBLISHED_AT = "published_at";
    public static final String COLUMN_URL = "url";

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = COLUMN_ID)
    private int id;

    @ColumnInfo(name = COLUMN_TITLE)
    private String title;

    @ColumnInfo(name = COLUMN_DESCRIPTION)
    private String description;

    @ColumnInfo(name = COLUMN_URL_TO_IMAGE)
    private String urlToImage;

    @ColumnInfo(name = COLUMN_URL)
    private String url;

    @ColumnInfo(name = COLUMN_CONTENT)
    private String content;

    @ColumnInfo(name = COLUMN_SOURCE)
    private String source;

    @ColumnInfo(name = COLUMN_PUBLISHED_AT)
    private String publishedAt;

    public News(String title, String description, String urlToImage, String url, String content, String source, String publishedAt) {
        this.title = title;
        this.description = description;
        this.urlToImage = urlToImage;
        this.url = url;
        this.content = content;
        this.source = source;
        this.publishedAt = publishedAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getPublishedAt() {

        return publishedAt.substring(0, 10);
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


}
