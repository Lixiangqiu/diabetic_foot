<template>
  <el-card style="position: relative;background-color: #FEFDFD;width: 920px;">

    <div class="tou" style="background-color: #FEFDFD">
      <div style="margin-top: 10px;margin-left: 20px;">
        <i class="el-icon-d-arrow-left"></i>
        <el-button type="text" @click="returnLast()" size="normal" style="color: black;transform: translateY(-1px)">返回</el-button>
        <span style="margin-left: 357px;">{{comment.commentPersonName}}</span>
      </div>
    </div>

    <button class="cancle" v-if="user.role === 1">删除帖子</button>

    <a href="#"><i class="el-icon-caret-top"></i></a>

    <div class="headerBox">
      <img :src="comment.commentPersonPic"/>
      <div class="info">
        <div>{{comment.commentPersonName}}</div>
        <div>{{comment.commentTime}}</div>
      </div>
      <h2 id="h2" ref="hhhh" style="width: 300px;text-align: center;margin-left: 24px;">{{comment.commentTitle}}</h2>
    </div>

    <el-divider></el-divider>

    <div class="main">
      <div style="font-size: 17px;">{{comment.commentDes}}</div>
      <div v-html="comment.commentContent"></div>
    </div>

    <el-divider></el-divider>

    <h4 style="margin-left: 35px;">{{messages.length}}  评论</h4>

    <el-divider></el-divider>

    <div class="pinglun">
          <div>
            <div style="padding: 10px; color: #888">
              <div>
                <el-input type="textarea" :rows="3" v-model="entity.pub" class="input" placeholder="发表你的看法"></el-input>
                <div style="text-align: right; padding: 10px 0 5px 0"><el-button type="primary" @click="publishMessage()">评论</el-button></div>
              </div>
            </div>
            <el-divider style="transform: translateY(-10px)"></el-divider>
          </div>

          <div class="messageBox" v-for="item in messages">
            <div style="text-align: center; flex: 1">
              <el-image :src="item.photo" style="width: 60px; height: 60px; border-radius: 50%"></el-image>
            </div>
            <div style="padding: 0 8px; flex: 5">
              <div><b style="font-size: 14px">{{ item.userName }}</b></div>
              <div style="padding: 10px 3px; color: #666;width: 585px;word-wrap: break-word;">
                {{ item.messageContent }}
                <el-button type="text" size="mini" @click="del(item.messageId)" v-if="item.userName === user.name"
                style="transform: translateY(-2px);">删除</el-button>
              </div>
              <div style="background-color: #eee; padding: 10px" v-if="item.parentMessage">{{ item.parentMessage.userName }}：{{ item.parentMessage.messageContent }}</div>
              <div style="color: #888; font-size: 12px; border-bottom: 1px solid silver;">
                <span>{{ item.messageTime  }}</span>
                <el-button type="text" style="margin-left: 20px;transform: translateY(1px)" @click="reReply(item.messageId)">回复</el-button>
              </div>
            </div>
          </div>

        <span style="margin: 15px 0 10px 350px;font-size: 12px;color: #999999">没有更多评论</span>
    </div>

    <el-dialog title="回复信息" v-model="dialogFormVisible" width="30%">
      <el-form :model="entity" label-width="80px">
        <el-form-item>
          <el-input v-model="entity.reply" autocomplete="off" type="textarea" :rows="3" placeholder="你有什么想说的"></el-input>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="cancel">取 消</el-button>
        <el-button type="primary" @click="reply()">确 定</el-button>
      </template>
    </el-dialog>

  </el-card>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "tiezi",
  data() {
    return {
      comment: {},
      user:{},
      messages: [
        {
          messageId:1,
          messageContent:'这是一个回复',
          messageTime:'2022-05-04 18:32:00',
          userName:"admin",
          userId:1,
          photo:'http://localhost:8887/files/7e4e6b7f5763477f9dda789a5c200a88',
          commentId:1,
          parentId:2,
          parentMessage:{
            messageContent: '这是父级回复'
          }
        },
        {
          messageId:2,
          messageContent:'这是一个回复',
          messageTime:'2022-05-04 18:35:33',
          userName:"admin",
          userId:1,
          photo:'http://localhost:8887/files/7e4e6b7f5763477f9dda789a5c200a88',
          commentId:1,
          parentId:2,
          parentMessage:{
            content: '这是父级回复'
          }
        }
      ],
      dialogFormVisible: false,
      entity: {}
    }
  },

  created() {
    let str = sessionStorage.getItem("user") || "{}"
    this.user = JSON.parse(str)
    this.load()
  },

  methods: {
    load(){
       request.get("/api/comment/ctInfo").then(res =>{
         console.log(res)
         this.comment = res.data
         this.loadMessage()
       })
     },

    returnLast(){
      this.$router.push('/commentList')
    },

    loadMessage() {
      request.get("/api/message/messageList" + this.comment.commentId).then(res => {
        this.messages = res.data;
      })
    },

    cancel() {
      this.dialogFormVisible = false;
      this.entity = {};
    },

    reReply(id) {
      this.dialogFormVisible = true;
      this.entity.parentId = id;
    },

    reply() {
      this.entity.messageContent = this.entity.reply;
      this.save();
    },

    publishMessage(){
      this.entity.messageContent = this.entity.pub;
      this.save();
    },
    save() {
      if (!this.entity.messageContent) {
        this.$message({
          message: "请填写内容",
          type: "warning"
        });
        return;
      }
      // 如果是评论的话，在 save的时候要注意设置 当前模块的id为 foreignId。也就是  entity.foreignId = 模块id
      this.entity.commentId = this.comment.commentId
      this.entity.userId = this.user.id
      this.entity.userName = this.user.name
      this.entity.photo = this.user.photo
      request.post("/api/message/saveMessage", this.entity).then(res => {
        if (res.code === 0) {
          this.$message({
            message: "评论成功",
            type: "success"
          });
        } else {
          this.$message({
            message: res.msg,
            type: "error"
          });
        }
        this.entity = {}
        this.loadMessage();
        this.dialogFormVisible = false;
      })
    },
    del(id) {
      request.delete("/api/message/delete" + id).then(res => {
        this.$message({
          message: "删除成功",
          type: "success"
        });
        this.loadMessage()
      })
    }
  }
}
</script>

