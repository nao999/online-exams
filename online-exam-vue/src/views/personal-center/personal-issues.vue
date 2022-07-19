<template>
 <div class="content">
   <el-timeline>
     <el-timeline-item v-for="item in studentLog" :key="item.id" :timestamp="item.createTime" placement="top">
       <el-card>
         <h4>{{item.operationType}}</h4>
       </el-card>
     </el-timeline-item>

   </el-timeline>
 </div>
</template>

<script>
export default {
  name: "personal-issues",
  data(){
    return{
      studentLog:[]
    }
  },

  created() {
    this.getLogInfo();
  },
  methods:{
    getLogInfo(){
      this.$http.get("/studentLog/getLogInfo")
        .then(response=>{
          this.studentLog = response.data.response;
          console.log(this.studentLog)
        })
    }
  }

}
</script>

<style scoped>
.content {
  display: flex;
  flex-direction: column;
  padding: 10px 20px;
  margin-top: 50px;
  width: 60%;
}
</style>
