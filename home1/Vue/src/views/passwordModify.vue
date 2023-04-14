<template>
    <div class="content">
      <div class="test"></div>
      <h1>修改密码</h1>
      <div class="box_info">
        <div class="modify_box">
          <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" class="demo-ruleForm" size="medium">
            <el-form-item prop="oldPassword">
              <el-input type="password" v-model="ruleForm.oldPassword" autocomplete="off" placeholder="请输入旧密码"></el-input>
            </el-form-item>
            <el-form-item prop="newPassword">
              <el-input type="password" v-model="ruleForm.newPassword" autocomplete="off" placeholder="请输入新密码"></el-input>
            </el-form-item>
            <el-form-item prop="rePassword">
              <el-input type="password" v-model="ruleForm.rePassword" autocomplete="off" placeholder="密码确认"></el-input>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="submitForm('ruleForm')" style="width: 40%;margin: 0px auto">修改密码</el-button>
            </el-form-item>
          </el-form>
        </div>
      </div>
    </div>
  </template>
  
  <script>
      import md5 from 'js-md5';
      import request from "@/utils/request";
      export default {
          name: "passwordModify",
          data() {
              let validatePass = (rule, value, callback) => {
                  if (value === '') {
                      callback(new Error('请输入密码'));
                  } else if(value.length<3) {
                      callback(new Error('密码长度不能小于3'));
                  }else {
                      callback();
                  }
              };
              let validateRePass = (rule, value, callback) => {
                  if (value !==this.ruleForm.newPassword){
                      callback(new Error('密码不一致'));
                  }else{
                      callback();
                  }
              }
              return {
                  ruleForm: {
                      oldPassword: '',
                      newPassword: '',
                      rePassword: '',
                  },
                  rules: {
                      oldPassword: [
                          { required: true, message: '旧密码不能为空', trigger: 'blur' },
                      ],
                      newPassword: [
                          { validator: validatePass, trigger: 'blur' }
                      ],
                      rePassword: [
                          { validator: validateRePass, trigger: 'blur' }
                      ]
                  },
                  user: '',
                  form:{},
              };
          },
          created(){
            let str = sessionStorage.getItem("user") || "{}"
            this.user = JSON.parse(str)
          },
          methods: {
            submitForm(formName) {
              this.$refs[formName].validate((valid) => {
                if (valid) {
                  console.log("=====this.ruleForm.oldPassword:========"+this.user.id+";;;;"+this.ruleForm.newPassword+"====")
                  //数据校验成功，可以进行提交操作
                  this.form.id = this.user.id
                  this.form.oldPassword = md5(this.ruleForm.oldPassword)
                  this.form.newPassword = md5(this.ruleForm.newPassword)
                  console.log('form',this.form)
                  request.post("/api/allUser/passwordModify", this.form).then((res)=>{
                    if(res.code === 0){
                      this.$message({
                        type: 'success',
                        message: '您的密码修改成功',
                        duration: 1000
                      })
                      sessionStorage.removeItem('user')
                      setTimeout(() => {
                        this.$router.push({path:'/login'});
                      }, 1000);
                    }else{
                      this.$message({
                        type:"error",
                        message:"添加失败"
                      })
                    }
                  })
                } else {
                  this.$message.error("请保证密码不为空且两次输入密码一致");
                  return false;
                }
              });
            },
            resetForm(formName) {
              this.$refs[formName].resetFields();
            }
          }
  
      }
  </script>
  
  <style scoped>
    .content{
      margin: 0px auto;
      width:400px;
      height:400px;
      background-color: white;
      /* padding: 30px 20px; */
      overflow: hidden;
    }
    h1{
      color: #757575;
      font-family: 新宋体;
      display:block;
      width:4.1em;
      margin:70px auto;
      margin-bottom:0px;
    }
    .box_info{
      width: 960px;
      /* background-color: #8acfd1; */
      margin: 10px auto;
    }
    .modify_box{
      margin-top: 0px;
      margin-left: 250px;
      width: 400px;
      height: 260px;
      /*border: 1px solid #dfdfdf;*/
      text-align: center;
      padding: 15px;
      margin:0px auto;
    }
  
  </style>
  