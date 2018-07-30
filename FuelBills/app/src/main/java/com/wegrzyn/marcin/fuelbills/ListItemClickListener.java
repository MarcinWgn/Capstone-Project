package com.wegrzyn.marcin.fuelbills;

/**
 * Created by Marcin Węgrzyn on 30.07.2018.
 * wireamg@gmail.com
 */
public interface ListItemClickListener {
    void onItemClick(int itemIndex);
    void onItemDelete(int itemDelete);
    void editItemData(int id);
}
