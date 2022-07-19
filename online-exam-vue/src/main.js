import Vue from 'vue'
import App from './App.vue'
import router from './router'

// 导入elementUI
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import locale from 'element-ui/lib/locale/lang/zh-CN' // lang i18n

import axios from 'axios'

// 配置请求根路径
axios.defaults.baseURL = 'http://localhost:8000/'
Vue.prototype.$http = axios

//导入全局样式表
import '@/assets/css/global.css'

// 导入字体图标
import '@/assets/fonts/iconfont.css'

import '@/assets/fonts/iconfont'

// 引入全局inco
import '@/icons'

import * as echarts from 'echarts'


Vue.prototype.$http = axios

Vue.config.productionTip = false

Vue.use(ElementUI, {locale})

Vue.prototype.$echarts = echarts

new Vue({
    el: '#app',
    router,
    render: h => h(App),
    components: {App},
    template: '<App/>'
})

// 添加请求拦截器，在请求头中加token

axios.interceptors.request.use(
    config => {

        if (window.sessionStorage.getItem('token')) {
            console.log("tokennnn...");

            config.headers.token = window.sessionStorage.getItem('token');

        }
        return config;

    },

    error => {

        return Promise.reject(error);

    });
