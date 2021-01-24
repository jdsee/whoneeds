module.exports = {
  moduleNameMapper: {
    '^@/(.*)$': '<rootDir>/$1',
    '^~/(.*)$': '<rootDir>/$1',
    '^vue$': 'vue/dist/vue.common.js'
  },
  moduleFileExtensions: ['js', 'jsx', 'json', 'vue'],
  transform: {
    '^.+\\.vue$': 'vue-jest',
    '^.+\\.(js|jsx)?$': 'babel-jest',
    'vee-validate/dist/rules': 'babel-jest',
    'vuetify/dist': 'babel-jest'
  },
  transformIgnorePatterns: ['/node_modules/(?!vee-validate/dist/rules)'],
  timers: 'fake',
  setupFilesAfterEnv: ['./test/jest.setup.js']
}
