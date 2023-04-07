package cn.edu.cqu.boot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ToString
@TableName("problem")
public class Problem {

    private static final long serialVersionUID = 1L;

    @TableId(value = "problemId", type = IdType.AUTO)
    private Integer problemId;

    @TableField("problemDesc")
    private String problemDesc;

    @TableField("optionOne")
    private String optionOne;

    @TableField("optionTwo")
    private String optionTwo;

    @TableField("optionThree")
    private String optionThree;

    @TableField("optionFour")
    private String optionFour;

    @TableField("answer")
    private Integer answer;

    @TableField("isRight")
    private Boolean isRight;

    @TableField("isFalse")
    private Boolean isFalse;
}
