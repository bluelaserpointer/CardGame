import Cookies from 'js-cookie'

const TokenKey = 'Admin-Token';

export function getToken() {
  console.log("Getting Token");
  console.log(Cookies.get(TokenKey));
  return Cookies.get(TokenKey)
}

export function setToken(token) {
  console.log("Setting Token");
  return Cookies.set(TokenKey, token)
}

export function removeToken() {
  return Cookies.remove(TokenKey)
}
