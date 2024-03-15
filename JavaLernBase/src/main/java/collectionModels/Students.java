package collectionModels;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Students {
    private String fullName;
    private Integer course;

    @Override
    public String toString() {
        return "fullName: " + fullName +
                " course: " + course;
    }
}
