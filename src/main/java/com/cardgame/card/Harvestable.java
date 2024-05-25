package com.cardgame.card;

import java.util.ArrayList;

public interface Harvestable {
    public String harvest();
    public boolean readyToHarvest();
    public boolean hasProtect();
    public boolean hasTrap();
    public void addItem(String item);
    public ArrayList<String> getItems();
    public String getItemDetails();
}
