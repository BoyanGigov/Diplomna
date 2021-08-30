package all.component.diplomna.model;

import all.persistence.ModelBase;
import javax.persistence.*;

@Entity
@Table(name = "DATA_EXAMPLE")
public class DataMO extends ModelBase {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "DataMOGenerator")
    @SequenceGenerator(name = "DataMOGenerator", sequenceName = "DATA_SEQ")
    private Long id;

    @Column(name = "DATA", nullable = false)
    private String data;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
