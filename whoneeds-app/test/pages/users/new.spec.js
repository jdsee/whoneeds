import { mount } from '@vue/test-utils'
import Vuetify from 'vuetify'
import { ValidationObserver, ValidationProvider } from 'vee-validate'
import New from '@/pages/users/new.vue'

describe('User registration form', () => {
  const mockPost = jest.fn()
  mockPost.mockReturnValue(new Promise(() => { }))
  const mockAxios = {
    post: mockPost
  }
  const mockLoginWith = jest.fn()
  mockLoginWith.mockReturnValue(new Promise(() => { }))
  const mockAuth = {
    loginWith: mockLoginWith,
    loggedIn: false
  }
  const wrapper = mount(New, {
    components: { ValidationObserver, ValidationProvider },
    mocks: { $auth: mockAuth, $axios: mockAxios },
    vuetify: new Vuetify()
  })

  it('should be cleared when cancel is clicked', async () => {
    const cancelButton = wrapper.find('button[data-test="cancelButton"')
    const initialFields = wrapper.findAll('input')
    for (let i = 0; i < initialFields.length; i++) {
      await initialFields.at(i).setValue('test')
    }

    await cancelButton.trigger('click')

    const actualFields = wrapper.findAll('input')
    for (let i = 0; i < actualFields.length; i++) {
      expect(actualFields.at(i).element.value).toEqual('')
    }
  })

  it('should register user with valid input', async () => {
    const emailInput = wrapper.find('input[type="email"]')
    const passwordInputFields = wrapper.findAll('input[type="password"]')
    const textFields = wrapper.findAll('input[type=text]')
    emailInput.setValue('valid@mail.com')
    passwordInputFields.at(0).setValue('valid_password')
    passwordInputFields.at(1).setValue('valid_password')
    textFields.at(0).setValue('name')
    textFields.at(1).setValue('surname')

    await wrapper.find('form').trigger('submit')

    expect(mockAxios.post).toHaveBeenCalledWith('/users', {
      email: 'valid@mail.com',
      password: 'valid_password',
      name: 'name',
      surname: 'surname'
    })
  })
})
