<template>
    <div class="main">
      <div class = "container">
        <div class = "form-box">
          <el-form class="register-box hidden" ref="form1" :rules="rules" :model="form1" >
            <h1>register</h1>
            <el-form-item prop="name">  <!---注意此处的prop和model和rules中的要一样-->
              <el-input class="input" prefix-icon="el-icon-user-solid" v-model="form1.name"></el-input>
            </el-form-item>
            <el-form-item prop="password">
              <el-input class="input" prefix-icon="el-icon-lock" v-model="form1.password" show-password></el-input>
            </el-form-item>
            <el-form-item prop="confirm">
              <el-input class="input" prefix-icon="el-icon-lock" v-model="form1.confirm" show-password></el-input>
            </el-form-item>
            <el-form-item>
              <el-button class="button" @click="register">注册</el-button>
            </el-form-item>
          </el-form>

          <el-form class="login-box" ref="form2" :model="form2" >
            <h1>login</h1>
            <el-form-item prop="name">
              <el-input class="input" prefix-icon="el-icon-user-solid" v-model="form2.name" clearable> </el-input>
            </el-form-item>
            <el-form-item prop="password">
              <el-input class="input" prefix-icon="el-icon-lock" v-model="form2.password" show-password clearable></el-input>
            </el-form-item>
            <el-form-item>
              <el-button class="button" @click="login">登录</el-button>
            </el-form-item>
          </el-form>

        </div>
        <div class="con-box left">
          <h2>关爱健康<span>守护生命</span></h2>
          <img src="../assets/4.webp "/>
          <p>已有账号</p>
          <el-button id="login" class="button" @click="goToLogin">去登录</el-button>
        </div>
        <div class="con-box right">
          <h2>关爱健康<span>守护生命</span></h2>
          <img src="../assets/4.webp "/>
          <p>没有账号？</p>
          <el-button id="register" class="button" @click="goToRegister">去注册</el-button>
        </div>
      </div>
    </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "Login",
  data(){
    return {
      form1:{},
      form2:{},
      rules: {
        name: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
          { min: 3, max: 10, message: '长度在 3 到 10 个字符', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请正确输入密码：限字母+数字组合', pattern : /^[A-Za-z0-9]+$/g, type : "string", trigger: 'blur'},
          { min: 3, max: 15, message: '长度在 3 到 15 个字符', trigger: 'blur' }
        ],
        confirm: [
          { required: true, message: '请确认密码', trigger: 'blur' },
        ]
      },
      url:require("../assets/logo2.png"),
    };
  },

  created(){
    sessionStorage.removeItem("user")
  },
  methods:{
    goToLogin(){
      console.log(111)
      let formBox = document.getElementsByClassName("form-box")[0]
      let registerBox = document.getElementsByClassName("register-box")[0]
      let loginBox = document.getElementsByClassName("login-box")[0]
      formBox.style.transform = 'translateX(0%)'
      registerBox.classList.add('hidden')
      loginBox.classList.remove('hidden')
    },
    goToRegister(){
      console.log(222)
      let formBox = document.getElementsByClassName("form-box")[0]
      let registerBox = document.getElementsByClassName("register-box")[0]
      let loginBox = document.getElementsByClassName("login-box")[0]
      formBox.style.transform = 'translateX(85%)'

      loginBox.classList.add('hidden')
      registerBox.classList.remove('hidden')
    },
    register(){
      if(this.form1.password !== this.form1.confirm){
        this.$message({
          type:"error",
          message:"两次输入的密码不一致"
        })
        return
      }
      this.$refs['form1'].validate((valid) => {
        if (valid) {
          request.post("/api/allUser/register",this.form1).then(res=>{
            if(res.code === 0){
              this.$message({
                type:"success",
                message:"注册成功！"
              })
              this.form2.name = this.form1.name
              this.form2.password = this.form1.password
              this.goToLogin()
            }else {
              this.$message({
                type:"error",
                message:res.msg,
              })
            }
          })
        }
      });
    },
    login(){
      this.$refs['form2'].validate((valid) => {
        if (valid) {
          request.post("/api/allUser/login",this.form2).then(res=>{
            if(res.code === 0){
              this.$message({
                type:"success",
                message:"登录成功！"
              })
              sessionStorage.setItem("user", JSON.stringify(res.data))  // 缓存用户信息
              let str = sessionStorage.getItem("user") || "{}"
              let form = JSON.parse(str)
              console.log(form)
              if(form.role ===1){
                this.$router.push("/manager") //页面跳转
              }else if(form.role ===2){
                this.$router.push("/dMp") //页面跳转
              }else if(form.role ===3){
                this.$router.push("/Info") //页面跳转
              }
            }else {
              this.$message({
                type:"error",
                message:res.msg
              })
            }
          })
        }
      });
    }
  }
}
</script>

