package sg.edu.rp.c346.wheredidiputit3;

import java.io.Serializable;

/**
 * Created by 15017608 on 28/11/2017.
 */

public class Item implements Serializable {
    private int id;
    private String itemTitle;
    private String itemPlace;

    public Item(int id, String itemTitle, String itemPlace) {
        this.id = id;
        this.itemTitle = itemTitle;
        this.itemPlace = itemPlace;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItemTitle() {
        return itemTitle;
    }

    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }

    public String getItemPlace() {
        return itemPlace;
    }

    public void setItemPlace(String itemPlace) {
        this.itemPlace = itemPlace;
    }

    @Override
    public String toString() {
        return "Item: " + itemTitle
                + "\n Place: " + itemPlace;
    }
}
