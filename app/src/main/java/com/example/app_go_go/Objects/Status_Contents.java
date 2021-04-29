package com.example.app_go_go.Objects;

public class Status_Contents {
    public String id_stt, acc_stt, content_stt ,time_stt ,emoji_stt, link_emoji_stt, location_stt, per_stt;

    public Status_Contents() {

    }

    public String getLink_emoji_stt() {
        return link_emoji_stt;
    }

    public void setLink_emoji_stt(String link_emoji_stt) {
        this.link_emoji_stt = link_emoji_stt;
    }

    public Status_Contents(String id_stt, String acc_stt, String content_stt, String time_stt, String emoji_stt, String link_emoji_stt, String location_stt, String per_stt) {
        this.id_stt = id_stt;
        this.acc_stt = acc_stt;
        this.content_stt = content_stt;
        this.time_stt = time_stt;
        this.emoji_stt = emoji_stt;
        this.link_emoji_stt = link_emoji_stt;
        this.location_stt = location_stt;
        this.per_stt = per_stt;
    }

    public String getId_stt() {
        return id_stt;
    }

    public void setId_stt(String id_stt) {
        this.id_stt = id_stt;
    }

    public String getAcc_stt() {
        return acc_stt;
    }

    public void setAcc_stt(String acc_stt) {
        this.acc_stt = acc_stt;
    }

    public String getContent_stt() {
        return content_stt;
    }

    public void setContent_stt(String content_stt) {
        this.content_stt = content_stt;
    }

    public String getTime_stt() {
        return time_stt;
    }

    public void setTime_stt(String time_stt) {
        this.time_stt = time_stt;
    }

    public String getEmoji_stt() {
        return emoji_stt;
    }

    public void setEmoji_stt(String emoji_stt) {
        this.emoji_stt = emoji_stt;
    }

    public String getLocation_stt() {
        return location_stt;
    }

    public void setLocation_stt(String location_stt) {
        this.location_stt = location_stt;
    }

    public String getPer_stt() {
        return per_stt;
    }

    public void setPer_stt(String per_stt) {
        this.per_stt = per_stt;
    }
}
