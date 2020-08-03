import { login, logout, getInfo } from '@/api/user'
import { getToken, setToken, removeToken } from '@/utils/auth'
import router, { resetRouter } from '@/router'

const state = {
  token: getToken(),
  name: '',
  avatar: '',
  introduction: '',
  roles: []
};

const mutations = {
  SET_TOKEN: (state, token) => {
    state.token = token
  },
  SET_INTRODUCTION: (state, introduction) => {
    state.introduction = introduction
  },
  SET_NAME: (state, name) => {
    state.name = name
  },
  SET_AVATAR: (state, avatar) => {
    state.avatar = avatar
  },
  SET_ROLES: (state, roles) => {
    state.roles = roles
  }
};

const actions = {
  // user login
  login({ commit }, userInfo) {
    const { username, password } = userInfo;
    return new Promise((resolve, reject) => {
      console.log("Within login inside modules/usr.js");
      console.log(commit);
      console.log(userInfo);
      login({ adminName: username.trim(), password: password }).then(response => {
        const { data } = response;
        console.log(data);
        let token = "Bearer " + data.token;
        commit('SET_TOKEN', token);
        setToken(token);
        resolve()
      }).catch(error => {
        reject(error)
      })
    })
  },

  // get user info
  getInfo({ commit, state }) {
    console.log("Within getInfo inside modules/usr.js");
    console.log(commit);
    console.log(state);
    return new Promise((resolve, reject) => {
      getInfo(state.token).then(response => {
        console.log("Within promise");
        console.log(response);


        response.data.roles = ['admin', 'editor'];
        response.data.name = 'admin1';

        const { data } = response;

        if (!data) {
          console.log("In !data");
          reject('Verification failed, please Login again.')
        }

        console.log(data);

        const { roles, name, avatar, introduction } = data;



        console.log("roles " + roles);
        console.log("name " + name);

        // roles must be a non-empty array
        if (!roles || roles.length <= 0) {
          reject('getInfo: roles must be a non-null array!')
        }

        commit('SET_ROLES', roles);
        // commit('SET_ROLES', roles);
        commit('SET_NAME', "");
        commit('SET_AVATAR', "");
        commit('SET_INTRODUCTION', "");
        resolve(data);
      }).catch(error => {
        reject(error)
      })
    })
  },

  // user logout
  logout({ commit, state, dispatch }) {
    return new Promise((resolve, reject) => {
      logout(state.token).then(() => {
        console.log("In logout-then");
        commit('SET_TOKEN', '');
        commit('SET_ROLES', []);
        removeToken();
        resetRouter();
        // reset visited views and cached views
        // to fixed https://github.com/PanJiaChen/vue-element-admin/issues/2485
        dispatch('tagsView/delAllViews', null, { root: true });

        resolve()
      }).catch(error => {
        reject(error)
      })
    })
  },

  // remove token
  resetToken({ commit }) {
    return new Promise(resolve => {
      commit('SET_TOKEN', '');
      commit('SET_ROLES', []);
      removeToken();
      resolve()
    })
  },

  // dynamically modify permissions
  async changeRoles({ commit, dispatch }, role) {
    const token = role + '-token';

    commit('SET_TOKEN', token);
    setToken(token);

    const { roles } = await dispatch('getInfo');

    resetRouter();

    // generate accessible routes map based on roles
    const accessRoutes = await dispatch('permission/generateRoutes', roles, { root: true });
    // dynamically add accessible routes
    router.addRoutes(accessRoutes);

    // reset visited views and cached views
    dispatch('tagsView/delAllViews', null, { root: true })
  }
};

export default {
  namespaced: true,
  state,
  mutations,
  actions
}
