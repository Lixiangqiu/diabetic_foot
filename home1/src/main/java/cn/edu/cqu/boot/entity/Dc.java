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

import java.io.Serializable;

public class Dc implements Serializable {

    @TableId(value = "dcid", type = IdType.AUTO)
    private Integer dcId;

    @TableField("cpid")
    private Integer cpId;

    @TableField("doctorid")
    private Integer doctorId;

    @TableField("caseDesc")
    private String caseDesc;
}
