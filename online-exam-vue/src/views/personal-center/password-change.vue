<template>
  <div class="content">
    <el-form ref="form" :model="form" :rules="rules" label-width="150px">
      <el-form-item label="旧密码:" prop="oldPassword" required>
        <el-input v-model="form.oldPassword"></el-input>
      </el-form-item>
      <el-form-item label="新密码:" prop="newPassword" required>
        <el-input type="password" v-model="form.newPassword"></el-input>
      </el-form-item>
      <el-form-item label="确认密码:" prop="confirmPassword" required>
        <el-input type="password" v-model="form.confirmPassword"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submitForm('form')">修改</el-button>
        <el-button>取消</el-button>
      </el-form-item>

    </el-form>
  </div>
</template>

<script>
export default {
  name: "password-change",
  data() {
    var checkPassword = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次输入密码'));
      } else if (value !== this.form.newPassword) {
        callback(new Error('两次输入密码不一致!'));
      } else {
        callback();
      }
    };
    return {
      form: {
        oldPassword: '',
        newPassword: '',
        confirmPassword: '',

      },
      rules: {
        oldPassword: [
          {required: true, message: '请输入旧密码', trigger: 'blur'}
        ],
        newPassword: [
          {required: true, message: '请输入新密码', trigger: 'blur'}
        ],
        confirmPassword: [
          {required: true, validator:checkPassword, trigger: 'blur'}
        ],
      }
    }

  },

  methods: {
    passWordChange(){
      this.$http.put("/user/changePassword",{
        oldPassword: this.form.oldPassword,
        newPassword: this.form.newPassword
      })
        .then(response => {
          console.log(response.data)
          if(response.data.code === 603){
            this.$message({
              showClose: true,
              message: response.data.message,
              type: 'error'
            });
            return false;
          }else{
            this.$message({
              showClose: true,
              message: '修改成功',
              type: 'success'
            });
          }
        })
    },
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.passWordChange();
        } else {
          return false;
        }
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    }
  }

}
</script>

<style scoped>
.content {
  display: flex;
  flex-direction: column;
  padding: 10px 20px;
  margin-top: 50px;
  width: 60%;
}
</style>
