<template>
  <div class="test">
    <h2>my params</h2>
    <p>das ist mein param{{ $route.query.token}}</p>
  </div>
</template>

<script>
export default {
  auth: false
}
</script>
