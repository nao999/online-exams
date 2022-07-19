<template>
  <el-row>
    <el-col :span="18" :offset="3">
      <el-card style="margin-bottom: 10px">
        <div class="statistics-head">
          <span>学科：</span>
          <el-tag v-for="item in subjectOption" :key="item.id" :effect="selectType(item.id)"
                  @click="selectSubject(item.id)">{{ item.name }}
          </el-tag>
        </div>
      </el-card>
      <el-row :gutter="10">
        <el-col :span="16">
          <el-row :gutter="10">
            <el-col :span="24">
              <el-card>
                <div class="total-rate-header">预测分数</div>
                <div class="total-rate-figure">
                  <el-progress type="circle" stroke-width="10" :format="format" :percentage="25"></el-progress>
                </div>
                <div class="total-rate-item">
                  <span class="iconfont icon-yonghu">&nbsp;正确数量</span>
                  <span>{{ rightNum }} / {{ totalQuestionNum }}</span>
                </div>
                <div class="total-rate-item">
                  <span class="iconfont icon-yonghu">&nbsp;排名</span>
                  <span>10/500</span>
                </div>
                <div class="total-rate-item">
                  <span class="iconfont icon-yonghu">&nbsp;正确率</span>
                  <span>{{ rightRate }}%</span>
                </div>
                <div class="total-rate-item">
                  <span class="iconfont icon-yonghu">&nbsp;平均用时</span>
                  <span>{{ aveTime }}</span>
                </div>
              </el-card>
            </el-col>

            <el-col :span="24" style="margin-top: 10px">
              <el-card>
                <div id="myChart" style="width: 600px;height:400px;"></div>
              </el-card>
            </el-col>
          </el-row>
        </el-col>
        <el-col :span="8">

          <el-card>
            <div slot="header" class="clearfix">
              <span>正确率</span>
            </div>

            <div class="right-rate-item" v-for="(item,index) in point" :key="item.id">
              <div class="parent-item" @click="showChildren(index)">
                <div>
                  <span class=" iconfont icon-down1"></span>
                  <span style="flex-shrink: 1" class="side-item">&nbsp;{{ item.label }}</span>
                </div>
                <el-progress class="side-item" style="margin-left: 10px;width: 40%" :width="16"
                             :percentage="item.questionNumber === 0 ? 0 : parseInt(item.doneQuestionNumber/item.questionNumber*100)"></el-progress>
              </div>
              <template v-if="visibleOrNot(index)">
                <div class="child-item" v-for="childItem in item.children" :key="childItem.id">
                  <span style="flex-shrink: 1" class="side-item">&nbsp;{{ childItem.label }}</span>
                  <el-progress class="side-item" style="margin-left: 10px;width: 40%" :width="16"
                               :percentage="childItem.questionNumber === 0 ? 0 : parseInt(childItem.doneQuestionNumber/item.questionNumber*100)"></el-progress>
                </div>
              </template>
            </div>

          </el-card>

        </el-col>
      </el-row>
    </el-col>
  </el-row>
</template>

