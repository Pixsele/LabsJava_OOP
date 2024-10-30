package sql.models;

import javax.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity

@Table(name = "resultsbyparametr")
public class ResultsByParametrEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "function_id")
    private MathFunctionsEntity function;

    @Column(name = "creation_time")
    private LocalDateTime timestamp = LocalDateTime.now();

    @Column(name = "parametr")
    private double parametr;

    @OneToOne(mappedBy = "resultsByParametr", cascade = CascadeType.ALL)
    private ResultEntity result;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public MathFunctionsEntity getMathFunction() {
        return function;
    }

    public void setMathFunction(MathFunctionsEntity mathFunction) {
        this.function = mathFunction;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public double getParametr() {
        return parametr;
    }

    public void setParametr(double parametr) {
        this.parametr = parametr;
    }
}
