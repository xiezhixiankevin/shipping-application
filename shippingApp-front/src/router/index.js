import Vue from 'vue'
import Router from 'vue-router'
import Login from '@/components/Login'
import Register from '@/components/Register'
import index from '@/components/index'
import CreateOrder from '@/components/order/CreateOrder'
import QueryOrder from '@/components/order/QueryOrder'
import UserDashboard from '@/components/user/UserDashboard'
import DepartingTrunks from '@/components/carrier/DepartingTrunks'
import DepartedTrunks from '@/components/carrier/DepartedTrunks'
import HistoryTrunks from '@/components/carrier/HistoryTrunks'
import CarrierDashboard from '@/components/carrier/CarrierDashboard'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Login',
      component: Login
    },
    {
      path: '/Register',
      name: 'Register',
      component: Register
    },
    {
      path: '/index',
      name: 'index',
      component: index
    },
    {
      path: '/order/createOrder',
      name: 'createOrder',
      component: CreateOrder,
    },
    {
      path: '/order/queryOrder',
      name: 'queryOrder',
      component: QueryOrder
    },
    {
      path: '/user/dashboard',
      name:  'userDashboard',
      component: UserDashboard
    },
    {
      path: '/carrier/departingTrunks',
      name: 'departingTrunks',
      component: DepartingTrunks
    },
    {
      path: '/carrier/departedTrunks',
      name: 'departedTrunks',
      component: DepartedTrunks
    },
    {
      path: '/carrier/historyTrunks',
      name: 'historyTrunks',
      component: HistoryTrunks
    },
    {
      path: '/carrier/dashboard',
      name: 'carrierDashboard',
      component: CarrierDashboard,
    }
  ]
})
