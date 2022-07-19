<template>
  <div>
    <div class="carousel">
      <el-carousel :interval="5000" type="card" >
          <el-carousel-item >
            <img src="@/assets/carousel/1.png" class="carousel-img">
          </el-carousel-item>
          <el-carousel-item >
            <img src="@/assets/carousel/2.png" class="carousel-img">
          </el-carousel-item>
          <el-carousel-item >
            <img src="@/assets/carousel/3.png" class="carousel-img">
          </el-carousel-item>
          <el-carousel-item >
            <img src="@/assets/carousel/4.png" class="carousel-img">
          </el-carousel-item>
      </el-carousel>
    </div>
    <div class="index-content">
    <el-row class="content-item" :gutter="20">
      <el-col span="10">
      <el-card  style="margin-bottom: 20px">
        <h3 class="index-title-h3" style="border-left: solid 10px #2ce8b4;">排行榜</h3>
        <div class="top-content">
          <div class="top-title">
            <div style="display: flex; flex-direction: column;align-items: center;">
              <div style="position: relative">
                <el-image
                    style="width: 100px; height: 100px;border-radius: 100%;border: 5px solid #eac947"
                    src="https://nao190830.oss-cn-beijing.aliyuncs.com/20210730/9baa307c07c24b378ec2c56018ba11aa.png"
                    fit="fill"></el-image>
                <svg class="icon" aria-hidden="true" style="height: 30px;position: absolute;left:-130px">
                  <use xlink:href="#icon-guanjun"></use>
                </svg>
              </div>
              <div>
                <span style="color: rgb(255, 161, 22);">naooo</span>
              </div>
            </div>
            <div style="display: flex;justify-content: space-evenly;">
              <div style="position: relative;display: flex;flex-direction: column;align-items: center;">
                <el-image
                    style="width: 100px; height: 100px;border-radius: 100%;border: 5px solid #cdd0ca"
                    src="https://nao190830.oss-cn-beijing.aliyuncs.com/20210730/9baa307c07c24b378ec2c56018ba11aa.png"
                    fit="fill"></el-image>
                <svg class="icon" aria-hidden="true" style="height: 30px;position: absolute;left:-130px">
                  <use xlink:href="#icon-yajun1"></use>
                </svg>
                <div>
                  <span style="color: rgb(255, 161, 22);">naooo</span>
                </div>
              </div>

              <div>
                <div style="position: relative;display: flex;flex-direction: column;align-items: center;">
                  <el-image
                      style="width: 100px; height: 100px;border-radius: 100%;border: 5px solid #cab08e"
                      src="https://nao190830.oss-cn-beijing.aliyuncs.com/20210730/9baa307c07c24b378ec2c56018ba11aa.png"
                      fit="fill"></el-image>
                  <svg class="icon" aria-hidden="true" style="height: 30px;position: absolute;left: -130px">
                    <use xlink:href="#icon-jijun"></use>
                  </svg>
                  <div>
                    <span style="color: rgb(255, 161, 22);">naooo</span>
                  </div>
                </div>

              </div>
            </div>

          </div>
          <div class="top-list">
            <div class="top-list-item">
              <span style="margin-right: 20px">4</span>
              <el-image
                  style="width: 50px; height: 50px;border-radius: 100%;"
                  src="https://nao190830.oss-cn-beijing.aliyuncs.com/20210730/9baa307c07c24b378ec2c56018ba11aa.png"
                  fit="fill"></el-image>
              <div class="top-list-item-content">
                <span>naoooo</span>
                <span style="color: grey;">正确率：97%</span>
              </div>
            </div>
            <div class="top-list-item">
              <span style="margin-right: 20px">5</span>
              <el-image
                  style="width: 50px; height: 50px;border-radius: 100%;"
                  src="https://nao190830.oss-cn-beijing.aliyuncs.com/20210730/9baa307c07c24b378ec2c56018ba11aa.png"
                  fit="fill"></el-image>
              <div class="top-list-item-content">
                <span>naoooo</span>
                <span style="color: grey;">正确率：97%</span>
              </div>
            </div>
            <div class="top-list-item">
              <span style="margin-right: 20px">6</span>
              <el-image
                  style="width: 50px; height: 50px;border-radius: 100%;"
                  src="https://nao190830.oss-cn-beijing.aliyuncs.com/20210730/9baa307c07c24b378ec2c56018ba11aa.png"
                  fit="fill"></el-image>
              <div class="top-list-item-content">
                <span>naoooo</span>
                <span style="color: grey;">正确率：97%</span>
              </div>
            </div>
          </div>
        </div>
      </el-card>
      </el-col>
      <el-col span="14">
      <el-card style="margin-bottom: 20px">
        <h3 class="index-title-h3" style="border-left: solid 10px #3651d4;">任务中心</h3>
        <div class="task-content">
          <el-card v-for="item in taskData" :key="item.id" class="task-item" style="padding-left: 15px">
            <div slot="header" class="clearfix">
              <span>{{item.title}}</span>
            </div>
            <div class="task-item-body">
            <span>学科：语文</span>
            <span>建议时长：{{item.time}}</span>
            <span>试卷总分：{{item.score}}</span>
            <span>发布时间：{{item.createTime}}</span>
            <span>截止时间：{{item.deadline}}</span>
            </div>
          </el-card>

        </div>
      </el-card>
      </el-col>
    </el-row>
    </div>


  </div>
</template>

<script>
export default {
  data() {
    return {
      activeIndex: '1',
      activeIndex2: '1',
      taskData: []
    };
  },
  created() {
    this.getTaskList();
  },
  methods: {
    getTaskList() {
      this.$http.get("/task/stu/unfinishedTask").
      then(response => {
        console.log(response.data)
        this.taskData = response.data.response;
        // this.tableData = response.data.response.records;
        // this.pageTotal = response.data.response.total;
      })
    },
    handleSelect(key, keyPath) {
      console.log(key, keyPath);
    }
  }
}
</script>

<style>
.el-carousel__item h3 {
  color: #475669;
  font-size: 14px;
  opacity: 0.75;
  line-height: 200px;
  margin: 0;
}

.el-carousel__item:nth-child(2n) {
  background-color: #99a9bf;
}

.el-carousel__item:nth-child(2n+1) {
  background-color: #d3dce6;
}

/*轮播图*/
.carousel-img {
  display: inline-block;
  height: 300px;
  width: 100%;
}

/*内容*/
.index-content{
  padding: 0 50px;
}
.content-item .index-title-h3 {
  font-size: 22px;
  font-weight: 400;
  color: #1f2f3d;
  border-left: solid 10px #2ce8b4;
  padding-left: 10px;
}

.task-content{
  display: flex;
  flex: 0 0 33.3%;
  flex-flow: row wrap;
}
.task-content .el-card{
  margin-right: 20px;
  margin-bottom: 10px;
}
.task-item-body{
  display: flex;
  flex-direction: column;
}

.top-title{
  border-bottom: 1px solid #eee;
  padding: 10px 0;
}

.top-list .top-list-item{
  display: flex;
  align-items: center;
  margin-top: 20px;
  padding: 20px 0;
  border-bottom: 1px solid #eee;
  justify-content: center;
}
.top-list-item .top-list-item-content{
  display: flex;
  flex-direction: column;
  margin-left: 10px;
}
</style>
