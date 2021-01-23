<template>
  <v-container fill-height>
    <v-row justify="center" align="center">
      <div class="content">
        <h1>My Profile</h1>
        <hr>
        <p>
          <strong>Username:</strong>
          Daniel Duesentrieb
          <!-- {{ $auth.user.username }} TODO: send username on api request -->
        </p>
        <p>
          <strong>Email:</strong>
          {{ $auth.user.email }}
        </p>
      </div>
    </v-row>
  </v-container>
</template>
