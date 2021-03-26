<template>
  <v-layout row wrap text-xs-center fill-height align-center>
    <v-flex xs8 sm6 offset-sm3 offset-xs2>
      <h2>Sign Up</h2>
      <v-container fill-height>
        <validation-observer ref="observer" v-slot="{ invalid }">
          <v-form
            v-model="valid"
            lazy-validation
            @submit.prevent="submitRegistration"
          >
            <v-row>
              <v-col cols="12" sm="6" md="6">
                <validation-provider
                  v-slot="{ errors }"
                  name="First Name"
                  rules="required"
                >
                  <v-text-field
                    ref="firstNameInput"
                    v-model="registration.name"
                    label="First Name"
                    maxlength="20"
                    :error-messages="errors"
                    tabindex="1"
                  />
                </validation-provider>
              </v-col>
              <v-col cols="12" sm="6" md="6">
                <validation-provider
                  v-slot="{ errors }"
                  name="Last Name"
                  rules="required"
                >
                  <v-text-field
                    v-model="registration.surname"
                    label="Last Name"
                    maxlength="20"
                    :error-messages="errors"
                    tabindex="2"
                  />
                </validation-provider>
              </v-col>
              <v-col cols="12">
                <validation-provider
                  v-slot="{ errors }"
                  name="E-mail"
                  rules="required|email"
                >
                  <v-text-field
                    ref="emailInput"
                    v-model="registration.email"
                    label="E-mail"
                    type="email"
                    :error-messages="errors"
                    tabindex="3"
                  />
                </validation-provider>
              </v-col>
              <v-col cols="12">
                <validation-provider
                  v-slot="{ errors }"
                  name="Password"
                  vid="password"
                  :rules="`required|min:${minPasswordLength}`"
                >
                  <v-text-field
                    v-model="registration.password"
                    :append-icon="showPassword ? 'mdi-eye' : 'mdi-eye-off'"
                    :type="showPassword ? 'text' : 'password'"
                    name="Password"
                    label="Password"
                    :error-messages="errors"
                    counter
                    loading
                    tabindex="4"
                    @click:append="showPassword = !showPassword"
                    @blur="showPassword = false"
                  >
                    <template #progress>
                      <v-progress-linear
                        :value="pwProgress"
                        :color="pwProgressColor"
                        absolute
                        height="7"
                      />
                    </template>
                  </v-text-field>
                </validation-provider>
              </v-col>
              <v-col cols="12">
                <validation-provider
                  v-slot="{ errors }"
                  vid="confirm"
                  name="Password"
                  rules="confirmedBy:@password"
                >
                  <v-text-field
                    v-model="passwordConfirmation"
                    block
                    :append-icon="showPassword ? 'mdi-eye' : 'mdi-eye-off'"
                    :type="showPassword ? 'text' : 'password'"
                    :error-messages="errors"
                    name="Confirm"
                    label="Confirm Password"
                    counter
                    tabindex="5"
                    @click:append="showPassword = !showPassword"
                    @blur="showPassword = false"
                  />
                </validation-provider>
              </v-col>
              <v-spacer />
              <v-flex class="text-xs-center" mt-5 mb-13>
                <v-btn
                  data-test="cancelButton"
                  text
                  tabindex="7"
                  @click="resetForm"
                >
                  Cancel
                </v-btn>
                <v-btn
                  :disabled="invalid"
                  class="mr-4"
                  color="primary"
                  text
                  type="submit"
                  tabindex="6"
                >
                  Register
                </v-btn>
              </v-flex>
            </v-row>
          </v-form>
        </validation-observer>
      </v-container>
    </v-flex>
  </v-layout>
</template>

<script>
import { ValidationObserver, ValidationProvider } from 'vee-validate'

export default {
  components: {
    ValidationProvider,
    ValidationObserver
  },
  auth: false,
  data: () => ({
    dialog: true,
    valid: true,
    registration: {
      name: '',
      surname: '',
      email: '',
      password: ''
    },
    passwordConfirmation: '',
    minPasswordLength: 8,
    showPassword: false
  }),
  head: { title: 'Sign Up' },
  computed: {
    pwProgress () {
      return Math.min(100, this.registration.password.length * 5)
    },
    pwProgressColor () {
      return ['error', 'warning', 'success'][Math.floor(this.pwProgress / 40)]
    }
  },
  mounted () {
    this.$refs.firstNameInput.focus()
  },
  methods: {
    async submitRegistration () {
      if (
        this.$refs.observer.validate() &&
        this.registration.password === this.passwordConfirmation
      ) {
        await this.$axios
          .post('/users', this.registration)
          .then(() => {
            this.$auth.loginWith('local', {
              data: {
                email: this.registration.email,
                password: this.registration.password
              }
            })
          })
          .then(() => {
            this.$router.push('/users/me')
            this.$toast.success('Congratulations! You have been successfully registered.')
          })
          .catch((error) => {
            if (error.response && error.response.status === 409) {
              this.$toast.error('This E-mail address is already in use.')
            } else {
              this.$toast.error('Ops.. Something went wrong.')
            }
            this.focusEmailInput()
          })
      }
    },
    resetForm () {
      this.$refs.observer.reset()
      Object.keys(this.registration).map(k => (this.registration[k] = ''))
      this.passwordConfirmation = ''
    },
    focusEmailInput () {
      this.$refs.emailInput.focus()
    }
  }
}
</script>

<style scoped>
h2 {
  margin-top: 40px;
  margin-bottom: 10px;
}
</style>
