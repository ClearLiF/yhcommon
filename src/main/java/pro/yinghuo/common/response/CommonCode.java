package pro.yinghuo.common.response;

import lombok.Getter;
import lombok.ToString;

/**
 * 2020.9.19
 *
 * @author blade
 */
@ToString
@Getter
public enum CommonCode implements ResultCode {
    //枚举类型
    INVALID_PARAM(false, 10003, "非法参数"),
    SUCCESS(true, 10000, "操作成功！"),
    SERVER_ERROR(false, 99999, "抱歉，系统繁忙，请稍后重试！"),
    BIND_INFORMATION(false, 99998, "请绑定个人信息再试"),
    FAIL(false, 11111, "操作失败！"),

    APPID_ERROR(false, 22222, "appid非法！"),
    TEST_PIC_FAILED(false, 11111, "请不要传违禁图片！");
    /**
     * 操作是否成功
     */
    boolean success;
    /**
     * 操作代码
     */
    int code;
    /**
     * 提示信息
     */
    String message;

    private CommonCode(boolean success, int code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }

    @Override
    public boolean success() {
        return success;
    }

    @Override
    public int code() {
        return code;
    }

    @Override
    public String message() {
        return message;
    }


}