<script>
let myChart;
export default {
  name: "index",
  data() {
    return {
      predictScoreDate: [],
      predictScoreList: [],
      rightRate: 0,
      subjectOption: [],
      totalQuestionNum: 0,
      rightNum: 0,
      aveTime: '',
      point: [],
      subject: '',
      childrenVisible: []
    }
  },


  created() {
    this.getSubject();
  },

  mounted() {
    this.draw();
    this.savePredictScore();
  },

  methods: {
    // 更新图表
    refreshChart() {
      console.log("refresh");
      // 绘制图表
      myChart.setOption({

        title: {text: '预测分趋势'},
        tooltip: {},
        xAxis: {
          type: 'category',
          data: this.predictScoreDate
        },
        yAxis: {
          type: 'value'
        },
        series: [{
          data: this.predictScoreList,
          type: 'line',
          smooth: true
        }]
      });
    },
    getPredictScore() {
      this.$http.get("/predict-score/getPredictScore", {
        params: {
          subject: this.subject
        }
      }).then(response => {
        // console.log(response.data.response)
        // 判断是否已经存在图表数据
        // console.log(index)
        if (this.predictScoreList.length !== 0 || this.predictScoreDate.length !== 0) {
          this.predictScoreList.splice(0, this.predictScoreList.length);
          this.predictScoreDate.splice(0, this.predictScoreDate.length);
        }
        let predictList = response.data.response;
        for (let index in predictList) {

          this.predictScoreList.push(predictList[index].score);
          let time = predictList[index].time.substring(5,predictList[index].time.length);
          this.predictScoreDate.push(time);

        }
        this.refreshChart();
      })
    },
    savePredictScore() {
      this.$http.post("/predict-score/savePredictScore")
          .then(response => {
            console.log(response.data)
          })
    },
    selectType(id) {

      if (this.subject === id) {
        return 'dark';
      }
      return 'light'
    },
    selectSubject(id) {
      this.subject = id;
      this.getStatisticsInfo();
      this.getPoint();
      this.getPredictScore();
    },
    // 当年级选好时，发送请求获得所有的学科
    getSubject() {
      this.$http.get("/subject/getAllSubject", {
        params: {
          level: 4
        }
      }).then(response => {
        this.subjectOption = response.data.response;
        this.subject = this.subjectOption[0].id;
        // console.log(response.data)
        this.getStatisticsInfo();
        this.getPoint();
        this.getPredictScore();
      })

    },
    getStatisticsInfo() {

      this.$http.get("/exercise-records/getStatisticsInfo", {
        params: {
          subject: this.subject
        }
      })
          .then(response => {
            // console.log(response);
            this.aveTime = response.data.response.aveTime;
            this.rightNum = response.data.response.rightNum;
            this.totalQuestionNum = response.data.response.totalQuestionNum;
            this.rightRate = parseInt(this.rightNum / this.totalQuestionNum * 100);
          })
    },

    visibleOrNot(index) {
      let dataIndex = this.childrenVisible.findIndex(item => {
        return item === index;
      });
      if (dataIndex === -1) {
        // 存在就删除
        return false;
      } else {
        return true;
      }
    },
    // 显示子元素
    showChildren(index) {
      // 判断是否已经保存
      let dataIndex = this.childrenVisible.findIndex(item => {
        return item === index;
      });
      if (dataIndex !== -1) {
        // 存在就删除
        this.childrenVisible.splice(dataIndex, 1);
      } else {
        // 将索引保存
        this.childrenVisible.push(index);
      }
    },
    // 得到知识点
    getPoint() {
      // console.log(this.ruleForm.subject);
      // 得到表单中的学科id并发送请求得到所有的知识点
      this.$http.get("/point/getAllPoint", {
        params: {
          subjectId: this.subject
        }
      })
          .then(response => {
            this.point = response.data.response;
            // console.log(response.data.response)
          })
    },
    draw() {
      // 基于准备好的dom，初始化echarts实例
      myChart = this.$echarts.init(document.getElementById('myChart'))
      // 绘制图表
      myChart.setOption({

        title: {text: '预测分趋势'},
        tooltip: {},
        xAxis: {
          type: 'category',
          data: this.predictScoreDate
        },
        yAxis: {
          type: 'value'
        },
        series: [{
          data: this.predictScoreList,
          type: 'line',
          smooth: true
        }]
      });

    },
    format() {

      return this.rightRate;
    }
  }
}


</script>

<style scoped>
.statistics-head {
  display: flex;
  align-items: center;
}

.statistics-head .el-tag {
  margin-left: 10px;
  font-size: 15px;
}

/**/
.total-rate-header {
  font-size: 18px;
  color: #3c464f;
  line-height: 24px;
  font-weight: 600;
  margin-bottom: 10px;
}

.total-rate-figure {
  display: flex;
  justify-content: center;
  margin-bottom: 10px;
}

.total-rate-item {
  display: flex;
  justify-content: space-between;
  padding: 10px 0;
  border-bottom: 1px solid #eee;


}

.total-rate-item span {
  font-size: 14px;
  color: #6d7278;
}

/*侧边栏正确率*/
.parent-item {
  display: flex;
  margin-bottom: 10px;
  padding: 10px 0;
  border-bottom: 1px solid #eee;
  justify-content: space-between;
}

.child-item {
  display: flex;
  /*margin-bottom: 10px;*/
  padding: 10px 0;
  justify-content: space-between;
}

.side-item {
  /*flex: 0 0 33%;*/
}
</style>
