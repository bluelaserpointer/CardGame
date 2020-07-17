const tokens = {
  admin: {
    token: 'admin-token'
  },
  editor: {
    token: 'editor-token'
  },
  admin1: {
    token: 'admin-token'
  }
};

const users = {
  'admin-token': {
    roles: ['admin'],
    introduction: 'I am a super administrator',
    avatar: 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif',
    name: 'Super Admin'
  },
  'editor-token': {
    roles: ['editor'],
    introduction: 'I am an editor',
    avatar: 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif',
    name: 'Normal Editor'
  }
};

module.exports = [
  // user login
  {
    url: '/vue-element-admin/user/login',
    type: 'post',
    response: config => {
      console.log("In mock login");
      const { username } = config.body;
      // localStorage.setItem('TestingStorage', 'testToken');
      // console.log("--------------------------");
      // console.log(localStorage.getItem('TestingStorage'));
      // let token = 'admin-token';
      // switch(role){
      //   case 1:
      //     token = 'admin-token';
      //     break;
      //   case 2:
      //     token = 'editor-token';
      //     break;
      //   default:
      //     token = 'admin-token';
      // }
      const token = tokens[username];

      // TODO: GET ROLE HERE
      // mock error
      if (!token) {
        return {
          code: 60204,
          message: 'Account and password are incorrect.'
        }
      }
      return {
        code: 20000,
        data: token
      }
    }
  },

  // get user info
  {
    url: '/vue-element-admin/user/info\.*',
    type: 'get',
    response: config => {
      console.log("In mock info");
      const { token } = config.query;
      const info = users[token];

      // TODO: GET INFO HERE

      console.log("GETTING USER INFO");

      // mock error
      if (!info) {
        return {
          code: 50008,
          message: 'Login failed, unable to get user details.'
        }
      }

      return {
        code: 20000,
        data: info
      }
    }
  },

  // user logout
  {
    url: '/vue-element-admin/user/logout',
    type: 'post',
    response: _ => {
      console.log("In mock logout");
      return {
        code: 20000,
        data: 'success'
      }
    }
  }
];
