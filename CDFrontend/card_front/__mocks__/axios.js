// const mockAxios  = jest.genMockFromModule('axios');
//
// // this is the key to fix the axios.create() undefined error!
// // @ts-ignore
// mockAxios.create = jest.fn(() => mockAxios);
// // mockAxios.get = jest.fn(() => Promise.resolve({ data: {} }));
// // mockAxios.post = jest.fn(() => Promise.resolve({ data: {} }));
// // mockAxios.put = jest.fn(() => Promise.resolve({ data: {} }));
// // mockAxios.delete = jest.fn(() => Promise.resolve({ data: {} }));
// // mockAxios.all = jest.fn(() => Promise.resolve());
//
// export default mockAxios;


const axios = {
  get: jest.fn(() => Promise.resolve({ data: {} })),
  post: jest.fn(() => Promise.resolve({ data: {} })),
  create: () => axios,
  defaults: {
    adapter: {},
  },
  interceptors: {
    request:{
      use: jest.fn(() => Promise.resolve())
    },
    response:{
      use: jest.fn(() => {})
    }
  }
};

export default axios;
