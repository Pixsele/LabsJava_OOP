package sql.models;

import javax.persistence.*;

@Entity
@Table(name = "results")
public class ResultEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    @JoinColumn(name = "resultsbyparametr", nullable = false)
    private ResultsByParametrEntity resultsByParametr;

    @Column(name = "result_value")
    private double resultValue;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ResultsByParametrEntity getResultsByParametr() {
        return resultsByParametr;
    }

    public void setResultsByParametr(ResultsByParametrEntity resultsByParametr) {
        this.resultsByParametr = resultsByParametr;
    }

    public double getResult_value() {
        return resultValue;
    }

    public void setResult_value(double resultValue) {
        this.resultValue = resultValue;
    }
}
