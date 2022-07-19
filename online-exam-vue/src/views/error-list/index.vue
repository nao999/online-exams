<template>
  <div>
    <el-row>
      <el-col :span="18" :offset="3">
        <div class="error-list-head">
          <el-dropdown @command="handleCommand">
            <span class="el-dropdown-link">
              选择科目<i class="el-icon-arrow-down el-icon--right"></i>
            </span>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item v-for="item in subjectOption" :key="item.id" :command="item">{{ item.name }}
              </el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
          <el-tag >{{ subject }}</el-tag>
        </div>

        <el-table
            :data="tableData"
            style="width: 100%">
          <el-table-column type="expand">
            <template slot-scope="props">
              <div class="content-question">
                <div class="question-title">
                  <!--                  <el-tag effect="plain">{{ props }}</el-tag>-->
                  <span v-html="props.row.questionContent.questionContent"></span>
                  <div class="question-reply">
                    <div class="question-items"
                         v-for="item in props.row.questionContent.items" :key="item.id">
                      <el-tag>{{ item.prefix }}</el-tag>
                      <span v-html="item.content"></span>
                    </div>
                  </div>
                </div>
                <div class="content-footer">
                  <div class="footer-item">
                    <span>
                      正确答案：
                      <span style="color: #67C23A" v-html="props.row.rightAnswer.trim()">
                      </span>
                      <span>
                        &nbsp;你的答案：
                        <span style="color:#F56C6C;">
                        {{ props.row.studentAnswer.trim() }}
                        </span>
                      </span>
                    </span>

                  </div>
                  <div class="footer-item">
                    <div class="footer-title">
                      <i class="iconfont icon-kaodian"></i>&nbsp;难度
                    </div>
                    <div class="footer-content">
                      <el-rate
                          v-model="props.row.questionContent.difficult"
                          disabled
                          show-score
                          text-color="#ff9900"
                          score-template="{value}">
                      </el-rate>
                    </div>
                  </div>
                  <div class="footer-item">
                    <div class="footer-title">
                      <i class="iconfont icon-kaodian"></i>&nbsp;考点
                    </div>
                    <div class="footer-content">
                      <el-tag class="question-point" v-for="item in props.row.questionContent.pointList"
                              :key="item">
                        {{ item }}
                      </el-tag>
                    </div>
                  </div>

                  <div class="footer-item">
                    <div class="footer-title">
                      <i class="iconfont icon-GroupCopy"></i>&nbsp;解析
                    </div>
                    <div class="footer-content">
                      <span v-html="props.row.questionContent.analysis"></span>
                    </div>
                  </div>
                </div>
                <!--                <div class="question-reply">-->

                <!--                  <div class="question-items"-->
                <!--                       v-for="item in questions[currentQuestion-1].items" :key="item.id">-->
                <!--                    <el-tag :type="selectItemType(item)">{{ item.prefix }}</el-tag>-->
                <!--                    <span v-html="item.content"></span>-->
                <!--                  </div>-->

                <!--                  -->
                <!--                </div>-->

              </div>

            </template>
          </el-table-column>
          <el-table-column
              label="题目内容"
              prop="questionContent"
              width="500"
          >
            <template slot-scope="scope">
              <p v-html='scope.row.questionContent.questionContent'></p>

            </template>
          </el-table-column>
          <el-table-column
              label="题型"
              prop="questionContent.questionTypeName">
          </el-table-column>
          <el-table-column
              label="学科"
              prop="questionContent.subject">
          </el-table-column>
          <el-table-column
              label="保存时间"
              prop="createTime"
          >

          </el-table-column>
        </el-table>

        <div class="page">
          <el-pagination
              @size-change="handleSizeChange"
              @current-change="handleCurrentChange"
              :current-page="this.currentPage"
              :page-sizes="[5, 15, 20]"
              :page-size="this.pageSize"
              layout="total, sizes, prev, pager, next, jumper"
              :total="this.pageTotal">
          </el-pagination>
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
      subjectOption: [],
      subject: "全部错题",
      subjectId: '',
      currentPage: 1,
      pageTotal: 0,
      pageSize: 5,
      keyword: '',
      tableData: []
    };
  },
  created() {
    this.getSubject();
    this.getErrorList();
  },
  methods: {
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
    handleCommand(command) {
      // this.$message('click on item ' + command);
      // console.log(command)
      this.subject = command.name;
      this.subjectId = command.id;
      this.getErrorList();
    },
    getErrorList() {
      this.$http.get("/errorList/page/list", {
        params: {
          pageNum: this.currentPage,
          pageSize: this.pageSize,
          keyword: this.keyword,
          subject: this.subjectId
        }
      }).then(response => {
        console.log(response.data)
        this.tableData = response.data.response.records;
        this.pageTotal = response.data.response.total;
      })
    },
    handleChange(val) {
      console.log(val);
    },

    handleSizeChange(val) {
      this.pageSize = val;
      this.getErrorList();
    },
    handleCurrentChange(val) {
      this.currentPage = val;
      this.getErrorList();
      window.scrollTo(0, 0);

    }
  }
}

</script>

<style scoped>

.error-list-head {
  display: flex;
  /*flex-direction: column;*/
  align-items: flex-start;
  margin-bottom: 10px;
}

.error-list-head .el-tag {
  margin-left: 10px;
  font-size: 15px;
}


.el-dropdown-link {
  cursor: pointer;
  font-size: 20px;
  font-weight: 600;
  /*color: #409EFF;*/
}

.el-icon-arrow-down {
  font-size: 12px;
}


.demo-table-expand {
  font-size: 0;
}

.demo-table-expand label {
  width: 90px;
  color: #99a9bf;
}

.demo-table-expand .el-form-item {
  margin-right: 0;
  margin-bottom: 0;
  width: 50%;
}

.question-reply {
  display: flex;
  flex-wrap: wrap;
}

.question-reply .question-items {
  display: flex;
  flex: 0 0 50%;
  align-items: center;
}

.question-items .el-tag {
  margin-right: 10px;
}

.content-footer{
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
