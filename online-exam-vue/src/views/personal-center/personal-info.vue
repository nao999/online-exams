<template>
  <div class="content">
    <div class="center">
      <div class="center-item">
        <span class="item-header">用户名：</span>
        {{student.userName}}
      </div>
      <div class="center-item">
        <span class="item-header">姓名：</span>
        {{student.realName}}
      </div>
      <div class="center-item">
        <span class="item-header">手机号：</span>
        {{student.phone}}
      </div>
      <div class="center-item">
        <span class="item-header">性别：</span>
        {{student.sex}}
      </div>
      <div class="center-item">
        <span class="item-header">年龄：</span>
        {{student.age}}
      </div>
      <div class="center-item">
        <span class="item-header">出生日期：</span>
        {{student.birthDay}}
      </div>
      <div class="center-item">
        <span class="item-header">注册时间：</span>
        {{student.createTime}}
      </div>
    </div>
    <div class="right">
      <span class="right-item">
        <span class="item-header">年级：</span>
        {{student.userLevel}}
      </span>
      <span class="right-item">
        <span class="item-header">班级：</span>
        {{student.userClass}}
      </span>
      <el-image
          style="width: 150px; height: 150px;border-radius: 100%;"
          :src="student.imagePath"
          fit="fill"></el-image>
    </div>
  </div>
</template>

<script>
import utils from '@/store/utils';
export default {
  name: "personal-info",
  data() {
    return {
      student:{}
    }
  },
  created() {
    this.getStudentInfo();
  },

  methods: {
    getStudentInfo() {
      this.$http.get("/user/getStudentInfo")
          .then(response => {
            // console.log(response.data.response)
            this.student = response.data.response;
            // 转换班级，年级
            this.student.userClass = utils.changeClass(this.student.userClass)
            this.student.userLevel = utils.changeLevel(this.student.userLevel);
            this.student.imagePath = this.student.imagePath ? this.student.imagePath : 'https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg';
          })
    }
  }
}
</script>

<style scoped>
.content {
  display: flex;
}

.center {
  display: flex;
  flex-direction: column;
  padding: 10px 50px;
  margin-top: 50px;
}

.center .center-item {
  margin-bottom: 10px;
  font-weight: 300;
}

.item-header{
  font-weight: 600;
}

.right {
  display: flex;
  flex-direction: column;
  padding: 10px 50px;
  margin-top: 50px;
}

.right .right-item {
  margin-bottom: 10px;
  font-weight: 300;
}
</style>
