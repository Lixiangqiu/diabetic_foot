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
            url: '/manager',
            requiresManage: true 
          },
       },
        {
          path: 'user',
          name: 'User',
          component: () => import('@/views/user'),
          meta: {
            title: '用户管理界面',
            url: '/user',
            requiresManage: true 
          },
        },
        {
          path: 'worker',
          name: 'Worker',
          component: () => import('@/views/worker'),
          meta: {
            title: '医生管理界面',
            url: '/worker',
            requiresManage: true 
          },
        },

        //用户界面
        {
          path:'detailedMedicalRecord',
          name:'DetailedMedicalRecord',
          component: () => import('@/views/detailedMedicalRecord'),
          meta: {
            title: '个人详细病历',
            url: '/detailedMedicalRecord',
            requiresPatient: true 
          },
        },
        {
          path: 'doctorInfo',
          name: 'DoctorInfo',
          component: () => import('@/views/doctorInfo'),
          meta: {
            title: '医生列表',
            url: '/doctorInfo',
            requiresPatient: true 
          },
        },
        {
          path: 'seeDoctorInfo',
          name: 'seeDoctorInfo',
          component: () => import('@/views/seeDoctorInfo'),
          meta: {
            title: '医生详情',
            url: '/seeDoctorInfo',
            requiresPatient: true 
          },
        },
        //医生界面
        {
            path: 'dMp',
            name: 'dMp',
            component: () => import('@/views/dMp'),
            meta: {
              title: '个人病历',
              url: '/dMp',
              requiresDoctor: true 
            },
        },
        {
          path: 'dMpOther',
          name: 'dMpOther',
          component: () => import('@/views/dMpOther'),
          meta: {
            title: '病历界面',
            url: '/dMpOther',
            requiresDoctor: true 
          },
      },
      {
        path: 'dMpSuggestion',
        name: 'dMpSuggestion',
        component: () => import('@/views/dMpSuggestion'),
        meta: {
          title: '提供意见的病历',
          url: '/dMpSuggestion',
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
            path: 'question',
            name: 'question',
            component: () => import('@/views/question')
        },
        {
            path: 'seePatientCp',
            name: 'seePatientCp',
            component: () => import('@/views/seePatientCp'),
            meta: {
              title: '病人病历',
              url: '/seePatientCp',
              requiresDoctor: true 
            },
        },
        {
          path: 'passwordModify',
          name: 'passwordModify',
          component: () => import('@/views/passwordModify')
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
        path: 'Info',
        name: 'Info',
        component: () => import('@/views/Info')
    },
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
