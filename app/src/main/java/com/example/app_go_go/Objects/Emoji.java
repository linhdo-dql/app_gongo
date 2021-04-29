package com.example.app_go_go.Objects;

public class Emoji {
    public int id;
    public String emoji;

    public Emoji(int id, String emoji) {
        this.id = id;
        this.emoji = emoji;
    }

    public Emoji() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmoji() {
        return emoji;
    }

    public void setEmoji(String emoji) {
        this.emoji = emoji;
    }

    @Override
    public String toString() {
        return "Emoji{" +
                "id=" + id +
                ", emoji='" + emoji + '\'' +
                '}';
    }
}
