/**
 * @Classname UserAccount
 * @Description TODO
 * @Date 2023/4/12 15:41
 * @Created by WangSanmu
 */
package cn.edu.cqu.boot.entity;

import lombok.Data;
import java.io.Serializable;

@Data
public class UserAccount implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;
    private String password;
    private String email;
}
