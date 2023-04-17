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

import java.io.Serializable;

@Data
@TableName("dc")
public class Dc implements Serializable {

    @TableId(value = "dcId", type = IdType.AUTO)
    private Integer dcId;

    @TableField(value = "dcDate")
    private String dcDate;

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
