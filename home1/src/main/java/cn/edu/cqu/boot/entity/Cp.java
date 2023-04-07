package cn.edu.cqu.boot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ToString
@TableName("cp")
public class Cp {
    private static final long serialVersionUID = 1L;

    @TableId(value = "cpId", type = IdType.AUTO)
    private Integer cpId;

    @TableField("doctorId")
    private Integer doctorId;

    @TableField("patientId")
    private Integer patientId;

    @TableField("date")
    private LocalDate date;

    @TableField("paraT1")
    private Float paraT1;

    @TableField("paraM1")
    private Float paraM1;

    @TableField("paraM2")
    private Float paraM2;

    @TableField("paraM3")
    private Float paraM3;

    @TableField("paraM4")
    private Float paraM4;

    @TableField("paraM5")
    private Float paraM5;

    @TableField("paraHL")
    private Float paraHL;

    @TableField("paraHM")
    private Float paraHM;

    @TableField("caseDesc")
    private String caseDesc;

    @TableField("paraRT1")
    private Float paraRT1;

    @TableField("paraRM1")
    private Float paraRM1;

    @TableField("paraRM2")
    private Float paraRM2;

    @TableField("paraRM3")
    private Float paraRM3;

    @TableField("paraRM4")
    private Float paraRM4;

    @TableField("paraRM5")
    private Float paraRM5;

    @TableField("paraRHL")
    private Float paraRHL;

    @TableField("paraRHM")
    private Float paraRHM;

}
