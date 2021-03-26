<template>
  <v-container fill-height>
    <v-row justify="center" align="center">
      <div class="content">
        <h1>My Profile</h1>
        <hr>
        <p>
          <strong>Name:</strong>
          {{ `${user.name} ${user.surname}` }}
        </p>
        <p>
          <strong>E-Mail:</strong>
          {{ user.email }}
        </p>
      </div>
    </v-row>
    <v-row>
      <v-col cols="12">
        <v-btn color="primary" to="/projects/new">
          <v-icon left>
            mdi-plus
          </v-icon>
          Create new project
        </v-btn>
      </v-col>
      <v-col cols="12">
        <v-btn to="/changePassword" class="ma-2" text color="secondary">
          <v-icon left color="primary">
            mdi-refresh
          </v-icon>
          Change Password
        </v-btn>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
export default {
  data: () => ({
    user: {
      name: '',
      surname: '',
      email: ''
    }
  }),
  created () {
    const authenticated = this.$auth.user
    this.user.name = authenticated.name
    this.user.surname = authenticated.surname
    this.user.email = authenticated.email
  }
}
</script>

<style scoped>
v-btn {
  margin-top: 5px;
  margin-bottom: 5px;
}
</style>
