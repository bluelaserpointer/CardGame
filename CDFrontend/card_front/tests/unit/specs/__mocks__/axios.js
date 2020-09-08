// const mockAxios = jest.genMockFromModule('axios');
//
// // this is the key to fix the axios.create() undefined error!
// mockAxios.create = jest.fn(  () => mockAxios);
//
// export default mockAxios

// const axios = {
//   get: jest.fn(() => Promise.resolve({ data: {} })),
//   post: jest.fn(() => Promise.resolve({ data: {} })),
//   create: () => axios,
//   defaults: {
//     adapter: {},
//   },
//   interceptors: {
//     request:{
//       use: jest.fn(() => Promise.resolve())
//     },
//     response:{
//       use: jest.fn(() => {})
//     }
//   }
// };
//
// export default axios;

const axios = {
  get: jest.fn(() => Promise.resolve({ data: {} })),
  create: () => axios,
  defaults: {
    adapter: {},
  },
};

export default axios;
