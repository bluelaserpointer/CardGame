import Cookies from 'js-cookie'

const TokenKey = 'Admin-Token';

export function getToken() {
  return Cookies.get(TokenKey)
}

export function setToken(token) {
  return Cookies.set(TokenKey, token)
}

export function removeToken() {
  console.log(Cookies.get(TokenKey));
  Cookies.remove(TokenKey);
  console.log(Cookies.get(TokenKey));

}
