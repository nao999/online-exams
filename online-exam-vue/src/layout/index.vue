<template>
  <div class="layout">
    <el-container>
    <el-header height="61" class="student-header">
      <div class="header">

        <el-menu  :default-active="$route.name" :router="true" class="el-menu-demo header-options" mode="horizontal" >
          <div class="header-logo">
            <img src="../assets/logo2.png" width="30px"/>
            <span>上弦月在线考试系统</span>
          </div>
          <el-menu-item index="Dashboard" :route="{path:'/index'}">首页</el-menu-item>
          <el-menu-item index="Train" :route="{path:'/train/data-statistics'}">试题训练</el-menu-item>
          <el-menu-item index="Task" :route="{path:'/task'}">任务中心</el-menu-item>

          <el-menu-item index="errorList" :route="{path: '/errorList'}">错题本</el-menu-item>
          <el-menu-item index="dataStatistics" :route="{path: '/dataStatistics'}">数据统计</el-menu-item>


          <div class="head-user">
            <el-dropdown trigger="click" placement="bottom">
              <el-badge :is-dot="messageCount!==0">
                <el-avatar size="large" fit="cover"
                           src="https://nao190830.oss-cn-beijing.aliyuncs.com/20210730/9baa307c07c24b378ec2c56018ba11aa.png"></el-avatar>
              </el-badge>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item @click.native="$router.push({path:'/personalCenter'})">个人中心</el-dropdown-item>
                <el-dropdown-item @click.native="$router.push({path:'http:///user/message'})">
                  <el-badge :value="messageCount" v-if="messageCount!==0">
                    <span>消息中心</span>
                  </el-badge>
                  <span v-if="messageCount===0">消息中心</span>
                </el-dropdown-item>
                <el-dropdown-item @click.native="logout" divided>退出</el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
          </div>

        </el-menu>
      </div>


    </el-header>
    <el-main class="student-main">
      <router-view/>

    </el-main>

  </el-container>

  </div>


</template>

<script>
export default {
  name: "index",
  data() {
    return {
      messageCount: 1
    }
  },
  methods:{
    logout(){
      this.logoutSave();

    },

    logoutSave(){
      this.$http.post("/user/logoutSave")
          .then(response=>{
            if(response.data.code === 1){
              window.sessionStorage.clear();
              this.$router.push('/login')
            }

          })
    }
  }
}
</script>

<style scoped>
.layout{
  height: 100%;
}

.layout .el-container{
  height: 100%;
}

.el-menu {
  display: flex;
  align-items: center;
}
.header{
  /*display: flex;*/
  /*align-items: center;*/
}
/*顶部导航栏logo*/
.header-logo{
  display: flex;
  align-items: center;
  margin-right: 20px;
}

.header-logo span{
  font-size: 20px;
  font-weight: 600;
  font-family: 华文中宋;
}

.header-options {
  display: flex;
  align-items: center;
  /*margin-left: auto;*/
  /*margin-right: 20px;*/
}

.head-user{
  margin-left: auto;
}

.student-main{
  background-color: #eeeeee;
}

</style>
