package huahua.common;

import lombok.Data;

/**
 * 功能描述：公共的返回类
 * @Author LiHuaMing
 * @Date 16:13 2019/4/10
 * @Param
 * @return
 */
@Data
public class Result {
    //状态码
    private Integer code;
    //是否成功
    private boolean flag;
    //请求信息
    private String message;
    //响应的数据
    private Object data;

    public Result() {
    }

    public Result(Integer code, boolean flag, String message) {
        this.code = code;
        this.flag = flag;
        this.message = message;
    }

    public Result(Integer code, boolean flag, String message, Object data) {
        this.code = code;
        this.flag = flag;
        this.message = message;
        this.data = data;
    }
}
