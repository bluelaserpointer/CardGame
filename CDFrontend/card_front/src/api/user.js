import request from '@/utils/request'

export function login(data) {
  console.log('In api login');
  console.log(data);

  // const postData = new FormData();
  // postData.append('userName', data.adminName);
  // postData.append('password', data.password);

  let postData = {
    userName: data.adminName,
    password: data.password
  };

  // return request({
  //   url: '/admin/identifyAdmin',
  //   method: 'post',
  //   data: postData
  // });

  return request({
    url: 'user/login',
    method: 'post',
    data: JSON.stringify(postData)
  });

}

export function getInfo(token) {
  console.log('In api getInfo');
  console.log(token);

  // return (request({
  //   url: '/admin/getAdminName',
  //   method: 'get',
  // }));

  const postData = new FormData();
  postData.append('userName', localStorage.getItem('AdminName'));
  // postData.append('password', data.password);

  return request({
    url: 'user/getUserByUserName',
    method: 'post',
    data: postData
  });
}

export function logout() {
  console.log('In api logout');

  return Promise.resolve();

  // return (request({
  //   url: '/admin/getAdminName',
  //   method: 'get',
  // }));

  // return request({
  //   url: '/vue-element-admin/user/logout',
  //   method: 'post'
  // })
}
