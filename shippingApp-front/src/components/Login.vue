<template>
  <div class="login-page">
    <el-card class="box-card">
      <div slot="header" class="clearfix">
        <span class="login-title">🔐Shipping货运系统</span>
      </div>
      <div class="login-form">
        <el-form :model="form" :rules="loginRules" ref="loginForm">
          <el-form-item prop="email">
            <el-input type="text" v-model="form.email" auto-complete="off" placeholder="请输入邮箱">
              <template slot="prepend"><i style="font-size:20px" class="el-icon-user"></i></template>
            </el-input>
          </el-form-item>
          <el-form-item prop="password">
            <el-input type="text" v-model="form.password" auto-complete="off" placeholder="请输入密码">
              <template slot="prepend"><i style="font-size:20px" class="el-icon-key"></i></template>
            </el-input>
          </el-form-item>
          <el-form-item>
            <el-button style="width:100%;" type="primary" @click="handleLogin" :loading="loading">登录</el-button>
          </el-form-item>
        </el-form>
      </div>
      <div class="to-register-div-parent">
        <div class="to-register-div-child">
          <router-link to="/register">
            <p>没有账号?点此注册</p>
          </router-link>
        </div>
      </div>
    </el-card>
  </div>
</template>
  
<script>
import Cookies from "js-cookie";
export default {
  name: 'Login',
  data() {
    return {
      loading: false,
      form: {
        email: '',
        password: ''
      },
      loginRules: {
        email: [
          { required: true, message: '请输入邮箱', trigger: 'blur' },
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    handleLogin() {
      this.$refs.loginForm.validate().then(() => {
        this.loading = true;
        this.axios({
          method: "post",
          url: "/user/login",
          params: {
            email: this.form.email,
            password: this.form.password,
          }
        }).then(res => {
          var jsonObj = JSON.parse(JSON.stringify(res.data));
          if (jsonObj.success) {
            // 将token存在cookie
            Cookies.set("token", jsonObj.data.token, { expires: 1 });

            if (jsonObj.data.user_info.identity === 0) {
              this.$router.push(
                {
                  path: '/user/dashboard',
                });
            } else if (jsonObj.data.user_info.identity === 1){
              this.$router.push(
                {
                  path: '/carrier/dashboard',
                });
            }

          } else {
            alert("登录失败！:" + jsonObj.message);
            this.loading = false;
            return false;
          }
        }).catch(function (error) {
          console.log(error);
        });
      }).catch((error => {
        this.$message({
          message: '输入错误！',
          type: 'warning'
        });
      }))
    },
    checkCookie(c_name) {
      let arg1 = Cookies.get(c_name);
      if (arg1 != null && arg1 != '') {
        return true;
      } else {
        return false;
      }
    },
  }
}
</script>
  
<style scoped>
.login-page {
  background-image: linear-gradient(180deg, #2af598 0%, #009efd 100%);
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
}

.login-title {
  font-size: 20px;
}

.box-card {
  width: 375px;
}

.to-register-div-parent {
  text-align: center;
  height: auto;
}

.to-register-div-child {
  float: right;
  height: auto;
}
</style>
