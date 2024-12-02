import { createRouter, createWebHistory } from 'vue-router'
import PageHome from '@/views/PageHome.vue'
import PostList from '@/views/post/PostList.vue'
import PostDetail from '@/views/post/PostDetail.vue'

const routes = [
    {
        path: '/',
        name: 'PageHome',
        component: PageHome
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
    }
]

const router = createRouter(
    {
        history: createWebHistory(process.env.BASE_URL),
        routes
    }
)

export default router