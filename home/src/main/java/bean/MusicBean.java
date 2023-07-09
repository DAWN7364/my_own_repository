package bean;

public class MusicBean {

    private String url;//路径
    private String title;//歌曲名
    private String artist;//艺术家
    private long duration;//歌曲时长
    private long id;
    private long album;//专辑图片

    public MusicBean() {

    }

    public MusicBean(String url, String title, String artist, long duration, long id, long album) {
        this.url = url;
        this.title = title;
        this.artist = artist;
        this.duration = duration;
        this.id = id;
        this.album = album;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setAlbum(long album) {
        this.album = album;
    }

    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public long getDuration() {
        return duration;
    }

    public long getId() {
        return id;
    }

    public long getAlbum() {
        return album;
    }
}
