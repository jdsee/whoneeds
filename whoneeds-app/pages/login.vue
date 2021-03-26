<template>
  <v-layout row wrap text-xs-center fill-height align-center>
    <v-flex xs8 sm6 offset-sm3 offset-xs2>
      <h2>Sign In</h2>

      <validation-observer ref="observer" v-slot="{ invalid }">
        <v-form @submit.prevent="submit">
          <v-layout column>
            <v-flex>
              <validation-provider
                v-slot="{ errors }"
                name="E-Mail address"
                rules="required|email"
              >
                <v-text-field
                  ref="emailInput"
                  v-model="login.email"
                  :error-messages="errors"
                  type="email"
                  label="E-Mail"
                  required
                  tabindex="1"
                />
              </validation-provider>
            </v-flex>
            <v-flex>
              <validation-provider
                v-slot="{ errors }"
                name="Password"
                :rules="`required|min:${minPasswordLength}`"
              >
                <v-text-field
                  v-model="login.password"
                  :type="showPassword ? 'text' : 'password'"
                  :append-icon="showPassword ? 'mdi-eye' : 'mdi-eye-off'"
                  counter
                  :error-messages="errors"
                  label="Password"
                  tabindex="2"
                  @click:append="showPassword = !showPassword"
                  @blur="showPassword = false"
                />
              </validation-provider>
            </v-flex>
            <v-flex class="text-xs-center" mt-5>
              <v-btn text tabindex="4" @click="resetForm">
                Cancel
              </v-btn>
              <v-btn
                :disabled="invalid"
                class="mr-4"
                text
                color="primary"
                type="submit"
                tabindex="3"
              >
                Submit
              </v-btn>
            </v-flex>
          </v-layout>
        </v-form>
      </validation-observer>
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
  data: () => ({
    login: {
      email: '',
      password: ''
    },
    valid: true,
    showPassword: false,
    minPasswordLength: 8
  }),
  head: {
    title: 'Sign In'
  },
  mounted () {
    this.focusEmailInput()
  },
  methods: {
    submit () {
      if (this.$refs.observer.validate()) {
        this.$auth
          .loginWith('local', { data: this.login })
          .then(() => this.$toast.success('Logged In!'))
          .catch(() => this.$toast.error('Login credentials invalid'))
          .finally(() => {
            this.focusEmailInput()
            this.resetForm()
          })
      }
    },
    resetForm () {
      this.login.email = ''
      this.login.password = ''
      this.$refs.observer.reset()
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
  margin-bottom: 20px;
}
</style>
