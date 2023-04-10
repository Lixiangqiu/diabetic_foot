/**
 * @Classname UserLoginReponse
 * @Description TODO
 * @Date 2023/4/9 17:28
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
@TableName("comment")
public class UserLoginResponse implements Serializable {

    private Integer id;
    private String name;
    private String password;
    private String email;
}
