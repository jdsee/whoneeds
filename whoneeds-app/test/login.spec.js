import { mount } from '@vue/test-utils'
import Login from '../pages/login.vue'

describe('Login', () => {
  // Inspect the raw component options
  it('has data', () => {
    expect(typeof Login.data).toBe('function')
  })
})

describe('Mounted Login page', () => {
  const wrapper = mount(Login, {})

  test('is instantiated', () => {
    expect(wrapper.vm).toBeTruthy()
  })

  it('has a heading', () => {
    expect(wrapper.html()).toContain('<h2>Sign In</h2>')
  })

  test('should output error message for non valid email', () => {
    wrapper.setData()
    // todo...
    expect(wrapper).toBeTruthy()
  })
})
