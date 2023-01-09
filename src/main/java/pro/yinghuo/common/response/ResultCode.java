package pro.yinghuo.common.response;

/**
 * The interface Result code.
 *
 * @author clearli
 * @date 2018 /3/5 10000-- 通用错误代码
 */
public interface ResultCode {
    /**
     * 操作是否成功,true为成功，false操作失败
     *
     * @return the boolean
     */
    boolean success();

    /**
     * 操作代码
     *
     * @return the int
     */
    int code();

    /**
     * 提示信息
     *
     * @return the string
     */
    String message();

}
