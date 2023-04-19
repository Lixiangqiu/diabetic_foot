package cn.edu.cqu.boot.controller;


import cn.edu.cqu.boot.config.FileDelete;
import cn.edu.cqu.boot.config.Result;
import cn.edu.cqu.boot.config.Hash;
import cn.edu.cqu.boot.entity.*;
import cn.edu.cqu.boot.mapper.AllUserMapper;
import cn.edu.cqu.boot.mapper.DoctorMapper;
import cn.edu.cqu.boot.mapper.PatientMapper;
import cn.edu.cqu.boot.service.IAllUserService;
import cn.edu.cqu.boot.service.IDoctorService;
import cn.edu.cqu.boot.service.IPatientService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

@RestController
@RequestMapping("/allUser")
public class AllUserController {

    @Resource
    private IAllUserService userService;

    @Resource
    private AllUserMapper allUserMapper;

    @Resource
    private IDoctorService doctorService;

    @Resource
    private DoctorMapper doctorMapper;

    @Resource
    private IPatientService patientService;

    @Resource
    private PatientMapper patientMapper;


    private static final File file = new File("E:/pycharm/code/t1/detector.py");
    private static final String PATH = file.getPath();

    /**
     * @return cn.edu.cqu.boot.config.Result<OnInfo / AllUser>
     * @Description 个人信息展示
     * @Param [user]
     * @Date 2023/4/19 16:01
     * @Auther WangSanmu
     */
    @PostMapping(value = "/showOneInfo")
    public Result<?> showOneInfo(@RequestBody AllUser user) {

        AllUser userRes;
        userRes = userService.getById(user.getId());
        userRes.setPassword(null);
        System.err.println("paraR");

        OneInfo oneInfo;
        if (userRes.getRole() == 2) {//判断展示医生个人信息
            oneInfo = userService.selectJoinOne(OneInfo.class,
                    new MPJLambdaWrapper<AllUser>()
                            .select(AllUser.class, i -> !i.getProperty().startsWith("password"))
                            .select(AllUser::getId)
                            .select(Doctor::getDoctorPosition, Doctor::getDoctorDes, Doctor::getDoctorPic)
                            .leftJoin(Doctor.class, Doctor::getDoctorId, AllUser::getId)
                            .eq(AllUser::getId, user.getId())
            );
            return Result.success(oneInfo);
        } else if (userRes.getRole() == 3) {//判断展示病人个人信息
            oneInfo = userService.selectJoinOne(OneInfo.class,
                    new MPJLambdaWrapper<AllUser>()
                            .select(AllUser.class, i -> !i.getProperty().startsWith("password"))
                            .select(AllUser::getId)
                            .select(Patient::getPatientAddress, Patient::getPatientPhone)
                            .leftJoin(Patient.class, Patient::getPatientId, AllUser::getId)
                            .eq(AllUser::getId, user.getId())
            );
            return Result.success(oneInfo);
        }

        // 判断展示管理员信息
        return Result.success(userRes);
    }


    /**
     * @Description 管理员新增用户(allUser, doctor, patient)
     * @Param [info]
     * @return cn.edu.cqu.boot.config.Result<null>
     * @Date 2023/4/19 17:02
     * @Auther WangSanmu
     */
    @PostMapping("/saveData")
    public Result<?> saveData(@RequestBody OneInfo info) throws Exception {

        // 新增allUser记录
        AllUser user = new AllUser();

        user.setPhoto("http://localhost:8887/files/cae603dda1974bc6bf347e4e2be2b703");
        user.setRole(info.getRole());

        if (info.getPassword() == null) {
            user.setPassword(Hash.encode("e10adc3949ba59abbe56e057f20f883e"));
        } else {
            user.setPassword(Hash.encode(info.getPassword()));
        }

        user.setName(info.getName());
        user.setGender(info.getGender());
        user.setAge(info.getAge());
        user.setEmail(info.getEmail());

        userService.save(user);
        System.err.println(info.getId());

        // 判断新增doctor记录
        if (info.getRole() == 2) {

            Doctor d = new Doctor();

            d.setDoctorId(user.getId());
            d.setDoctorName(user.getName());
            d.setDoctorGender(user.getGender());
            d.setDoctorAge(user.getAge());
            d.setDoctorEmail(user.getEmail());

            d.setDoctorPic(info.getDoctorPic());
            d.setDoctorPosition(info.getDoctorPosition());
            d.setDoctorDes(info.getDoctorDes());

            doctorService.save(d);
        } else if (info.getRole() == 3) { //判断新增patient记录

            Patient p = new Patient();

            p.setPatientId(user.getId());
            p.setPatientName(user.getName());
            p.setPatientGender(user.getGender());
            p.setPatientAge(user.getAge());
            p.setPatientEmail(user.getEmail());

            p.setPatientAddress(info.getPatientAddress());
            p.setPatientPhone(info.getPatientPhone());

            patientService.save(p);
        }
        System.err.println(info);

        return Result.success();
    }


