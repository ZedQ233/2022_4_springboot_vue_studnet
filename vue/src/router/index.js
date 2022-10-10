import Vue from 'vue'
import VueRouter from 'vue-router'
import Manage from '../views/Manage.vue'
import User from "@/views/User";
import Home from "@/views/Home";
import Login from "@/views/Login";
import Register from "@/views/Register";
import Person from "@/views/Person";
import File from "@/views/File";

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: '大概框架',
    component: Manage,
    redirect:"/home",
    children:[
      {
        path: 'user',
        name: '用户管理',
        component:User
      },
      {
        path: 'home',
        name: '主页',
        component:Home
      },
      {
        path: 'person',
        name: '用户',
        component:Person
      },
      {
        path:'file',
        name:'文件管理',
        component: File
      }
    ]
  },
  {
    path: '/about',
    name: 'about',
    component: () => import(/* webpackChunkName: "about" */ '../views/AboutView.vue')
  },
  {
    path: '/login',
    name: '登录',
    component:Login
  },
  {
    path: '/register',
    name: '注册',
    component:Register
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

router.beforeEach((to, from, next) =>{
  console.log(to)
  next(vm => {
    vm.$bus.$emit("getPathName",to.name)
  });

})


export default router
