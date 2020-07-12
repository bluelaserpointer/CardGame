import request from '@/utils/request'

export function login(data) {
  console.log("In api login");
  console.log(data);

  console.log( request({
    url: '/vue-element-admin/user/login',
    method: 'post',
    data
  }));
  return( request({
    url: '/vue-element-admin/user/login',
    method: 'post',
    data
  }));
}

export function getInfo(token) {
  console.log("In api getInfo");
  console.log(token);
  return request({
    url: '/vue-element-admin/user/info',
    method: 'get',
    params: { token }
  })
}

export function logout() {
  console.log("In api logout");
  return request({
    url: '/vue-element-admin/user/logout',
    method: 'post'
  })
}