    /**
     * @Description 管理员修改自身个人信息
     * @Param [user]
     * @return cn.edu.cqu.boot.config.Result<null>
     * @Date 2023/4/19 17:42
     * @Auther WangSanmu
     */
    @PutMapping("/updateDataManager")
    public Result<?> updateDataManager(@RequestBody AllUser user) {
        userService.updateById(user);
        return Result.success();
    }


    /**
     * @Description 管理员或病人 修改病人个人信息
     * @Param [oneInfo]
     * @return cn.edu.cqu.boot.config.Result<null>
     * @Date 2023/4/19 17:51
     * @Auther WangSanmu
     */
    @PutMapping("/updateDataPatient")
    public Result<?> updateDataPatient(@RequestBody OneInfo oneInfo) {

        // 更新表patient
        Patient res = patientService.getById(oneInfo.getId());

        res.setPatientName(oneInfo.getName());
        res.setPatientEmail(oneInfo.getEmail());
        res.setPatientGender(oneInfo.getGender());
        res.setPatientAge(oneInfo.getAge());
        res.setPatientAddress(oneInfo.getPatientAddress());
        res.setPatientPhone(oneInfo.getPatientPhone());

        patientService.updateById(res);

        // 更新表allUser
        AllUser res1 = userService.getById(oneInfo.getId());

        res1.setName(oneInfo.getName());
        res1.setAge(oneInfo.getAge());
        FileDelete.deleteFile(res1.getPhoto());
        res1.setPhoto(oneInfo.getPhoto());
        res1.setPassword(oneInfo.getPassword());
        res1.setEmail(oneInfo.getEmail());
        res1.setGender(oneInfo.getGender());

        userService.updateById(res1);

        return Result.success();
    }


    /**
     * @Description 管理员或医生 修改医生个人信息
     * @Param [oneInfo]
     * @return cn.edu.cqu.boot.config.Result<null>
     * @Date 2023/4/19 18:23
     * @Auther WangSanmu
     */
    @PutMapping("/updateDataDoctor")
    public Result<?> updateDataDoctor(@RequestBody OneInfo oneInfo) {

        // 修改allUser表
        AllUser res = userService.getById(oneInfo.getId());

        res.setName(oneInfo.getName());
        res.setEmail(oneInfo.getEmail());
        res.setAge(oneInfo.getAge());
        res.setGender(oneInfo.getGender());
        FileDelete.deleteFile(res.getPhoto());
        res.setPhoto(oneInfo.getPhoto());
        userService.updateById(res);

        // 修改doctor表
        Doctor res1 = doctorService.getById(oneInfo.getId());

        res1.setDoctorGender(res.getGender());
        res1.setDoctorAge(res.getAge());
        res1.setDoctorName(res.getName());
        res1.setDoctorEmail(res.getEmail());

        res1.setDoctorPosition(oneInfo.getDoctorPosition());
        res1.setDoctorPic(oneInfo.getDoctorPic());
        res1.setDoctorDes(oneInfo.getDoctorDes());

        doctorService.updateById(res1);

        return Result.success();
    }

    @PutMapping("/updateData") //管理员更新信息
    public Result<?> updateData(@RequestBody AllUser user) {
        userService.updateById(user);
        if (user.getRole() == 2) {  //同步更新医生表
            Doctor res = doctorMapper.selectOne(Wrappers.<Doctor>query().lambda().eq(Doctor::getDoctorId, user.getId()));
            res.setDoctorAge(user.getAge());
            res.setDoctorName(user.getName());
            doctorService.updateById(res);
        } else if (user.getRole() == 3) {  //同步更新病人表
            Patient res = patientMapper.selectOne(Wrappers.<Patient>query().lambda().eq(Patient::getPatientId, user.getId()));
            res.setPatientName(user.getName());
            res.setPatientEmail(user.getEmail());
            res.setPatientGender(user.getGender());
            res.setPatientAge(user.getAge());
            patientService.updateById(res);
        }
        return Result.success();
    }

