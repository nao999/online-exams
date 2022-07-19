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

              <el-button plain @click="changeQuestion(index+1)" v-for="(question,index) in singleQuestions" :key="question.id"
                         :type="selectType(index)" circle>{{ index + 1 &lt; 10 ? '0' + (index + 1) : (index + 1) }}
              </el-button>

            </div>
            <div class="side-header">
              多项选择题部分
            </div>
            <div class="side-content">
              <el-button plain @click="changeQuestion(index+9)" v-for="(question,index) in multiQuestions" :key="question.id"
                         :type="selectType(index+8)" circle>
                {{ index + 9 &lt; 10 ? '0' + (index + 9) : (index + 9) }}
              </el-button>

            </div>
            <div class="side-footer">
              <div class="side-option">
                <el-button size="mini" @click="changeQuestion(currentQuestion - 1)">上一题</el-button>
                <el-button size="mini" @click="markQuestion()">标记此题</el-button>
                <el-button size="mini" @click="changeQuestion(currentQuestion + 1)">下一题</el-button>
              </div>
              <el-button type="primary" @click="saveExerciseResult()">提交结果</el-button>
            </div>
          </el-card>
        </div>
      </el-col>
      <el-col :span="19">
        <div class="exercise-content">
          <el-card>
            <div class="content-header">
              <span class="question-title">{{exerciseTypeName}}，共{{ questions.length }}题</span>
              <span class="count-down"><i class="iconfont icon-shizhong"></i>&nbsp;已用时间<span>{{ time }}</span></span>
            </div>
          </el-card>
          <el-card>
            <div class="content-question">
              <div class="question-title">
                <el-tag effect="plain">{{ currentQuestion }}</el-tag>
                <span v-html="questions[currentQuestion-1].questionContent"></span>
              </div>
              <div class="question-reply">

                <div class="question-items" @click="chooseItem(item.prefix,questions[currentQuestion-1].questionType)"
                     v-for="item in questions[currentQuestion-1].items" :key="item.id">
                  <el-tag :type="selectItemType(item.prefix)">{{ item.prefix }}</el-tag>
                  <span v-html="item.content"></span>
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
  name: "exercise",
  data() {
    return {
      // 训练类型
      exerciseType: 1,
      // 训练类型的名字
      exerciseTypeName: '专项练习',
      // 练习的知识点
      pointId: '',
      // 总共的正确数量
      rightNum: 0,
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
      markedQuestion: [],
    }
  },

  created() {
    if(this.$route.params.id){
      this.pointId = this.$route.params.id;
      this.exerciseType = 1;
      // 查询知识点的名字
      this.getPointName();

    }else{
      this.exerciseType = 0;
      this.exerciseTypeName = "快速开始";
    }
    this.getQuestions();
    this.countTime();
    // console.log(this.$route);
  },

  methods: {
    // 根据知识点id得到知识点名字
    getPointName(){
      let point = {};
       this.$http.get(`/point/getPointById/${this.pointId}`)
        .then(response => {
          console.log(response.data.response)
          point = response.data.response;
          this.exerciseTypeName = "专项练习(" + point.knowledgePointName + ")";

        })
      // console.log("pointName:"+point);

    },
    // 判断回答正误
    judgeAnswer(){
      for(let index in this.questions){
        let itemChoose = this.itemChooseList.find((item)=>{
          console.log(item.questionId)

          return item.questionId === this.questions[index].id;
        })
        console.log(itemChoose)

        if(itemChoose !== undefined){
          // 一个一个匹配是否在答案中
          let rightAnswer = this.questions[index].rightAnswer;
          rightAnswer = rightAnswer.split(",");
          // 记录当前题目正确个数
          let rightNum = 0;
          for(let prefixIndex in itemChoose.prefix){
            if(rightAnswer.findIndex((item) => {
              return item === itemChoose.prefix[prefixIndex];
            }) !== -1){
              rightNum += 1;
            }
          }
          // 如果正确的个数等于正确答案数组的长度说明本题回答正确
          if(rightNum === rightAnswer.length){
            itemChoose.correctOrNot = 1;
            this.rightNum += 1;
          }else{
            itemChoose.correctOrNot = 0;
          }
        }
      }
    },
    // 保存练习结果
    saveExerciseResult() {
      // 进行判断正确数量
      this.judgeAnswer();
      // 判断练习类型
      // if()
      this.$http.post("/exercise-records/saveExerciseRecords", {
        exerciseType: this.exerciseType,
        exerciseTypeName: this.exerciseTypeName,
        questionList: this.questions,
        correctNum: this.rightNum,
        time: this.time,
        chooseAnswer: this.itemChooseList,
        subjectId: this.subject
      }).then((response)=>{
        if (response.data.code === 1) {
          this.$message({
            type: 'success',
            message: '提交成功'
          });
          let exerciseRecordsId = response.data.response;
          this.$router.push('/train/review/'+exerciseRecordsId);
        } else {
          this.$message({
            type: 'error',
            message: '提交失败'
          });
        }
      })
    },

    countTime() {
      this.timeInterval = setInterval(() => {
        if (this.second < 59) {
          this.second++;
        } else {
          this.second = 0;
          if (this.minute < 59) {
            this.minute++;
          } else {
            this.minute = 0;
            this.hour++;
          }
        }
        let second = '';
        let minute = '';
        let hour = '';
        if (this.second < 10) {
          second = '0' + this.second;
        } else {
          second = this.second;
        }
        if (this.minute < 10) {
          minute = '0' + this.minute;
        } else {
          minute = this.minute;
        }
        if (this.hour < 10) {
          hour = '0' + this.hour;
        } else {
          hour = this.hour;
        }

        this.time = hour + ':' + minute + ':' + second;
        // console.log(this.time)
      }, 1000)
    },

    // 查找选项是否选中的状态
    selectItemType(prefix) {
      let type = '';
      let item = this.itemChooseList.find((itemChoose) => {
        return itemChoose.questionId === this.questions[this.currentQuestion - 1].id;
      })

      if (item === undefined) {
        // console.log(item)
        return type;
      }
      // console.log(item.prefix)
      let index = (item.prefix).findIndex((data) => {
        return data === prefix;
      });
      // console.log(data-statistics)
      if (index !== -1) {
        type = 'dark';
      }
      return type;
    },
    // 选中选项
    chooseItem(prefix, questionType) {
      let itemChoose = {
        questionId: this.questions[this.currentQuestion - 1].id,
        prefix: [prefix],
      };
      // 判断有没有存在
      let data = this.itemChooseList.find((itemChoose) => {
        return itemChoose.questionId === this.questions[this.currentQuestion - 1].id
      });

      if (data === undefined) {
        this.itemChooseList.push(itemChoose);
      } else {
        if (questionType === 1) {
          data.prefix = [prefix];
        } else {
          data.prefix.push(prefix);
        }
      }
    },
    // 判断题目号的状态
    selectType(index) {
      let type = '';
      if (this.markedQuestion.indexOf(index + 1) !== -1) {
        type = 'danger';
      }
      if (this.currentQuestion === index + 1) {
        type = 'primary';
      }

      return type;

    },
    // 标记当前题目
    markQuestion() {
      if (this.markedQuestion.indexOf(this.currentQuestion) === -1) {
        this.markedQuestion.push(this.currentQuestion);
      } else {
        let index = this.markedQuestion.indexOf(this.currentQuestion);
        this.markedQuestion.splice(index, 1);
      }
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
          subjectId: this.subject,
          pointId: this.pointId,
          exerciseType: this.exerciseType
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
.content-header {
  display: flex;
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

/*.question-items:focus{*/
/*  background-color: #eeeeee;*/
/*}*/
</style>
