package sql.models;


import javax.persistence.*;

@Entity
@Table(name = "points")
public class PointEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "function")
    private MathFunctionsEntity function;

    @Column(name = "x")
    private Double x;

    @Column(name = "y")
    private Double y;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MathFunctionsEntity getFunction() {
        return function;
    }

    public void setFunction(MathFunctionsEntity function) {
        this.function = function;
    }

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }
}