    @DeleteMapping("/{id}")   //根据ID删除用户
    public Result<?> deleteData(@PathVariable Long id) {
        AllUser u = userService.getById(id);
        if (u.getRole() == 2) {  //同步删除医生表
            QueryWrapper<Doctor> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("doctorId", u.getId());
            System.out.println(u);
            doctorService.remove(queryWrapper);
        } else if (u.getRole() == 3) {  //同步删除病人表
            QueryWrapper<Patient> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("patientId", u.getId());
            patientService.remove(queryWrapper);
        }
        userService.removeById(id);
        return Result.success();
    }

    @GetMapping("/findPage")  //查询权限为管理员的用户
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "5") Integer pageSize,
                              @RequestParam(defaultValue = "") String search) {
        IPage<AllUser> userIPage = allUserMapper.selectPage(new Page<>(pageNum, pageSize), Wrappers.<AllUser>query().lambda().eq(AllUser::getRole, "1").like(AllUser::getName, search));
        System.out.println(userIPage);
        return Result.success(userIPage);
    }

    @GetMapping("/findPage3")  //查询权限为医生的用户
    public Result<?> findPage3(@RequestParam(defaultValue = "1") Integer pageNum,
                               @RequestParam(defaultValue = "5") Integer pageSize,
                               @RequestParam(defaultValue = "") String search) {
//        IPage<AllUser> userIPage = allUserMapper.selectPage(new Page<>(pageNum, pageSize), Wrappers.<AllUser>query().lambda().eq(AllUser::getRole, "2").like(AllUser::getName, search));
//        Doctor doctor = doctorMapper.selectOne(Wrappers.<AllUser>query().lambda().eq(AllUser::getName, user.getName()).
//                eq(AllUser::getPassword, Hash.encode(user.getPassword())));
//        IPage<Doctor> doctorIPage = doctorMapper.selectPage(new Page<>(pageNum, pageSize),
//                Wrappers.<Doctor>query().lambda().like(Doctor::getDoctorName, search));

        IPage<OneInfo> doctorIPage = userService.selectJoinListPage(new Page<>(pageNum, pageSize), OneInfo.class,
                new MPJLambdaWrapper<AllUser>()
                        .select(AllUser.class, i -> !i.getProperty().startsWith("password"))
                        .select(AllUser::getId)
                        .select(Doctor::getDoctorPosition, Doctor::getDoctorPic, Doctor::getDoctorDes)
                        .leftJoin(Doctor.class, Doctor::getDoctorId, AllUser::getId)
                        .eq(AllUser::getRole, 2)
                        .like(AllUser::getName, search)
        );
        System.out.println(doctorIPage);
        return Result.success(doctorIPage);
    }

    @GetMapping("/findPage2")   //查询权限为病人的用户
    public Result<?> findPage2(@RequestParam(defaultValue = "1") Integer pageNum,
                               @RequestParam(defaultValue = "5") Integer pageSize,
                               @RequestParam(defaultValue = "") String search) {
        IPage<AllUser> userIPage = allUserMapper.selectPage(new Page<>(pageNum, pageSize), Wrappers.<AllUser>query().lambda().eq(AllUser::getRole, "3").like(AllUser::getName, search));
        System.out.println(userIPage);
        return Result.success(userIPage);
    }


    @PostMapping("/saveDoctorData")  //管理员增加医生
    public Result<?> saveDoctorData(@RequestBody Doctor doctor) {
        doctorService.save(doctor);
        return Result.success();
    }

    @PutMapping("/updateDoctorData")  //管理员更新医师信息
    public Result<?> updateDoctorData(@RequestBody Doctor doctor) {
        AllUser res = allUserMapper.selectOne(Wrappers.<AllUser>query().lambda().eq(AllUser::getId, doctor.getDoctorId()));
        res.setAge(doctor.getDoctorAge());
        res.setName(doctor.getDoctorName());
        doctorService.updateById(doctor);
        userService.updateById(res);
        return Result.success();
    }


    @GetMapping("/findAllDoctor")   //查询所有医生
    public Result<?> findAllDoctor(@RequestParam(defaultValue = "1") Integer pageNum,
                                   @RequestParam(defaultValue = "3") Integer pageSize,
                                   @RequestParam(defaultValue = "") String search) {
        IPage<Doctor> doctorIPage = doctorMapper.selectPage(new Page<>(pageNum, pageSize), Wrappers.<Doctor>query().lambda().like(Doctor::getDoctorName, search));
        System.out.println(doctorIPage);
        return Result.success(doctorIPage);
    }

    @GetMapping("/findOneDoctor")   //查询医生详情
    public Result<?> findOneDoctor(@RequestParam(defaultValue = "1") Integer doctorId) {
        Doctor d = doctorMapper.selectById(doctorId);
        System.out.println(d);
        return Result.success(d);
    }

    @GetMapping("/selectDoctor")  //病人选择医生, @RequestParam Integer did+ did
    public Result<?> selectDoctor(@RequestParam Integer userId, @RequestParam Integer doctorId) {
        System.out.println(userId + "**12315**" + doctorId);

        return Result.success();
    }

    @PostMapping("/login")  //用户登录页
    public Result<?> login(@RequestBody AllUser user) throws Exception {
        AllUser res = allUserMapper.selectOne(Wrappers.<AllUser>query().lambda().eq(AllUser::getName, user.getName()).
                eq(AllUser::getPassword, Hash.encode(user.getPassword())));
        if (res == null) {
            return Result.error(-1, "用户名或密码错误");
        }
        res.setPassword(null);
//        System.out.println(res);
//        System.err.println("正在进行人脸检测...");
//
//        final ProcessBuilder processBuilder = new ProcessBuilder("python", PATH);
//        processBuilder.redirectErrorStream(true);
//
//        final Process process = processBuilder.start();
//
//        final BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
//
//        String s = null;
//        while ((s = in.readLine()) != null)
//        {
//            System.out.println(s);
//        }
//
//        final int exitCode = process.waitFor();
//        System.out.println(exitCode == 0);
//        System.err.println("0");
        return Result.success(res);
    }

    @PostMapping("/register")  //用户注册页 用户名不能重复
    public Result<?> register(@RequestBody AllUser user) throws Exception {
        AllUser res = allUserMapper.selectOne(Wrappers.<AllUser>query().lambda().eq(AllUser::getName, user.getName()));
        if (res != null) {
            return Result.error(-1, "用户名重复");
        }
        user.setPhoto("http://localhost:8887/files/cae603dda1974bc6bf347e4e2be2b703");
        user.setRole(3);
        user.setPassword(Hash.encode(user.getPassword()));
        userService.save(user);

        Patient p = new Patient();
        p.setPatientId(user.getId());
        p.setPatientName(user.getName());
        p.setPatientEmail(user.getEmail());
        patientService.save(p);
        return Result.success();
    }

    //    @PostMapping("/passwordModify")
