/**
 * @Classname Dc
 * @Description TODO
 * @Date 2023/4/8 15:34
 * @Created by WangSanmu
 */

package cn.edu.cqu.boot.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ToString
@TableName("dc")
public class Dc implements Serializable {

    @TableId(value = "dcId", type = IdType.AUTO)
    private Integer dcId;

    @TableField(value = "dcDate")
    private LocalDate dcDate;

    @TableField("cpId")
    private Integer cpId;

    @TableField("doctorId")
    private Integer doctorId;

    @TableField("doctorCon")
    private String doctorCon;

    @TableField(exist = false)
    private String doctorName;

    @TableField(exist = false)
    private String doctorPosition;
}
