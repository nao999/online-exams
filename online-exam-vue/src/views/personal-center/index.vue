<template>
  <div>
    <el-row :gutter="10">
      <el-col :span="18" :offset="3">
        <div class="personal-center">

          <div class="left">
            <span class="left-header">个人中心</span>
            <span class="left-item" :style="getFocusIndex(1)" @click="selectOption(1)">
                <i class="iconfont icon-yonghu"></i>&nbsp;个人信息
              </span>
            <span class="left-item" :style="getFocusIndex(2)" @click="selectOption(2)">
                <i class="iconfont icon-bianji"></i>&nbsp;更新信息
              </span>
            <span class="left-item" :style="getFocusIndex(3)" @click="selectOption(3)">
                <i class="iconfont icon-mima"></i>&nbsp;修改密码
              </span>
            <span class="left-item" :style="getFocusIndex(4)" @click="selectOption(4)">
                <i class="iconfont icon-shebeidongtai"></i>&nbsp;个人动态
              </span>
            <span class="left-item" :style="getFocusIndex(5)" @click="selectOption(5)">
                <i class="iconfont icon-renwu1"></i>&nbsp;我的任务
              </span>

          </div>

          <router-view/>

        </div>
      </el-col>

    </el-row>
  </div>
</template>

<script>
export default {
  name: "index",
  data() {
    return {
      focusIndex: 0
    }
  },

  created() {
    // alert(this.$router.meta)
    this.selectFocus();
  },

  methods: {
    selectFocus(){
      let route = this.$route.path;
      switch (route){
        case '/personal-info':
          this.focusIndex = 1;
          break;
        case '/update-info':
          this.focusIndex  = 2;
          break;
        case '/password-change':
          this.focusIndex = 3;
          break;
      }
    },

    getFocusIndex(index) {
      let style = '';
      if (index === this.focusIndex) {
        style = 'background-color: #c6e2ff;';
      }
      return style;
    },
    selectOption(index) {
      let router = '';
      switch (index) {
        case 1:
          this.focusIndex = 1;
          router = '/personal-info';
          break;
        case 2:
          this.focusIndex = 2;
          router = '/update-info';
          break;
        case 3:
          this.focusIndex = 3;
          router = '/password-change';
          break;
        case 4:
          this.focusIndex = 4;
          router = '/personal-issues';
          break;
      }
      this.$router.push(router);
    }
  }
}

</script>

<style scoped>
.personal-center {
  display: flex;
  background-color: white;
  /*padding: 10px;*/
  /*height: 600px;*/
  min-height: 600px;
}

.left {
  display: flex;
  flex-direction: column;
  border-right: 1px solid #eee;
  /*padding: 10px 50px;*/
  margin-top: 20px;
  align-items: center;
  width: 20%;
}

.left .left-header {
  font-size: 25px;
  font-weight: 600;
  margin-bottom: 20px;
}

.left-item {
  /*margin-bottom: 20px;*/
  padding: 10px 0;
  width: 100%;
  text-align: center;
}

.left-item:hover {
  background-color: #c6e2ff;
}




.student-info-header {
  display: flex;
  flex-direction: column;
  align-items: center;
  border-bottom: 1px solid #eee;
  padding: 10px 0;
}

.student-info-header .el-image {
  margin-bottom: 10px;
}

.student-info-content {
  display: flex;
  flex-direction: column;
  margin-top: 10px;
}

.content-item {
  display: flex;
  justify-content: center;
}
</style>
