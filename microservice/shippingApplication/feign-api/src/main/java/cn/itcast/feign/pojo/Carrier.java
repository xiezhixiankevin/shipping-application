package cn.itcast.feign.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author xzx
 * @since 2023-04-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Carrier implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 承运商id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 承运商名
     */
    private String name;


}
