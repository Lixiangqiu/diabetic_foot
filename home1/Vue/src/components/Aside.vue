<template>
<!--  <el-card class="box-card" style="background-color:#EBEBEB;padding: 0">-->
  <div style="width:200px;min-height:1300px;background-color: #FEFDFD;">
    <el-menu
        style="background-color: #FEFDFD;width:200px;min-height:1300px"
        :default-active="path"
        router
        class="el-menu-vertical-demo">
      <div style="background-color: #28a4c9"></div>
      <el-submenu index="1" v-if="user.role === 1">
        <template #title>
          <i class="el-icon-location"></i ><span style="color: #080808">系统管理</span>
        </template>
        <el-menu-item index="/manager" style="color: #292929" ><i class="el-icon-user-solid" ></i>管理员信息管理</el-menu-item>
        <el-menu-item index="/worker" style="color: #292929" ><i class="el-icon-dish" ></i>医师信息管理</el-menu-item>
        <el-menu-item index="/user" style="color: #292929" ><i class="el-icon-s-custom" ></i>病人信息管理</el-menu-item>
        <el-menu-item index="/commentList" style="color: #292929"><i class="el-icon-dish"></i>论坛管理</el-menu-item>
      </el-submenu>
      <el-submenu index="2" v-if="user.role !== 1">
        <template #title>
          <i class="el-icon-location"></i ><span style="color: #080808">功能模块</span>
        </template>
        <el-submenu v-if="user.role !== 1">
          <template #title>
            <i class="el-icon-s-custom"></i ><span style="color: #080808">病历管理</span>
          </template>
            <el-menu-item index="/dMP" style="color: #292929" v-if="user.role ===2"><i class="el-icon-user-solid" ></i>个人病历</el-menu-item>
            <el-menu-item index="/dMPOther" style="color: #292929" v-if="user.role ===2"><i class="el-icon-user" ></i>他人病历</el-menu-item>
        </el-submenu>
        <el-menu-item index="/doctorInfo" style="color: #292929" v-if="user.role ===3"><i class="el-icon-dish"></i>选择医师</el-menu-item>
        <el-menu-item index="/im"> <i class="el-icon-chat-dot-round"></i>在线咨询</el-menu-item>
        <el-menu-item index="/commentList" style="color: #292929"><i class="el-icon-dish"></i>热门讨论</el-menu-item>
        <el-menu-item index="/questionInfo" style="color: #292929" v-if="user.role ===3"><i class="el-icon-user-solid"></i>糖尿病病理检测题</el-menu-item>
      </el-submenu>
    </el-menu>
  </div>

</template>

<script>
export default {
  name: "Aside",
  data(){
    return {
      path:this.$route.path,
      user: {}
    }
  },
  created() {
    let str = sessionStorage.getItem("user") || "{}"
    this.user = JSON.parse(str)
  },
}
</script>

<style scoped>

</style>