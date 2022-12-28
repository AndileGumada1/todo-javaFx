package com.andile.todolist.utils;

public enum TodoCategory {
    EXERCISE("🚴"),
    WORK("📊"),
    RELAX("🧘"),
    TV("📺"),
    READ("📚"),
    EVENT("🎭"),
    CODE("💻"),
    COFFEE("☕️"),
    EAT("🍽"),
    SHOP("🛒"),
    SLEEP("😴");
    private String emoji;

    TodoCategory(String emoji) {
        this.emoji = emoji;
    }

    public String getEmoji() {
        return emoji;
    }
}