<style scoped>
    *{
      padding:0;
      margin:0;
    }

    .main{
      height:100vh;
      width:100%;
      display:flex;
      justify-content: center;
      align-items:center;
      background: linear-gradient(200deg,#f3e7e9,#e3eeff);
    }

    .container{
      background-color: white;
      width:650px;
      height:415px;
      border-radius:10px;
      box-shadow:5px 5px 5px rgba(0,0,0,0.1);
      position:relative;
    }

    .form-box{
      position:absolute;
      top:-10%;
      left:5%;
      background-color: #d3b7d8;
      width:320px;
      height:500px;
      border-radius:6px;
      box-shadow:2px 0 10px rgba(0,0,0,0.1);
      display:flex;
      justify-content:center;
      align-items:center;
      z-index:2;
      transition:0.5s ease-in-out;
    }

    .register-box,.login-box{
      display:flex;
      flex-direction: column;
      align-items: center;
      width:100%;
    }

    .hidden{
      display:none;
    }

    h1{
      text-align:center;
      margin-bottom: 25px;
      margin-left: 16px;
      text-transform: uppercase;
      color:white;
      letter-spacing: 6px;
    }

    input{
      background-color: transparent;
      color:white;
      border:none;
      border-bottom: 1px solid rgba(255,255,255,0.4);
      padding:10px 0;
      text-indent: 10px;
      margin:8px 0;
      font-size:14px;
      letter-spacing: 2px;
    }

    input::placeholder{
      color: #fff;
    }

    input:focus{
      color:#a262ad;
      outline:none;
      border-bottom:1px solid #a262ad80;
      transition:0.5s
    }

    input:focus::placeholder{
      opacity: 0;
    }

    .input{
      background-color: transparent;
      width:230px;
      color:white;
      border:none;
      text-indent: 10px;
      margin:10px 0 0 -10px;
      font-size:14px;
      letter-spacing: 2px;
    }

    .input::placeholder{
      color: #fff;
    }

    .input:focus{
      outline:none;
      transition:0.5s
    }

    .form-box button{
      width:150px;
      margin-top:35px;
      background-color: #f6f6f6;
      outline:none;
      border-radius:8px;
      padding:13px;
      color:#a262ad;
      letter-spacing:2px;
      border:none;
      cursor: pointer;
    }

    .form-box button:hover{
      background-color: #a262ad;
      color: #f6f6f6;
      transition: background-color 0.5s ease;
    }

    .con-box{
      width:50%;
      display:flex;
      flex-direction: column;
      justify-content:center;
      align-items:center;
      position:absolute;
      top:50%;
      transform:translateY(-50%);
    }

    .con-box.left{
      left:-2%
    }

    .con-box.right{
      right:-2%
    }

    .con-box h2{
      color:#8e9aaf;
      font-size: 25px;
      font-weight: bold;
      letter-spacing: 3px;
      text-align: center;
      margin-bottom:4px;
    }

    .con-box p{
      color:#d3b7d8;
    }

    .con-box img{
      width:150px;
      height:150px;
      opacity: 0.9;
      margin:40px 0;
    }

    .con-box button{
      margin-top:3%;
      width:150px;
      background-color: white;
      color: #a262ad;
      border:1px solid #d3b7d8;
      padding:6px 10px;
      border-radius:5px;
      letter-spacing:1px;
      outline:none;
      cursor:pointer;
    }

    .con-box button:hover{
      background-color: #d3b7d8;
      color: white;
    }
</style>