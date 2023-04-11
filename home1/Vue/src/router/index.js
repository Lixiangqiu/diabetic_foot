import { createRouter, createWebHistory } from 'vue-router'
import Layout from '../Layout/Layout.vue'

const routes = [
  {
    path: '/',
    name: 'Layout',
    component: Layout,
    redirect:'/login',
    children:[
      //管理员
        {
          path: 'manager',
          name: 'Manager',
          component: () => import('@/views/manager'),
          meta: {
            title: '管理员界面',
            url: '@/views/manager',
            requiresManage: true 
          },
       },
        {
          path: 'user',
          name: 'User',
          component: () => import('@/views/user'),
          meta: {
            title: '用户管理界面',
            url: '@/views/user',
            requiresManage: true 
          },
        },
        {
          path: 'worker',
          name: 'Worker',
          component: () => import('@/views/worker'),
          meta: {
            title: '医生管理界面',
            url: '@/views/worker',
            requiresManage: true 
          },
        },

        //用户界面
        {
            path: 'Info',
            name: 'Info',
            component: () => import('@/views/Info')
        },
        {
          path: 'commentList',
          name: 'CommentList',
          component: () => import('@/views/commentList')
        },
        {
            path: 'tiezi',
            name: 'Tiezi',
            component: () => import('@/views/tiezi')
        },

        //医生界面
        {
            path: 'dMp',
            name: 'dMp',
            component: () => import('@/views/dMp'),
            meta: {
              title: '个人病历',
              url: '@/views/dMp',
              requiresDoctor: true 
            },
        },
        {
          path: 'dMpOther',
          name: 'dMpOther',
          component: () => import('@/views/dMpOther'),
          meta: {
            title: '病历界面',
            url: '@/views/dMpOther',
            requiresDoctor: true 
          },
      },
        //公共界面
        {
            path: 'im',
            name: 'Im',
            component: () => import("@/views/Im"),
        },
        {
            path: 'questionInfo',
            name: 'questionInfo',
            component: () => import("@/views/questionInfo"),
        },
        {
            path: 'doctorInfo',
            name: 'DoctorInfo',
            component: () => import('@/views/doctorInfo')
        },
        {
            path: 'seeDoctorInfo',
            name: 'seeDoctorInfo',
            component: () => import('@/views/seeDoctorInfo')
        },
        {
            path: 'question',
            name: 'question',
            component: () => import('@/views/question')
        },
        {
            path: 'seePatientCp',
            name: 'seePatientCp',
            component: () => import('@/views/seePatientCp')
        }
    ]
  },

  {
    path: '/login',
    name: 'Login',
    component: () => import('@/views/Login.vue')
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
