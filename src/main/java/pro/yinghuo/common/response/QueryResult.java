package pro.yinghuo.common.response;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * 用来存放返回的数据
 * @author clear
 * @param <T>
 */
@Data
@ToString
public class QueryResult<T> implements Serializable {
    /**
     * 数据列表
     */
    private List<T> list;
    /**
     * 数据总数
     */
    private long total;

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public QueryResult() {
    }

    public QueryResult(List<T> list) {
        this.list = list;
        this.total = list.size();
    }
}
