import { createRouter, createWebHistory } from 'vue-router'
import PageHome from '@/views/PageHome.vue'
import PostList from '@/views/post/PostList.vue'
import PostDetail from '@/views/post/PostDetail.vue'
import PostWrite from '@/views/post/PostWrite.vue'
import MemberLogin from '@/views/common/MemberLogin.vue'
import store from "@/vuex/store"

const requireAuth = () => (from, to, next) => {
    const token = localStorage.getItem('user_token')
    if (token) {
        store.state.isLogin = true
        return next()
    } 
    next('/member/login')
}

const routes = [
    {
        path: '/',
        name: 'PageHome',
        component: PageHome
    },
    {
        path: '/member/login',
        name: 'MemberLogin',
        component: MemberLogin
    },
    {
        path: '/about',
        name: 'PageAbout',
        component: () => import('../views/PageAbout.vue')
    },
    {
        path: '/post/list',
        name: 'PostList',
        component: PostList
    },
    {
        path: '/post/detail',
        name: 'PostDetail',
        component: PostDetail
    },
    {
        path: '/post/write',
        name: 'PostWrite',
        component: PostWrite,
        beforeEnter: requireAuth()
    }
]

const router = createRouter(
    {
        history: createWebHistory(process.env.BASE_URL),
        routes
    }
)

export default router