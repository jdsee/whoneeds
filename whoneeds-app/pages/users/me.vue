<template>
  <v-container fill-height>
    <v-row justify="center" align="center">
      <div class="content">
        <h1>My Profile</h1>
        <hr>
        <p>
          <strong>Name:</strong>
          {{ `${$auth.user.name} ${$auth.user.surname}` }}
        </p>
        <p>
          <strong>E-Mail:</strong>
          {{ $auth.user.email }}
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
        <v-btn
          to="/changePassword"
          class="ma-2"
          text
          color="secondary"
        >
          <v-icon left color="primary">
            mdi-refresh
          </v-icon>
          Change Password
        </v-btn>
      </v-col>
    </v-row>
  </v-container>
</template>

<style scoped>
v-btn {
  margin-top: 5px;
  margin-bottom: 5px;
}
</style>