<style scoped>

  .tou{
    position: fixed;
    top:50px;
    left:400px;
    width: 920px;
    background-color: white;
    border-bottom: 1px solid black;
    z-index: 101;
  }

  .headerBox{
    margin-top: 50px;
    display: flex;
  }

  .headerBox img{
    width: 80px;
    height: 80px;
    border-radius:50%;
    margin-left: 15px;
  }

  .headerBox .info{
    display: flex;
    flex-direction: column;
  }

  .headerBox > h2{
    margin-top: 18px;
    margin-left: 20px;

  }

  .info{
    margin-left: 15px;
  }

  .info > div{
    margin-top: 15px;
    width: 150px;
  }

  .info :first-child{
    font-size: 17px;
  }

  .info :last-child{
    color:gray;
    font-size: 13px;
  }

  a{
    width:40px;
    height: 40px;
    position: fixed;border-radius: 50%;
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

  .cancle{
    width: 63px;
    height: 25px;
    background-color: #F0F8FF;
    border:1px solid #C6E2FF;
    border-radius: 3px;
    position: absolute;
    top:92px;
    right:20px;
  }

  .cancle:hover{
    background: linear-gradient(200deg,#f3e7e9,#e3eeff);
    transition: background-color 0.5s ease;
  }

  .main{
    width:723px;
    margin-left: 65px;
    min-height: 200px;
    border-radius: 5px;
  }

  .messageBox{
    display: flex;
    padding: 18px 18px 18px 5px;
  }

  .pinglun{
    width: 750px;
    margin-left: 65px;
    border-radius: 5px;
  }

  .input{
    border-radius: 3px;
    color: #999999;
  }
</style>
