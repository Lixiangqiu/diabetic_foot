import router from './router'
import {ElMessage} from 'element-plus';

router.beforeEach((to, from, next) => {
  console.log("=======路由跳转需要匹配！！=======")
  console.log(to.matched);
  let user = JSON.parse(sessionStorage.getItem('user')||"{}")
  if (to.matched.some(record => record.meta.requiresManage)) {
    if (user.role !== 1) {  // 管理员没登录
      ElMessage({
        type: 'error',
        message: "此页面需要管理员权限！",
        duration: 2000
      })
      setTimeout(() => {
        next({
          path: '/login',
        })
      }, 2000);
    } else {
      next();
    }
  } else if (to.matched.some(record => record.meta.requiresDoctor)){
    if (user.role !== 2) {  // 医生没登录
      ElMessage({
        type: 'error',
        message: "此页面需要医生权限！",
        duration: 2000
      })
      setTimeout(() => {
        next({
          path: '/login',
        })
      }, 2000);
    } else {
      next();
    }
  }
  // else if (to.matched.some(record => record.meta.requiresUser)) {
  //   if (!token || manage == 'true') {  // 用户没登录
  //     ElMessage({
  //       type: 'waring',
  //       Message: "未登录,请先登录！",
  //       duration: 2000
  //     })
  //     setTimeout(() => {
  //       next({
  //         path: '/login',
  //       })
  //     }, 2000);
  //   } else {
  //     next();
  //   }
  // } 
  else {
    next(); //确保一定要调用 next()
  }
})
