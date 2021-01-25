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
  </v-container>
</template>
