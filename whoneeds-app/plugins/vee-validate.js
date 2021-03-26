import { extend, setInteractionMode } from 'vee-validate'
import { required, digits, email, max, regex, min } from 'vee-validate/dist/rules'

setInteractionMode('eager')

extend('required', {
  ...required,
  message: '{_field_} can not be empty'
})

extend('max', {
  ...max,
  message: '{_field_} may not be greater than {length} characters'
})

extend('min', {
  ...min,
  message: '{_field_} may not be less than {length} characters'
})

extend('max-value', {
  ...max,
  message: '{_field_} may not be greater than {length} digits'
})

extend('email', {
  ...email,
  message: 'Email must be valid'
})

extend('confirmedBy', {
  params: ['target'],
  validate (value, { target }) {
    return value === target
  },
  message: '{_field_} must match this field.'
})

extend('digits', {
  ...digits,
  message: '{_field_} needs to be {length} digits. ({_value_})'
})

extend('regex', {
  ...regex,
  message: '{_field_} {_value_} does not match {regex}'
})
