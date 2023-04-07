<template>
  <el-card>
    <div class="container">
      <h1>糖尿病病理检测题目</h1>
      <div><p>本题库旨在普及糖尿病相关知识..........................................</p></div>
      <el-button type="primary" @click="start">确 定</el-button>
    </div>
  </el-card>
</template>

<script>
import request from "@/utils/request";
export default {
  name: "questionInfo",
  methods:{
    start(){
      this.$message({
        type: "success",
        message: "请开始答题"
      })
      this.$router.push("/question")
    }
  }
}
</script>

<style scoped>
.container{
  margin-top: 100px;
  width:500px;
  height:300px;
  display: flex;
  flex-direction: column;
  justify-content:space-evenly;
  align-items:center;
}
</style>