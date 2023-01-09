package pro.yinghuo.common.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


/**
 * The type Response result.
 *
 * @author clearli
 */
@Data
@ToString
@NoArgsConstructor
public class ResponseResult implements Response {

    /**
     * 操作是否成功
     */
    boolean success = SUCCESS;

    /**
     * 操作代码
     */
    int code = SUCCESS_CODE;

    /**
     * 提示信息
     */
    String message;

    /**
     * Instantiates a new Response result.
     *
     * @param resultCode the result code
     */
    public ResponseResult(ResultCode resultCode){
        this.success = resultCode.success();
        this.code = resultCode.code();
        this.message = resultCode.message();
    }

    /**
     * Success response result.
     *
     * @return the response result
     */
    public static ResponseResult success(){
        return new ResponseResult(CommonCode.SUCCESS);
    }

    /**
     * Fail response result.
     *
     * @return the response result
     */
    public static ResponseResult fail(){
        return new ResponseResult(CommonCode.FAIL);
    }

}
