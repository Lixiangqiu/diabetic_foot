<template>
  <el-card style="position: relative;background-color: #FEFDFD;">
    <a href="#"><i class="el-icon-caret-top"></i></a>
    <div class="box">
      <h2>热 门 讨 论</h2>
      <div>
        <el-input v-model="search" placeholder="请输入关键词" style="width:220px" clearable></el-input>
        <el-button style="margin-left:5px" icon="el-icon-search" circle @click="load()"></el-button>
<!--        <el-button type="primary" style="margin-left:5px" @click="load">热帖搜索</el-button>-->
      </div>
      <el-button class="button" @click="Add">我要发帖</el-button>
    </div>
    <div class="sort">
      <el-button class="goodButton" @click="goodSort">按热度</el-button>
      <el-button class="timeButton" @click="timeSort">按时间</el-button>
    </div>
    <hr/>
    <div v-for="comment in showCommentList" :key="comment.commentId">
      <div class="container">
        <img :src="comment.commentPic"/>
        <div class="box1">
          <div>
            <span class="title" @click="choose(comment)">【#{{comment.commentTitle}}#】</span>
            <el-button type="text" size="mini" @click="del(comment.commentId)" v-if="user.role===1"
                       style="transform: translateY(-2px);">删除</el-button>
          </div>
          <span class="info" @click="choose(comment)">{{comment.commentDes}}</span>
          <div class="box2">
            <div class="box3">
              <img :src="comment.commentPersonPic" alt="123"/>
              <div>{{comment.commentPersonName}}</div>
              <div style="width: 95px">{{comment.commentTime}}</div>
            </div>
            <div class="box4">
              <div >
                <i class="el-icon-chat-line-round" style="margin-left: 19px;" @click="choose(comment)"></i>
                <div class="pinglunshuliang">{{comment.commentNum}}</div>
                <div style="height:10px;width: 1px; background-color: #999999;display: inline-block;margin-left: 20px;transform: translateY(-10%)"></div>
              </div>
              <div>
                <i class="el-icon-thumb" style="margin-left: 19px;" @click="zan(comment)"></i>
                <div class="dianzanshuliang">{{comment.commentGood}}</div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <el-divider></el-divider>
    </div>

    <div style="margin: 10px 0">
      <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="currentPage"
          :page-sizes="[3, 6, 9]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total"
      >
      </el-pagination>
    </div>

    <el-dialog title="我要发帖" v-model="dialogVisible" width="40%">

      <el-form :model="form" label-width="120px" :rules="rules">
        <el-form-item label="标题" prop="commentTitle">
          <el-input v-model="form.commentTitle" style="width:80%"></el-input>
        </el-form-item>
        <el-form-item label="简单描述" prop="commentDes">
          <el-input v-model="form.commentDes" style="width:80%"></el-input>
        </el-form-item>
        <el-form-item label="封面图片">
          <el-upload ref="upload" class="upload-demo" action="http://localhost:8887/files/upload" :on-success="filesUploadSuccess">
            <el-button type="primary">点击上传</el-button>
          </el-upload>
        </el-form-item>
        <el-form-item label="详细内容">
          <div id="richText"></div>
        </el-form-item>
      </el-form>

      <template #footer>
          <span class="dialog-footer">
            <el-button @click="dialogVisible = false">取 消</el-button>
            <el-button type="primary" @click="saveData">确 定</el-button>
          </span>
      </template>

    </el-dialog>

  </el-card>


</template>


