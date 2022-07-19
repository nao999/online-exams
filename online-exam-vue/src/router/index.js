import Vue from 'vue'
import Router from 'vue-router'
import NProgress from 'nprogress'
import 'nprogress/nprogress.css'// nprogress样式文件
import Layout from '@/layout/index'

Vue.use(Router)

const router = new Router({
    routes: [
        // {
        //     path:'/',
        //     redirect: '/login'
        //
        // },
        {
            path: '/login',
            component: () => import('@/views/login')
        },
        {
            path: '/',
            component: Layout,
            redirect: '/index',
            children: [
                {
                    path: '/index',
                    component: () => import('@/views/dashboard/index'),
                    name: 'Dashboard',
                    meta: {title: '首页'}
                },
                {
                    path: '/train/data-statistics',
                    component: () => import('@/views/train/index'),
                    name: 'Train',
                    meta: {title: '试题训练'}
                },
                {
                    path: '/train/exercise',
                    component: () => import('@/views/train/exercise'),
                    name: 'Train',
                    meta: {title: '试题训练'}
                },
                {
                    // 根据知识点id进行训练
                    path: '/train/exercise/:id',
                    component: () => import('@/views/train/exercise'),
                    name: 'Train',
                    meta: {title: '试题训练'}
                },
                {
                    // 查看训练结果
                    path: '/train/review/:id',
                    component: () => import('@/views/train/review'),
                    name: 'Train',
                    meta: {title: '试题训练'}
                },

                {
                    // 只能组卷训练
                    path: '/train/paperExercise',
                    component: () => import('@/views/train/paper-exercise'),
                    name: 'Train',
                    meta: {title: '试题训练'}
                },

                {
                    // 任务中心
                    path: '/task',
                    component: () => import('@/views/task/index'),
                    name: 'Task',
                    meta: {title: '任务中心'}
                },
                {
                    // 完成任务中的试题
                    path: '/task/do/:id',
                    component: () => import('@/views/task/do'),
                    name: 'Task',
                    meta: {title: '任务中心'}
                },
                {
                    path: '/errorList',
                    component: () => import('@/views/error-list/index'),
                    name: 'errorList',
                    meta: {title: '错题本'}
                },
                {
                    path: '/dataStatistics',
                    component: () => import('@/views/data-statistics/index'),
                    name: 'dataStatistics',
                    meta: {title: '数据统计'}
                },
                {
                    path: '/personalCenter',
                    component: () => import('@/views/personal-center/index'),
                    name: 'personalCenter',
                    meta: {title: '个人中心'},
                    redirect: '/personal-info',
                    children:[
                        {
                            path: '/personal-info',
                            component: () => import('@/views/personal-center/personal-info'),
                            name: 'PersonalInfo',
                            meta: {title: '个人信息'}
                        },
                        {
                            path: '/update-info',
                            component: () => import('@/views/personal-center/update-info'),
                            name: 'PersonalInfo',
                            meta: {title: '更新信息'}
                        },
                        {
                            path: '/password-change',
                            component: () => import('@/views/personal-center/password-change'),
                            name: 'PasswordChange',
                            meta: {title: '修改密码'}
                        },
                        {
                            path: '/personal-issues',
                            component: () => import('@/views/personal-center/personal-issues'),
                            name: 'PersonalIssues',
                            meta: {title: '个人动态'}
                        }
                    ]
                }

            ]
        }
    ]
})

export default router;

router.beforeEach((to, from, next) => {
    // 开启进度条
    NProgress.start();
    // to表示将要访问的路径
    // from表示从哪个路径跳转而来
    // next是一个函数，表示放行 next()放行，next('/login') 强制跳转
    if (to.path === '/login') return next();
    // 获取token
    const tokenStr = window.sessionStorage.getItem('token');
    if (!tokenStr) return next('/login');
    next();
});

//当路由跳转结束后
router.afterEach(() => {
    // 关闭进度条
    NProgress.done()
})
