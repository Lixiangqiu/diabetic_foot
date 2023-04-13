<template>
<!--  <el-card class="box-card" style="background-color:#EBEBEB;padding: 0">-->
  <div style="position: relative;width: 1518px;">
      <Header style="position: fixed;top:0; z-index: 100"/>
      <div  style="position: fixed;top:50px;left:0px;width:190px;height:100%;z-index: 100;background-color: #F0F8FF;"></div>
      <!--      侧边栏-->
      <Aside style="position: fixed;top:50px;left:190px;z-index: 100"/>
      <!--      主体内容-->
      <router-view style="margin-left: 400px;margin-top: 50px;width: 920px;min-height: 1000px;"/>

  </div>
</template>

<script>
import Aside from "@/components/Aside";
import Header from "@/components/Header";

export default {
    name :"layout",
    components:{
    Header,
    Aside
  }
}
</script>

