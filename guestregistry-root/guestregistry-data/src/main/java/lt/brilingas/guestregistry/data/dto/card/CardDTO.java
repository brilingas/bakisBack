package lt.brilingas.guestregistry.data.dto.card;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import javax.validation.constraints.*;
import java.util.Date;
@Data
public class CardDTO {
   // @NotBlank
    //@Size(min = 1,max = 5)
    private String id;
    //@PastOrPresent
    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss X")
    private Date validFrom;
    //@Future
    private Date validTo;
    private String locationId;
    //@NotBlank
    private CardType cardType;
    private boolean cardAvailability;
}