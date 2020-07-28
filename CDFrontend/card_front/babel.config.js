module.exports = {
  presets: [
    '@vue/cli-plugin-babel/preset',
  ],
  plugins: [
    ['babel-plugin-istanbul', {
      extension: ['.js', '.vue']
    }]
  ]
};

// const isTest = String(process.env.NODE_ENV) === 'test';
// const presetEnv = isTest
//   ? ['@babel/preset-env', { modules: 'commonjs', targets: { node: 'current' } }]
//   : ['@babel/preset-env', { modules: false }];
//
// module.exports = {
//   presets: [presetEnv, '@babel/preset-typescript'],
//   plugins: [
//     '@babel/plugin-transform-runtime',
//     '@babel/plugin-syntax-dynamic-import',
//   ],
// };
