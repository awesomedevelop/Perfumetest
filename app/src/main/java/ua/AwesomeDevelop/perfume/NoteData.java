package ua.AwesomeDevelop.perfume;

/**
 * Created by Taras on 22.01.2015.
 */
public class NoteData {
    String note_name;
    Boolean isSelected;

    public NoteData(String note_name,boolean isSelected){
    this.note_name = note_name;
        this.isSelected = isSelected;
    }
public String getNote_name(){
        return note_name;
    }
    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

}
