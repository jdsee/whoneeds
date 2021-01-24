import { render, fireEvent } from '@testing-library/vue'
import { extend } from 'vee-validate'
import { required, email } from 'vee-validate/dist/rules.umd.js'

import Login from '@/pages/login.vue'

extend('required', required)
extend('email', email)

describe('Login form validation', () => {
  it('should reset the form when clear is clicked', async () => {
    const { getByRole } = render(Login)

    const emailInput = getByRole('textbox', /e-mail/i)
    const passwordInput = getByRole('textbox', /password/i)
    const clearButton = getByRole('button', { name: /clear/i })
    await fireEvent.update(emailInput, 'user@mail.com')
    await fireEvent.update(passwordInput, 'user_password')

    await fireEvent.click(clearButton)

    expect(getByRole('textbox', /e-mail/i)).toBeEmptyDOMElement()
    expect(getByRole('textbox', /password/i)).toBeEmptyDOMElement()
  })
})
