<template>
  <div>
    <el-row :gutter="10">
      <el-col :span="5">
        <div class="exercise-side">
          <el-card>
            <div class="side-header">
              单项选择题部分
            </div>
            <div class="side-content">

              <el-button plain @click="changeQuestion(index+1)" v-for="(question,index) in singleQuestions"
                         :key="question.id"
                         :type="selectType(index)" circle>{{ index + 1 &lt; 10 ? '0' + (index + 1) : (index + 1) }}
              </el-button>

            </div>
            <div class="side-header">
              多项选择题部分
            </div>
            <div class="side-content">
              <el-button plain @click="changeQuestion(index+9)" v-for="(question,index) in multiQuestions"
                         :key="question.id"
                         :type="selectType(index + 8)" circle>
                {{ index + 9 &lt; 10 ? '0' + (index + 9) : (index + 9) }}
              </el-button>

            </div>

            <div class="side-header">
              计算题部分
            </div>
            <div class="side-content">
              <el-button plain @click="changeQuestion(index+13)" v-for="(question,index) in calculateQuestions" :key="question.id"
                         :type="selectType(index+12)" circle>
                {{ index + 13 &lt; 10 ? '0' + (index + 13) : (index + 13) }}
              </el-button>

            </div>

            <div class="side-footer">
              <div class="side-option">
                <el-button size="mini" @click="changeQuestion(currentQuestion - 1)">上一题</el-button>
                <el-button size="mini" type="primary" @click="addErrorBook()">加入错题本</el-button>
                <el-button size="mini" @click="changeQuestion(currentQuestion + 1)">下一题</el-button>
              </div>
              <!--              <el-button type="primary" @click="saveExerciseResult()">提交结果</el-button>-->
            </div>
          </el-card>
        </div>
      </el-col>
      <el-col :span="19">
        <div class="exercise-content">
          <el-card>
            <div class="content-header">
              <div class="review-title">{{exerciseTypeName}}</div>
              <div class="review-msg">
                <div class="review-number">
                  <span style="font-weight: 600"><i class="iconfont icon-dadui"></i>&nbsp;答对题目数：</span>
                  <span class="correct-number">{{ correctNum }}</span>/{{ questions.length }}
                </div>
                <div class="review-time">
                  <span style="font-weight: 600"><i class="iconfont icon-shizhong"></i>&nbsp;用时：</span>
                  <span style="color: #67C23A">{{ time }}</span>
                </div>

              </div>
            </div>
          </el-card>
          <el-card>
            <div class="content-question">
              <div class="question-title"
                   :style="itemChooseList[currentQuestion-1].correctOrNot? 'background-color: #f0f9eb;':'background-color: #fff7f7;'">
                <el-tag effect="plain">{{ currentQuestion }}</el-tag>
                <span v-html="questions[currentQuestion-1].questionContent"></span>
              </div>
              <div class="question-reply">

                <div class="question-items"
                     v-for="item in questions[currentQuestion-1].items" :key="item.id">
                  <el-tag :type="selectItemType(item)">{{ item.prefix }}</el-tag>
                  <span v-html="item.content"></span>
                </div>

              </div>

            </div>

          </el-card>
          <el-card>
            <div class="content-footer">
              <div class="footer-item">
                <span>
                  正确答案：
                  <span style="color: #67C23A" v-html="questions[currentQuestion - 1].rightAnswer.trim()">
                  </span>
                  <span v-if="!(currentQuestion>12 || itemChooseList[currentQuestion - 1].correctOrNot)">
                    &nbsp;你的答案：
                    <span style="color:#F56C6C;">
                    {{ itemChooseList[currentQuestion - 1].prefix.toLocaleString().trim() }}
                    </span>
                  </span>
                </span>

              </div>
              <div class="footer-item">
                <div class="footer-title">
                  <i class="iconfont icon-kaodian"></i>&nbsp;考点
                </div>
                <div class="footer-content">
                  <el-tag class="question-point" v-for="item in questions[currentQuestion - 1].pointList" :key="item">
                    {{ item }}
                  </el-tag>
                </div>
              </div>

              <div class="footer-item">
                <div class="footer-title">
                  <i class="iconfont icon-GroupCopy"></i>&nbsp;解析
                </div>
                <div class="footer-content">
                  <span v-html="questions[currentQuestion - 1].analysis"></span>
                </div>
              </div>
            </div>
          </el-card>
        </div>

      </el-col>
    </el-row>
  </div>
</template>

