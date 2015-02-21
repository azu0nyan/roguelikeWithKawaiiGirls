package roguelike.DAO.prototypes;

import it.biobytes.ammentos.AutomaticType;
import it.biobytes.ammentos.PersistentEntity;
import it.biobytes.ammentos.PersistentField;
import roguelike.DAO.HasId;

/**
 * Created with IntelliJ IDEA.
 * User: nik
 * Date: 17.01.14
 * Time: 12:22
 * To change this template use File | Settings | File Templates.
 */
@PersistentEntity(
        sourceDomain = "CONNECTED_BODY_PARTS",
        targetDomain = "CONNECTED_BODY_PARTS",
        primaryKey = "id"
)
public class ConnectedBodyPartPrototype implements HasId{
    @PersistentField(
            fieldName = "id",
            automaticType = AutomaticType.FRAMEWORK
    )
    int id;

    @PersistentField(
            fieldName = "partID",
            automaticType = AutomaticType.FRAMEWORK
    )
    int partId;

    @PersistentField(
            fieldName = "ownerId",
            automaticType = AutomaticType.FRAMEWORK
    )
    int ownerId;

    @Override
    public int getID() {
        return id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPartId() {
        return partId;
    }

    public void setPartId(int partId) {
        this.partId = partId;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

}
