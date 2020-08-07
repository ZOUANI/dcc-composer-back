package ma.zs.dynamiquecall.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Request implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String url;
    private String httpMethod;
    private int num;
    private String pathVariableName;
    private int pathVariableResponseSource;
    private String pathVariableValue;
    private String response;
    @JsonIgnore
    @ManyToOne
    @ToString.Exclude
    private RequestCollection requestCollection;
    @OneToMany(mappedBy = "request")
    private List<BodyParam> bodyParams;
}