//    public Result<?> passwordModify(HttpServletRequest request, @RequestBody PasswordModify passwordModify) throws Exception {
//        String userId = (String) request.getSession().getAttribute("id");
//        AllUser user = allUserMapper.selectOne(Wrappers.<AllUser>query().lambda().eq(AllUser::getId, userId));
//        assert user != null;
//        if (Objects.equals(passwordModify.getOldPassword(), user.getPassword())) {
//            if (Objects.equals(passwordModify.getRePassword(), passwordModify.getNewPassword())) {
//                user.setPassword(passwordModify.getNewPassword());
//                userService.save(user);
//                return Result.success();
//            }
//        } else {
//            return Result.error(-1,"旧密码错误！");
//        }
//        return Result.success();
//    }
    @PostMapping("/passwordModify")
    public Result<?> passwordModify(@RequestBody PasswordModify passwordModify) throws Exception {
        AllUser user = allUserMapper.selectOne(Wrappers.<AllUser>query().lambda().eq(AllUser::getId, passwordModify.getId()));
        assert user != null;
        if (Objects.equals(Hash.encode(passwordModify.getOldPassword()), user.getPassword())) {
            user.setPassword(Hash.encode(passwordModify.getNewPassword()));
            userService.updateById(user);
            return Result.success();
        } else {
            return Result.error(-1, "旧密码错误！");
        }
    }
}
