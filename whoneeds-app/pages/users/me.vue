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
      <v-btn
        to="/changePassword"
        class="ma-2"
        outlined
        text
        color="indigo"
        type="submit"
      >
        Change Password
      </v-btn>
    </v-row>
    <v-row>
      <v-btn color="primary" to="/projects/new">
        Create new project
      </v-btn>
    </v-row>
  </v-container>
</template>
