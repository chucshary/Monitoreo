package shary.monitoreo;

/**
 * Created by Shary on 21/10/2015.
 */
public class DescripcionPaciente {
    String photo;
    String name;
    String category;

    public DescripcionPaciente(String photo, String name, String category) {
        this.photo = photo;
        this.name = name;
        this.category = category;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
