package all.persistence;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@MappedSuperclass
public abstract class ModelBase implements Serializable {

    private static final long serialVersionUID = -1234567890L;

    protected ModelBase() {
    }

    public abstract Long getId();

    public abstract void setId(Long id);
}
