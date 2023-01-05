package pro.yinghuo.common.response;

import cn.hutool.core.collection.CollectionUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author blade
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class QueryResponseResult<T> extends ResponseResult implements Serializable {

    private QueryResult<T> queryResult;

    public QueryResponseResult(ResultCode resultCode) {
        super(resultCode);
    }

    public QueryResponseResult(ResultCode resultCode, QueryResult<T> queryResult) {
        super(resultCode);
        this.queryResult = queryResult;
    }

    public QueryResponseResult(ResultCode resultCode, T t) {
        super(resultCode);
        QueryResult<T> queryResult = new QueryResult<>();
        queryResult.setList(Lists.newArrayList(t));
        this.queryResult = queryResult;
    }

    /**
     * 获取当前列表中的第一个值（用于list中只有一个值的情况）
     *
     * @return T
     * @author ClearLi
     * @date 2020/10/24 11:03
     */
    @JsonIgnore
    public T getIndex0() {
        if (queryResult != null && CollectionUtil.isNotEmpty(queryResult.getList())) {
            return queryResult.getList().get(0);
        }
        return null;
    }

    @JsonIgnore
    public QueryResponseResult<T> getEmptyList() {
        this.success = CommonCode.SUCCESS.success();
        this.code = CommonCode.SUCCESS.code();
        this.message = CommonCode.SUCCESS.message();
        QueryResult<T> queryResult = new QueryResult<>();
        queryResult.setList(Lists.newArrayList());
        this.queryResult = queryResult;
        return this;
    }

    //Singleton
    @JsonIgnore
    public QueryResponseResult<T> getSingletonResponse(T t) {
        this.success = CommonCode.SUCCESS.success();
        this.code = CommonCode.SUCCESS.code();
        this.message = CommonCode.SUCCESS.message();
        QueryResult<T> queryResult = new QueryResult<>();
        queryResult.setList(Lists.newArrayList(t));
        this.queryResult = queryResult;
        return this;
    }
}
