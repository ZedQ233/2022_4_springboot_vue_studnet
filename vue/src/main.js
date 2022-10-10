import Vue from 'vue'
import App from './App.vue'
import router from './router'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import './assets/gloable.css'
// import request from "../../src/main/java/com/qx/demo/utils/request";
import request from "../src/utils/request"

Vue.config.productionTip = false

Vue.use(ElementUI,{size:"mini"})

Vue.prototype.request = request

new Vue({
  router,
  render: h => h(App),
  beforeCreate() {
    Vue.prototype.$bus = this
  }
}).$mount('#app')
