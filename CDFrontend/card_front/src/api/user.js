import request from '@/utils/request'

export function login(data) {
  console.log('In api login');
  console.log(data);

  const postData = new FormData();
  console.log(data);
  postData.append('adminName', 'admin1');
  postData.append('password', '111111');

  return (request({
    url: '/admin/identifyAdmin',
    method: 'post',
    postData
  }))
}

export function getInfo(token) {
  console.log('In api getInfo');
  console.log(token);
  console.log(request({
    url: '/vue-element-admin/user/info',
    method: 'get',
    params: { token }
  }));
  return request({
    url: '/vue-element-admin/user/info',
    method: 'get',
    params: { token }
  })
}

export function logout() {
  console.log('In api logout');
  return request({
    url: '/vue-element-admin/user/logout',
    method: 'post'
  })
}
