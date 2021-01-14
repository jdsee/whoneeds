import { extend, setInteractionMode } from 'vee-validate'
import { required, digits, email, max, regex } from 'vee-validate/dist/rules'

setInteractionMode('eager')

extend('required', {
  ...required,
  message: '{_field_} can not be empty'
})

extend('max', {
  ...max,
  message: '{_field_} may not be greater than {length} characters'
})

extend('email', {
  ...email,
  message: 'Email must be valid'
})

extend('digits', {
  ...digits,
  message: '{_field_} needs to be {length} digits. ({_value_})'
})

extend('regex', {
  ...regex,
  message: '{_field_} {_value_} does not match {regex}'
})
