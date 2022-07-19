<template>
  <div class="content">
    <div class="center">
      <el-upload
          accept=".jpg, .png, .jpeg"
          ref="uploadAvatar"
          class="upload-demo"
          action="http://localhost:8000/upload/uploadImg"
          :limit="1"
          name="image"
          :show-file-list="false"
          :on-success="handleUploadSuccess"
          :on-exceed="exceedHandler"
          :file-list="fileList">
        <el-image
            style="width: 100px; height: 100px;border-radius: 100%;margin-left: 10px"
            :src="form.imagePath"
            fit="fill">
        </el-image>
      </el-upload>
    </div>
    <div class="right">
      <el-form ref="form" :model="form" label-width="80px">
        <el-form-item label="真实姓名:">
          <el-input v-model="form.realName"></el-input>
        </el-form-item>
        <el-form-item label="手机号:">
          <el-input v-model="form.phone"></el-input>
        </el-form-item>
        <el-form-item label="性别:" prop="resource">
          <el-radio-group v-model="form.sex">
            <el-radio label="男"></el-radio>
            <el-radio label="女"></el-radio>
          </el-radio-group>
        </el-form-item>

        <el-form-item label="活动时间:">
          <el-col :span="11">
            <el-date-picker value-format="yyyy-MM-dd" type="date" placeholder="选择日期" v-model="form.birthDay"></el-date-picker>
          </el-col>

        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="editStudentInfo">修改</el-button>
          <el-button>取消</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>

export default {
  name: "update-info",
  data() {
    return {
      form: {
        realName: '',
        phone: '',
        sex: 0,
        birthDay: '',
        imagePath: '',
      },
      fileList:[
      ]

    }
  },

  created() {
    this.getStudentInfo();
  },

  methods: {

    exceedHandler(files, fileList){
      console.log("files")
      console.log(files);
      console.log("fileList")
      console.log(fileList);
    },
    handleUploadSuccess(response){
      console.log(response)
      if(response.errno === 0){
        this.form.imagePath = response.data[0].url;
        console.log(this.$refs.uploadAvatar)
        this.$refs.uploadAvatar.clearFiles();
      }
    },

    editStudentInfo() {
      this.form.sex = this.form.sex === '男' ? 0 : 1;
      this.$http.put("/user/editStudent", this.form)
          .then(response => {
            // console.log(response.data.code)
            if(response.data.code === 1){
              this.$message({
                message: '修改成功',
                type: 'success'
              });
            }else{
              this.$message.error('修改失败');
            }
            this.getStudentInfo();
          })

    },
    getStudentInfo() {
      this.$http.get("/user/getStudentInfo")
          .then(response => {
            // console.log(response.data.response)
            let data = response.data.response;
            this.form.realName = data.realName;
            this.form.birthDay = data.birthDay;
            this.form.phone = data.phone;
            this.form.sex = data.sex === 0 ? '男' : '女';
            this.form.imagePath = data.imagePath ? data.imagePath : 'https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg';

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
  padding: 10px 20px;
  margin-top: 50px;
}

.right {
  display: flex;
  flex-direction: column;
  padding: 10px 20px;
  margin-top: 50px;
}
</style>
