package p.lodz.Model;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

import java.io.Serializable;

@NoArgsConstructor
@Getter
@Setter
public class AbstractEntity implements Serializable {

    @BsonProperty("_id")
    private ObjectId entityId;
    public AbstractEntity(ObjectId entityId) {
        this.entityId = entityId;
    }
}