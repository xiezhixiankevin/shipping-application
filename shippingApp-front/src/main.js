// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import elementUI from 'element-ui'
import Cookies from "js-cookie";
import axios from 'axios'
import VueAxios from 'vue-axios'
import 'element-ui/lib/theme-chalk/index.css'
import { VueJsonp } from 'vue-jsonp';



axios.defaults.timeout = 5000 // 请求超时
axios.defaults.baseURL = '/api/'

Vue.config.productionTip = false
Vue.use(elementUI)
Vue.use(Cookies)
Vue.use(VueAxios,axios)
Vue.use(VueJsonp);


/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>'
})
