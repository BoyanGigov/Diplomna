package all.component.greeting;

public class GreetingMO {

    private long id;
    private String content;

    public GreetingMO() {

    }

    public GreetingMO(long id, String content) {
        this.id = id;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
