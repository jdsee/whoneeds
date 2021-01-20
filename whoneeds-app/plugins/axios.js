/* eslint-disable no-console */
export default function ({ $auth, $toast }) {
  $auth.onError((error, name, endpoint) => {
    console.error(name, error)
    $toast.clear()
    if (error.response && error.response.status === 401) {
      $toast.error('Login credentials invalid!')
    } else {
      $toast.error('Oops.. Something went wrong. Please try again later')
    }
  })
}
