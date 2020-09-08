import request from '@/utils/request'

export function login(data) {
  console.log('In api login')
  console.log(data)

  const postData = {
    userName: data.adminName,
    password: data.password
  }

  return request.post('user/login', JSON.stringify(postData))
}

export function getInfo(token) {
  console.log('In api getInfo')
  console.log(token)

  const postData = new FormData()
  postData.append('userName', localStorage.getItem('AdminName'))

  return request.post('user/getUserByUserName', postData)
}

export function logout() {
  console.log('In api logout')
  const postData = new FormData()
  postData.append('userId', localStorage.getItem('UserId'))
  postData.append('type', 1)

  return request.post('user/logout', postData)
}
