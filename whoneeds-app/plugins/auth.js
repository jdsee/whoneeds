/* eslint-disable no-console */
export default function ({ $auth, $toast }) {
  $auth.onError((error, name, endpoint) => {
    console.error(name, error)
    $toast.clear()
    if (error.response &&
      (error.response.status === 401 ||
        error.response.status === 403)) {
      $toast.error('You are unauthorized!')
    } else {
      $toast.error('Oops.. Something went wrong. Please try again later')
    }
  })
}