<script>
import request from "@/utils/request";
import E from "wangeditor"
let editor;
export default {
  name:"commentList",
  components:{
  },
  data() {
    return {
      form:{},
      commentList:[
        {
          commentId:1,
          commentTitle:"糖尿病怎么治?",
          commentDes:"这个问题问的好这个问题问的好这个问题问的好这个问题问的好这个问题问的好这个问题问的好这" +
              "个问题问的好这个问题问的好这个问题问的好这个问题问的好这个问题问的好这个问题这个问题问的好这个问题" +
              "问的好这个问题问的好这个问题问的好这个问题问的好这个问题问的好这个问题问的好这个问题问的好这个问题问的好问的好这个问题问这个问题问的好的好",
          commentPersonId:1,
          commentPersonName:"狗蛋",
          commentPersonPic:"http://localhost:8887/files/7e4e6b7f5763477f9dda789a5c200a88",
          commentTime:"2020-02-20",
          commentPic:"http://localhost:8887/files/ffd80adf473540859d2ace0866910dd0",
          commentNum:1,
          commentGood:1
        },
        {
          commentId:2,
          commentTitle:"糖尿病怎么治?",
          commentDes:"这个问题问的好这个问题问的好这个问题问的好这个问题问的好这个问题问的好这个问题问的好这" +
              "个问题问的好这个问题问的好这个问题问的好这个问题问的好这个问题问的好这个问题这个问题问的好这个问题" +
              "问的好这个问题问的好这个问题问的好这个问题问的好这个问题问的好这个问题问的好这个问题问的好这个问题问的好问的好这个问题问这个问题问的好的好",
          commentPersonId:1,
          commentPersonName:"狗蛋",
          commentPersonPic:"http://localhost:8887/files/7e4e6b7f5763477f9dda789a5c200a88",
          commentTime:"2020-02-20",
          commentPic:"http://localhost:8887/files/ffd80adf473540859d2ace0866910dd0",
          commentNum:1000,
          commentGood:1000
        },
        {
          commentId:2,
          commentTitle:"糖尿病怎么治?",
          commentDes:"这个问题问的好这个问题问的好这个问题问的好这个问题问的好这个问题问的好这个问题问的好这" +
              "个问题问的好这个问题问的好这个问题问的好这个问题问的好这个问题问的好这个问题这个问题问的好这个问题" +
              "问的好这个问题问的好这个问题问的好这个问题问的好这个问题问的好这个问题问的好这个问题问的好这个问题问的好问的好这个问题问这个问题问的好的好",
          commentPersonId:1,
          commentPersonName:"狗蛋",
          commentPersonPic:"http://localhost:8887/files/7e4e6b7f5763477f9dda789a5c200a88",
          commentTime:"2020-02-20",
          commentPic:"http://localhost:8887/files/ffd80adf473540859d2ace0866910dd0",
          commentNum:1000,
          commentGood:1000
        },{
          commentId:2,
          commentTitle:"糖尿病怎么治?",
          commentDes:"这个问题问的好这个问题问的好这个问题问的好这个问题问的好这个问题问的好这个问题问的好这" +
              "个问题问的好这个问题问的好这个问题问的好这个问题问的好这个问题问的好这个问题这个问题问的好这个问题" +
              "问的好这个问题问的好这个问题问的好这个问题问的好这个问题问的好这个问题问的好这个问题问的好这个问题问的好问的好这个问题问这个问题问的好的好",
          commentPersonId:1,
          commentPersonName:"狗蛋",
          commentPersonPic:"http://localhost:8887/files/7e4e6b7f5763477f9dda789a5c200a88",
          commentTime:"2020-02-20",
          commentPic:"http://localhost:8887/files/ffd80adf473540859d2ace0866910dd0",
          commentNum:1000,
          commentGood:1000
        },
        {
          commentId:2,
          commentTitle:"糖尿病怎么治?",
          commentDes:"这个问题问的好这个问题问的好这个问题问的好这个问题问的好这个问题问的好这个问题问的好这" +
              "个问题问的好这个问题问的好这个问题问的好这个问题问的好这个问题问的好这个问题这个问题问的好这个问题" +
              "问的好这个问题问的好这个问题问的好这个问题问的好这个问题问的好这个问题问的好这个问题问的好这个问题问的好问的好这个问题问这个问题问的好的好",
          commentPersonId:1,
          commentPersonName:"狗蛋",
          commentPersonPic:"http://localhost:8887/files/7e4e6b7f5763477f9dda789a5c200a88",
          commentTime:"2020-02-20",
          commentPic:"http://localhost:8887/files/ffd80adf473540859d2ace0866910dd0",
          commentNum:1000,
          commentGood:1000
        }
      ],  //接收到的原始帖子列表
      sortList:[],        //排序后的帖子列表
      showCommentList:[], //展示的帖子列表
      dialogVisible:false,
      search:'',
      currentPage:1,
      pageSize:3,
      total:0,
      sort:false,
      adSort:1,
      sortFlag:false,
      content: '',
      user:{},
      rules: {
        commentTitle: [
          { required: true, message: '请输入帖子标题', type : "string", trigger: 'blur', min: 1},
        ],
        commentDes: [
          { required: true, message: '请简要介绍帖子', type : "string", trigger: 'blur', min: 1},
        ],
      }
    }
  },

  created(){
    let str = sessionStorage.getItem("user") || "{}"
    this.user = JSON.parse(str)
    this.load();
  },

  methods:{

    choose(comment){
      console.log(comment)
      request.post("/api/comment/saveCommentStatus", comment).then(res =>{
        if(res.code === 0){
          this.$router.push("/tiezi")
        }else{
          this.$message({
            type:"error",
            message:res.msg
          })
        }
      })
    },

    zan(obj){
      console.log(obj.commentId)
      request.post("/api/comment/zan", obj).then(res => {
        if(res.code === 0){
          this.$message({
            type:"success",
            message:"点赞成功"
          })
          this.load()
        }else{
          this.$message({
            type:"error",
            message:res.msg
          })
        }
      })
    },

    filesUploadSuccess(res){
      console.log(res)
      this.form.commentPic = res.data
    },

    goodSort(){
      console.log("热度排序")
      this.sort = true
      if(this.sortFlag){
        this.sortFlag = false
        this.sortList = this.commentList.sort((a,b)=>{
          return (b.commentGood - a.commentGood) * this.adSort
        })
      }else {
        this.adSort = -this.adSort
        this.sortFlag = false
        this.sortList = this.commentList.sort((a,b)=>{
          return (b.commentGood - a.commentGood) * this.adSort
        })
      }
      this.show()
    },

    timeSort(){
      console.log("时间排序")
      this.sort = false
      if(this.sortFlag){
        this.sortFlag = false
        this.sortList = this.commentList.sort((a,b)=>{
          return (parseInt((b.commentTime).split("-").join("")) - parseInt((a.commentTime).split("-").join(""))) * (-this.adSort)
        })
      }else {
        this.adSort = -this.adSort
        this.sortFlag = false
        this.sortList = this.commentList.sort((a,b)=>{
          return (parseInt((b.commentTime).split("-").join("")) - parseInt((a.commentTime).split("-").join(""))) * (-this.adSort)
        })
      }
      this.show()
    },

    load(){
      this.sortFlag = true
      request.get("/api/comment/findList",{
        params:{
          search:this.search
        }
      }).then(res =>{
        console.log(res)
        this.commentList = res.data
        this.total=res.data.length
        if(this.sort){
          this.goodSort()
        }else {
          this.timeSort()
        }
      })
    },

    handleSizeChange(pageSize){
      this.pageSize = pageSize
      this.sortFlag = true
      if(this.sort){
        this.goodSort()
      }else {
        this.timeSort()
      }
    },

    handleCurrentChange(pageNum){
      this.currentPage = pageNum
      this.sortFlag = true
      if(this.sort){
        this.goodSort()
      }else {
        this.timeSort()
      }
    },

    show(){
      console.log("show调用了")
      this.showCommentList = []
      for(let i = this.pageSize * (this.currentPage - 1); i < Math.min(this.pageSize * this.currentPage,this.total); i++){
        console.log("循环调用了")
        this.showCommentList.push(this.sortList[i])
        // console.log(this.showCommentList[i])
        // console.log(this.sortList[i])
      }
    },

    Add(){
      this.$nextTick(()=>{
        this.dialogVisible = true
        this.form={}
        this.$refs['upload'].clearFiles()

          editor = new E("#richText")
          editor.config.uploadImgServer = 'http://localhost:8887/files/uploadImg'
          editor.config.uploadFileName = 'file'
          editor.create()

        editor.txt.html('')  // 清除内容
      })
    },

    saveData(){
      const content = editor.txt.html()
      console.log(content)
      // 注意：这个地方需要手动赋值
      this.form.commentContent = content
      request.post("/api/comment/saveUser",this.user)
      request.post("/api/comment/saveComment", this.form).then(res => {
        if(res.code === 0){
          this.$message({
            type:"success",
            message:"保存成功"
          })
          this.load()
        }else{
          this.$message({
            type:"error",
            message:res.msg
          })
        }
      })
      this.dialogVisible = false
      this.load()
    },

    handleEdit(row){
     //
      this.form = JSON.parse(JSON.stringify(row))
      this.dialogVisible = true
      this.$nextTick(()=>{
        this.$refs['upload'].clearFiles()
      })
    },

    del(commentId){
      console.log(commentId)
      request.delete("/api/comment/delete"+commentId).then(res => {
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
  h2{
    color:#665C5C;
  }

  .sort{
    margin-top: 10px;
    margin-bottom: 5px;
  }

  .goodButton{
    background-color: #f6f6f6;
    outline:none;
    border-radius:8px;
    padding:13px;
    color:#a262ad;
    letter-spacing:2px;
    border:none;
    cursor: pointer;
  }

  .goodButton:hover{
    background: linear-gradient(200deg,#f3e7e9,#e3eeff);
    transition: background-color 0.5s ease;
  }

  .timeButton{
    background-color: #f6f6f6;
    outline:none;
    border-radius:8px;
    padding:13px;
    color:#a262ad;
    letter-spacing:2px;
    border:none;
    cursor: pointer;
  }

  .timeButton:hover{
    background: linear-gradient(200deg,#f3e7e9,#e3eeff);
    transition: background-color 0.5s ease;
  }

  .container{
    width:920px;
    display:flex;
    align-items: center;
    margin-left: 15px;
  }

  .container > img {
    width:135px;
    height:135px;
    border-radius: 5px;
  }

  .box1{
    width:715px;
    display: flex;
    flex-direction: column;
    margin-left:20px;
    margin-top:20px;
    overflow: hidden;
  }

  .title{
    line-height: 18px;
    font-size: 18px;
    font-weight: bold;
  }

  .info{
    line-height: 17px;
    font-size: 17px;
    margin-left: 8px;
    height:34px;
    display: -webkit-box;
    -webkit-box-orient: vertical;
    -webkit-line-clamp: 2;
    overflow: hidden;
    margin-top: 10px;
  }

  .box2{
    width:740px;
    height:100px;
    display: flex;
    align-items: center;
  }

  .box3{
    display: flex;
    margin-left: 7px;
    width: 280px;
  }

  .box3 > div{
    margin-left: 15px;
    margin-top: 13px;
  }

  .box3 > img {
    width:45px;
    height:45px;
    border-radius:50%;
    transform: translateY(3px);
    border: 1px solid black;
  }

  .box4{
    display: flex;
    width:230px;
    align-items: center;
    margin-left: 300px;
    margin-top: -4px;
  }

  .pinglunshuliang{
    width:25px;
    margin-left: 5px;
    display: inline-block;
  }

  .dianzanshuliang{
    width:25px;
    margin-left: 5px;
    display: inline-block;
  }

  .el-icon-thumb:hover{
    color: red;
  }

  .el-icon-chat-line-round:hover{
    color: dodgerblue;
  }

  .box{
    display: flex;
    align-items: center;
    justify-content: space-around;
  }

  .button{
    background-color: #f6f6f6;
    outline:none;
    border-radius:8px;
    padding:13px;
    color:#a262ad;
    letter-spacing:2px;
    border:none;
    cursor: pointer;
  }

  .button:hover{
    background: linear-gradient(200deg,#f3e7e9,#e3eeff);
    color: black;
    transition: background-color 0.5s ease;
  }

  a{
    width:40px;
    height: 40px;
    position: fixed;
    border-radius: 50%;
    bottom:60px;
    right:135px;
    background-color: #F0F8FF;
    border:1px solid #C6E2FF;
  }

  a i{
    position: fixed;
    bottom: 73px;
    right: 146px;
  }

  a:hover{
    background: linear-gradient(200deg,#f3e7e9,#e3eeff);
    transition: background-color 0.5s ease;
  }
</style>
