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
                  label="E-mail"
                  required
                />
              </validation-provider>
            </v-flex>
            <v-flex>
              <validation-provider
                v-slot="{ errors }"
                name="Password"
                :rules="`required|max:${maxPasswordLength}`"
              >
                <v-text-field
                  v-model="login.password"
                  type="password"
                  :counter="maxPasswordLength"
                  :error-messages="errors"
                  label="Password"
                  required
                />
              </validation-provider>
            </v-flex>
            <v-flex class="text-xs-center" mt-5>
              <v-btn class="mr-4" type="submit" :disabled="invalid">
                submit
              </v-btn>
              <v-btn @click="clear">
                clear
              </v-btn>
              <nuxt-link class="ma-4" :to="{name:'forgotPassword', params:{ mail:login.mail }}">
                Forgot password?
              </nuxt-link>
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
    maxPasswordLength: 32
  }),
  mounted () {
    this.focusEmailInput()
  },
  methods: {
    submit () {
      this.$refs.observer.validate()
      this.$auth
        .loginWith('local', { data: this.login })
        .then(() => this.$toast.success('Logged In!'))
        .finally(() => {
          this.focusEmailInput()
          this.clear()
        })
    },
    clear () {
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
