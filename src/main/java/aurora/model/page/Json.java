package aurora.model.page;

import org.springframework.stereotype.Repository;

/**
 * 后台向前台返回的包含简单信息的JSON对象
 * @param <T>
 */
@Repository
public class Json<T> implements java.io.Serializable {

    private boolean success = false;

    private String msg = "";

    private T obj = null;



    /** default constructor */
    public Json(){

    }

    /** full constructor */
    public  Json(boolean success, String msg, T obj) {
        super();
        this.success = success;
        this.msg = msg;
        this.obj = obj;
    }

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getObj() {
        return obj;
    }

    public void setObj(T obj) {
        this.obj = obj;
    }

}
