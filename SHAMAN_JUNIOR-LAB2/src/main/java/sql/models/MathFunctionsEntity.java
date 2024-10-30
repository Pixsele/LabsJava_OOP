package sql.models;


import function.api.MathFunction;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "functions")

public class MathFunctionsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "function_name")
    private String functionName;

    @Column(name = "function_type")
    private String functionType;

    @Column(name = "creation_time")
    private LocalDateTime creationTime = LocalDateTime.now();

    @OneToMany(mappedBy = "function", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ResultsByParametrEntity> resultsByParametrList;

    public String getFunction_name() {
        return functionName;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setFunction_name(String function_name) {
        this.functionName = function_name;
    }

    public String getFunction_type() {
        return functionType;
    }

    public void setFunction_type(String function_type) {
        this.functionType = function_type;
    }

    public LocalDateTime getCreation_time() {
        return creationTime;
    }

    public void setCreation_time(LocalDateTime creation_time) {
        this.creationTime = creation_time;
    }

    public List<ResultsByParametrEntity> getResultsByParametrList() {
        return resultsByParametrList;
    }

    public void setResultsByParametrList(List<ResultsByParametrEntity> resultsByParametr) {
        this.resultsByParametrList = resultsByParametr;
    }
}
