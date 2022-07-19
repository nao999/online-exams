<template>
  <div>
    <el-row>
      <el-col :span="12" :offset="5">
        <div class="paper-head">
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
        <div class="paper-list">
          <el-table
              :data="tableData"
              style="width: 100%">
            <el-table-column
                prop="createTime"
                label="创建时间"
                width="180">
            </el-table-column>
            <el-table-column
                prop="paper"
                label="试卷标题"
                width="180">
            </el-table-column>
            <el-table-column
                prop="title"
                label="任务说明">
            </el-table-column>
            <el-table-column
                prop="option"
                label="操作">
              <template slot-scope="scope">
                <el-button v-if="scope.row.status===0" size="mini" type="primary" @click="toFinishTask(scope.row)">去完成
                </el-button>
                <el-button v-if="scope.row.status===1" size="mini" type="warning">待批改</el-button>
                <el-button v-if="scope.row.status===2" size="mini" type="success">已完成</el-button>

              </template>
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
      subject: "全部任务",
      subjectId: '',
      currentPage: 1,
      pageTotal: 0,
      pageSize: 5,
      tableData: []
    }
  },

  created() {
    this.getSubject();
    this.getTaskList();
  },

  methods: {
    toFinishTask(row) {
      this.$router.push(`/task/do/${row.id}`);
    },
    getTaskList() {
      this.$http.get("/task/stu/page/list", {
        params: {
          pageNum: this.currentPage,
          pageSize: this.pageSize,
          keyword: this.keyword,
          subjectId: this.subjectId
        }
      }).then(response => {
        this.tableData = response.data.response.records;
        this.pageTotal = response.data.response.total;
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
    handleCommand(command) {
      // this.$message('click on item ' + command);
      // console.log(command)
      this.subject = command.name;
      this.subjectId = command.id;
      this.getTaskList();
    },
    handleSizeChange(val) {
      this.pageSize = val;
      this.getTaskList();
    },
    handleCurrentChange(val) {
      this.currentPage = val;
      this.getTaskList();
      window.scrollTo(0, 0);

    }
  }
}
</script>

<style scoped>

.paper-head {
  display: flex;
  /*flex-direction: column;*/
  align-items: baseline;
}

.paper-head .el-tag {
  margin-left: 10px;
  /*margin-top: 10px;*/
  font-size: 15px;
}

/*主体部分*/
.paper-list {
  margin-top: 10px;
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

.page{
  margin-top: 10px;
}
</style>
