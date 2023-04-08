/**
 * @Classname Dc
 * @Description TODO
 * @Date 2023/4/8 15:34
 * @Created by WangSanmu
 */

package cn.edu.cqu.boot.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

public class Dc implements Serializable {

    @TableId(value = "Dcid", type = IdType.AUTO)
    private Integer Dcid;
}
