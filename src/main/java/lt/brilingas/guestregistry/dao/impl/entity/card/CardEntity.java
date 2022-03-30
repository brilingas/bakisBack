package lt.brilingas.guestregistry.dao.impl.entity.card;
import lombok.Data;
import lt.brilingas.guestregistry.data.dto.card.CardType;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Reference;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;

@Data
@Document(collection = "card")
public class CardEntity {
    @Id
    private String id;
    private Date validFrom;
    private Date validTo;
    //@Reference
    private String locationId;
    private CardType cardType;
    private boolean cardAvailability;
}
