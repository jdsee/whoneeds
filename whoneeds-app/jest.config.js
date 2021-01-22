module.exports = {
  preset: '@nuxt/test-utils',
  moduleFileExtensions: [
    'js',
    'json',
    'vue'
  ],
  transform: {
    '.*\\.(vue)$': 'vue-jest',
    '.*\\.(js)$': 'babel-jest'
  },
  transformIgnorePatterns: ['/node_modules/(?!vee-validate)'],
  moduleNameMapper: {
    '^@/(.*)$': '<rootDir>/src/$1'
  },
  setupFilesAfterEnv: ['./test/jest.setup.js']
}
