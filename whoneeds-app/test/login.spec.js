import { mount } from '@vue/test-utils'
import Login from '../pages/login.vue'

test('should render login form', () => {
  const wrapper = mount(Login, {})

  expect(wrapper.text()).toContain('Sign In')
})
