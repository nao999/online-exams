<template>
  <div>
    <el-row>
      <el-col :span="12" :offset="3">
        <div class="train-subject-select">
          <span>科目：</span>
          <el-select v-model="subject" placeholder="请选择" @change="getPoint()">
            <el-option
                v-for="item in subjectOption"
                :key="item.id"
                :label="item.name"
                :value="item.id">
            </el-option>
          </el-select>
        </div>
      </el-col>
    </el-row>
    <el-row :gutter="20">
      <el-col :span="12" :offset="3">
        <el-card style="margin-bottom: 10px">
          <el-row :gutter="20" class="train-items">
            <el-col :span="12">
              <el-card shadow="hover">
                <div slot="header" class="clearfix">
                  <div class="train-item">
                    <svg class="icon" aria-hidden="true" style="height: 60px;margin-bottom: 20px">
                      <use xlink:href="#icon-kuaisukaishi"></use>
                    </svg>
                    <span>快速开始进行习题练习</span>

                  </div>
                </div>
                <div class="card-footer">
                  <el-button type="primary"
                             @click="quickStart()">
                    快速开始</el-button>
                </div>
              </el-card>
            </el-col>
            <el-col :span="12">
              <el-card shadow="hover">
                <div slot="header" class="clearfix">
                  <div class="train-item">
                    <svg class="icon" aria-hidden="true" style="height: 60px;margin-bottom: 20px">
                      <use xlink:href="#icon-shijuan1"></use>
                    </svg>
                    <span>自动生成试卷进行测试</span>
                  </div>
                </div>
                <div class="card-footer">
                  <el-button type="warning" @click="paperExercise()">智能组卷</el-button>
                </div>
              </el-card>
            </el-col>
          </el-row>

        </el-card>
        <el-card>
          <div slot="header" class="clearfix">
            专项练习
          </div>
          <el-tree :data="point" >
           <span class="custom-tree-node" slot-scope="{ node, data }">
              <span>{{ node.label }}</span>
              <span style="color:#999aaa;margin-left: 10px">({{data.doneQuestionNumber}}/{{data.questionNumber}})</span>
              <span style="margin-left: auto">
                <el-button
                    type="text"
                    size="mini"
                    @click="to_exercise(data)">
                  去练习&gt;
                </el-button>

              </span>
           </span>
          </el-tree>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card>
          <div slot="header" class="clearfix">
            <span>练习历史</span>
          </div>
          <div class="exercise-history">
            <div class="exercise-history-item" @click="toReview(item.id)" v-for="item in exerciseHistoryList" :key="item.id">
              <div class="exercise-message">
                <div class="exercise-title">{{item.exerciseTypeName}}</div>
                <div class="exercise-footer">
                  <el-tag size="mini" >练习</el-tag>
                  <div class="exercise-time">完成时间：{{item.createTime}}</div>
                </div>
              </div>
              <div class="exercise-status">
                <el-tag>已完成</el-tag>
              </div>
            </div>

          </div>
        </el-card>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="12" :offset="3">

      </el-col>
    </el-row>

  </div>
</template>

<script>
export default {
  name: "index",

  data() {
    return {
      exerciseHistoryList: [],
      point: [],
      subject: '',
      subjectOption: [],
    }
  },

  created() {
    this.getSubject();
    this.exerciseHistory();
  },

  methods: {
    paperExercise(){
      this.$router.push({
        path: '/train/paperExercise',
        query: {
          subject: this.subject
        }
      })
    },
    toReview(id){
      this.$router.push(`/train/review/${id}`);
    },

    // 获得练习的历史记录
    exerciseHistory(){
      this.$http.get("/exercise-records/getExerciseList")
        .then(response => {
          this.exerciseHistoryList = response.data.response;
        })
    },

    // 根据知识点训练
    to_exercise(data){
      this.$router.push({
        path: `/train/exercise/${data.id}`,
        query: {
          subject: this.subject
        }
      })
    },
    // 快速开始
    quickStart(){
      this.$router.push({
        path: '/train/exercise',
        query: {
          subject: this.subject
        }
      })
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
            console.log(response.data.response)
          })
    },
    // 当年级选好时，发送请求获得所有的学科
    getSubject() {
      this.$http.get("/subject/getAllSubject", {
        params: {
          level: 4
        }
      })
          .then(response => {
            this.subjectOption = response.data.response;
            console.log(response.data)
          })
    },
  }
}
</script>

<style scoped>
.el-row {
  margin-bottom: 20px;
}

.train-items {
  /*display: flex;*/
}

.train-items .el-card {
  margin-right: 20px;
}

/*快速开始*/
.train-item {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 10px;
}

.train-item span {
  font-size: 20px;
}

.train-item .el-button {
  border: 1px solid #e5e5e5;
}

.card-footer {
  display: flex;
  justify-content: center;
}

.custom-tree-node {
  flex: 1;
  display: flex;
  align-items: center;
  /*justify-content: space-between;*/
  font-size: 14px;
  padding-right: 8px;
}

/*训练的历史记录*/
.exercise-history-item{
  display: flex;
  align-items: center;
  justify-content: space-between;
  border-bottom: 1px solid #eeeeee;
  padding: 10px 0;
  /*transition: .3s;*/
  cursor:pointer;
}



.exercise-message .exercise-footer{
  display: flex;
  align-items: center;
}
.exercise-footer{
  margin-top: 10px;
}
.exercise-footer .exercise-time{
  font-size: 12px;
  margin-left: 5px;
}
</style>
