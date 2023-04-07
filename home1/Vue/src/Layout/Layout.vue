<template>
<!--  <el-card class="box-card" style="background-color:#EBEBEB;padding: 0">-->
  <div style="position: relative;width: 1518px;">
    <Header style="position: fixed;top:0; z-index: 100"/>

      <!--      侧边栏-->
      <Aside style="position: fixed;top:50px;left:190px;"/>
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