<script>
export default {
  name: "review",
  data() {
    return {
      // 训练类型
      exerciseTypeName: '',
      // 总共的正确数量
      correctNum: 0,
      // 用时
      time: '',
      second: 0,
      minute: 0,
      hour: 0,
      // 选项选择
      itemChooseList: [],
      subject: this.$route.query.subject,
      questions: [],
      currentQuestion: 1,
      singleQuestions: [],
      multiQuestions: [],
      calculateQuestions: [],
      markedQuestion: [],
    }
  },

  created() {
    this.getRecords();

    // console.log(this.$route);
  },

  methods: {
    addErrorBook(){
      this.$http.post("/errorList/addErrorQuestion",{
        questionId: this.questions[this.currentQuestion - 1].id,
        studentAnswer: this.itemChooseList[this.currentQuestion - 1].prefix.join(",").trim(),
        rightAnswer: this.questions[this.currentQuestion - 1].rightAnswer
      })
      .then(response=>{
        // console.log(response)
        if(response.data.code === 1){
          this.$message({
            message: '添加成功',
            type: 'success'
          });
        }else if(response.data.code === 602){
          this.$message({
            message: response.data.message,
            type: 'error'
          });
        }else{
          this.$message({
            message: "添加失败",
            type: 'error'
          });
        }
      })
    },
    // 判断选项状态
    selectItemType(item) {
      let type = '';
      if (this.itemChooseList[this.currentQuestion - 1].prefix === null) {
        return type;
      }
      // 把用户的选择显示出来，正确就用绿色，错误就用红色
      let index = this.itemChooseList[this.currentQuestion - 1].prefix.findIndex(data => {
        return data.trim() === item.prefix
      })
      if (index !== -1) {
        if (this.itemChooseList[this.currentQuestion - 1].correctOrNot === 0) {
          type = 'danger';
        } else {
          type = 'success';
        }
      }
      return type;
    },
    getRecords() {
      let id = this.$route.params.id;
      this.$http.get(`/exercise-records/getExerciseRecordsById/${id}`)
          .then(response => {
            console.log(response)
            this.exerciseTypeName = response.data.response.exerciseTypeName;
            this.questions = response.data.response.questionList;
            this.singleQuestions = this.questions.slice(0, 8);
            this.multiQuestions = this.questions.slice(8, 12);
            this.calculateQuestions = this.questions.slice(12,16);
            this.correctNum = response.data.response.correctNum;
            this.time = response.data.response.time;
            this.itemChooseList = response.data.response.chooseAnswer;
          })
    },
    // 判断题目号的状态
    selectType(index) {
      let type = '';
      if (this.itemChooseList[index].correctOrNot === 0) {
        type = 'danger';
      } else {
        type = 'success';
      }
      if (this.currentQuestion === index + 1) {
        type = 'primary';
      }

      return type;

    },


    changeQuestion(val) {
      if (val === 0 || val === this.questions.length + 1) {
        return;
      }
      this.currentQuestion = val;

    },
    getQuestions() {
      this.$http.get("/question/getExerciseQuestions", {
        params: {
          subjectId: this.subject
        }
      })
          .then(response => {
            // this.point = response.data.response;
            console.log(response.data.response)
            this.questions = response.data.response;
            this.singleQuestions = this.questions.slice(0, 8);
            this.multiQuestions = this.questions.slice(8, 12);
          })
    },
  }
}


</script>

<style scoped>
.exercise-side .el-card {
  /*height: 100%;*/
}

.side-content {
  margin-top: 10px;
  display: flex;
  flex-wrap: wrap;
}

.side-content .el-button {
  margin-right: 10px;
  margin-bottom: 10px;
}

.side-footer {
  display: flex;
  flex-direction: column;
}

.side-option {
  display: flex;
  flex: 1;
  justify-content: space-between;
  margin-bottom: 10px;
  /*margin-right: 10px;*/
}

/*取消按钮之间的间隔*/
.el-button + .el-button {
  margin-left: 0px;
}

.count-down span {
  padding: 0 10px;
  font-weight: 600;
  color: #E6A23C
}


/*练习主体部分*/
.content-header .review-title {
  /*color: #f5f7fa*/
  font-size: 25px;
  font-weight: 600;

  /*text-align: center;*/
}

.review-msg {
  display: flex;
  align-items: baseline;
  margin-top: 10px;
  /*padding: 0 10px;*/
}

.review-msg .correct-number {

  font-size: 30px;
  color: #409EFF;
}

.review-msg .review-time {
  margin-left: 30px;
}

.count-down {
  margin-left: auto;
}

.exercise-content .el-card {
  margin-bottom: 10px;
}

/*题目部分*/
.content-question {
  /*display: flex;*/
  /*height: 490px;*/
}

.question-title {
  display: flex;
  align-items: baseline;
}

.question-title .el-tag {
  margin-right: 10px;
}

.question-reply {
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
}

.question-reply .question-items {
  margin-top: 10px;
  flex: 0 0 50%;
  display: flex;
  align-items: center;
}

.question-items .el-tag {
  margin-right: 10px;
}

.question-reply .question-items:hover {
  background-color: #f5f7fa;
  /*color: blue;*/
}


/*解析*/
.content-footer {
  display: flex;
  flex-direction: column;
}

.content-footer .footer-item {
  margin-bottom: 10px;
  /*display: flex;*/
}

.footer-item .footer-title {
  font-weight: 600;
}

.footer-item .footer-content {
  margin-top: 10px;
  margin-left: 20px;
}

.footer-content .question-point {
  margin-right: 10px;
}

</style>
