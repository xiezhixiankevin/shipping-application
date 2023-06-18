<template>
    <div class="register-page">
      <el-card class="box-card">
        <div slot="header" class="clearfix">
          <span class="register-title">🔐Shipping货运系统</span>
        </div>
        <div class="register-form">
          <el-form :model="form" :rules="registerRules" ref="registerForm">
            <el-form-item prop="email">
              <el-input type="text" v-model="form.email" auto-complete="off" placeholder="请输入邮箱">
                <template slot="prepend"><i style="font-size:20px" class="el-icon-user"></i></template>
              </el-input>
            </el-form-item>
            <el-form-item prop="username">
              <el-input type="text" v-model="form.username" auto-complete="off" placeholder="请输入用户名">
                <template slot="prepend"><i style="font-size:20px" class="el-icon-user"></i></template>
              </el-input>
            </el-form-item>
            <el-form-item prop="password">
              <el-input type="text" v-model="form.password" auto-complete="off" placeholder="请输入密码">
                <template slot="prepend"><i style="font-size:20px" class="el-icon-key"></i></template>
              </el-input>
            </el-form-item>
            <el-form-item  prop="code">
                    <el-input style="width: 65%;" type="text" v-model="form.code" auto-complete="off" placeholder="请输入验证码">
                        <template slot="prepend"><i style="font-size:20px" class="el-icon-key"></i></template>
                    </el-input>
                    <el-button @click="handleSendCode">获取验证码</el-button>
            </el-form-item>
            <el-form-item  prop="identity">
                <el-switch
                    style="display: block;margin-left: 60%;"
                    v-model="form.identity"
                    active-color="#13ce66"
                    inactive-color="#00aaff"
                    active-text="客户"
                    inactive-text="承运商">
                </el-switch>
            </el-form-item>
            <el-form-item>
              <el-button style="width:100%;" type="primary" @click="handleRegister" :loading="loading">注册</el-button>
            </el-form-item>
          </el-form>
        </div>
        <div class="to-register-div-parent">            
            <div class="to-register-div-child">
                <router-link to="/"> <p>已有账号?点此登录</p> </router-link>
            </div>
        </div>
      </el-card>
    </div>
  </template>
  
  <script>
  
export default {
  name: 'Register',
  data(){
    return {
      loading: false,
      form:{
        email: '',
        username: '',
        password: '',
        code:'',
        identity:true,
      },
      registerRules:{
        email: [
          { required: true, message: '请输入邮箱', trigger: 'blur' },
        ],
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur'}
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur'}
        ],
        code: [
          { required: true, message: '请输入验证码', trigger: 'blur'}
        ]
      }
    }
  },
  methods:{
    handleRegister(){
      this.$refs.registerForm.validate().then(()=>{
        this.loading = true;
        //异步请求后台接口 注册操作
        this.axios({
        method: "post",
        url: "/user/register",
        data: {
            username: this.form.username,
            password: this.form.password,
            email: this.form.email,
            code: this.form.code,
            identity: this.form.identity? 0 : 1,
        }
        }).then(res => {
            //使用vue-router路由到指定页面，该方式称之为编程式导航
            var jsonObj = JSON.parse(JSON.stringify(res.data));
            if(jsonObj.success){
                alert("注册成功！即将跳转到登录页面....");
                this.$router.push(
                {
                  path: '/',
                  // query:{token:jsonObj.data.token}
                });
            }else{
                alert("注册失败！:" + jsonObj.message);
                this.loading = false;
                return false;
            }
        }).catch(function (error) {
          console.log(error);
        });
      }).catch((error=>{
        this.$message({
          message: '输入错误！',
          type: 'warning'
        });
      }))
    },
    handleSendCode(){
        if(this.form.email == null || this.form.email.length == 0){
          alert("邮箱不能为空！！");
          return ;
        }
        var verify = /^\w[-\w.+]*@([A-Za-z0-9][-A-Za-z0-9]+\.)+[A-Za-z]{2,14}/;
        // 校验邮箱格式
        if (!verify.test(this.form.email)) {
            alert("邮箱格式错误, 请重新输入");
            return ;
        }
        //异步请求后台接口 获取验证码操作
        this.axios({
        method: "get",
        url: "/user/get-register-code",
        params: {
            email: this.form.email,
        }
        }).then(res => {
            var jsonObj = JSON.parse(JSON.stringify(res.data));
            if(jsonObj.success){
                alert("验证码发送成功，请查看您的邮箱！");
            }else{
                alert("验证码发送失败:" + jsonObj.message);
                return false;
            }
        }).catch(function (error) {
          console.log(error);
        });
    },
  }
}
</script>

  <style scoped>
  .register-page{
    background-image: linear-gradient(180deg, #2af598 0%, #009efd 100%);
    height: 100vh;
    display: flex;
    justify-content: center;
    align-items: center;
  }
  
  .register-title{
    font-size: 20px;
  }
  
  .box-card {
    width: 375px;
  }

  .to-register-div-parent{
    text-align: center;
    height: auto;
  }

  .to-register-div-child{
    float:right;
    height: auto;
  }
  
  </style>
