package ma.zs.dynamiquecall.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestDto {
    private String collectionName;
    private String pathVariable;
    private String bodyParams;
}
