import '@testing-library/jest-dom'
import Vue from 'vue'
import Vuetify from 'vuetify'
import { extend } from 'vee-validate'
import { required, email, max, min } from 'vee-validate/dist/rules.umd.js'

extend('required', required)
extend('email', email)
extend('max', max)
extend('min', min)
extend('confirmedBy', {
  params: ['target'],
  validate (value, { target }) {
    return value === target
  },
  message: '{_field_} does not match'
})

Vue.use(Vuetify)
