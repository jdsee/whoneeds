<template>
  <v-container>
    <validation-observer ref="observer" v-slot="{ invalid }">
      <v-form v-model="valid" lazy-validation @submit.prevent="submitReset">
        <v-row>
          <v-col cols="12">
            <v-text-field v-model="email" label="E-mail" required />
          </v-col>
          <v-col cols="12">
            <validation-provider
              v-slot="{ errors }"
              name="Password"
              vid="password"
              :rules="`required|min:${minPasswordLength}`"
            >
              <v-text-field
                v-model="password"
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
                v-model="confirmed"
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
          <v-col class="d-flex ml-auto" cols="12" sm="3" xsm="12">
            <v-btn
              :disabled="invalid"
              class="mr-4"
              text
              color="primary"
              type="submit"
            >
              Reset
            </v-btn>
          </v-col>
        </v-row>
      </v-form>
    </validation-observer>
  </v-container>
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
    email: '',
    password: '',
    confirmed: '',
    minPasswordLength: 8,
    showPassword: false
  }),
  head: { title: 'Reset Password' },
  computed: {
    pwProgress () {
      return Math.min(100, this.password.length * 5)
    },
    pwProgressColor () {
      return ['error', 'warning', 'success'][Math.floor(this.pwProgress / 40)]
    }
  },
  methods: {
    submitReset () {
      if (this.$refs.observer.validate()) {
        this.$axios.put('/users/changePassword', { email: this.email, password: this.password },
          { headers: { authorization: `Bearer ${this.$route.query.token}` } })
          .then(() => { this.$toast.success('Your password has been changed.') })
          .catch(() => { this.$toast.success('Error.') })
          .finally(() => {
            this.resetForm()
          })
      }
    },
    resetForm () {
      this.email = ''
      this.password = ''
      this.$refs.observer.reset()
    }
  }
}
</script>
