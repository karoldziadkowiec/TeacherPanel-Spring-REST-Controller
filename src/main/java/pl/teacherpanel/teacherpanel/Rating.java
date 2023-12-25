package pl.teacherpanel.teacherpanel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Rating {
    private int id;
    private int groupID;
    private String groupName;
    private int rating;
    public Rating(int groupID, int rating) {
        this.groupID = groupID;
        this.rating = rating;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGroupID() {
        return groupID;
    }

    public void setGroupID(int groupID) {
        this.groupID = groupID;
    }

    public int getRating() {
        return rating;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}