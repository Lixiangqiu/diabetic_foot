/**
 * @Classname PasswordModify
 * @Description TODO
 * @Date 2023/4/13 17:00
 * @Created by WangSanmu
 */
package cn.edu.cqu.boot.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;
import java.io.Serializable;
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ToString
public class PasswordModify {
    private String id;
    private String oldPassword;
    private String newPassword;
}
