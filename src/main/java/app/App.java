package app;

import lombok.Getter;

public class App {
    private static @Getter
    final String backendBaseUrl = "http://127.0.0.1:8000";

    public static void main(String[] args) {
        System.out.println("""
                Béreslegény, jól megrakd a szekeret,
                sarjútüske böködi a tenyered.
                Mennél jobban böködi a tenyered,
                annál jobban rakd meg a szekeredet.
                """);
    }
}
