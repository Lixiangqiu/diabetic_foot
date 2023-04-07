import { createRouter, createWebHistory } from 'vue-router'
import Layout from '../Layout/Layout.vue'

const routes = [
  {
    path: '/',
    name: 'Layout',
    component: Layout,
    redirect:'/manager',
    children:[
        {
          path: 'manager',
          name: 'Manager',
          component: () => import('@/views/manager')
       },
        {
          path: 'user',
          name: 'User',
          component: () => import('@/views/user')
        },
        {
          path: 'worker',
          name: 'Worker',
          component: () => import('@/views/worker')
        },
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
        {
            path: 'dMp',
            name: 'dMp',
            component: () => import('@/views/dMp')
        },
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
