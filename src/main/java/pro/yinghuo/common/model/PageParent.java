package pro.yinghuo.common.model;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: zhanghp
 * @date: 2022-06-25 1:02
 */
@ApiModel(value = "分页")
@Data
public class PageParent {

    @ApiModelProperty(value = "页数")
    private Integer pageNum = 1;

    @ApiModelProperty(value = "页大小")
    private Integer pageSize = 10;

    @Override
    public String toString() {
        return "PageParent{" +
                "pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                '}';
    }

    public PageParent() {
    }

    public PageParent(Integer pageNum, Integer pageSize) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }

}
