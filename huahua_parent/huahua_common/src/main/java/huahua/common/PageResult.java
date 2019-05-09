package huahua.common;

import lombok.Data;

import java.util.List;
/**
 * 功能描述：PageResult
 * @Author LiHuaMing
 * @Date 16:25 2019/4/10
 * @Param
 * @return
 */
@Data
public class PageResult<T> {

    private Long total;

    private List<T> rows;

    public PageResult(Long total, List<T> rows) {
        this.total = total;
        this.rows = rows;
    }

    public PageResult() {
    }
}
