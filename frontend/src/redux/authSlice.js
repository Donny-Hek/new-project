import { createSlice } from '@reduxjs/toolkit'

const token = localStorage.getItem('token')

const authSlice = createSlice({
  name: 'auth',
  initialState: {
    token,
    isAuthenticated: !!token
  },
  reducers: {
    login(state, action) {
      state.token = action.payload
      state.isAuthenticated = true
      localStorage.setItem('token', action.payload)
    },
    logout(state) {
      state.token = null
      state.isAuthenticated = false
      localStorage.removeItem('token')
    }
  }
})

export const { login, logout } = authSlice.actions
export default authSlice.reducer
