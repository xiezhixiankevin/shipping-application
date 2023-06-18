const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  // 关闭语法检查
  lintOnSave: false,
  // 开启代理服务器，注意：这里的端口号写后端的端口号（方式一）
  devServer: {
    port: 8081,
    //以上的ip和端口是我们本机的;下面为需要跨域的
    //代理请求路径
    proxy: {
        '/api': {
            target: "http://localhost:8088/",
            ws: true,
            changeOrigin: true,
            pathRewrite: {
                '^/api': ''
            }
        }
    },
},
})
