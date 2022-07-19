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
                         :type="selectType(index+8)" circle>
                {{ index + 9 &lt; 10 ? '0' + (index + 9) : (index + 9) }}
              </el-button>

            </div>

            <div class="side-header">
              计算题部分
            </div>
            <div class="side-content">
              <el-button plain @click="changeQuestion(index+13)" v-for="(question,index) in calculateQuestions"
                         :key="question.id"
                         :type="selectType(index+12)" circle>
                {{ index + 13 &lt; 10 ? '0' + (index + 13) : (index + 13) }}
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
              <span class="question-title">{{ exerciseTypeName }}，共{{ questions.length }}题</span>
              <span class="count-down"><i class="iconfont icon-shizhong"></i>&nbsp;已用时间<span>{{ time }}</span></span>
            </div>
          </el-card>
          <el-card>
            <div class="content-question">
              <div class="question-title">
                <el-tag effect="plain">{{ currentQuestion }}</el-tag>
                <span v-html="questions[currentQuestion-1].questionContent"></span>
              </div>
              <div class="question-reply" v-if="questions[currentQuestion-1].items.length !== 0">


                <div class="question-items" @click="chooseItem(item.prefix,questions[currentQuestion-1].questionType)"
                     v-for="item in questions[currentQuestion-1].items" :key="item.id">
                  <el-tag :type="selectItemType(item.prefix)">{{ item.prefix }}</el-tag>
                  <span v-html="item.content"></span>
                </div>

              </div>

              <div class="question-reply" v-if="questions[currentQuestion-1].items.length === 0">

                <div class="question-upload">
                  <el-upload
                      class="upload-demo"
                      action="http://localhost:8000/upload/uploadImg"
                      :on-remove="handleRemove"
                      :file-list="fileList"
                      name="image"
                      :on-success="handleUploadSuccess"
                      list-type="picture"
                      limit="1">
                    <el-button size="small" type="primary">点击上传</el-button>
                    <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>
                  </el-upload>
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
  name: "do",
  data() {
    return {
      fileList: [],
      // 选项选择
      itemChooseList: [],
      // 用时
      time: '',
      second: 0,
      minute: 0,
      hour: 0,

      exerciseTypeName: '',
      paper: {},
      questions: [],
      currentQuestion: 1,
      singleQuestions: [],
      multiQuestions: [],
      calculateQuestions: [],
      markedQuestion: [],
    }
  },
  created() {
    // 根据任务的id查询
    let taskId = this.$route.params.id;
    this.getPaper(taskId);
    // this.countTime();
    console.log(123)
  },
  methods: {
    handleRemove(file){
      console.log(file)
      // 删除上传回答图片
      this.itemChooseList.forEach(itemChoose => {
        if(itemChoose.questionId === this.questions[this.currentQuestion - 1].id){
          itemChoose.answer = [];
          // console.log(file, fileList);
        }

      })


    },
    getAnswerImg() {
      let itemChoose = this.itemChooseList.find((item) => {
        return item.questionId === this.questions[this.currentQuestion - 1].id;
      });
      console.log(itemChoose);
      if (itemChoose !== undefined && itemChoose.length !== 0) {
        for(let index in itemChoose.answer){
          let file = {
            name: 'answer',
            url: itemChoose.answer[index]
          }
          this.fileList.push(file);
        }


      }
    },
    saveExerciseResult() {
      this.$http.post("/taskResult/saveTaskResult",
          {
            answer: this.itemChooseList,
            taskId: this.$route.params.id
          })
        .then(response =>{
          console.log(response)
          if(response.data.code === 1){
            this.$message({
              type: 'success',
              message: '提交成功'
            });
            this.$router.push("/task")
          }else{
            this.$message({
              type: 'error',
              message: '提交失败'
            });
          }
        })
    },
    handleUploadSuccess(response) {

      let answer = {
        questionId: this.questions[this.currentQuestion - 1].id,
        answer: [response.data[0].url]
      };
      // 判断是否已经存在
      let index = this.itemChooseList.findIndex(item => {
        return item.questionId === answer.questionId;
      })
      if(index !== -1){
        this.itemChooseList[index].answer.push(response.data[0].url)
      }else{
        this.itemChooseList.push(answer);
      }
    },
    async getPaperId(taskId) {
      let data = await this.$http.get(`/task/getTaskById/${taskId}`);
      console.log(data.data.response);
      return data.data.response.paper;
    },
    async getPaper(taskId) {
      let paperId = await this.getPaperId(taskId);
      this.$http.get(`/paper/getPaperById/${paperId}`)
          .then(response => {
            this.paper = response.data.response;
            this.exerciseTypeName = this.paper.paperTitle;
            this.questions = this.paper.questionItems[0].questionContent;
            this.questions = this.questions.concat(this.paper.questionItems[1].questionContent);

            this.singleQuestions = this.questions.slice(0, 8);
            this.multiQuestions = this.questions.slice(8, 12);
            this.calculateQuestions = this.questions.slice(12, 16);
            // this.questions
          });
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
      let index = (item.answer).findIndex((data) => {
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
        answer: [prefix],
      };
      // 判断有没有存在
      let data = this.itemChooseList.find((itemChoose) => {
        return itemChoose.questionId === this.questions[this.currentQuestion - 1].id
      });
      console.log(data)
      if (data === undefined) {
        this.itemChooseList.push(itemChoose);
      } else {
        // 判断是否已经选中
        let index = data.answer.findIndex(item =>{
          return item === prefix;
        });
        if(index !== -1){
          data.answer.splice(index,1);
        }else{
          if (questionType === 1) {
            data.answer = [prefix];
          } else {
            data.answer.push(prefix);
          }
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
      // 改变题目时如果是计算题则把储存计算题回答的当前fileList清空，并从itemChoose返回显示
      if (this.currentQuestion > 12) {
        this.fileList = [];
        this.getAnswerImg();
      }
    }
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

.question-reply .question-upload {
  margin-left: 50px;
}

/*.question-items:focus{*/
/*  background-color: #eeeeee;*/
/*}*/
</style>
