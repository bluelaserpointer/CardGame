import request from '@/utils/request'

export function login(data) {
  console.log('In api login');
  console.log(data);

  const postData1 = new FormData();
  postData1.append('adminName', 'admin1');
  postData1.append('password', '111111');

  return (request({
    url: '/admin/identifyAdmin',
    method: 'post',
    data: postData1
  }));

  // console.log("--------");
  //
  // const postData2 = new FormData();
  // postData2.append('adminName', 'admin1');
  // postData2.append('password', '222222');
  //
  // console.log(request({
  //   url: '/admin/identifyAdmin',
  //   method: 'post',
  //   data: postData2
  // }).then( response => {
  //   console.log(response);
  // }).catch( error => {
  //   console.log(error);
  // }));
}

export function getInfo(token) {
  console.log('In api getInfo');
  console.log(token);

  return (request({
    url: '/admin/getAdminName',
    method: 'get',
  }));

  // console.log(request({
  //   url: '/vue-element-admin/user/info',
  //   method: 'get',
  //   params: { token }
  // }));
  // return request({
  //   url: '/vue-element-admin/user/info',
  //   method: 'get',
  //   params: { token }
  // })
}

export function logout() {
  console.log('In api logout');
  return request({
    url: '/vue-element-admin/user/logout',
    method: 'post'
  })
}
