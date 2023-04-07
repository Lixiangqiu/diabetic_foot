<template>
    <el-card class="wrapper">
      <div class="question" v-for="(question,index) in questionList" :key="question.problemId">
          <el-card :id="question.problemId" class="box-card" style="margin-top:10px;background-color: whitesmoke;border: 1px solid transparent">
            <div style="margin-left: 20px">第{{(question.problemId)}}题
              <span style="margin-left: 25px">{{question.problemDesc}}</span>
            </div>
            <div style="margin: 20px 0px 0px 0px;width: 100%;display: flex;justify-content: space-around;align-items: center" >
              <el-radio-group v-model="radio[question.problemId]" style="width:80%;display:flex;justify-content:space-around;">
                <el-radio :label="1">{{question.optionOne}}</el-radio>
                <el-radio :label="2">{{question.optionTwo}}</el-radio>
                <el-radio :label="3">{{question.optionThree}}</el-radio>
                <el-radio :label="4">{{question.optionFour}}</el-radio>
              </el-radio-group>
              <el-button  @click="handleAns(question.problemId,radio[question.problemId],question)">确定</el-button>
              <div  style="margin-bottom: 5px" v-show="question.isRight">
                正确
              </div>
              <div  style="margin-bottom: 5px" v-show="question.isFalse">
                错误
              </div>
            </div>
          </el-card>
        </div>

      <el-pagination
          style="margin: 15px 0 0 20px;"
          @current-change="handleCurrentChange"
          :current-page="currentPage"
          :page-sizes="3"
          :page-size="pageSize"
          layout="total, prev, pager, next, jumper"
          :total="total">
      </el-pagination>


    </el-card>
</template>

<script>


import request from "@/utils/request";

export default {
  name:"question",
  components:{
  },
  data() {
    return {
      radio:[],
      value:0,
      form:{},
      dialogVisible:false,
      search:'',
      currentPage:1,
      pageSize:3,
      total:0,
      tableData: [],
      user:{},
      questionList:[],
      questions:[
        {
          id:1,
          q:"1+1",
          ans:2,
          options:[1,2,3,4],
          isRight:false,
          isFalse:false
        },
        {
          id:2,
          q:"1+1",
          ans:2,
          options:[1,2,3,4],
          isRight:false,
          isFalse:false
        },
        {
          id:3,
          q:"1+1",
          ans:2,
          options:[1,2,3,4],
          isRight:false,
          isFalse:false
        },
        {
          id:4,
          q:"1+1",
          ans:2,
          options:[1,2,3,4],
          isRight:false,
          isFalse:false
        },
        {
          id:5,
          q:"1+1",
          ans:2,
          options:[1,2,3,4],
          isRight:false,
          isFalse:false
        },
        {
          id:6,
          q:"1+1",
          ans:2,
          options:[1,2,3,4],
          isRight:false,
          isFalse:false
        },
        {
          id:7,
          q:"1+1",
          ans:2,
          options:[1,2,3,4],
          isRight:false,
          isFalse:false
        },
        {
          id:8,
          q:"1+1",
          ans:2,
          options:[1,2,3,4],
          isRight:false,
          isFalse:false
        },
        {
          id:9,
          q:"1+1",
          ans:2,
          options:[1,2,3,4],
          isRight:false,
          isFalse:false
        },
      ]
    }
  },
  created(){
    this.questionLoad();
    let str = sessionStorage.getItem("user") || "{}"
    this.user = JSON.parse(str)
  },
  methods:{
    /**
     * 答题
     *
     * **/
    handleAns(qid,ans,question){
      console.log(typeof ans,qid,typeof this.questions[(qid-1)].answer)

      if(ans === undefined){
        alert("未选择答案")
      }else {
        if(parseInt(ans) === this.questions[(qid-1)].answer){
          console.log("right")
          question.isFalse = false
          question.isRight = true
        }else {
          console.log("wrong")
          question.isRight = false
          question.isFalse = true
        }
      }
    },

    handleCurrentChange(pageNum){
      this.currentPage = pageNum
      this.load()
    },

    questionLoad(){
      request.post("/api/problem/problemList").then(res =>{
        console.log(res)
        this.questions = res.data
        this.total=res.data.length
        this.load()
      })
    },

    load(){
      this.questionList = []
      for(let index = 3 * (this.currentPage-1);index < Math.min(3 * this.currentPage,this.questions.length); index++){
        this.questionList.push(this.questions[index])
      }
      console.log(this.questionList)
    },

    Add(){
      this.dialogVisible = true
      this.form={}
    },

    saveData(){
      if(this.form.workerID){
        request.put("/api/allUser/updateData",this.form).then(res =>{
          console.log(res)
          if(res.code === 0){
            this.$message({
              type:"success",
              message:"修改成功"
            })}else{
            this.$message({
              type:"error",
              message:res.msg
            })
          }
        })
      }else {
        request.post("/api/allUser/saveData", this.form).then(res => {
          console.log(res)
          if(res.code === 0){
            this.$message({
              type:"success",
              message:"添加成功"
            })}else{
            this.$message({
              type:"error",
              message:res.msg
            })
          }
        })
      }
      this.dialogVisible = false
      this.load()
    },

    handleEdit(row){
      this.form = JSON.parse(JSON.stringify(row))
      this.dialogVisible = true
    },

    handleDelete(workerID){
      console.log(workerID)
      request.delete("/api/allUser/"+workerID).then(res => {
        console.log(res)
        if(res.code === 0){
          this.$message({
            type:"success",
            message:"删除成功"
          })}else{
          this.$message({
            type:"error",
            message:res.msg
          })
        }
      })
      this.load()
    },




  }
}
</script>

<style scoped>
.wrapper{
  width:920px;
  min-height:1000px;
  position: relative;
}

#title{
  position: absolute;
  left:50%;
  top:10px;
  transform: translateX(-50%);
  font-size: 25px;
  width:200px;
  height:20px;
  line-height: 20px;
  text-align: center;
}

.question{
  margin-top: 20px;
}
</style>