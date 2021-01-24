<template>
  <v-layout row wrap text-xs-center fill-height align-center>
    <v-flex xs8 sm6 offset-sm3 offset-xs2>
      <h2>Sign Up</h2>
      <v-container>
        <validation-observer ref="observer" v-slot="{ invalid }">
          <v-form
            v-model="valid"
            lazy-validation
            @submit.prevent="submitRegistration"
          >
            <v-row>
              <v-col cols="12" sm="6" md="6">
                <v-text-field
                  v-model="registration.firstName"
                  label="First Name"
                  maxlength="20"
                  required
                />
              </v-col>
              <v-col cols="12" sm="6" md="6">
                <v-text-field
                  v-model="registration.lastName"
                  label="Last Name"
                  maxlength="20"
                  required
                />
              </v-col>
              <v-col cols="12">
                <v-text-field
                  v-model="registration.email"
                  label="E-mail"
                  required
                />
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
                    @click:append="showPassword = !showPassword"
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
                  name="Passwords"
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
                    @click:append="showPassword = !showPassword"
                  />
                </validation-provider>
              </v-col>
              <v-spacer />
              <v-flex class="text-xs-center" mt-5>
                <v-btn text @click="resetForm">
                  Cancel
                </v-btn>
                <v-btn :disabled="invalid" class="mr-4" text type="submit">
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
      firstName: '',
      lastName: '',
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
  methods: {
    submitRegistration () {
      if (this.$ref.obser.validate()) {
        console.log('Reqeust will be send to backend here...')
      }
    },
    resetForm () {
      console.log('reset form')
      this.$refs.observer.reset()
      Object.keys(this.registration).map((k) => (this.registration[k] = ''))
    }
  }
}
</script>
