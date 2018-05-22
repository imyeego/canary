package greendao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Administrator on 2017/12/24 0024.
 */
@Entity
public class Account {
    @Id
    private Long id;
    @Property(nameInDb = "TITLE")
    private String title;
    @Property(nameInDb = "MONEY")
    private String money;
    @Property(nameInDb = "TIME")
    private String time;
    @Generated(hash = 833934907)
    public Account(Long id, String title, String money, String time) {
        this.id = id;
        this.title = title;
        this.money = money;
        this.time = time;
    }
    @Generated(hash = 882125521)
    public Account() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getMoney() {
        return this.money;
    }
    public void setMoney(String money) {
        this.money = money;
    }
    public String getTime() {
        return this.time;
    }
    public void setTime(String time) {
        this.time = time;
    }
}
