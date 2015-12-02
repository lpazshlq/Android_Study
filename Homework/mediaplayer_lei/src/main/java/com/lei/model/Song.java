package com.lei.model;

import java.io.Serializable;

public class Song implements Serializable {
    private String album_ID;//专辑ID
    private String album_Name;//专辑名称
    private String album_Pic_Big;//专辑大图片，高宽300
    private String album_Pic_Small;//专辑小图片，高宽90
    private String down_Url;//mp3下载链接
    private String stream_Url;//流媒体地址
    private String singer_ID;//歌手ID
    private String singer_Name;//歌手名
    private String song_ID;//歌曲ID
    private String song_Name;//歌曲名称

    public String getAlbum_ID() {
        return album_ID;
    }

    public void setAlbum_ID(String album_ID) {
        this.album_ID = album_ID;
    }

    public String getAlbum_Name() {
        return album_Name;
    }

    public void setAlbum_Name(String album_Name) {
        this.album_Name = album_Name;
    }

    public String getAlbum_Pic_Big() {
        return album_Pic_Big;
    }

    public void setAlbum_Pic_Big(String album_Pic_Big) {
        this.album_Pic_Big = album_Pic_Big;
    }

    public String getAlbum_Pic_Small() {
        return album_Pic_Small;
    }

    public void setAlbum_Pic_Small(String album_Pic_Small) {
        this.album_Pic_Small = album_Pic_Small;
    }

    public String getDown_Url() {
        return down_Url;
    }

    public void setDown_Url(String down_Url) {
        this.down_Url = down_Url;
    }

    public String getStream_Url() {
        return stream_Url;
    }

    public void setStream_Url(String stream_Url) {
        this.stream_Url = stream_Url;
    }

    public String getSinger_ID() {
        return singer_ID;
    }

    public void setSinger_ID(String singer_ID) {
        this.singer_ID = singer_ID;
    }

    public String getSinger_Name() {
        return singer_Name;
    }

    public void setSinger_Name(String singer_Name) {
        this.singer_Name = singer_Name;
    }

    public String getSong_ID() {
        return song_ID;
    }

    public void setSong_ID(String song_ID) {
        this.song_ID = song_ID;
    }

    public String getSong_Name() {
        return song_Name;
    }

    public void setSong_Name(String song_Name) {
        this.song_Name = song_Name;
    }
}
