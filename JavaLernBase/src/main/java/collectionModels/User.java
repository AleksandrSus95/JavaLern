package collectionModels;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.util.UUID;

@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User implements Comparable<User> {
    private String name;
    private LocalDate birthDay;
    private UUID uuid;

    @Override
    public String toString() {
        return "name: " + name +
                " birthDay: " + birthDay;
    }

    @Override
    public int compareTo(User user) {
        return this.uuid.compareTo(user.getUuid());
    }
}
