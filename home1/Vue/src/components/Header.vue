<template>

  <div class="head1" style="background-color: #C6E2FF;width: 1518px;" >

    <div style="width:280px;margin-left: 620px; font-size: 26px; color: #3c3c3f  ">糖尿病足健康管理系统</div>
    <div style="width:130px; margin-left: 480px;margin-top: 4px;">
      <el-dropdown>
        <span class="el-dropdown-link" style="display: flex;align-items: center;">
          <img :src="userNewPhoto" alt="未上传头像" style="width:44px;height:44px;border-radius: 50%"/>
          <span style="margin-left: 10px;">{{ user.name }}</span>
          <i class="el-icon-arrow-down el-icon--right"></i>
        </span>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item @click="$router.push('/Info')">个人信息</el-dropdown-item>
            <el-dropdown-item @click="Exit">退出登录</el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </div>
  </div>
</template>

<script>
export default {
  name: "Header",
  data() {
    return {
      user: {},
      userPhoto:''
    }
  },
  created() {
    let str = sessionStorage.getItem("user") || "{}"
    this.user = JSON.parse(str)
    this.$store.commit("update",this.user)
    console.log(this.$store.state.userPhoto)
    this.userPhoto = this.$store.state.userPhoto
  },
  computed:{
    userNewPhoto(){
      return this.$store.state.userPhoto
    }
  },
  methods:{

    Exit(){
      sessionStorage.removeItem("user")
      this.$router.push('/login')
    }
  }
}
</script>

<style scoped>

 .head1{
   height:50px;
   line-height: 50px;

   display: flex;
 }
</style>