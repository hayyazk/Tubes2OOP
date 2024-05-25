package com.cardgame.cardcontainer;

import com.cardgame.card.*;

import java.util.ArrayList;
import java.util.HashMap;

public class CardFactory {
    private static HashMap<String, Animal> animals;
    private static HashMap<String, Product> products;
    private static HashMap<String, Plant> plants;
    private static HashMap<String, Item> items;

    public static void init() {
        animals = new HashMap<>();
        products = new HashMap<>();
        plants = new HashMap<>();
        items = new HashMap<>();
        setAnimals();
        setProducts();
        setPlants();
        setItems();
    }

    private static void setAnimals() {
        animals.put("HIU_DARAT", new Animal("Hiu Darat",
                "Hewan/hiu_darat.png", "HIU_DARAT", 0, 20,
                "SIRIP_HIU", "CARNIVORE"));
        animals.put("SAPI", new Animal("Sapi",
                "Hewan/sapi.png", "SAPI", 0, 10,
                "SUSU", "HERBIVORE"));
        animals.put("DOMBA", new Animal("Domba",
                "Hewan/domba.png", "DOMBA", 0, 12,
                "DAGING_DOMBA", "HERBIVORE"));
        animals.put("KUDA", new Animal("Kuda",
                "Hewan/kuda.png", "KUDA", 0, 14,
                "DAGING_KUDA", "HERBIVORE"));
        animals.put("AYAM", new Animal("Ayam",
                "Hewan/ayam.png", "AYAM", 0, 5,
                "TELUR", "OMNIVORE"));
        animals.put("BERUANG", new Animal("Beruang",
                "Hewan/beruang.png", "BERUANG", 0, 25,
                "DAGING_BERUANG", "OMNIVORE"));
    }

    private static void setPlants() {
        plants.put("BIJI_JAGUNG", new Plant("Biji Jagung",
                "Tanaman/biji_jagung.png", "BIJI_JAGUNG", 0, 3, "JAGUNG"));
        plants.put("BIJI_LABU", new Plant("Biji Labu",
                "Tanaman/biji_labu.png", "BIJI_LABU", 0, 5, "LABU"));
        plants.put("BIJI_STROBERI", new Plant("Biji Stroberi",
                "Tanaman/biji_stroberi.png", "BIJI_STROBERI", 0, 4, "STROBERI"));
    }

    private static void setProducts() {
        products.put("SIRIP_HIU", new Product("Sirip Hiu",
                "Produk/sirip_hiu.png", "SIRIP_HIU", 500, 12, "CARNIVORE"));
        products.put("SUSU", new Product("Susu",
                "Produk/susu.png", "SUSU", 100, 4, "CARNIVORE"));
        products.put("DAGING_DOMBA", new Product("Daging Domba",
                "Produk/daging_domba.png", "DAGING_DOMBA", 120, 6, "CARNIVORE"));
        products.put("DAGING_KUDA", new Product("Daging Kuda",
                "Produk/daging_kuda.png", "DAGING_KUDA", 150, 8, "CARNIVORE"));
        products.put("TELUR", new Product("Telur",
                "Produk/telur.png", "TELUR", 50, 2, "CARNIVORE"));
        products.put("JAGUNG", new Product("Jagung",
                "Produk/jagung.png", "JAGUNG", 150, 3, "HERBIVORE"));
        products.put("LABU", new Product("Labu",
                "Produk/labu.png", "LABU", 500, 10, "HERBIVORE"));
        products.put("STROBERI", new Product("Stroberi",
                "Produk/stroberi.png", "STROBERI", 350, 5, "HERBIVORE"));
    }

    public static void setItems() {
        items.put("ACCELERATE", new Item("Accelerate", "Item/accelerate.png", "ACCELERATE", "+8 animal weight/+2 plant age"));
        items.put("DELAY", new Item("Delay", "Item/delay.png", "DELAY", "-5 animal weight/-2 plant age"));
        items.put("INSTANT_HARVEST", new Item("Instant Harvest", "Item/instant_harvest.png", "INSTANT_HARVEST", "Instantly harvest an animal/plant"));
        items.put("DESTROY", new Item("Destroy", "Item/destroy.png", "DESTROY", "Destroy the enemy's animal/plant"));
        items.put("PROTECT", new Item("Protect", "Item/protect.png", "PROTECT", "Protect animal/plant from destruction"));
        items.put("TRAP", new Item("Trap", "Item/trap.png", "TRAP", "Trap bears"));
    }

    public static Animal createAnimal(String name) {
        return new Animal(animals.get(name));
    }

    public static Plant createPlant(String name) {
        return new Plant(plants.get(name));
    }

    public static Product createProduct(String name) {
        return new Product(products.get(name));
    }

    public static Item createItem(String name) {
        return new Item(items.get(name));
    }

    public static Card createCard(String name) {
        if (animals.containsKey(name)) {
            return new Animal(animals.get(name));
        } else if (plants.containsKey(name)) {
            return new Plant(plants.get(name));
        } else if (products.containsKey(name)) {
            return new Product(products.get(name));
        } else if (items.containsKey(name)) {
            return new Item(items.get(name));
        }
        return null;
    }

    public static ArrayList<String> animalNames() {
        return new ArrayList<>(animals.keySet());
    }
    public static ArrayList<String> plantNames() {
        return new ArrayList<>(plants.keySet());
    }
    public static ArrayList<String> productNames() {
        return new ArrayList<>(products.keySet());
    }
    public static ArrayList<String> itemNames() {
        return new ArrayList<>(items.keySet());
    }
}
